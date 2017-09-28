package com.heepay.billing.job;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.BillingClearAPIClient;
import com.heepay.billing.client.DoneSettleDifferRecordClient;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.PaymentRecordStatus;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingDealOpinion;
import com.heepay.enums.billing.BillingDifferType;
import com.heepay.enums.billing.ErrorStatus;
import com.heepay.enums.billing.SettleDifferTransStatus;
import com.heepay.rpc.billing.model.DoneSettleDifferRecordModel;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;

/**
 * *
 * 
 * 
 * 描 述：查找差异类型为'中态'未知的改为长款，短款和'终态未知'，回填差异表数据，调用补单，撤单接口
 * 
 * 创 建 者： wangjie 创建时间： 2016年10月13日下午2:57:20 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间: 审核描述：
 *
 */
@Service
public class DoneSettleDifferRecordJob extends AbstractSimpleElasticJob {

	@Autowired
	DoneSettleDifferRecordClient doneSettleDifferRecordClient;

	@Autowired
	BillingClearAPIClient billingClearAPIClient;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {

		// 查询差异结果为未知的差异记录
		List<DoneSettleDifferRecordModel> list = doneSettleDifferRecordClient.selectSettleDifferRecord();

		// 便历差异类型为未知且处理状态为未处理的数据
		if (null != list && list.size() > 0) {
			for (DoneSettleDifferRecordModel doneSettleDifferRecordModel : list) {
				// 用户侧 调用交易系统查询数据回填差异表
				ClearMerchantQueryRecordModel clearMerchantQueryRecordVO = billingClearAPIClient
						.merchantQueryTransByPaymentId(doneSettleDifferRecordModel.getPaymentId());
				logger.info("调用交易系统返回封装的用户侧对象clearMerchantQueryRecordVO" + clearMerchantQueryRecordVO);

				// 已处理 最终的未知
				if (clearMerchantQueryRecordVO == null) {
					DoneSettleDifferRecordModel sdr = new DoneSettleDifferRecordModel();
					sdr.setDifferType(BillingDifferType.BDTYPEW.getValue()); // 差异类型
					sdr.setPaymentId(doneSettleDifferRecordModel.getPaymentId()); // 支付单号
					sdr.setHandleResult(BillingBillStatus.BBSTATUSY.getValue()); // 处理状态
					sdr.setHandleMessage(ErrorStatus.BDTYPEW.getValue()); //处理意见
					doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
					logger.info("交易系统没找到数据,支付单号为" + clearMerchantQueryRecordVO.getPaymentID());
				} else {
					// 非空值替换
					clearMerchantQueryRecordVO.setFeeAmount(clearMerchantQueryRecordVO.getFeeAmount() == null ? "0.0000"
							: clearMerchantQueryRecordVO.getFeeAmount());
					clearMerchantQueryRecordVO.setRequestAmount(clearMerchantQueryRecordVO.getRequestAmount() == null
							? "0.0000" : clearMerchantQueryRecordVO.getRequestAmount());

					logger.info("支付单号=" + doneSettleDifferRecordModel.getPaymentId() + "从交易系统获取的数据交易类型为"
							+ clearMerchantQueryRecordVO.getTransType() + ",从交易系统获取的数据交易状态为"
							+ clearMerchantQueryRecordVO.getTransStatus());

					// 充值
					if (TransType.CHARGE.getValue().equals(clearMerchantQueryRecordVO.getTransType())) {
						if (PaymentRecordStatus.PREPAY.getValue().equals(clearMerchantQueryRecordVO.getTransStatus())) {// 待支付
							// 跟新差异表，（差异类型:长款，处理意见:补单）

							// 差异表回填数据，更改状态，处理结果
							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_PRE.getValue());

							// 调用补单接口
							noSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.PAYING.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 处理中
							// 跟新差异表，（差异类型:长款，处理意见:补单）

							// 差异表回填数据，更改状态，处理结果
							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_ING.getValue());

							// 调用补单接口
							noSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.FAILED.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 失败
							// 跟新差异表，（差异类型:长款，处理意见:补单）

							// 差异表回填数据，更改状态，处理结果
							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_FAI.getValue());
							// 调用补单接口
							noSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.TIMEOUT.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 超时
							// 跟新差异表，（差异类型:长款，处理意见:补单）

							// 差异表回填数据，更改状态，处理结果
							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_TIM.getValue());

							// 调用补单接口
							noSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.SUCCESS.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 成功
							// 跟新差异表，（差异类型:长款，处理意见:补单）
							// 交易系统和通道金额一致
							if (null != clearMerchantQueryRecordVO.getRequestAmount()
									&& null != doneSettleDifferRecordModel.getSuccessAmount()) {
								if ((new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount()).compareTo(
										new BigDecimal(doneSettleDifferRecordModel.getSuccessAmount()))) == 0) {

									// 差异表回填数据，更改状态，处理结果
									DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
											doneSettleDifferRecordModel);

									sdr = updateByTranStatuSuccess(sdr, BillingDifferType.BDTYPEC.getValue(),
											BillingBillStatus.BBSTATUSY.getValue(),
											BillingDealOpinion.BILLINGDEALBD.getValue(),
											SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue());
									// 更改差异结果
									// 操作时间
									doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
								} else {

									// 交易系统和通道金额不一致 差异表回填数据，更改状态，处理结果
									DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
											doneSettleDifferRecordModel);

									// 差异类型 处理结果 交易状态
									sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEJ.getValue(),
											BillingBillStatus.BBSTATUSN.getValue(),
											SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue());

