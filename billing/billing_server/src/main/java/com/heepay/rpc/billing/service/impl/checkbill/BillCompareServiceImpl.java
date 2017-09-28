/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年6月6日下午1:33:47
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
package com.heepay.rpc.billing.service.impl.checkbill;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleBillRecordMapper;
import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.dao.SettleDifferBillRecordMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.billing.entity.SettleDifferBillRecord;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.TransTypeUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.ChannelType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.enums.billing.BillingDifferType;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.enums.billing.SettleDifferTransStatus;
import com.heepay.rpc.billing.service.ICheckBillRecordService;
import com.heepay.rpc.client.BillingClearAPIClient;
import com.heepay.rpc.payment.model.ClearChannelQueryRecordModel;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年6月6日下午1:33:47
 * 创建描述：清结算对账代码重构
 * 
 * 修 改 者：  
 * 修改时间： 
 * 修改描述： 
 * 
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Component
public class BillCompareServiceImpl implements ICheckBillRecordService{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private SettleChannelLogMapper settleChannelLogDaoImpl;
	
	@Autowired
	private SettleBillRecordMapper settleBillRecordDaoImpl;
	
	@Autowired
	private ClearChannelRecordMapper clearChannelRecordDaoImpl;
	
	@Autowired
	private SettleDifferRecordMapper settleDifferRecordDaoImpl;
	
	@Autowired
	private SettleChannelManagerMapper settleChannelManagerDaoImpl;
	
	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordDaoImpl;
	
	@Autowired
	BillingClearAPIClient billingClearAPIClient;
	
	@Autowired
	SettleDifferBillRecordMapper SettleDifferBillRecordDao;
	
	/**
	 * @author xuangang
	 * @param checkBathno  对账批次
	 * @描述 清结算对账入口
	 */
	@Transactional()
	@Override
	public boolean billCompare(String checkBathno) {
		
		logger.info("清结算对账开始 ，对账批次号：{}" , checkBathno);
		try{
			updateSettleChannelLogStatus(checkBathno);	//将该批次修改为【对账中中】对账中状态的中间值		
			SettleChannelLog settleChannelLog = settleChannelLogDaoImpl.selectByCheckBathNo(checkBathno);
			
			if(settleChannelLog == null){
				logger.info("清结算对账日志表不存在该记录 ，对账批次号：{}" , checkBathno);				
			}else{				
				SettleBillRecord record = new SettleBillRecord();
				record.setCheckBathNo(checkBathno);
				//查找所有该批次未对账的银行账单明细
				List<SettleBillRecord> settleBillList = settleBillRecordDaoImpl.queryBillRecordDetailByBatchNo(record);
				
			     if(settleBillList == null || settleBillList.size() == 0){
			    	 logger.info("不存在对账批次为：{}的账单明细", checkBathno);
			    	 settleChannelLog.setCheckStatus(BillingCheckStatus.CHECKSTATUSCE.getValue());
					 updateSettleChannelLog(settleChannelLog);
			    	 return true;
			     }
					
				for (int i = 0, length = settleBillList.size(); i < length; i++) {
					//账单明细
					SettleBillRecord settleBillRecord = settleBillList.get(i);
					//统计该批次的对账总笔数、总金额 
					countRecordNumAndAmount(settleChannelLog, settleBillRecord, "total");
					//账单明细、商户侧、通道侧三方对账
					billDetailCompare(settleBillRecord, settleChannelLog, checkBathno);
                }	
				
				updateSettleChannelLog(settleChannelLog);  // 更新 对账日志表,对账完成
				logger.info("{FIND_SUCCESS} 批次号为" + checkBathno + "对账结束！");					
			}			
		}catch (Exception e) {
			logger.error("清结算对账异常，对账批次号：{}", checkBathno, e);
			throw new RuntimeException();
		}	
		return true;
	}

	@Override
	public void insertSettleDifferRecordMethod(SettleChannelLog log,
			ClearChannelRecord cRecord, ClearMerchantRecord mRecord,
			SettleBillRecord bRecord, SettleDifferRecord settleDifferRecord)
			throws Exception {		
		
	}
	
