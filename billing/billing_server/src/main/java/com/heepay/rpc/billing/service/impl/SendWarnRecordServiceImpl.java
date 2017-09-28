package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.dao.SettleChannelRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecordVo;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.common.util.BillDateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.rpc.client.WarningClient;
import com.heepay.rpc.service.RpcService;

/**
 * * 清结算对账预警功能
 * 
 * 
 * 描 述：
 *
 * 创 建 者： wangjie 创建时间： 2017年1月17日上午9:42:42 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "SendWarnRecordServiceImpl", processor = com.heepay.rpc.billing.service.SendWarnRecordService.Processor.class)
public class SendWarnRecordServiceImpl implements com.heepay.rpc.billing.service.SendWarnRecordService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleChannelLogMapper settleChannelLogDaoImpl;

	@Autowired
	private SettleChannelRecordMapper settleChannelRecordDaoImpl;

	@Autowired
	private SettleMerchantRecordMapper settleMerchantRecordDaoImpl;
	
	@Autowired
	private WarningClient warningClient;

	@Autowired
	private SettleChannelManagerMapper settleChannelManagerDaoImpl;

	@Autowired
	private ClearChannelRecordMapper clearChannelRecordDapImpl;

	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordMapper;
	// 查询所有通道是否有未对账的数据
	@Override
	public void isCheckBillRecord() throws TException {
		// 格式化当前日期
		String date = DateUtils.getFormatDateDir(new Date(), DateUtils.DATE_FORMAT_DATE);
		String dateTimePlan = DateUtils.getFormatDateDir(new Date(), DateUtils.DATE_FORMAT_DATE_TIME_START);
		try {
			// 查找应该对账的通道数据
			List<ClearChannelRecord> channelRecordList = clearChannelRecordDapImpl.queryChannelRecord(dateTimePlan);
			logger.info("应结算通道{}",channelRecordList);
			List<String> chnnelCodeList = new ArrayList<String>();
			//List<String> chnnelTypeList = new ArrayList<String>();
			StringBuilder sb = new StringBuilder();
			JsonMapperUtil jsonUtil = new JsonMapperUtil();
			
			if (!channelRecordList.isEmpty()) {
				for (ClearChannelRecord clearChannelRecord : channelRecordList) {
					chnnelCodeList.add(clearChannelRecord.getChannelCode()+"-"+clearChannelRecord.getChannelType());
				}
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("operBeginTime", date);
			map.put("effectFlg", BillingBillStatus.BBSTATUSY.getValue());
			map.put("checkStatus", BillingCheckStatus.CHECKSTATUSCE.getValue());
			// 查找所有通道数据 生效
			List<SettleChannelManager> settleChannelManagerList = settleChannelManagerDaoImpl
					.querySettleChannelManagerDetail(map);
			logger.info("未对账通道{}",settleChannelManagerList);
			if (settleChannelManagerList != null && settleChannelManagerList.size() > 0) {

				// 每天9点为对账的通道
				for (int i = 0, length = settleChannelManagerList.size(); i < length; i++) {

					SettleChannelManager settleChannelManager = settleChannelManagerList.get(i);
					//chnnelTypeList.add(settleChannelManager.getChannelCode()+"-"+settleChannelManager.getChannelType());
					String channelType = settleChannelManager.getChannelCode()+"-"+settleChannelManager.getChannelType();
					if ((!chnnelCodeList.isEmpty()  && chnnelCodeList.contains(channelType))) {
						String message = "对账日期是" + date + "。通道合作方(" + settleChannelManager.getChannelName()
								+ "),支付通道类型(" + ChannelType.labelOf(settleChannelManager.getChannelType())
								+ "),系统自动对账异常。请负责该通道的财务人员，尽快上传对账文件。";
						logger.info("未对账数据{}",message);
						sb = sb.append("对账日期是" + date + "。通道合作方(" + settleChannelManager.getChannelName() + "),支付通道类型("
								+ ChannelType.labelOf(settleChannelManager.getChannelType())
								+ "),系统自动对账异常。请负责该通道的财务人员，尽快上传对账文件。").append("\r\n");
						logger.info("未对账数据1{}",sb);
						try {
							Map<String, Object> infoMap = new HashMap<String, Object>();
							String json = warningClient.selectGroupId(settleChannelManager.getChannelCode());
							logger.info("组Id=" + settleChannelManager.getChannelCode()+"{}", json);
							Map<String, Object> mapMessage = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
							if (mapMessage != null && mapMessage.containsKey("groupId")) {
								infoMap.put("groupId", Integer.parseInt(mapMessage.get("groupId").toString()));
								infoMap.put("type", "email");
								infoMap.put("msgHead", "清结算预警消息");
								infoMap.put("msgBody", message);
								String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap));
								logger.info("查找未对账的通道信息(对账文件没下载)发送邮件返回:{}", result);
							}
						} catch (Exception e) {
							logger.error("发送邮件出错{}",e);
						}
						

						try {
							Map<String, Object> infoMap1 = new HashMap<String, Object>();
							String json1 = warningClient.selectGroupId(settleChannelManager.getChannelCode());
							logger.info("组Id=" + settleChannelManager.getChannelCode()+"{}", json1);
							Map<String, Object> mapMessage1 = JsonMapperUtil.nonEmptyMapper().fromJson(json1, Map.class);
							if (mapMessage1 != null && mapMessage1.containsKey("groupId")) {
								infoMap1.put("groupId", Integer.parseInt(mapMessage1.get("groupId").toString()));
								infoMap1.put("type", "sms");
								infoMap1.put("msgHead", "清结算预警消息");
								infoMap1.put("msgBody", message);
								String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap1));
								logger.info("查找未对账的通道信息(对账文件没下载)发送短信返回:{}", result);
							}
						} catch (Exception e) {
							logger.error("发送短信出错{}",e);
						}
					} 
				}
				
			

				if (sb.length() > 5) {
					try {
						Map<String, Object> infoMap = new HashMap<String, Object>();
						String json = warningClient.selectGroupId("team");
						logger.info("组Id=team{}", json);
						Map<String, Object> mapMessage = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
						if (mapMessage != null && mapMessage.containsKey("groupId")) {
							infoMap.put("groupId", Integer.parseInt(mapMessage.get("groupId").toString()));
							infoMap.put("type", "all");
							infoMap.put("msgHead", "清结算预警消息");
							infoMap.put("msgBody", sb.toString());
							String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap));
							logger.info("查找未对账的通道信息(对账文件没下载)发送短信返回:{}", result);
						}
					} catch (Exception e) {
						logger.error("内部通知出错{}",e);
					}
				}

			}
			
		} catch (Exception e) {
			logger.error("清结算预警是否有未对账的数据出错{}", e);
		}

	}

	// 查询已对账未结算的数据
	@Override
	public void isSettleRecord() throws TException {

	}

	// 查找未对账的通道信息 (财务，运营，内部)
	@Override
	public void isCheckBillChannelRecord() throws TException {

	}

	// 统计对账周期内数据
	@Override
	public void reconciliationCycleRecord() throws TException {
		this.sendWarn_new();
	}

	/**
	 * 
	 * @方法说明：优化
	 * @author chenyanming
	 * @时间：2017年6月21日下午2:20:13
	 */
	private void sendWarn_new() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sbEmail = new StringBuilder();
		String date1 = DateUtils.getFormatDateDir(new Date(), DateUtils.DATE_FORMAT_DATE);
		String yesterDay = BillDateUtil.getYesterDay("yyyy-MM-dd");
		String yesterDayBefor = BillDateUtil.getYesterDayBefor("yyyy-MM-dd");
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		try {
			// 汇总通道测数据
			sb.append("通道侧对账统计(对账日期是").append(date1).append(")：").append("\r\n");
			sbEmail.append("通道侧对账统计(对账日期是").append(date1).append(")：").append("<br>").append("&nbsp&nbsp&nbsp&nbsp");
			this.sumChannelData(sb, sbEmail, jsonUtil);
			// 汇总商户侧数据
			sb.append("\r\n");
			sbEmail.append("<br>");
			sb.append("商户侧结算统计(交易日期是").append(yesterDayBefor).append(",结算日期是").append(yesterDay).append(")：").append("\r\n");
			sbEmail.append("商户侧结算统计(交易日期是").append(yesterDayBefor).append(",结算日期是").append(yesterDay).append(")：").append("<br>").append("&nbsp&nbsp&nbsp&nbsp");
			this.sumMerchantData(sb, sbEmail, jsonUtil);
			
			this.sendWarnMethod(sb.toString(), "teamWX", "wechat", jsonUtil);
			this.sendWarnMethod(sbEmail.toString(), "teamEmail", "email", jsonUtil);
			
		} catch (Exception e) {
			logger.error("清结算预警统计对账周期内数据出错{}", e);
		}

	}

	/**
	 * 
	 * @方法说明：汇总商户侧对账结算数据
	 * @author chenyanming
	 * @param sb
	 * @param sbEmail
	 * @param jsonUtil
	 * @时间：2017年6月21日下午4:14:10
	 */
	private void sumMerchantData(StringBuilder sb, StringBuilder sbEmail, JsonMapperUtil jsonUtil) {
		// 昨天时间
		String start = BillDateUtil.getYesterDay("yyyy-MM-dd 00:00:00");
		String end = BillDateUtil.getYesterDay("yyyy-MM-dd 23:59:59");
		// 前天时间
		String startBefor = BillDateUtil.getYesterDayBefor("yyyy-MM-dd 00:00:00");
		String endBefor = BillDateUtil.getYesterDayBefor("yyyy-MM-dd 23:59:59");
		List<String> merchantIdList = new ArrayList<String>();
		// 汇总商户数据
		List<ClearMerchantRecordVo> clearMerchantList = clearMerchantRecordMapper.getMerchantClearData(startBefor, endBefor);
		if(clearMerchantList == null || clearMerchantList.size() == 0) {
			return;
		}
		for (ClearMerchantRecordVo clearMerchantRecordVo : clearMerchantList) {
			Integer settleTotalNumT0 = 0; // 总结算笔数
			BigDecimal settleTotalAmountT0 = new BigDecimal(0); // 总结算金额
			Integer settleTotalNumT1 = 0; // 总结算笔数
			BigDecimal settleTotalAmountT1 = new BigDecimal(0); // 总结算金额
			merchantIdList.add(clearMerchantRecordVo.getMerchantId());
			// 根据商户编码查询结算数据
			List<SettleMerchantRecord> settleMerchantRecordList = 
					settleMerchantRecordDaoImpl.querySettleMerchantByMerchantId(clearMerchantRecordVo.getMerchantId(), start, "Y");
			if(settleMerchantRecordList == null || settleMerchantRecordList.size() == 0) {
				continue;
			}
			for (SettleMerchantRecord settleMerchantRecord : settleMerchantRecordList) {
				if("0".equals(settleMerchantRecord.getSettleCyc())) { // 实时
					settleTotalNumT0 = settleTotalNumT0 + settleMerchantRecord.getPayNum();
					settleTotalAmountT0 = settleTotalAmountT0.add(settleMerchantRecord.getSettleAmount());
				}else { // 周期
					settleTotalNumT1 = settleTotalNumT1 + settleMerchantRecord.getPayNum();
					settleTotalAmountT1 = settleTotalAmountT1.add(settleMerchantRecord.getSettleAmount());
				}
			}
			sb = sb.append("商户编码(" + clearMerchantRecordVo.getMerchantId() + "),商户名称("
					+ clearMerchantRecordVo.getMerchantName() + "),交易总笔数:"
					+ clearMerchantRecordVo.getPayNum() + ",交易总金额:" + clearMerchantRecordVo.getTotalAmount()
					+ ",结算笔数(周期):" + settleTotalNumT1 + ",结算金额(周期):"
					+ settleTotalAmountT1 + ", 结算笔数(实时):" + settleTotalNumT0 + ", 结算金额(实时):"
					+ settleTotalAmountT0).append("\r\n");
			
			sbEmail = sbEmail.append("商户编码(" + clearMerchantRecordVo.getMerchantId() + "),商户名称("
					+ clearMerchantRecordVo.getMerchantName() + "),交易总笔数:"
					+ clearMerchantRecordVo.getPayNum() + ",交易总金额:" + clearMerchantRecordVo.getTotalAmount()
					+ ",结算笔数(周期):" + settleTotalNumT1 + ",结算金额(周期):"
					+ settleTotalAmountT1 + ", 结算笔数(实时):" + settleTotalNumT0 + ", 结算金额(实时):"
					+ settleTotalAmountT0).append("<br>").append("&nbsp&nbsp&nbsp&nbsp");
		}
		List<String> merchantIdList2 = settleMerchantRecordDaoImpl.queryAllSettleData(start);
		if(merchantIdList2 != null) {
			for (String merchantId : merchantIdList2) {
				Integer settleTotalNumT0 = 0; // 总结算笔数
				BigDecimal settleTotalAmountT0 = new BigDecimal(0); // 总结算金额
				Integer settleTotalNumT1 = 0; // 总结算笔数
				BigDecimal settleTotalAmountT1 = new BigDecimal(0); // 总结算金额
				if(merchantIdList.contains(merchantId)) {
					continue;
				}
				String merchantName = null;
				List<SettleMerchantRecord> settleMerchantRecordList = 
						settleMerchantRecordDaoImpl.querySettleMerchantByMerchantId(merchantId, start, "Y");
				if(settleMerchantRecordList == null || settleMerchantRecordList.size() == 0) {
					continue;
				}
				for (SettleMerchantRecord settleMerchantRecord : settleMerchantRecordList) {
					if("0".equals(settleMerchantRecord.getSettleCyc())) { // 实时
						settleTotalNumT0 = settleTotalNumT0 + settleMerchantRecord.getPayNum();
						settleTotalAmountT0 = settleTotalAmountT0.add(settleMerchantRecord.getSettleAmount());
					}else { // 周期
						settleTotalNumT1 = settleTotalNumT1 + settleMerchantRecord.getPayNum();
						settleTotalAmountT1 = settleTotalAmountT1.add(settleMerchantRecord.getSettleAmount());
					}
					merchantName = settleMerchantRecord.getMerchantName();
				}
				sb = sb.append("商户编码(" + merchantId + "),商户名称(" + merchantName + "),交易总笔数:"
						+ "0" + ",交易总金额:" + "0" + ",结算笔数(周期):" + settleTotalNumT1 + ",结算金额(周期):"
						+ settleTotalAmountT1 + ", 结算笔数(实时):" + settleTotalNumT0 + ", 结算金额(实时):"
						+ settleTotalAmountT0).append("\r\n");
				
				sbEmail = sbEmail.append("商户编码(" + merchantId + "),商户名称(" + merchantName + "),交易总笔数:"
						+ "0" + ",交易总金额:" + "0" + ",结算笔数(周期):" + settleTotalNumT1 + ",结算金额(周期):"
						+ settleTotalAmountT1 + ", 结算笔数(实时):" + settleTotalNumT0 + ", 结算金额(实时):"
						+ settleTotalAmountT0).append("<br>").append("&nbsp&nbsp&nbsp&nbsp");
			}
		}
	}

	/**
	 * 
	 * @方法说明：汇总通道侧对账结算数据
	 * @author chenyanming
	 * @param sb
	 * @param sbEmail
	 * @param jsonUtil
	 * @时间：2017年6月21日下午4:11:46
	 */
	private void sumChannelData(StringBuilder sb, StringBuilder sbEmail, JsonMapperUtil jsonUtil) {
		// 格式化当前日期
		String date = DateUtils.getFormatDateDir(new Date(), DateUtils.DATE_FORMAT_DATE_TIME_START);
		//String date1 = DateUtils.getFormatDateDir(new Date(), DateUtils.DATE_FORMAT_DATE);
		// 查找所有通道数据 生效
		List<SettleChannelManager> settleChannelManagerList = settleChannelManagerDaoImpl
				.querySettleChannelManagerWarnDetail(BillingBillStatus.BBSTATUSY.getValue());
		if(settleChannelManagerList == null || settleChannelManagerList.size() == 0) {
			return;
		}
		for (SettleChannelManager settleChannelManager : settleChannelManagerList) {
			List<SettleChannelLog> settleChannelLogList = settleChannelLogDaoImpl.selectByCodeAndType(
					settleChannelManager.getChannelCode(), settleChannelManager.getChannelType(), date);
			if(settleChannelLogList == null || settleChannelLogList.size() == 0) {
				continue;
			}
			
			Integer settleTotalNum = 0; // 总结算笔数
			BigDecimal settleTotalAmount = new BigDecimal(0); // 总结算金额
			Integer errorRecordNum = 0; // 差错笔数
			BigDecimal errorTotalAmount = new BigDecimal(0); // 差错金额
			Integer recordNum = 0; // 对账总笔数
			BigDecimal totalAmount = new BigDecimal(0); // 对账总金额
			
			// 通过channelCode查找channelName
			SettleChannelManager scm = settleChannelManagerDaoImpl
					.findChannelNameBySettleChannelManageChannelCode(settleChannelManager.getChannelCode());
			
			// 查找日记表中数据按channelCode和channelType②按照通道统计总笔数、总金额、结算笔数、结算金额、差错笔数、差错金额
			for (SettleChannelLog settleChannelLog : settleChannelLogList) {
				//根据对账批次号查结算批次号
				/*List<String> settleBaths = clearChannelRecordDapImpl.querySettleBatch(settleChannelLog.getCheckBathNo());
				for (String settleBath : settleBaths) {
					// 查找结算笔数//查找结算金额
					List<SettleChannelRecord> settleChannelRecordList = settleChannelRecordDaoImpl
							.getSettleChannelRecordByCode(settleChannelLog.getChannelCode(),
									settleChannelLog.getChannelType(), date, "Y",settleBath);
					for (SettleChannelRecord settleChannelRecord : settleChannelRecordList) {
						settleTotalNum = settleTotalNum + settleChannelRecord.getPayNum();
						settleTotalAmount = settleTotalAmount.add(settleChannelRecord.getTotalAmount());
					}
				}*/
				settleTotalNum = settleTotalNum + settleChannelLog.getInRecordNum().intValue() + settleChannelLog.getOutRecordNum().intValue();
				settleTotalAmount = settleTotalAmount.add(settleChannelLog.getInTotalAmount()).add(settleChannelLog.getOutTotalAmount());
				recordNum = recordNum + settleChannelLog.getRecordNum().intValue();
				totalAmount = totalAmount.add(settleChannelLog.getTotalAmount());
				errorRecordNum = errorRecordNum + settleChannelLog.getErrorRecordNum().intValue();
				errorTotalAmount = errorTotalAmount.add(settleChannelLog.getErrorTotalAmount());
			}
			//如果总笔数为0，跳过
			if(recordNum == 0){
				continue;
			}
			String msg = "通道合作方(" + scm.getChannelName() + "),支付通道类型("
					+ ChannelType.labelOf(settleChannelManager.getChannelType()) + "),总笔数:"
					+ recordNum + ",总金额:" + totalAmount + ",平账总笔数:" + settleTotalNum + ",平账总金额:"
					+ settleTotalAmount + ",差异账总笔数:" + errorRecordNum + ",差异账总金额:" + errorTotalAmount;
			if(errorRecordNum > 0 || errorTotalAmount.compareTo(new BigDecimal(0)) == 1) {
				// 对账出现差异，发给财务
				this.sendWarnMethod(msg, settleChannelManager.getChannelCode() + "II", "email", jsonUtil);
				this.sendWarnMethod(msg, settleChannelManager.getChannelCode() + "II", "wechat", jsonUtil);
			}
			sb = sb.append(msg).append("\r\n");
			sbEmail = sbEmail.append(msg).append("<br>").append("&nbsp&nbsp&nbsp&nbsp");
		}
	}

	/**
	 * 
	 * @方法说明：发送邮件和微信方法
	 * @author chenyanming
	 * @param msg
	 * @param groupId
	 * @param jsonUtil 
	 * @时间：2017年6月21日下午3:40:22
	 */
	private void sendWarnMethod(String msg, String groupId, String type, JsonMapperUtil jsonUtil) {
		Map<String, Object> infoMap = new HashMap<String, Object>();
		String json = warningClient.selectGroupId(groupId);
		logger.info("组Id=" + groupId, json);
		Map<String, Object> mapMessage = JsonMapperUtil.nonEmptyMapper().fromJson(json,Map.class);
		if (mapMessage != null && mapMessage.containsKey("groupId")) {
			infoMap.put("groupId", Integer.parseInt(mapMessage.get("groupId").toString()));
			infoMap.put("type", type);
			infoMap.put("msgHead", "对账结算通知" + " - " + BillDateUtil.getCurrentDate(new Date()));
			infoMap.put("sender", "汇付宝 - 清结算系统");
			infoMap.put("msgBody", msg);
			String result = warningClient.sendWaringMsg(jsonUtil.toJson(infoMap));
			logger.info("发送" + type == "email"?"邮件":"微信" + "返回结果:{}", result);
		}
		
	}

}
