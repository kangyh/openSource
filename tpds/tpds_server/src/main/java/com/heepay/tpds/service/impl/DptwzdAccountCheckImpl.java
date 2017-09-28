package com.heepay.tpds.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.date.DateUtils;
import com.heepay.file.FileUtils;
import com.heepay.rpc.billing.model.SettleMerchantDetail;
import com.heepay.rpc.billing.model.TpdsSettleMerchantModel;
import com.heepay.rpc.billing.model.querySettleMerchantModel;
import com.heepay.tpds.client.ClearSettleAPIClient;
import com.heepay.tpds.client.TpdsCommonClient;
import com.heepay.tpds.dao.TpdsBankWithdrawMapper;
import com.heepay.tpds.dao.TpdsCustomChargeMapper;
import com.heepay.tpds.dao.TpdsCutDayMapper;
import com.heepay.tpds.dao.TpdsFileLogMapper;
import com.heepay.tpds.dao.TpdsSettleItemMapper;
import com.heepay.tpds.dao.TpdsSettleRecordMapper;
import com.heepay.tpds.dao.impl.TpdsFileLogDaoImpl;
import com.heepay.tpds.entity.TpdsBankWithdraw;
import com.heepay.tpds.entity.TpdsCustomCharge;
import com.heepay.tpds.entity.TpdsCutDay;
import com.heepay.tpds.entity.TpdsFileLog;
import com.heepay.tpds.entity.TpdsSettleItem;
import com.heepay.tpds.entity.TpdsSettleRecord;
import com.heepay.tpds.enums.Customerstatus;
import com.heepay.tpds.util.IpUtil;
import com.heepay.tpds.util.TpdsDataUtils;
import com.heepay.tpds.vo.CommonRequestHeaderMessage;
import com.heepay.tpds.vo.RequestMessage;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.utils.fileutil.FileRSA;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.SftpCilent;
import com.heepay.utils.security.SignRSA;
import com.jcraft.jsch.ChannelSftp;