	/**
	 * 
	 * @方法说明：将对账批次为checkBathno的日志记录修改为中间状态
	 * @author xuangang
	 * @param checkBathno
	 * @throws Exception
	 * @时间：2017年6月6日下午1:48:18
	 */
	private void updateSettleChannelLogStatus(String checkBathno) throws Exception {
		
		SettleChannelLog updateSettleChannelLog = new SettleChannelLog();
		updateSettleChannelLog.setCheckBathNo(checkBathno);
		updateSettleChannelLog.setCheckStatus(BillingCheckStatus.CHECKSTATUSCDD.getValue());  //"CD-D","对账中的中间状态"		
		settleChannelLogDaoImpl.updateSettleChannelLog(updateSettleChannelLog);
		logger.info("更新对账批次的状态为中间状态 checkBathno：{}", checkBathno);		
	}
	
	/**
	 * 
	 * @方法说明：统计平账、差异账，总笔数，总金额
	 * @author xuangang
	 * @param settleChannelLog
	 * @param settleBillRecord
	 * @param flag
	 * @时间：2017年1月16日下午1:20:46
	 */
	private void countRecordNumAndAmount(SettleChannelLog settleChannelLog, SettleBillRecord settleBillRecord, String flag){
		
		BigDecimal totalAmount = new BigDecimal("0");
		
		//统计总笔数、总金额
		if("total".equals(flag)){
			//对账总笔数、总金额（包括平账、差异账）			
			if (null != settleChannelLog.getRecordNum()) {
				settleChannelLog.setRecordNum(settleChannelLog.getRecordNum() + Long.valueOf("1"));// 对账总笔数
			} else {
				settleChannelLog.setRecordNum(Long.valueOf(1));// 总笔数
			}
			// 对账金额累加
			 totalAmount = (settleBillRecord.getSuccessAmount()==null?totalAmount:settleBillRecord.getSuccessAmount());
			if (null != settleChannelLog.getTotalAmount()) {
				settleChannelLog.setTotalAmount(settleChannelLog.getTotalAmount().add(totalAmount));// 对账总金额
			} else {
				settleChannelLog.setTotalAmount(totalAmount);// 对账总金额
			}
		}else if("error".equals(flag)){ 
			//对账差异总笔数、总金额（差异账）			
			if (null != settleChannelLog.getErrorRecordNum()) {
				settleChannelLog.setErrorRecordNum(settleChannelLog.getErrorRecordNum() + Long.valueOf("1"));// 差异总笔数
			} else {
				settleChannelLog.setErrorRecordNum(Long.valueOf(1));// 差异总笔数
			}
			// 差异金额累加
			 totalAmount = (settleBillRecord.getSuccessAmount()==null?totalAmount:settleBillRecord.getSuccessAmount());
			if (null != settleChannelLog.getErrorTotalAmount()) {
				settleChannelLog.setErrorTotalAmount(settleChannelLog.getErrorTotalAmount().add(totalAmount));// 差异总金额
			} else {
				settleChannelLog.setErrorTotalAmount(totalAmount);// 差异总金额
			}
		}	
	}
	