									logger.info(doneSettleDifferRecordModel.getPaymentId() + "调用交易系统补单金额不一致");
									doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

								}
							} else {
								logger.info("通道金额：" + clearMerchantQueryRecordVO.getRequestAmount() + ";订单金额"
										+ doneSettleDifferRecordModel.getSuccessAmount());
							}

						}

						// 提现,转账，退款,打款认证
					} else if ((TransType.WITHDRAW.getValue().equals(clearMerchantQueryRecordVO.getTransType()))
							|| (TransType.REFUND.getValue().equals(clearMerchantQueryRecordVO.getTransType()))
							|| (TransType.BATCHPAY.getValue().equals(clearMerchantQueryRecordVO.getTransType()))
							|| (TransType.PLAY_MONEY.getValue().equals(clearMerchantQueryRecordVO.getTransType()))) {
						// 短款
						if (PaymentRecordStatus.PREPAY.getValue().equals(clearMerchantQueryRecordVO.getTransStatus())) {// 待支付
							// 跟新差异表，（差异类型:短款，处理意见:补单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPED.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_PRE.getValue());

							// 调用补单接口
							noWSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.PAYING.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 处理中
							// 跟新差异表，（差异类型:短款，处理意见:补单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPED.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_ING.getValue());
							// 调用补单接口
							noWSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.FAILED.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 失败
							// 跟新差异表，（差异类型:短款，处理意见:补单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPED.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_FAI.getValue());

							// 调用补单接口
							noWSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.TIMEOUT.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 超时
							// 跟新差异表，（差异类型:短款，处理意见:补单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPED.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_TIM.getValue());
							// 调用补单接口
							noWSuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.SUCCESS.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 成功
							// 跟新差异表，（差异类型:短款，处理意见:补单）
							// 金额一致
							if (null != clearMerchantQueryRecordVO.getRequestAmount()
									&& null != doneSettleDifferRecordModel.getSuccessAmount()) {
								if ((new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount()).compareTo(
										new BigDecimal(doneSettleDifferRecordModel.getSuccessAmount()))) == 0) {

									DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
											doneSettleDifferRecordModel);

									sdr = updateByTranStatuSuccess(sdr, BillingDifferType.BDTYPED.getValue(),
											BillingBillStatus.BBSTATUSY.getValue(),
											BillingDealOpinion.BILLINGDEALBD.getValue(),
											SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue());

									doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

								} else {
									// 金额不一致

									DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
											doneSettleDifferRecordModel);
									sdr.setDifferType(BillingDifferType.BDTYPEJ.getValue());
									sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
									sdr.setTransStatus(SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue());
									doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
									logger.info(doneSettleDifferRecordModel.getPaymentId() + "调用交易系统补单处理金额不一致");
								}
							} else {
								logger.info("通道金额：" + clearMerchantQueryRecordVO.getRequestAmount() + ";订单金额"
										+ doneSettleDifferRecordModel.getSuccessAmount());
							}

						}
						// 消费
					} else if (TransType.PAY.getValue().equals(clearMerchantQueryRecordVO.getTransType())) {
						// 长款
						if (PaymentRecordStatus.PREPAY.getValue().equals(clearMerchantQueryRecordVO.getTransStatus())) {// 待支付
							// 跟新差异表，（差异类型:长款，处理意见:撤单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_PRE.getValue());

							// 调用撤单接口
							noPaySuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.PAYING.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 处理中
							// 跟新差异表，（差异类型:长款，处理意见:撤单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_ING.getValue());

							// 调用撤单接口
							noPaySuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.FAILED.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 失败
							// 跟新差异表，（差异类型:长款，处理意见:撤单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);

							sdr = updateByTranStatu(sdr, BillingDifferType.BDTYPEC.getValue(),
									BillingBillStatus.BBSTATUSN.getValue(),
									SettleDifferTransStatus.PAYMENTSTATUS_FAI.getValue());
							// 调用撤单接口
							noPaySuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.TIMEOUT.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 超时
							// 跟新差异表，（差异类型:长款，处理意见:撤单）

							DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
									doneSettleDifferRecordModel);
							sdr.setDifferType(BillingDifferType.BDTYPEC.getValue());
							sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
							sdr.setTransStatus(SettleDifferTransStatus.PAYMENTSTATUS_TIM.getValue());
							doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

							// 调用撤单接口
							noPaySuccessSetDifferRecord(clearMerchantQueryRecordVO, doneSettleDifferRecordModel, sdr);

						} else if (PaymentRecordStatus.SUCCESS.getValue()
								.equals(clearMerchantQueryRecordVO.getTransStatus())) {// 成功
							// 跟新差异表，（差异类型:长款，处理意见:撤单）
							// 金额一致
							if (null != clearMerchantQueryRecordVO.getRequestAmount()
									&& null != doneSettleDifferRecordModel.getSuccessAmount()) {
								if ((new BigDecimal(clearMerchantQueryRecordVO.getRequestAmount()).compareTo(
										new BigDecimal(doneSettleDifferRecordModel.getSuccessAmount()))) == 0) {

									DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
											doneSettleDifferRecordModel);

									sdr = updateByTranStatuSuccess(sdr, BillingDifferType.BDTYPEC.getValue(),
											BillingBillStatus.BBSTATUSY.getValue(),
											BillingDealOpinion.BILLINGDEALCD.getValue(),
											SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue());
									logger.info(doneSettleDifferRecordModel.getPaymentId() + "交易系统处理成功，金额一致");

									doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
								} else {
									// 金额不一致

									DoneSettleDifferRecordModel sdr = setDifferType(clearMerchantQueryRecordVO,
											doneSettleDifferRecordModel);
									sdr.setDifferType(BillingDifferType.BDTYPEJ.getValue());
									sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
									sdr.setTransStatus(SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue());
									logger.info(doneSettleDifferRecordModel.getPaymentId() + "交易系统成功，但金额不一致");
									doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
								}

							} else {
								logger.info("通道金额：" + clearMerchantQueryRecordVO.getRequestAmount() + ";订单金额"
										+ doneSettleDifferRecordModel.getSuccessAmount());
							}
						}

					}

				}

			}
		}

	}

	// 抽取方法，从交易系统给差异表回填数据
	public DoneSettleDifferRecordModel setDifferType(ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			DoneSettleDifferRecordModel doneSettleDifferRecordModel) {

		DoneSettleDifferRecordModel sdr = new DoneSettleDifferRecordModel();

		// sdr.setDifferType(BillingDifferType.BDTYPEC.getValue()); // 差异类型
		sdr.setProductCode(clearMerchantQueryRecordVO.getProductCode()); // 产品编码
		sdr.setTransNo(clearMerchantQueryRecordVO.getTransNo()); // 交易单号
		sdr.setPaymentId(doneSettleDifferRecordModel.getPaymentId()); // 支付单号
		sdr.setMerchantId(Integer.parseInt(clearMerchantQueryRecordVO.getMerchantId())); // 商品编码
		sdr.setFeeAmount((clearMerchantQueryRecordVO.getFeeAmount())); // 手续费
		// sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue()); // 处理结果
		sdr.setTransType(clearMerchantQueryRecordVO.getTransType()); // 交易类型
		sdr.setRequestAmountAmount((clearMerchantQueryRecordVO.getRequestAmount())); // 定金
		sdr.setCostAmount(doneSettleDifferRecordModel.getCostAmount()); // 成本

		if (!StringUtils.isNotBlank(clearMerchantQueryRecordVO.getSuccessAmount())) {
			sdr.setSettleAmountPlan(null); // 应结算金额=成功金额减去手续费
		} else {
			if (!StringUtils.isNotBlank(clearMerchantQueryRecordVO.getFeeAmount())) {
				sdr.setSettleAmountPlan(clearMerchantQueryRecordVO.getSuccessAmount());
			} else {
				sdr.setSettleAmountPlan(new BigDecimal(clearMerchantQueryRecordVO.getSuccessAmount())
						.subtract(new BigDecimal(clearMerchantQueryRecordVO.getFeeAmount())).toString());
			}

		}

		return sdr;
	}

	// 充值，交易系统返回失败时调用补单接口
	public DoneSettleDifferRecordModel noSuccessSetDifferRecord(
			ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			DoneSettleDifferRecordModel doneSettleDifferRecordModel, DoneSettleDifferRecordModel sdr) {

		// 开始调用交易系统接口，防止重复调用，把处理结果改为处理中
		sdr.setHandleResult(BillingBillStatus.BBSTATUSD.getValue());
		doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

		AsyncMsgModel channelMsg = null;
		AsyncMsgModel merchantMsg = null;
		try {
			// 调用通道补单接口

			channelMsg = billingClearAPIClient.suppleChannelTrans(doneSettleDifferRecordModel.getPaymentId(),
					sdr.getRequestAmountAmount(), sdr.getCostAmount());

			// 调用用户侧补单接口

			merchantMsg = billingClearAPIClient.suppleMerchantTrans(doneSettleDifferRecordModel.getPaymentId(),
					sdr.getSettleAmountPlan(), sdr.getFeeAmount());
		} catch (Exception e) {

			logger.error("充值时调用交易系统补单接口异常{}", e);
			logger.info("调用交易系统通道补单传的参数是：paymentId=" + doneSettleDifferRecordModel.getPaymentId()
					+ ",RequestAmountAmount=" + sdr.getRequestAmountAmount() + ",CostAmount=" + sdr.getCostAmount());
			logger.info("调用交易系统商户补单传的参数是：paymentId=" + doneSettleDifferRecordModel.getPaymentId() + ",FeeAmount="
					+ sdr.getFeeAmount() + ",SettleAmountPlan=" + sdr.getSettleAmountPlan());
		}

		// 处理成功
		if (null != channelMsg && InterfaceStatus.SUCCESS.getValue() == channelMsg.status && null != merchantMsg
				&& InterfaceStatus.SUCCESS.getValue() == merchantMsg.status) {
			sdr.setHandleResult(BillingBillStatus.BBSTATUSY.getValue()); // 处理结果
			sdr.setHandleMessage(BillingDealOpinion.BILLINGDEALBD.getValue()); // 处理意见
			sdr.setOperationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 操作日期

			logger.info(doneSettleDifferRecordModel.getPaymentId() + "补单成功 ," + "通道侧返回值=" + channelMsg.status
					+ ",商户侧返回值=" + merchantMsg.status);
			doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

		} else { // 失败
			sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue()); // 处理失败改为未处理
			logger.info(doneSettleDifferRecordModel.getPaymentId() + "补单失败 ," + "通道侧返回值=" + channelMsg + ",商户侧返回值="
					+ merchantMsg);
			doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

		}

		return sdr;
	}

	// 提现，转账，退款 交易系统返回失败时调用补单接口
	public DoneSettleDifferRecordModel noWSuccessSetDifferRecord(
			ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			DoneSettleDifferRecordModel doneSettleDifferRecordModel, DoneSettleDifferRecordModel sdr) {
		// 更改差异结果
		sdr.setHandleResult(BillingBillStatus.BBSTATUSD.getValue());
		doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

		AsyncMsgModel channelMsg = null;
		AsyncMsgModel merchantMsg = null;
		try {
			channelMsg = billingClearAPIClient.suppleChannelTrans(doneSettleDifferRecordModel.getPaymentId(),
					sdr.getRequestAmountAmount(), sdr.getCostAmount());

			merchantMsg = billingClearAPIClient.suppleMerchantTrans(doneSettleDifferRecordModel.getPaymentId(),
					sdr.getSettleAmountPlan(), sdr.getFeeAmount());
		} catch (Exception e) {

			logger.error("交易类型为" + doneSettleDifferRecordModel.getTransType() + "时调用交易系统补单接口异常{}", e);
			logger.info("调用交易系统传的参数是：paymentId=" + doneSettleDifferRecordModel.getPaymentId() + ",RequestAmountAmount="
					+ sdr.getRequestAmountAmount() + ",CostAmount=" + sdr.getCostAmount());
			logger.info("调用交易系统传的参数是：paymentId=" + doneSettleDifferRecordModel.getPaymentId() + ",FeeAmount="
					+ sdr.getFeeAmount() + ",SettleAmountPlan=" + sdr.getSettleAmountPlan());
		}

		if (null != channelMsg && InterfaceStatus.SUCCESS.getValue() == channelMsg.status && null != merchantMsg
				&& InterfaceStatus.SUCCESS.getValue() == merchantMsg.status) {
			sdr.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
			sdr.setHandleMessage(BillingDealOpinion.BILLINGDEALBD.getValue());
			sdr.setOperationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

			logger.info(doneSettleDifferRecordModel.getPaymentId() + "通道侧返回值=" + channelMsg.status + ",商户侧返回值="
					+ merchantMsg.status);
			doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
		} else {
			sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
			// 处理失败改为未处理
			logger.info(doneSettleDifferRecordModel.getPaymentId() + "通道侧返回值=" + channelMsg + ",商户侧返回值=" + merchantMsg);
			doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
		}
		return sdr;
	}

	// 消费，交易系统返回失败时调用撤单接口
	public DoneSettleDifferRecordModel noPaySuccessSetDifferRecord(
			ClearMerchantQueryRecordModel clearMerchantQueryRecordVO,
			DoneSettleDifferRecordModel doneSettleDifferRecordModel, DoneSettleDifferRecordModel sdr) {
		// 更改差异结果
		sdr.setHandleResult(BillingBillStatus.BBSTATUSD.getValue());
		doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);

		AsyncMsgModel channelMsg = null;
		AsyncMsgModel merchantMsg = null;
		try {

			channelMsg = billingClearAPIClient.cancelChannelTrans(doneSettleDifferRecordModel.getPaymentId(),
					sdr.getRequestAmountAmount(), sdr.getCostAmount());

			merchantMsg = billingClearAPIClient.cancelMerchantTrans(doneSettleDifferRecordModel.getPaymentId(),
					sdr.getSettleAmountPlan(), sdr.getFeeAmount());
		} catch (Exception e) {
			logger.error("交易类型为消费时调用交易系统撤单接口异常{}", e);
			logger.info("调用交易系统传的参数是：paymentId=" + doneSettleDifferRecordModel.getPaymentId() + ",RequestAmountAmount="
					+ sdr.getRequestAmountAmount() + ",CostAmount=" + sdr.getCostAmount());
			logger.info("调用交易系统传的参数是：paymentId=" + doneSettleDifferRecordModel.getPaymentId() + ",FeeAmount="
					+ sdr.getFeeAmount() + ",SettleAmountPlan=" + sdr.getSettleAmountPlan());
		}

		if (null != channelMsg && InterfaceStatus.SUCCESS.getValue() == channelMsg.status && null != merchantMsg
				&& InterfaceStatus.SUCCESS.getValue() == merchantMsg.status) {
			sdr.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
			sdr.setHandleMessage(BillingDealOpinion.BILLINGDEALCD.getValue());
			sdr.setOperationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

			logger.info(doneSettleDifferRecordModel.getPaymentId() + "通道侧返回值=" + channelMsg.status + ",商户侧返回值="
					+ merchantMsg.status);
			doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
		} else {
			sdr.setHandleResult(BillingBillStatus.BBSTATUSN.getValue());
			// 处理失败改为未处理
			logger.info(doneSettleDifferRecordModel.getPaymentId() + "通道侧返回值=" + channelMsg + ",商户侧返回值=" + merchantMsg);
			doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
		}

		return sdr;
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
	public DoneSettleDifferRecordModel updateByTranStatu(DoneSettleDifferRecordModel sdr, String differType,
			String handleResult, String transStatus) {
		sdr.setDifferType(differType); // 差异类型
		sdr.setHandleResult(handleResult); // 处理结果
		sdr.setTransStatus(transStatus); // 交易状态
		doneSettleDifferRecordClient.updateselectSettleDifferRecord(sdr);
		return sdr;

	}

	/**
	 * 抽取方法 交易状态是成功的
	 * 
	 * @param sdr
	 * @param differType
	 * @param handleResult
	 * @param transStatus
	 * @return
	 */
	public DoneSettleDifferRecordModel updateByTranStatuSuccess(DoneSettleDifferRecordModel sdr, String differType,
			String handleResult, String handleMessage, String transStatus) {

		sdr.setDifferType(differType); // 差异类型
		sdr.setHandleResult(handleResult); // 处理结果
		sdr.setHandleMessage(handleMessage); // 处理意见
		sdr.setOperationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 操作时间
		sdr.setTransStatus(transStatus); // 交易状态

		return sdr;

	}

}