/**
 * *
 * 
 * 
 * 描 述：存管提现提供对账文件
 *
 * 创 建 者： wangjie 创建时间： 2017年4月18日下午5:30:01 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class DptwzdAccountCheckImpl {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearSettleAPIClient clearSettleAPIClient;

	@Autowired
	private TpdsBankWithdrawMapper tpdsBankWithdrawMapperDao;

	@Autowired
	private TpdsSettleRecordMapper tpdsSettleRecordMapperDao;
	@Autowired
	private TpdsSettleItemMapper tpdsSettleItemMapperDao;
	@Autowired
	private TpdsCutDayMapper tpdsCutDayMapperDao;

	@Autowired
	private TpdsCommonClient tpdsCommonClient;
	
	@Autowired
	private TpdsFileLogMapper TpdsFileLogDaoImpl;

	@Resource(name = "httpsClient")
	WithoutAuthHttpsClient httpsClient;
	
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;

	public String dptwzdAccountCheck(String merchantId, String systemNo, String settleBath, String settleTime,
			String transType, String settleStatus) {

		try {
			// 1.根据日切点调用清算结算系统查询账单
			querySettleMerchantModel model = new querySettleMerchantModel();
			model.setMerchantId(merchantId);
			model.setSettleBath(settleBath);
			model.setSettleTime(settleTime);
			model.setTransType(transType);
			model.setSettleStatus(settleStatus);

			List<TpdsSettleMerchantModel> tpdsSettleMerchantList = clearSettleAPIClient
					.getTpdsSettleMerchantModel(model);
			if (tpdsSettleMerchantList != null && tpdsSettleMerchantList.size() != 0) {
				for (TpdsSettleMerchantModel tpdsSettleMerchantDetail : tpdsSettleMerchantList) {
					// 结算数据入库
					TpdsSettleRecord tpdsSettleRecord = this.getTpdsSettleRecord(tpdsSettleMerchantDetail);
					TpdsSettleRecord tpdsSettleRecord1 = tpdsSettleRecordMapperDao
							.queryBySettleBath(tpdsSettleRecord.getSettleBath());
					if (tpdsSettleRecord1 != null) {
						logger.info("结算单数据已存在，结算单号为:{}", tpdsSettleRecord.getSettleBath());
						continue;
					}
					tpdsSettleRecordMapperDao.insert(tpdsSettleRecord);
					logger.info("结算单数据入库成功，结算单号为:{}", tpdsSettleRecord.getSettleBath());

					List<SettleMerchantDetail> detailList = tpdsSettleMerchantDetail.getDetail();
					for (SettleMerchantDetail settleMerchantDetail : detailList) {
						// 清算明细入库
						TpdsSettleItem tpdsSettleItem = this.getTpdsSettleItem(settleMerchantDetail);
						tpdsSettleItemMapperDao.insert(tpdsSettleItem);
					}
				}

				// 获取商户对应的日切点
				TpdsCutDay tpdsCutDay = tpdsCutDayMapperDao.getCutTimeByBusiNo(merchantId,
						Customerstatus.ENABLE.getValue());
				if (tpdsCutDay == null) {
					logger.error("获取不到商户对应的日切点设置,商户号为:{}", merchantId);
					return null;
				}
				String d = tpdsCutDay.getCutTime();
				String dateType = "yyyy-MM-dd " + d;
				String format = new SimpleDateFormat(dateType).format(DateUtils.getYesterdayDate());
				Date successTime = null;
				try {
					successTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
				} catch (ParseException e) {
					logger.error("格式化日切点时间失败", e);
					return null;
				}

				// 汇总在日切点之前的数据
				TpdsSettleItem tpdsSettleItemVo = new TpdsSettleItem();
				tpdsSettleItemVo.setSuccessTime(successTime);
				tpdsSettleItemVo.setMerchantNo(merchantId);
				tpdsSettleItemVo.setTransType(transType);
				tpdsSettleItemVo.setSettleStatus("N");
				List<TpdsSettleItem> list = tpdsSettleItemMapperDao.getTpdsSettleItemBath(tpdsSettleItemVo);
				if (list == null || list.size() == 0) {
					logger.info("没有符合的对账数据，商户id和交易类型为{},{}", merchantId, transType);
					return null;
				}

				// 2.生成账单
				DecimalFormat myformat = new DecimalFormat("0.00");
				BigDecimal totalAmount = new BigDecimal(0);
				for (TpdsSettleItem tpdsSettleItem : list) {
					totalAmount = totalAmount.add(tpdsSettleItem.getRequestAmount());
				}

				String checkDate = DateUtil.getTodayYYYYMMDDHHmmss();
				String filePath = DateUtil.getCurrentDate(new Date());

				String fileName = "";
				String fileNameOk = "";
				String prarm = "000";
				Integer fileLog = TpdsFileLogDaoImpl.selectMaxId();
				if (fileLog != null) {
					if (fileLog < 10) {
						prarm = "00" + fileLog;
					} else if (fileLog < 100) {
						prarm = "0" + fileLog;
					} else {
						prarm = fileLog.toString();
					}
				}
				
				fileName = tpdsCommonClient.getBusiBranchNo() + "_" + systemNo + "_OUTPAYTRADECHECK_"
						+ filePath + "_" + prarm + ".txt";
				fileNameOk = tpdsCommonClient.getBusiBranchNo() + "_" + systemNo + "_OUTPAYTRADECHECK_"
						+ filePath + "_" + prarm + ".OK";
				

				File directory = new File(tpdsCommonClient.getBankUploadFilePath() + filePath + "/");
				File directoryMi = new File(
						tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + checkDate + "/"); // 加密
				// 如果文件夹不存在则创建
				if (!directory.exists() && !directory.isDirectory()) {
					CreateDir.createLiunxDir(tpdsCommonClient.getBankUploadFilePath() + filePath + "/");
				}
				if (!directoryMi.exists() && !directoryMi.isDirectory()) {
					CreateDir.createLiunxDir(
							tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + checkDate + "/");
				}
				FileUtils.writeToFile(tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + fileName,
						"总笔数:" + list.size() + "|总金额:" + myformat.format(totalAmount) + "\r\n", true);
				// ok文件
				FileUtils.writeToFile(tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + fileNameOk, "", true);
				// "对账日期|充值台帐帐号|订单流水号|交易日期|交易金额|手续费金额|业务流水号|支付公司代码|"+"\r\n",
				// true);
				for (TpdsSettleItem tpdsSettleItem : list) {
					TpdsBankWithdraw tpdsBankWithdraw = tpdsBankWithdrawMapperDao
							.selectByBusinessOrderNo(tpdsSettleItem.getTransNo());
					if (tpdsBankWithdraw != null) {
						FileUtils.writeToFile(tpdsCommonClient.getP2pUploadFilePath() + fileName,
								checkDate + "^|" + tpdsBankWithdraw.getBankAccountNo() + "^|"
										+ tpdsBankWithdraw.getBusinessOrderNo() + "^|"
										+ DateUtils.getDateStr(tpdsSettleItem.getSuccessTime(), "yyyyMMdd") + "^|"
										+ tpdsBankWithdraw.getAmount() + "^|" + tpdsBankWithdraw.getBusinessSeqNo()
										+ "^|" + tpdsBankWithdraw.getSecBankaccNo() + "\r\n",
								true);
					}

					// 将对应的明细记录结算状态修改为已结算
					tpdsSettleItemMapperDao.updateByTransNo(tpdsSettleItem.getTransNo());
				}
				
				// 发送通知
				// 上传到文件服务器(p2p)
				// 上传到文件服务器(银行)
				SftpCilent sftpClient = new SftpCilent();
				ChannelSftp sftp = sftpClient.connect(tpdsCommonClient.getBankHostIp(), 22,
						tpdsCommonClient.getBankUserName(), tpdsCommonClient.getBankPassword(),
						tpdsCommonClient.getBankUploadFilePath() + "yb_files_down");
				SftpCilent sftpClient1 = new SftpCilent();
				ChannelSftp sftp1 = sftpClient1.connect(tpdsCommonClient.getBankHostIp(), 22,
						tpdsCommonClient.getBankUserName(), tpdsCommonClient.getBankPassword(),
						tpdsCommonClient.getBankUploadFilePath() + "yb_files_down");

				// 问价加密
				boolean ok = FileRSA.fileEncrypt(tpdsCommonClient.getBankUploadFilePath() + filePath + "/",
						tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + checkDate + "/", fileName,
						tpdsCommonClient.getBASE64PUBLICKEY());

				logger.info("文件加密返回{}", ok);
				if (true == ok) {
					boolean flag = sftpClient.upload(tpdsCommonClient.getBankUploadFilePath() + "yb_files_down",
							tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + checkDate + "/" + fileName,
							sftp);
					if (true == ok) { // 入库
						TpdsFileLog log = new TpdsFileLog();
						log.setCheckFileFrom(IpUtil.getHostIp());
						log.setCheckFileWhere(tpdsCommonClient.getBankHostIp());
						log.setFileDir(tpdsCommonClient.getBankUploadFilePath() + "yb_files_down/");
						log.setFileName(fileName);
						log.setMerchantNo(merchantId);
						log.setOperTime(new Date());
						TpdsFileLogDaoImpl.insert(log);
					}
					boolean flagOk = sftpClient.upload(tpdsCommonClient.getBankUploadFilePath() + "yb_files_down",
							tpdsCommonClient.getBankUploadFilePath() + filePath + "/" + fileNameOk, sftp1);
					if (true == flagOk) { // 入库
						TpdsFileLog log = new TpdsFileLog();
						log.setCheckFileFrom(IpUtil.getHostIp());
						log.setCheckFileWhere(tpdsCommonClient.getBankHostIp());
						log.setFileDir(tpdsCommonClient.getBankUploadFilePath() + "yb_files_down/");
						log.setFileName(fileNameOk);
						log.setMerchantNo(merchantId);
						log.setOperTime(new Date());
						TpdsFileLogDaoImpl.insert(log);
					}
					if (true == flag && true == flagOk) {
						// 发送通知
						String result = this.sendHttps(serverBankInfo.getBankServerAddr(), systemNo, fileName);
						logger.info("上传对账文件通知接口返回{}", result);
					}
				}

			}

		} catch (Exception e) {
			logger.error("提供对账文件出错{}", e);
		}
		return null;
	}

	/**
	 * 
	 * @方法说明：获取tpdsSettleItem数据信息
	 * @author chenyanming
	 * @param settleMerchantDetail
	 * @return
	 * @时间：2017年2月23日上午10:39:43
	 */
	private TpdsSettleItem getTpdsSettleItem(SettleMerchantDetail settleMerchantDetail) {
		TpdsSettleItem tpdsSettleItem = new TpdsSettleItem();
		tpdsSettleItem.setCurrency(settleMerchantDetail.getCurrency());
		tpdsSettleItem.setFee(new BigDecimal(settleMerchantDetail.getFee()));
		// tpdsSettleItem.setFinishTime(settleMerchantDetail.getfi);
		tpdsSettleItem.setMerchantNo(settleMerchantDetail.getMerchantId());
		tpdsSettleItem.setMerchantOrderNo(settleMerchantDetail.getMerchantOrderNo());
		tpdsSettleItem.setMerchantType(settleMerchantDetail.getMerchantType());
		tpdsSettleItem.setProductCode(settleMerchantDetail.getProductCode());
		tpdsSettleItem.setRequestAmount(new BigDecimal(settleMerchantDetail.getRequestAmount()));
		tpdsSettleItem.setSettleBath(settleMerchantDetail.getSettleBath());
		tpdsSettleItem.setSettleNo(settleMerchantDetail.getSettleNo());
		tpdsSettleItem.setSettleStatus("N");
		tpdsSettleItem.setSettleTime(DateUtils.getDate(settleMerchantDetail.getSettleTime()));
		tpdsSettleItem.setSuccessTime(DateUtils.getDate(settleMerchantDetail.getSuccessTime()));
		tpdsSettleItem.setTransNo(settleMerchantDetail.getTransNo());
		tpdsSettleItem.setTransType(settleMerchantDetail.getTransType());
		return tpdsSettleItem;
	}

	/**
	 * 
	 * @方法说明：获取tpdsSettleRecord数据信息
	 * @author chenyanming
	 * @param tpdsSettleMerchantDetail
	 * @return
	 * @时间：2017年2月23日上午10:37:47
	 */
	private TpdsSettleRecord getTpdsSettleRecord(TpdsSettleMerchantModel tpdsSettleMerchantDetail) {
		TpdsSettleRecord tpdsSettleRecord = new TpdsSettleRecord();
		tpdsSettleRecord.setCurrency(tpdsSettleMerchantDetail.getCurrency());
		tpdsSettleRecord.setMerchantNo(tpdsSettleMerchantDetail.getMerchantId());
		tpdsSettleRecord.setPayNum((int) tpdsSettleMerchantDetail.getPayNum());
		tpdsSettleRecord.setSettleBath(tpdsSettleMerchantDetail.getSettleBath());
		tpdsSettleRecord.setSettleStatus("Y");
		tpdsSettleRecord.setSettleTime(DateUtils.getDate(tpdsSettleMerchantDetail.getSettleTime()));
		tpdsSettleRecord.setTotalAmount(new BigDecimal(tpdsSettleMerchantDetail.getTotalAmount()));
		tpdsSettleRecord.setTotalFee(new BigDecimal(tpdsSettleMerchantDetail.getTotalFee()));
		tpdsSettleRecord.setTransType(tpdsSettleMerchantDetail.getTransType());
		return tpdsSettleRecord;
	}

	/*
	 * 保存信息【投标 放款 还款 出款等信息】 (non-Javadoc)
	 * 
	 * @see com.heepay.tpds.service.IAccoountCheckService#saveInform(java.lang.
	 * String)
	 */
	public String saveInform(String message) {

		return null;
	}

	// 发送通知
	private String sendHttps(String url, String systemNo, String fileName) {

		// 发送通知

		TreeMap<String, String> inBody = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// 指定排序器按照key升序排列
				return o1.compareTo(o2);
			}
		});

		CommonRequestHeaderMessage reqHeader = new CommonRequestHeaderMessage();
		reqHeader.setVersion("1.0");
		reqHeader.setMerchantCode(systemNo);
		reqHeader.setTxnType("R00003");
		reqHeader.setClientSn(TpdsDataUtils.businessOrderNo());
		reqHeader.setClientDate(DateUtil.getCurrentDate(new Date()));
		reqHeader.setClientTime(new SimpleDateFormat("HHmmss").format(new Date()));
		reqHeader.setFileName(fileName);
		reqHeader.setSignTime(String.valueOf(new Date().getTime()));

		inBody.put("businessSeqNo", TpdsDataUtils.businessSeqNo(systemNo));
		inBody.put("liquDate", DateUtil.getCurrentDate(new Date()));
		inBody.put("fileName", fileName);
		inBody.put("fileDownloadPath", tpdsCommonClient.getBankHostIp() + ":/"
				+ tpdsCommonClient.getBankUploadFilePath() + DateUtil.getCurrentDate(new Date()));
		inBody.put("fileUploadPath", "");

		// 加签
		String value = reqHeader.getSignTime() + "|" + SignRSA.getTreeMap(inBody);
		logger.info("签名串value{}",value);
		logger.info("签名路径{}",this.getClass().getResource("/").getPath()+serverBankInfo.getSignPrivateKey());
		String signature = SignRSA.addSign(value, this.getClass().getResource("/").getPath()+serverBankInfo.getSignPrivateKey());
		inBody.put("busiBranchNo", tpdsCommonClient.getBusiBranchNo());
		
		reqHeader.setSignature(signature);

		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setReqHeader(reqHeader);
		requestMessage.setInBody(inBody);
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		try {
			return httpsClient.send(url, jsonUtil.toJson(requestMessage), "UTF-8");

		} catch (Exception e) {
			logger.error("http对账通知出错{}", e);
		}
		return null;

	}

}