	/**
	 * 
	 * @方法说明：清结算账单明细对账
	 * @author xuangang
	 * @param settleBillRecord 账单明细
	 * @param settleChannelLog 对账日志
	 * @时间：2016年12月5日上午10:55:38
	 *
	 *
	 * @更新时间：2017年5月10日10:28:07
	 * @更新人：wangdong
	 * @更新内容：优化代码和整理日志信息
	 */
	private void billDetailCompare(SettleBillRecord settleBillRecord, SettleChannelLog settleChannelLog, String checkBathNo) throws Exception{
		
			logger.info("清结算三方对账开始,对账批次：{}", checkBathNo);
			ClearChannelRecord paramRecord = new ClearChannelRecord();// 通道侧清算记录
			
			if(StringUtils.equals(ChannelPartner.UNIONPAY.getValue(),settleBillRecord.getChannelCode())
					&& StringUtils.equals(ChannelType.QRCODE.getValue(),settleBillRecord.getChannelType())){
				//银联对账
				paramRecord.setBankSeq(settleBillRecord.getChannleNo());   //账单明细侧上游流水号
			} else if(Constants.Clear.TK.equals(settleBillRecord.getField1())){    
				//如果是财付通退款，则使用上游流水号
				paramRecord.setBankSeq(settleBillRecord.getChannleNo());   //账单明细侧上游流水号
			}else {
				//非银联对账
				paramRecord.setPaymentId(settleBillRecord.getPaymentId());  // 账单明细侧支付单号
			}
			// 根据支付单号或者上游流水号查询【未处理】的通道侧清算数据
			ClearChannelRecord clearChannelRecord = clearChannelRecordDaoImpl.queryClearChannelRecordByPaymentIdAndCheckStatus(paramRecord);;
			
			ClearMerchantRecord clearMerByTransNo = null;			
			
			if(clearChannelRecord != null){
				clearMerByTransNo = clearMerchantRecordDaoImpl.findClearMerchantRecordByTansNo(clearChannelRecord.getTransNo());
				if(clearMerByTransNo != null){
					// 账单明细，通道侧清算记录，商户侧清算记录，三方金额对比
					checkAmountChannelChantMerBillRecord(settleBillRecord, clearChannelRecord, clearMerByTransNo, settleChannelLog);
				}				
			}
			if(clearMerByTransNo == null || clearChannelRecord == null){
				logger.info("通道侧清算数据不存在，支付单号:{}, 上游流水号：{}", settleBillRecord.getPaymentId(), settleBillRecord.getChannleNo());
				
				SettleDifferRecord settleDifferRecord = new SettleDifferRecord();				
				settleDifferRecord.setDifferType(BillingDifferType.BDTYPEW.getValue());  //差异类型：未知
				//插入差异单表(未知差异)
				insertSettleDifferRecordMethod(settleBillRecord, settleDifferRecord);
				//统计对账差异总笔数、总金额  
				countRecordNumAndAmount(settleChannelLog, settleBillRecord, "error");
				if(clearMerByTransNo != null){
					clearMerByTransNo.setCheckFlg(BillingYCheckStatus.BDTYPEW.getValue());  // (用户侧清算记录表)对账标识(未知)
				}
				if(clearChannelRecord != null){
					clearChannelRecord.setCheckFlg(BillingYCheckStatus.BDTYPEW.getValue()); // (通道侧清算记录表)对账标识(未知)
				}
			}			
			//更新对账次数、对账状态、对账标识				
			updateCheckNum(settleBillRecord, settleChannelLog, clearChannelRecord, clearMerByTransNo);
			
		    //更新账单明细为已处理
			settleBillRecord.setBillStatus(BillingBillStatus.BBSTATUSY.getValue());
			settleBillRecordDaoImpl.updateSettleBillRecordByPrimaryKey(settleBillRecord);
		  logger.info("更新账单明细------>{}",settleBillRecord,"{FIND_SUCCESS} 对账批次为：" + checkBathNo + ",上游流水号为：" + settleBillRecord.getChannleNo()+"对账完毕！");
	}

	/**
	 * 
	 * @方法说明：未知差异数据入库,银行对账明细存在，清结算系统不存在
	 * @author xuangang
	 * @param cRecord 通道清算数据
	 * @param mRecord 商户清算数据
	 * @param bRecord 账单明细
	 * @时间：2017年6月6日下午3:08:25
	 */
	public void insertSettleDifferRecordMethod(SettleBillRecord bRecord, SettleDifferRecord settleDifferRecord){				  					
			// 账单明细
			if (null != bRecord) {				
				settleDifferRecord.setCheckBathNo(bRecord.getCheckBathNo()); // 对账批次
				
				if(Constants.Clear.TK.equals(bRecord.getField1())){   
					//如果是财付通退款，则通过上游流水号查询退款支付单号
					ClearChannelQueryRecordModel model = billingClearAPIClient.queryTransByBankSerialNo(bRecord.getChannleNo());
					if(model != null){
						settleDifferRecord.setPaymentId(model.getPaymentId());
					}else{
						logger.info("财付通退款，通过上游流水号查询退款支付单号失败，上游流水号：{}", bRecord.getChannleNo());
					}
				}else{
					settleDifferRecord.setPaymentId(bRecord.getPaymentId()); // 支付单号
				}				
				settleDifferRecord.setChannelType(bRecord.getChannelType()); // 通道类型
				settleDifferRecord.setSuccessAmount(bRecord.getSuccessAmount()); // 支付金额
				settleDifferRecord.setChannelCode(bRecord.getChannelCode()); // 通道编码
				settleDifferRecord.setChannleNo(bRecord.getChannleNo()); // 通道流水（上游流水）
				settleDifferRecord.setCostAmount(bRecord.getFee()); // 成本				
					// 根据通道编码获取通道合作方
				SettleChannelManager channelManager = settleChannelManagerDaoImpl
						.findChannelNameBySettleChannelManageChannelCode(settleDifferRecord.getChannelCode());
				if (null != channelManager && null != channelManager.getChannelName()) {
					settleDifferRecord.setChannelName(channelManager.getChannelName());// 通道名称
				}
				logger.info("【对账系统】插入差异记录表  查询通道合作方名称成功！支付单号：{}", settleDifferRecord.getPaymentId());
				}
			
				settleDifferRecord.setErrorDate(new Date()); // 差错日期
				settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue()); // 处理状态
				settleDifferRecord.setIsBill(ClearingCheckStatus.CHECKSTATUSN.getValue());// 记账标记
				settleDifferRecord.setIsProfit(SettleDifferIsProfit.ACCOUNTFLGN.getValue());// 是否分润
																								  // 默认N（否）	
				
