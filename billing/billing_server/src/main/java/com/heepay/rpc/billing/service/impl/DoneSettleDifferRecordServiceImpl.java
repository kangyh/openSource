package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.TransTypeUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.PaymentRecordStatus;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingDealOpinion;
import com.heepay.enums.billing.BillingDifferType;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.ErrorStatus;
import com.heepay.enums.billing.SettleDifferTransStatus;
import com.heepay.rpc.billing.model.DoneSettleDifferRecordModel;
import com.heepay.rpc.client.BillingClearAPIClient;
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.rpc.client.PayChannelCacheServiceClient;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;
import com.heepay.rpc.service.RpcService;

/**
 * *
 * 
 * 
 * 描 述：差异单处理service实现类、将ngp_job中的补撤单代码移植到billing_server
 *
 * 创 建 者： wangjie 创建时间： 2016年10月14日下午1:47:11 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "DoneSettleDifferRecordServiceImpl", processor = com.heepay.rpc.billing.service.DoneSettleDifferRecordService.Processor.class)
public class DoneSettleDifferRecordServiceImpl
		implements com.heepay.rpc.billing.service.DoneSettleDifferRecordService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	BillingClearAPIClient billingClearAPIClient;

	@Autowired
	SettleDifferRecordMapper settleDifferRecordDaoImpl;

	@Autowired
	ManagerServerClient managerServerClient;
	
	@Autowired
	PayChannelCacheServiceClient PayChannelCacheServiceClient;

	// 查询差异结果为未知的差异记录
	@Override
	public List<DoneSettleDifferRecordModel> getSettleDifferRecord(String differType) throws TException {
		logger.info("补撤单定时任务开始");
		// 查询差异结果为未知的且状态为未处理差异记录 待完善（终态未知暂时无法处理）
		List<SettleDifferRecord> list = settleDifferRecordDaoImpl
				.selectByDifferType(BillingDifferType.BDTYPEW.getValue());
		// 差异单处理
		//PayChannelCacheServiceClient.getBillFlag("billing_process_flag");
		this.process_new(list);
		/*if(Boolean.parseBoolean(PayChannelCacheServiceClient.getBillFlag("billing_process_flag"))) {
		}else {
			this.process(list);
		}*/
		logger.info("补撤单定时任务结束");
		return new ArrayList<DoneSettleDifferRecordModel>();
	}

	// 调用补单撤单借口后更新差异表数据(已没用)
	@Override
	public int updateSettleDifferRecor(DoneSettleDifferRecordModel doneSettleDifferRecordModel) throws TException {
		return 0;
	}

	// 抽取方法，从交易系统给差异表回填数据
	public SettleDifferRecord completeSettleDifferRecord (ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			SettleDifferRecord settleDifferRecord) throws Exception {

		settleDifferRecord.setProductCode(clearMerchantQueryRecordVO.getProductCode()); // 产品编码
		settleDifferRecord.setTransNo(clearMerchantQueryRecordVO.getTransNo()); // 交易单号
		settleDifferRecord.setMerchantId(Integer.parseInt(clearMerchantQueryRecordVO.getMerchantId())); // 商品编码
		settleDifferRecord.setRequestAmount(new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount())); // 定金
		settleDifferRecord.setFeeAmount(new BigDecimal(clearMerchantQueryRecordVO.getFeeAmount())); // 手续费
		settleDifferRecord.setTransType(clearMerchantQueryRecordVO.getTransType()); // 交易类型
		if("UNOPAY".equals(settleDifferRecord.getChannelCode()) && "QRCODE".equals(settleDifferRecord.getChannelType())){
			settleDifferRecord.setPaymentId(clearMerchantQueryRecordVO.getPaymentID());
		}
		// 根据坐外扣计算应结算金额（充值默认坐扣，出款类默认外扣，消费坐外扣）
		settleDifferRecord = this.getAmountPlanOnCostWay(clearMerchantQueryRecordVO, settleDifferRecord);
		/**
		 * 差异表添加新字段
		 */
		settleDifferRecord.setMerchantOrderNo(clearMerchantQueryRecordVO.getMerchantOrderNo()); // 商户交易订单号
		/// 支付发起时间
		if (StringUtil.isBlank(clearMerchantQueryRecordVO.getPayTime())) {
			logger.info("获取交易系统支付发起时间[CreateTime]为null：{}", clearMerchantQueryRecordVO.getPayTime());
			settleDifferRecord.setPayTime(null);
		} else {
			settleDifferRecord
					.setPayTime(DateUtils.getStrDate(clearMerchantQueryRecordVO.getPayTime(), "yyyy-MM-dd HH:mm:ss")); // 交易发起时间
		}
		// 成功支付时间
		if (StringUtil.isBlank(clearMerchantQueryRecordVO.getSuccessTime())) {
			logger.info("获取交易系统成功支付时间[CreateTime]为null：{}", clearMerchantQueryRecordVO.getSuccessTime());
			settleDifferRecord.setSuccessTime(null);
		} else {
			settleDifferRecord.setSuccessTime(
					DateUtils.getStrDate(clearMerchantQueryRecordVO.getSuccessTime(), "yyyy-MM-dd HH:mm:ss")); // 交易发起时间
		}
		// 交易发起时间
		if (StringUtil.isBlank(clearMerchantQueryRecordVO.getCreateTime())) {
			logger.info("获取交易系统交易发起时间[CreateTime]为null：{}", clearMerchantQueryRecordVO.getCreateTime());
			settleDifferRecord.setBusiTime(null); // 交易发起时间
		} else {
			settleDifferRecord.setBusiTime(
					DateUtils.getStrDate(clearMerchantQueryRecordVO.getCreateTime(), "yyyy-MM-dd HH:mm:ss")); // 交易发起时间
		}
		// settleDifferRecord.setRemark(); // 备注
		settleDifferRecord.setIsZip("unzip"); // 归是否归档unzip(默认)
		// settleDifferRecord.setAgentName(""); // 代理名称
		// settleDifferRecord.setAgentCode(); //代理代码
		settleDifferRecord.setFeeWay(clearMerchantQueryRecordVO.getFeeWay()); // 手续费扣除方式'
		settleDifferRecord.setBankcardType(clearMerchantQueryRecordVO.getBankcardType()); // 银行卡类型
		settleDifferRecord.setMerchantName(clearMerchantQueryRecordVO.getMerchantCompany()); // 商户名称

		settleDifferRecord.setBankSeq(clearMerchantQueryRecordVO.getBankSerialNo());// `bank_seq`
																					// '银行流水',
		settleDifferRecord.setBankName(clearMerchantQueryRecordVO.getBankName()); // `bank_name`
																					// '银行名称',
		settleDifferRecord.setBankCode(clearMerchantQueryRecordVO.getBankId());// `bank_code`
																				// '银行编码',
		// settleDifferRecord.setChannelProvider();// `channel_provider`
		// '通道提供者',
		settleDifferRecord.setPayType(clearMerchantQueryRecordVO.getPayType()); // `pay_type`
																				// '支付类型',
		// settleDifferRecord.setAccountNo();// `account_no` '账户编码',

		return settleDifferRecord;
	}

	/**
	 * 
	 * @方法说明：根据坐外扣计算应结算金额（充值默认坐扣，出款类默认外扣，消费坐外扣）
	 * @author chenyanming
	 * @param clearMerchantQueryRecordVO
	 * @param settleDifferRecord
	 * @return
	 * @时间：2017年6月6日下午6:04:58
	 */
	public SettleDifferRecord getAmountPlanOnCostWay(ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			SettleDifferRecord settleDifferRecord) {
		if (ChargeDeductType.INTERNAL_DEDUCT.getValue().equals(clearMerchantQueryRecordVO.getFeeWay())) {// 坐扣
			if (StringUtil.isBlank(clearMerchantQueryRecordVO.getRequestAmount())) {
				settleDifferRecord.setSettleAmountPlan(null); // 应结算金额=订单金额减去手续费
			} else {
				if (StringUtil.isBlank(clearMerchantQueryRecordVO.getFeeAmount())) {
					settleDifferRecord
							.setSettleAmountPlan(new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount()));
				} else {
					settleDifferRecord.setSettleAmountPlan(new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount())
							.subtract(new BigDecimal(clearMerchantQueryRecordVO.getFeeAmount())));
				}

			}
		} else {// 外扣
			settleDifferRecord.setSettleAmountPlan(new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount()));
		}
		return settleDifferRecord;
	}

	public void process_new(List<SettleDifferRecord> list) throws TException {

		// 便历差异类型为未知且处理状态为未处理的数据
		if (null != list && list.size() > 0) {
			for (SettleDifferRecord settleDifferRecord : list) {
				try {
					ClearMerchantQueryRecordModel clearMerchantQueryRecordVO = new ClearMerchantQueryRecordModel();
					if("UNOPAY".equals(settleDifferRecord.getChannelCode()) && "QRCODE".equals(settleDifferRecord.getChannelType())) {
						clearMerchantQueryRecordVO = billingClearAPIClient
								.queryTransByUnionpaySerialNo(settleDifferRecord.getChannleNo());
					}else {
						clearMerchantQueryRecordVO = billingClearAPIClient
								.merchantQueryTransByPaymentId(settleDifferRecord.getPaymentId());
					}

					// 已处理 最终的未知
					if (clearMerchantQueryRecordVO == null) {
						settleDifferRecord.setDifferType(BillingDifferType.BDTYPEW.getValue()); // 差异类型
						settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSY.getValue()); // 处理状态
						settleDifferRecord.setHandleMessage(ErrorStatus.BDTYPEW.getValue()); // 处理意见
						settleDifferRecord.setIsBill(ClearingCheckStatus.CHECKSTATUSN.getValue()); // 是否分润
						settleDifferRecord.setOperationDate(new Date()); // 操作日期
						settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
						logger.info("交易系统没找到数据,支付单号为" + settleDifferRecord.getPaymentId());
					} else {
						// 非空值替换
						clearMerchantQueryRecordVO.setFeeAmount(clearMerchantQueryRecordVO.getFeeAmount() == null ? "0.0000"
								: clearMerchantQueryRecordVO.getFeeAmount());
						clearMerchantQueryRecordVO.setRequestAmount(clearMerchantQueryRecordVO.getRequestAmount() == null
								? "0.0000" : clearMerchantQueryRecordVO.getRequestAmount());

						logger.info("支付单号=" + settleDifferRecord.getPaymentId() + "从交易系统获取的数据交易类型为"
								+ clearMerchantQueryRecordVO.getTransType() + ",从交易系统获取的数据交易状态为"
								+ clearMerchantQueryRecordVO.getTransStatus());

						// 差异表回填数据，更改状态，处理结果
						try {
							settleDifferRecord = completeSettleDifferRecord(clearMerchantQueryRecordVO, settleDifferRecord);
						} catch (Exception e) {
							logger.info("更改差异表数据异常,支付单号为{}", settleDifferRecord.getPaymentId());
							continue;
						}

						//定义长短款属性,出款类为短款，其他为长款
						String differType = this.getbdtype(clearMerchantQueryRecordVO);
						if(PaymentRecordStatus.SUCCESS.getValue().equals(clearMerchantQueryRecordVO.getTransStatus())){
							/*交易状态成功的情况
							  成功的情况下区分交易类型：充值、消费、出款类(提现、转账、退款、打款认证、存管提现)
							 */
							// 更新差异表，（差异类型:长款，处理意见:补单）
							// 交易系统和通道金额一致
							if(compareToAmount(clearMerchantQueryRecordVO.getSuccessAmount(),settleDifferRecord.getSuccessAmount())){
								// 出款类(提现、转账、退款、打款认证、存管提现):短款，补单；  其他：长款，补单
								this.updateSettleDifferRecorForSuccess(settleDifferRecord, differType);
							} else {
								// 交易系统和通道金额不一致 差异表回填数据，更改状态，处理结果
								this.updateForDiferAmount(settleDifferRecord, clearMerchantQueryRecordVO.getTransStatus());
							}
						}else{
							//交易状态非成功的情况(待支付、处理中、失败、超时)
							//更新差异表，（差异类型:长款，处理意见:补单）
							if(compareToAmount(clearMerchantQueryRecordVO.getRequestAmount(),settleDifferRecord.getSuccessAmount())){
							    settleDifferRecord = updateByTranStatu(settleDifferRecord, differType, BillingBillStatus.BBSTATUSN.getValue(),
							    		clearMerchantQueryRecordVO.getTransStatus());
						    	this.cancelOrSuppleBill(clearMerchantQueryRecordVO, settleDifferRecord);

							} else {
								this.updateForDiferAmount(settleDifferRecord, clearMerchantQueryRecordVO.getTransStatus());
							}
						}

					}
					//}
				} catch (Exception e) {
					logger.error("补撤单异常,支付单号为{}", settleDifferRecord.getPaymentId(),e);
				}
			}
		}
	}

	/**
	 * 
	 * @方法说明：撤单操作或补单操作
	 * @author chenyanming
	 * @param clearMerchantQueryRecordVO
	 * @param settleDifferRecord
	 * @时间：2017年6月6日下午2:46:58
	 */
	private void cancelOrSuppleBill(ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			SettleDifferRecord settleDifferRecord) {
		// 更改差异结果
		settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSD.getValue());
		settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
		AsyncMsgModel merchantMsg = null;
		String handleMessage = "CL";
		int flag = 0;
		/*if((TransType.PAY.getValue().equals(clearMerchantQueryRecordVO.getTransType()) && 
				!(("UNOPAY".equals(settleDifferRecord.getChannelCode()) && "QRCODE".equals(settleDifferRecord.getChannelType()))
						 || "CP54".equals(clearMerchantQueryRecordVO.getProductCode())))
				|| TransType.DEPOSIT_PAY.getValue().equals(clearMerchantQueryRecordVO.getTransType())) {
			// 交易系统失败的情况下，消费和存管充值为撤单；  其他为补单
			merchantMsg = billingClearAPIClient.cancelMerchantTrans(settleDifferRecord.getPaymentId(),
					settleDifferRecord.getSettleAmountPlan().toString(), settleDifferRecord.getFeeAmount() == null
							? "0.0000" : settleDifferRecord.getFeeAmount().toString());
			logger.info("调用交易系统撤单接口传的参数是：paymentId=" + settleDifferRecord.getPaymentId() + ",FeeAmount="
					+ settleDifferRecord.getFeeAmount() + ",SettleAmountPlan="
					+ settleDifferRecord.getSettleAmountPlan());
			handleMessage = BillingDealOpinion.BILLINGDEALCD.getValue();
			flag = 1;
		}else {
			merchantMsg = billingClearAPIClient.suppleMerchantTrans(settleDifferRecord.getPaymentId(),
					settleDifferRecord.getSettleAmountPlan().toString(), settleDifferRecord.getFeeAmount() == null
							? "0.0000" : settleDifferRecord.getFeeAmount().toString());
			logger.info("调用交易系统补单接口传的参数是：paymentId=" + settleDifferRecord.getPaymentId() + ",FeeAmount="
					+ settleDifferRecord.getFeeAmount() + ",SettleAmountPlan="
					+ settleDifferRecord.getSettleAmountPlan());
			handleMessage = BillingDealOpinion.BILLINGDEALBD.getValue();
			flag = 2;
		}*/
		merchantMsg = billingClearAPIClient.suppleMerchantTrans(settleDifferRecord.getPaymentId(),
				settleDifferRecord.getSettleAmountPlan().toString(), settleDifferRecord.getFeeAmount() == null
						? "0.0000" : settleDifferRecord.getFeeAmount().toString());
		logger.info("调用交易系统补单接口传的参数是：paymentId=" + settleDifferRecord.getPaymentId() + ",FeeAmount="
				+ settleDifferRecord.getFeeAmount() + ",SettleAmountPlan="
				+ settleDifferRecord.getSettleAmountPlan());
		handleMessage = BillingDealOpinion.BILLINGDEALBD.getValue();
		flag = 2;
		if (null != merchantMsg && InterfaceStatus.SUCCESS.getValue() == merchantMsg.status) {
			settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
			settleDifferRecord.setHandleMessage(handleMessage);
			settleDifferRecord.setOperationDate(new Date());
			settleDifferRecord.setRemark(merchantMsg.getMsg());  //补单结果
			settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
			logger.info("支付单号为" + settleDifferRecord.getPaymentId() + "自动"+ (flag == 2?"补单":"撤单") + "成功,商户侧返回值=" + merchantMsg.status);
		} else if (null != merchantMsg) {
			settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
			settleDifferRecord.setRemark(merchantMsg.getMsg());  //补单结果
			settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
			logger.info("支付单号为" + settleDifferRecord.getPaymentId() + "自动"+ (flag == 2?"补单":"撤单") +"失败,商户侧返回值=" + merchantMsg.status);
		} else {
			// 处理失败改为未处理
			settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
			settleDifferRecord.setDifferType(BillingDifferType.BDTYPEW.getValue());
			settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
			logger.info("支付单号为" + settleDifferRecord.getPaymentId() + "自动"+ (flag == 2?"补单":"撤单") +"失败,商户侧返回值=" + merchantMsg);
		}
	}

	/**
	 * 
	 * @方法说明：交易系统订单状态为成功，更新差异表
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @param bdtypeCD
	 * @时间：2017年6月6日下午2:03:13
	 */
	private void updateSettleDifferRecorForSuccess(SettleDifferRecord settleDifferRecord, String bdtypeCD) {
		settleDifferRecord.setDifferType(bdtypeCD); // 差异类型
		settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSY.getValue()); // 处理结果
		settleDifferRecord.setHandleMessage(BillingDealOpinion.BILLINGDEALBD.getValue()); // 处理意见
		settleDifferRecord.setOperationDate(new Date()); // 操作时间
		settleDifferRecord.setTransStatus(SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue()); // 交易状态
		settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
	}

	/**
	 * 
	 * @方法说明：补单操作时从交易查询的金额和账单金额不一致
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @param transStatus 
	 * @时间：2017年6月6日上午11:14:08
	 */
	private void updateForDiferAmount(SettleDifferRecord settleDifferRecord, String transStatus) {
		settleDifferRecord.setDifferType(BillingDifferType.BDTYPEJ.getValue());
		settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
		settleDifferRecord.setTransStatus(transStatus);
		settleDifferRecord.setOperationDate(new Date()); // 操作时间
		settleDifferRecordDaoImpl.updateSettleDifferRecord(settleDifferRecord);
		logger.info(settleDifferRecord.getPaymentId() + "调用交易系统补单金额不一致");
	}
	
	/**
	 * 抽取方法 交易状态不是成功的
	 * 
	 * @param sdr
	 * @param differType
	 * @param handleResult
	 * @param transStatus
	 * @return
	 */
	public SettleDifferRecord updateByTranStatu(SettleDifferRecord sdr, String differType, String handleResult,
			String transStatus) throws TException {
		sdr.setDifferType(differType); // 差异类型
		sdr.setHandleResult(handleResult); // 处理结果
		sdr.setTransStatus(transStatus); // 交易状态
		settleDifferRecordDaoImpl.updateSettleDifferRecord(sdr);
		return sdr;

	}

	/**
	 * 根据出款类、入款类获取长短款标识
	 * @param clearMerchantQueryRecordVO
	 * @return
	 */
	private String getbdtype(ClearMerchantQueryRecordModel clearMerchantQueryRecordVO){
		String bdtypeCD = "";
		//出款类
		if (TransTypeUtil.isOutcome(clearMerchantQueryRecordVO.getTransType())) {
			bdtypeCD = BillingDifferType.BDTYPED.getValue();
		}else{
			bdtypeCD = BillingDifferType.BDTYPEC.getValue();
		}

		return bdtypeCD;
	}

	/**
	 * 判断清算金额与结算金额是否一致
	 * @param amountA
	 * @param amountB
	 * @return
	 */
	private Boolean compareToAmount(String amountA,BigDecimal amountB){

		if(null!=amountA && null!=amountB && new BigDecimal(amountA).compareTo(amountB)==0){
			return true;
		}else{
			logger.info("通道金额：" + amountA + ";订单金额" + amountB);
			return false;
		}
	}

}