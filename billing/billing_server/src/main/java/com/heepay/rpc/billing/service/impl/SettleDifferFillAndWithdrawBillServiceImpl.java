package com.heepay.rpc.billing.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.dao.SettleDifferMerchantMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.billingutils.SettleDifferFill;
import com.heepay.common.util.TransTypeUtil;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.DifferencesBillType;
import com.heepay.enums.billing.SettleDifferCheckStatus;
import com.heepay.enums.billing.SettleDifferTransStatus;
import com.heepay.enums.billing.SettleInterval;
import com.heepay.rpc.billing.service.ISettleDifferFillAndWithdrawBillService;
import com.heepay.rpc.client.BillingAccountClient;
import com.heepay.rpc.client.DifferenceAccountServiceClient;
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.rpc.client.SettleDifferFillBillClient;
import com.heepay.rpc.client.SettleDifferWithDrawBillClient;

/**
 * 
 * 
 * 描    述：汇总差错批次数据，撤账和补账
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月18日下午2:34:52 
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
@Service
public class SettleDifferFillAndWithdrawBillServiceImpl implements ISettleDifferFillAndWithdrawBillService {
	
	@Autowired
	SettleDifferChannelMapper settleDifferChannelDaoImpl;
	@Autowired
	SettleDifferMerchantMapper settleDifferMerchantDaoImpl;
	@Autowired
	SettleDifferFillBillClient settleDifferFillBillClient;
	@Autowired
	SettleDifferWithDrawBillClient settleDifferWithDrawBillClient;
	@Autowired
	SettleDifferRecordMapper settleDifferRecordDaoImpl;
	@Autowired
	ManagerServerClient managerServerClient;
	@Autowired
	BillingAccountClient billingAccountClient;
	
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 
	 * @方法说明： 差错批次数据入库，补账和撤账逻辑
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @时间：2016年11月22日上午10:54:31
	 */
	@Override
	@Transactional()
	public void fillAndWithdrawBillMethod(SettleDifferRecord settleDifferRecord) {
		try {
			// 更新差错记录表记账状态为已记账
			//settleDifferRecordDaoImpl.updateIsBillStatus(settleDifferRecord.getPaymentId());
			settleDifferRecord.setIsBill(ClearingCheckStatus.CHECKSTATUSY.getValue());
			settleDifferRecordDaoImpl.updateByPrimaryKey(settleDifferRecord);
			// 生成差错批次数据
			Map<String,Object> map = this.putSettleDifferBathIntoStorage(settleDifferRecord);
			
			// 不需要补账和撤账
			if(map == null || map.size() == 0) {
				logger.info("此差异记录不需要处理！此记录的交易单号为{}", settleDifferRecord.getTransNo());
				return;
			}
			// 通道侧补账撤账
			if(map.get(SettleInterval.NTERVALCV.getValue()) != null) {
				SettleDifferChannel settleDifferChannel = (SettleDifferChannel) map.get(SettleInterval.NTERVALCV.getValue());
				settleDifferChannelDaoImpl.insert(settleDifferChannel);
				logger.info("通道侧差错批次入库成功！交易单号为{}" + settleDifferChannel.getTransNo());
				if(TransTypeUtil.isOutcome(settleDifferRecord.getTransType())) {
					// 出款类补单成功说明交易系统已经补撤账完成
					settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
					logger.info("出款类通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
					
				}else {
					// 通道侧入款类撤补账操作
					if(DifferencesBillType.FILL.getValue().equals(settleDifferChannel.getBillType())) {
						// 补账操作  通道侧补账方法
						this.doChannelFillBill(settleDifferChannel, settleDifferRecord);
					}else if(DifferencesBillType.REFUND.getValue().equals(settleDifferChannel.getBillType())) {
						// 撤账操作,通道侧和用户侧撤账需统一处理
						
					}
				}
			}
			// 商户侧补账撤账
			if(map.get(SettleInterval.NTERVALMV.getValue()) != null) {
				SettleDifferMerchant settleDifferMerchant = (SettleDifferMerchant) map.get(SettleInterval.NTERVALMV.getValue());
				settleDifferMerchantDaoImpl.insert(settleDifferMerchant);
				logger.info("商户侧差错批次入库成功！交易单号为{}", settleDifferMerchant.getTransNo());
				if(TransTypeUtil.isOutcome(settleDifferRecord.getTransType())) {
					// 出款类，差错批次数据只入库，不做撤补账处理
					
				}else {
					// 商户侧入款类撤补账操作
					if(DifferencesBillType.FILL.getValue().equals(settleDifferMerchant.getBillType())) {
						// 补账操作,商户侧补账方法
						this.doMerchantFillBill(settleDifferMerchant, settleDifferRecord);
					}else if(DifferencesBillType.REFUND.getValue().equals(settleDifferMerchant.getBillType())) {
						// 撤账操作,通道侧和商户侧撤账
						SettleDifferChannel settleDifferChannel = (SettleDifferChannel) map.get(SettleInterval.NTERVALCV.getValue());
						this.doMerchantWithDrawBill(settleDifferMerchant, settleDifferChannel, settleDifferRecord);
					}
				}
			}
		} catch (Exception e) {
			logger.error("撤账或补账失败，交易单号为{}", settleDifferRecord.getTransNo() , e);
			throw new RuntimeException();
		}

	}
	
	/**
	 * 
	 * @方法说明：出款类撤账,商户侧和通道侧
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @param settleDifferChannel
	 * @时间：2016年11月15日下午6:01:44
	 */
	public void doMerchantWithDrawBill(SettleDifferMerchant settleDifferMerchant,
			SettleDifferChannel settleDifferChannel, SettleDifferRecord settleDifferRecord) {
		int flag = settleDifferWithDrawBillClient.doMerchantWithDrawBill(settleDifferMerchant.getMerchantId(),
				settleDifferMerchant.getTransNo(), settleDifferRecord.getTransType(), null);
		if(InterfaceStatus.SUCCESS.getValue() == flag && InterfaceStatus.COMPLETED.getValue() == flag) {
			settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
			logger.info("商户侧撤账完成！交易单号和返回状态码为{}", settleDifferMerchant.getTransNo(), flag);
			if(settleDifferChannel != null) {
				settleDifferChannelDaoImpl.updateErrorStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("通道侧撤账完成！交易单号为{}", settleDifferChannel.getTransNo());
			}
		}else {
			logger.info("通道侧和用户侧撤账失败！交易单号和状态码为{},{}", settleDifferChannel.getTransNo(), flag);
		}
		
	}

	/**
	 * 
	 * @方法说明：商户侧补账方法
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @param settleDifferRecord
	 * @时间：2016年11月15日下午5:59:18
	 */
	public void doMerchantFillBill(SettleDifferMerchant settleDifferMerchant, SettleDifferRecord settleDifferRecord) {
		
		if(TransType.CHARGE.getValue().equals(settleDifferRecord.getTransType())) {
			// 充值商户侧补账
			int flag = settleDifferFillBillClient.doChargeMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					null, settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferMerchant.getRequestAmount().toString(), 
					settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("充值商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("充值商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("充值商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(), flag);
			}
		}else if(TransType.PAY.getValue().equals(settleDifferRecord.getTransType())) {
			// 消费商户侧补账
			int flag = settleDifferFillBillClient.doPayMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferRecord.getTransType(), 
					null, settleDifferMerchant.getRequestAmount().toString(), settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("消费商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("消费商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("消费商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(), flag);
			}
		}else if(TransType.BATCHCOLLECTION.getValue().equals(settleDifferRecord.getTransType())) {
			// 代收商户补账
			int flag = settleDifferFillBillClient.doBatcolMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferRecord.getTransType(), 
					null, settleDifferMerchant.getRequestAmount().toString(), settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 代收商户侧补账成功
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("代收商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("代收商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("代收商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(),flag);
			}
		}else if(TransType.DEPOSIT_PAY.getValue().equals(settleDifferRecord.getTransType())) {
			// 存管充值商户补账
			int flag = billingAccountClient.doDepositPayMerchantFillBill(settleDifferMerchant.getMerchantId(), settleDifferRecord.getChannelCode(), 
					settleDifferRecord.getPaymentId(), settleDifferMerchant.getTransNo(), settleDifferMerchant.getErrorBath(), settleDifferRecord.getTransType(), 
					null, settleDifferMerchant.getRequestAmount().toString(), settleDifferMerchant.getFee().toString());
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 存管充值商户侧补账成功
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("存管充值商户侧补账完成！交易单号为{}", settleDifferMerchant.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
				logger.info("存管充值商户侧账务系统已记账！交易单号为{}", settleDifferMerchant.getTransNo());
			}else {
				logger.info("存管充值商户侧补账失败！交易单号和返回状态码为{},{}", settleDifferMerchant.getTransNo(),flag);
			}
		}
	}

	/**
	 * 
	 * @方法说明：通道侧补账方法
	 * @author chenyanming
	 * @param settleDifferChannel
	 * @param settleDifferRecord
	 * @时间：2016年11月15日下午5:47:39
	 */
	public void doChannelFillBill(SettleDifferChannel settleDifferChannel, SettleDifferRecord settleDifferRecord) {
		String feeAmount = null;
		// 银行对账文件中没有手续费字段，如果出现差异账，通道侧调补账接口手续费字段默认传0值 
		if(settleDifferChannel.getCost() == null) {
			feeAmount = "0";
		}else {
			feeAmount = settleDifferChannel.getCost().toString();
		}
		if(TransType.CHARGE.getValue().equals(settleDifferRecord.getTransType())) {
			// 充值通道侧补账
			int flag = settleDifferFillBillClient.doChargeChannelFillBill(settleDifferChannel.getChannelCode(), null,settleDifferChannel.getPaymentId(), 
					settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), settleDifferChannel.getSuccessAmount().toString(), feeAmount);
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 充值通道侧补账成功
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("充值通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("充值通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
			}else {
				logger.info("充值通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(),flag);
			}
		}else if(TransType.PAY.getValue().equals(settleDifferRecord.getTransType())) {
			// 消费通道侧补账
			int flag = settleDifferFillBillClient.doPayChannelFillBill(settleDifferChannel.getChannelCode(), settleDifferChannel.getPaymentId(), 
					settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), null, settleDifferChannel.getSuccessAmount().toString(), feeAmount);
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 消费通道侧补账成功
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("消费通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("消费通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
			}else {
				logger.info("消费通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(),flag);
			}
		}else if(TransTypeUtil.isOutcome(settleDifferRecord.getTransType())) {
			// 出款类通道补账
			int flag = settleDifferFillBillClient.doExpendChannelFillBill(settleDifferChannel.getPaymentId(), settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), 
					settleDifferRecord.getTransType(), null, settleDifferChannel.getChannelCode(), 
					settleDifferChannel.getSuccessAmount().toString(), feeAmount);
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 消费通道侧补账成功
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("出款类通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("出款类通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
			}else {
				logger.info("出款类通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(),flag);
			}
		}else if(TransType.BATCHCOLLECTION.getValue().equals(settleDifferRecord.getTransType())) {
			// 代收通道补账
			int flag = settleDifferFillBillClient.doBatcolChannelFillBill(settleDifferChannel.getChannelCode(), settleDifferChannel.getPaymentId(), 
					settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), null, settleDifferChannel.getSuccessAmount().toString(), feeAmount);
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 代收通道侧补账成功
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("代收通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("代收通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
			}else {
				logger.info("代收通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(),flag);
			}
		}else if(TransType.DEPOSIT_PAY.getValue().equals(settleDifferRecord.getTransType())) {
			// 存管充值通道补账
			int flag = billingAccountClient.doDepositPayChannelFillBill(settleDifferChannel.getChannelCode(), settleDifferChannel.getPaymentId(), 
					settleDifferChannel.getTransNo(), settleDifferChannel.getErrorBath(), null, settleDifferChannel.getSuccessAmount().toString(), feeAmount);
			if(InterfaceStatus.SUCCESS.getValue() == flag) {
				// 存管充值通道侧补账成功
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("存管充值通道侧补账完成！交易单号为{}", settleDifferChannel.getTransNo());
			}else if(InterfaceStatus.COMPLETED.getValue() == flag) {
				// 账务系统已记账
				settleDifferChannelDaoImpl.updateErrorStatusAndCheckStatusByErrorBath(settleDifferChannel.getErrorBath());
				logger.info("存管充值通道侧账务系统已记账！交易单号为{}", settleDifferChannel.getTransNo());
			}else {
				logger.info("存管充值通道侧补账失败！交易单号和返回状态码为{},{}", settleDifferChannel.getTransNo(),flag);
			}
		}
		
	}

	/**
	 * 
	 * @方法说明：生成差错批次数据
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @param transStatus
	 * @return
	 * @时间：2016年10月28日下午2:31:35
	 */
	public Map<String,Object> putSettleDifferBathIntoStorage(SettleDifferRecord settleDifferRecord) {
		logger.info("生成差错批次数据,交易单号为:{}", settleDifferRecord.getTransNo());
		Map<String,Object> map = new HashMap<String,Object>();
		// 通道侧差错批次
		SettleDifferChannel settleDifferChannel = new SettleDifferChannel(); 
		settleDifferChannel.setChannelCode(settleDifferRecord.getChannelCode());//通道编码
		settleDifferChannel.setChannelType(settleDifferRecord.getChannelType());//通道类型
		settleDifferChannel.setCost(settleDifferRecord.getCostAmount());// 成本
		settleDifferChannel.setCurrency(BillingCurrency.CURRENCY.getValue());   //币种  
		settleDifferChannel.setDealTime(new Date());//处理时间
		settleDifferChannel.setErrorDate(settleDifferRecord.getErrorDate());//差错日期
		settleDifferChannel.setErrorStatus(BillingBillStatus.BBSTATUSN.getValue());//差错状态
		settleDifferChannel.setPaymentId(settleDifferRecord.getPaymentId());//支付单号
		settleDifferChannel.setSuccessAmount(settleDifferRecord.getSuccessAmount());//成功金额
		settleDifferChannel.setTransNo(settleDifferRecord.getTransNo());//交易单号
		settleDifferChannel.setCheckStatus(SettleDifferCheckStatus.STATUSN.getValue());//审核状态，N
		
		SettleDifferMerchant settleDifferMerchant = new SettleDifferMerchant();
		settleDifferMerchant.setMerchantId(settleDifferRecord.getMerchantId());//商户id
		settleDifferMerchant.setCurrency(BillingCurrency.CURRENCY.getValue());//币种
		settleDifferMerchant.setDealTime(new Date());//处理时间
		settleDifferMerchant.setErrorDate(settleDifferRecord.getErrorDate());// 差错日期
		settleDifferMerchant.setErrorStatus(BillingBillStatus.BBSTATUSN.getValue());//差错状态
		settleDifferMerchant.setFee(settleDifferRecord.getFeeAmount());//手续费
		settleDifferMerchant.setRequestAmount(settleDifferRecord.getRequestAmount());//成功金额
		settleDifferMerchant.setSettleAmountPlan(settleDifferRecord.getSettleAmountPlan());//应结算金额
		settleDifferMerchant.setTransNo(settleDifferRecord.getTransNo());//交易单号
		settleDifferMerchant.setTransType(settleDifferRecord.getTransType());//交易类型
		settleDifferMerchant.setCheckStatus(SettleDifferCheckStatus.STATUSN.getValue());//审核状态 N
		settleDifferMerchant.setMerchantOrderNo(settleDifferRecord.getMerchantOrderNo());//商户订单号
		settleDifferMerchant.setMerchantName(settleDifferRecord.getMerchantName()); // 商户名称
		
		if(SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue().equals(settleDifferRecord.getTransStatus())) { // 交易系统成功
			if(TransType.CHARGE.getValue().equals(settleDifferRecord.getTransType())) { // 充值
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 通道补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				map.put(SettleInterval.NTERVALMV.getValue(), null);
				return map;
			}else if(TransType.PAY.getValue().equals(settleDifferRecord.getTransType())) { // 消费
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 通道补账 商户补账？
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				/*//通过thrift 获取商户侧路由信息
				String merchantVo = managerServerClient.getMerchantProductVO(String.valueOf(settleDifferRecord.getMerchantId()), settleDifferRecord.getProductCode());
				//获取路由商户信息
				MerchantProductVO merVo = FetchRouteMessage.getMerchantMessage(merchantVo);
				
				if(merVo != null){
					if(StringUtils.isNotBlank(merVo.getSettleCyc())) {
						if(Constants.Clear.T1.equals(merVo.getSettleCyc())) {
							// 周期消费 ，通道侧和商户侧都需要补账
							settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
							settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
							map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
						}else if(Constants.Clear.T0.equals(merVo.getSettleCyc())) {
							// 实时消费，只有通道侧补账
							map.put(SettleInterval.NTERVALMV.getValue(), null);
						}
					}else {
						logger.info("获取商户侧订单结算周期失败, 商户编码:{}, 产品编码：{}",settleDifferRecord.getMerchantId(), settleDifferRecord.getProductCode());
					}
				}else {
					logger.info("获取商户路由信息失败, 商户编码:{}, 产品编码：{}",settleDifferRecord.getMerchantId(), settleDifferRecord.getProductCode());
				}*/
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				return map;
			}else if(TransTypeUtil.isOutcome(settleDifferRecord.getTransType())) { // 出款类（包括提现，批付，退款,打款认证，实名认证） 
				return null;
			}else if(TransType.BATCHCOLLECTION.getValue().equals(settleDifferRecord.getTransType())) { // 代收 通道测和商户侧补账
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 通道补账 商户补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				return map;
			}else if(TransType.DEPOSIT_PAY.getValue().equals(settleDifferRecord.getTransType())) {
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 存管充值通道补账 商户补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				return map;
			}
		}else if(SettleDifferTransStatus.PAYMENTSTATUS_ING.getValue().equals(settleDifferRecord.getTransStatus()) ||
				SettleDifferTransStatus.PAYMENTSTATUS_FAI.getValue().equals(settleDifferRecord.getTransStatus()) ||
				SettleDifferTransStatus.PAYMENTSTATUS_PRE.getValue().equals(settleDifferRecord.getTransStatus()) ||
				SettleDifferTransStatus.PAYMENTSTATUS_TIM.getValue().equals(settleDifferRecord.getTransStatus())) { // 其他情况，按失败处理
			if(TransType.CHARGE.getValue().equals(settleDifferRecord.getTransType())) { // 充值   通道侧和商户侧都需要补账
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				return map;
				
			}else if(TransType.PAY.getValue().equals(settleDifferRecord.getTransType())) { // 消费
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 取消撤单退款，支付长款都改为补单补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				return map;
			}else if(TransTypeUtil.isOutcome(settleDifferRecord.getTransType())) { // 出款类（包括提现，批付，退款,打款认证，实名认证） 
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue());   // 通道侧需要补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				//settleDifferBath.setBillType(DifferencesBillType.FILL.getValue());// 补账
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				map.put(SettleInterval.NTERVALMV.getValue(), null);
				return map;    // 
			}else if(TransType.BATCHCOLLECTION.getValue().equals(settleDifferRecord.getTransType())) {
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 通道补账 商户补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				return map;
			}else if(TransType.DEPOSIT_PAY.getValue().equals(settleDifferRecord.getTransType())) {
				settleDifferChannel.setBillType(DifferencesBillType.FILL.getValue()); // 存管充值通道补账 商户补账
				settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
				settleDifferMerchant.setBillType(DifferencesBillType.FILL.getValue());
				settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
				map.put(SettleInterval.NTERVALMV.getValue(), settleDifferMerchant);
				map.put(SettleInterval.NTERVALCV.getValue(), settleDifferChannel);
				return map;
			}
		}
		return null;
		
	}

	

}