				Map<String, String> paraMap = new HashMap<String, String>();
				paraMap.put("paymentId", settleDifferRecord.getPaymentId());
				
				int num = settleDifferRecordDaoImpl.countDifferRecordNumByPaymentId(paraMap);
				if(num > 0){
					logger.info("该交易记录差异表已存在，支付单号：{}", settleDifferRecord.getPaymentId());
					saveSettleDifferBillRecord(bRecord);   //差异表已存在，入对账异常表
					
				}else{
					// 存储差异表
					settleDifferRecordDaoImpl.insertSettleDifferRecord(settleDifferRecord);
					logger.info("插入差异记录表成功！对账批次号：{},支付单号：{}", settleDifferRecord.getCheckBathNo(), settleDifferRecord.getPaymentId());	
				}				
	}
	
	/**
	 * 
	 * @方法说明：未知差异数据入库,银行对账明细存在，
	 * @author xuangang
	 * @param cRecord 通道清算数据
	 * @param mRecord 商户清算数据
	 * @param bRecord 账单明细
	 * @时间：2017年6月6日下午3:08:25
	 */
	public void insertSettleDifferRecordMethod(ClearChannelRecord cRecord, ClearMerchantRecord mRecord,
			SettleBillRecord bRecord, SettleDifferRecord settleDifferRecord)
			throws Exception {		
          try {	
			// 账单明细
			if (null != bRecord) {
				settleDifferRecord.setCheckBathNo(bRecord.getCheckBathNo()); // 对账批次
				if(Constants.Clear.TK.equals(bRecord.getField1())){   
					//如果是财付通退款，则通过上游流水号查询退款支付单号
					ClearChannelQueryRecordModel model = billingClearAPIClient.queryTransByBankSerialNo(bRecord.getChannleNo());
					if(model != null){
						settleDifferRecord.setPaymentId(model.getPaymentId());
					}else{
						logger.info("财付通退款，通过上游流水号查询退款支付单号失败，上游流水号：{}", bRecord.getChannleNo());
					}
				}else{
					settleDifferRecord.setPaymentId(bRecord.getPaymentId()); // 支付单号
				}
				settleDifferRecord.setChannelType(bRecord.getChannelType()); // 通道类型
				settleDifferRecord.setSuccessAmount(bRecord.getSuccessAmount()); // 支付金额
				settleDifferRecord.setChannelCode(bRecord.getChannelCode()); // 通道编码
				settleDifferRecord.setChannleNo(bRecord.getChannleNo()); // 通道流水（上游流水）
				settleDifferRecord.setCostAmount(bRecord.getFee()); // 成本
				if (null == cRecord) {
					// 根据通道编码获取通道合作方
					SettleChannelManager channelManager = settleChannelManagerDaoImpl
							.findChannelNameBySettleChannelManageChannelCode(settleDifferRecord.getChannelCode());
					if (null != channelManager && null != channelManager.getChannelName()) {
						settleDifferRecord.setChannelName(channelManager.getChannelName());// 通道名称
					}
					logger.info("插入差异记录表  查询通道合作方名称成功！对账批次号：{}", settleDifferRecord.getCheckBathNo());
				}
			}
			// 通道侧清算记录
			if (null != cRecord) {
				settleDifferRecord.setChannelName(cRecord.getChannelName()); // 通道名称
				settleDifferRecord.setRequestAmount(cRecord.getSuccessAmount());// 支付金额（订单金额）
				settleDifferRecord.setTransType(cRecord.getTransType()); // 交易类型
			}
			// 用户侧清算记录
			if (null != mRecord) {
				settleDifferRecord.setMerchantId(mRecord.getMerchantId()); // 用户编码
				settleDifferRecord.setTransNo(mRecord.getTransNo()); // 交易单号
				settleDifferRecord.setProductCode(mRecord.getProductCode()); // 产品编码
				settleDifferRecord.setSettleAmountPlan(mRecord.getSettleAmountPlan()); // 订单应结算金额
				settleDifferRecord.setFeeAmount(mRecord.getFee()); // 手续费				
			
				settleDifferRecord.setMerchantName(mRecord.getMerchantName());   //商户名称
				settleDifferRecord.setBankcardType(mRecord.getBankcardType());   //银行卡类型
				settleDifferRecord.setFeeWay(mRecord.getFeeWay());               //手续费扣除方式
				settleDifferRecord.setBusiTime(mRecord.getBusiTime());           //交易发起时间
				settleDifferRecord.setPayTime(mRecord.getSettleTime());          //支付发起时间
				settleDifferRecord.setSuccessTime(mRecord.getSuccessTime());     //成功支付时间
				settleDifferRecord.setAgentCode(mRecord.getAgentCode());         //代理商编码
				settleDifferRecord.setAgentName(mRecord.getAgentName());         //代理商名称				
				settleDifferRecord.setProductName(mRecord.getProductName());     //产品名称				
			}
			settleDifferRecord.setErrorDate(new Date()); // 差错日期
			settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue()); // 处理状态
			settleDifferRecord.setIsBill(ClearingCheckStatus.CHECKSTATUSN.getValue());// 记账标记
			settleDifferRecord.setIsProfit(SettleDifferIsProfit.ACCOUNTFLGN.getValue());// 是否分润
			
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("paymentId", settleDifferRecord.getPaymentId());
			
			int num = settleDifferRecordDaoImpl.countDifferRecordNumByPaymentId(paraMap);
			if(num > 0){
				logger.info("该交易记录差异表已存在，支付单号：{}", settleDifferRecord.getPaymentId());
				
				saveSettleDifferBillRecord(bRecord);   //对账异常，差异表已存在，保存到对账异常表
			}else{
				// 存储差异表
				settleDifferRecordDaoImpl.insertSettleDifferRecord(settleDifferRecord);
				logger.info("【对账系统】插入差异记录表成功！支付单号：{}"	, settleDifferRecord.getPaymentId());
			}			
			
			
		} catch (Exception e) {
			logger.error("组装差异表需要的数据，插入差异表错误！支付单号：{}", settleDifferRecord.getPaymentId(),	e);
			throw new Exception();
		}
	}
	/**
	 * 
	 * @方法说明：比较三方金额是否一致
	 * @author xuangang
	 * @param settleBillRecord
	 * @param clearChannelRecord
	 * @param clearMerByTransNo
	 * @param settleChannelLog
	 * @return
	 * @throws Exception
	 * @时间：2017年6月6日下午6:14:19
	 */
	private void checkAmountChannelChantMerBillRecord(SettleBillRecord settleBillRecord,
			ClearChannelRecord clearChannelRecord, ClearMerchantRecord clearMerByTransNo,
			SettleChannelLog settleChannelLog) throws Exception {
		try {
			logger.info("账单明细，通道侧清算记录，商户侧清算记录，金额对比开始,交易订单号：{}", clearChannelRecord.getTransNo());
			SettleDifferRecord settleDifferRecord = new SettleDifferRecord(); // 差异记录
		
				// 商户侧、通道侧和账单明细金额对比
				if ((settleBillRecord.getSuccessAmount().compareTo(clearChannelRecord.getSuccessAmount()) == 0)
						&&(clearMerByTransNo.getRequestAmount().compareTo(clearChannelRecord.getSuccessAmount()) == 0)) {	
					
						balanceBill(settleBillRecord, clearMerByTransNo, clearChannelRecord, settleChannelLog);			
				} else {
					logger.info("通道侧和账单明细金额不一致,支付单号：{}", settleBillRecord.getPaymentId());
					// 通道侧和账单明细金额不一致
					clearMerByTransNo.setCheckFlg(BillingYCheckStatus.BCFQMTM.getValue());// (用户侧清算记录表)对账标识(清结算系统与上游金额不一致)
					clearChannelRecord.setCheckFlg(BillingYCheckStatus.BCFQMTM.getValue());// (通道侧清算记录表)对账标识(清结算系统与上游金额不一致)
					settleDifferRecord.setDifferType(BillingDifferType.BDTYPEJ.getValue());// 差异类型:金额不一致
					settleDifferRecord.setTransStatus(SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue()); // 支付状态
					insertSettleDifferRecordMethod(clearChannelRecord, clearMerByTransNo, settleBillRecord, settleDifferRecord);
					
					//统计对账差异总笔数、总金额
					countRecordNumAndAmount(settleChannelLog, settleBillRecord, "error");
				}			
			logger.info("支付单号为：{}的账单明细对账结束", settleBillRecord.getPaymentId());			
		} catch (Exception e) {
			logger.error("支付单号为：{}的账单明细对账失败", settleBillRecord.getPaymentId(), e);
			throw new Exception();
		}
	}
	
	/**
	 * 
	 * @方法说明：更新通道侧和商户侧的对账次数
	 * @author xuangang
	 * @param settleChannelLog
	 * @param clearChannelRecord
	 * @param clearMerByTransNo
	 * @throws ParseException 
	 * @时间：2017年6月7日下午2:47:14
	 */
	private void updateCheckNum(SettleBillRecord settleBillRecord, SettleChannelLog settleChannelLog, ClearChannelRecord clearChannelRecord, ClearMerchantRecord clearMerByTransNo) throws ParseException {
		
		if (clearMerByTransNo != null) {			
			clearMerByTransNo.setCheckNum(clearMerByTransNo.getCheckNum()==null?0: clearMerByTransNo.getCheckNum()+ 1);// (用户侧清算记录表)对账次数
			clearMerByTransNo.setCheckStatus(ClearingCheckStatus.CHECKSTATUSY.getValue());// 对账状态
			clearMerByTransNo.setCheckBath(settleChannelLog.getCheckBathNo()); // 对账批次号
			clearMerchantRecordDaoImpl.updateClearMerchantRecordByPrimaryKey(clearMerByTransNo);
			logger.info("商户侧清算数据对账完毕,交易订单号：{}",clearMerByTransNo.getTransNo());
		} 
		if (null != clearChannelRecord) {
			clearChannelRecord.setCheckNum(clearChannelRecord.getCheckNum()==null?0 :clearChannelRecord.getCheckNum()+ 1);// (通道侧清算记录表)对账次数
			clearChannelRecord.setCheckStatus(ClearingCheckStatus.CHECKSTATUSY.getValue()); // 对账状态
			clearChannelRecord.setCheckBath(settleChannelLog.getCheckBathNo());             // 对账批次号
			clearChannelRecord.setChannelTime(DateUtils.getStrDate(new Date(), "yyyy-MM-dd")); // 通道对账日期
			// 成本
			if (null != settleBillRecord.getFee()) {
				clearChannelRecord.setCostAmount(settleBillRecord.getFee());
			} else {
				clearChannelRecord.setCostAmount(new BigDecimal("0.0000"));
			}		
			clearChannelRecordDaoImpl.updateClearChannelRecordByPrimaryKey(clearChannelRecord);
			logger.info("通道侧清算数据对账完毕,交易订单号：{}", clearChannelRecord.getTransNo());
		}		
	}
	
	/**
	 * 
	 * @方法说明：统计入款、出款总笔数、总金额
	 * @author xuangang
	 * @param settleBillRecord
	 * @param clearMerchantRecord
	 * @param clearChannelRecord
	 * @param settleChannelLog
	 * @时间：2016年12月24日上午10:31:07
	 */
	private void balanceBill(SettleBillRecord settleBillRecord, ClearMerchantRecord clearMerchantRecord, ClearChannelRecord clearChannelRecord, SettleChannelLog settleChannelLog){

		// 平账
		clearMerchantRecord.setCheckFlg(BillingYCheckStatus.BCFQSTS.getValue());// (用户侧清算记录表)对账标识(平账)
		clearChannelRecord.setCheckFlg(BillingYCheckStatus.BCFQSTS.getValue());// (通道侧清算记录表)对账标识(平账)
		String transType = clearChannelRecord.getTransType();
		//判断交易类型是否为入款类
		if(TransTypeUtil.isIncome(transType)){
			
			logger.info("支付单号为：{}入款交易平账", settleBillRecord.getPaymentId());
			
			if (null != settleChannelLog.getInRecordNum()) {
				settleChannelLog.setInRecordNum(settleChannelLog.getInRecordNum() + Long.valueOf("1"));// 入款平账总笔数
			} else {
				settleChannelLog.setInRecordNum(Long.valueOf(1));// 入款平账总笔数
			}
			// 平账金额累加
			BigDecimal totalAmount = (settleBillRecord.getSuccessAmount());
			if (null != settleChannelLog.getInTotalAmount()) {
				settleChannelLog.setInTotalAmount(settleChannelLog.getInTotalAmount().add(totalAmount));// 入款平账总金额
			} else {
				settleChannelLog.setInTotalAmount(totalAmount);// 入款平账总金额
			}			
		}
		//出款类，包括退款，批付，提现、实名认证（新增实名认证交易类型）
		else if(TransTypeUtil.isOutcome(transType)){
			logger.info("支付单号为：{}，出款交易平账",  settleBillRecord.getPaymentId());
			if (null != settleChannelLog.getOutRecordNum()) {
				settleChannelLog.setOutRecordNum(settleChannelLog.getOutRecordNum() + Long.valueOf("1"));// 出款平账总笔数
			} else {
				settleChannelLog.setOutRecordNum(Long.valueOf(1));// 出款平账总笔数
			}
			// 平账金额累加
			BigDecimal totalAmount = (settleBillRecord.getSuccessAmount());
			if (null != settleChannelLog.getOutTotalAmount()) {
				settleChannelLog.setOutTotalAmount(settleChannelLog.getOutTotalAmount().add(totalAmount));// 出款平账总金额
			} else {
				settleChannelLog.setOutTotalAmount(totalAmount);// 出款平账总金额
			}	
		}			
	}
	/**
	 * 
	 * @方法说明：更新对账日志表为
	 * @author xuangang
	 * @param settleChannelLog
	 * @throws Exception
	 * @时间：2017年6月7日下午4:46:48
	 */
	private void updateSettleChannelLog(SettleChannelLog settleChannelLog) throws Exception {
		try {
			if(settleChannelLog == null)
				return;			
				settleChannelLog.setOperEndTime(new Date());    //对账结束时间
				settleChannelLog.setCheckStatus(BillingCheckStatus.CHECKSTATUSCE.getValue());  //对账状态，已完成
				settleChannelLogDaoImpl.updateSettleChannelLog(settleChannelLog);
				logger.info("更新对账日志表成功！对账批次号：{}", settleChannelLog.getCheckBathNo());			 
		} catch (Exception e) {
			logger.error("更新对账日志表异常，对账批次号：{}"	, settleChannelLog.getCheckBathNo(), e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：对账异常表入库
	 * @author xuangang
	 * @param settleBillRecord
	 * @时间：2017年7月28日上午10:58:38
	 */
	private void saveSettleDifferBillRecord(SettleBillRecord settleBillRecord){
		try{
			SettleDifferBillRecord settleDifferBillRecord = new SettleDifferBillRecord();
			settleDifferBillRecord.setChannelCode(settleBillRecord.getChannelCode());
			settleDifferBillRecord.setChannelType(settleBillRecord.getChannelType());
			settleDifferBillRecord.setCheckBathNo(settleBillRecord.getCheckBathNo());
			settleDifferBillRecord.setPaymentId(settleBillRecord.getPaymentId());
			settleDifferBillRecord.setSaveTime(DateUtils.getDate());
			settleDifferBillRecord.setSuccessAmount(settleBillRecord.getSuccessAmount());
			settleDifferBillRecord.setFee(settleBillRecord.getFee());
			settleDifferBillRecord.setChannleNo(settleBillRecord.getChannleNo());
			settleDifferBillRecord.setDifferType("CF");                           //未知差异重复入库
			settleDifferBillRecord.setFeeService(settleBillRecord.getFeeService());
			settleDifferBillRecord.setPromotionAmount(settleBillRecord.getPromotionAmount());
			settleDifferBillRecord.setRemark(settleBillRecord.getRemark());

			SettleDifferBillRecordDao.insert(settleDifferBillRecord);
		}catch(Exception e){
			logger.error("对账异常，未知差异入对账异常表失败：{}", settleBillRecord.getPaymentId(), e);
		}		
	}
}
