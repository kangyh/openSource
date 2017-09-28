package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.dao.SettleDifferMerchantMapper;
import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.billingutils.SettleDifferFill;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.ErrorNoteType;
import com.heepay.rpc.billing.service.IAjustMoneyService;
import com.heepay.rpc.client.DifferenceAccountServiceClient;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.service.RpcService;
/**
 * 
 * 
 * 描    述：清结算系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年5月10日 下午4:48:46
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
@Repository         
@RpcService(name = "AdjustMoneyServiceImpl", processor = com.heepay.rpc.billing.service.AdjustMoneyService.Processor.class)
public class AdjustMoneyServiceImpl implements com.heepay.rpc.billing.service.AdjustMoneyService.Iface {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	SettleDifferChannelMapper settleDifferChannelDaoImpl;
	@Autowired
	SettleDifferMerchantMapper settleDifferMerchantDaoImpl;
	@Autowired
	DifferenceAccountServiceClient differenceAccountServiceClient;
	/**
	 * //一、充值
		//1、调用接口查询 通道侧是否有记录，通道无记录 记为坏账，或通道有记录，但金额不一致 也记为坏账
		//2、通道 有记录，金额一致 ，走正常的结算
		//3、通道 有记录，但没成功，视为短款，走商户能撤账流程
	 * @param accountingInfoHash
	 * @return
	 */
	IAjustMoneyService<HashMap<String,String>, String> handleAdjustMoney_Charge = paramJsonHashMap->{
		Integer flag = null;
		HashMap<String,String> msg = new HashMap<String,String>();
		if 	(paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_Y.getValue())){	//充值通道有记录	
			if(new BigDecimal(paramJsonHashMap.get("successAmount")).compareTo(new BigDecimal(paramJsonHashMap.get("requestAmount"))) !=0){ 
				//金额不一致 ，暂不做处理
				
			}else{
				logger.info("充值通道有记录，调账开始transNo:{}", paramJsonHashMap.get("transNo"));
				//通道差异记录是否存在，若不存在，则入库（可能存在第一次调账失败、二次调账）
				SettleDifferChannel settleDifferChannel = querySettleDifferChannelByTransNo(paramJsonHashMap.get("transNo"));
				if(settleDifferChannel == null){
					//走正常的 充值入账流程
					settleDifferChannel = setSettleDifferChannelObj(paramJsonHashMap);  //先入库
				}
				
				flag = differenceAccountServiceClient.supplementChargeChannelAccount
				(paramJsonHashMap.get("paymentId"), paramJsonHashMap.get("transNo"), 
				 paramJsonHashMap.get("channelCode"), paramJsonHashMap.get("requestAmount"), 
				 paramJsonHashMap.get("successAmount"),"0");
				utilsRecordAndLog_channel(settleDifferChannel,flag,msg,"通道侧充值接口补账操作成功","通道侧充值接口补账操作成功,supplementChargeChannelAccount",paramJsonHashMap,true);
			}
		}else if (paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_N.getValue())){	//充值通道无记录	
			
			logger.info("充值通道无记录，商户侧撤账开始transNo:{}", paramJsonHashMap.get("transNo"));
			//走短款的流程，商户侧撤账
			AsyncMsgModel merchantMsg = null;
			 flag = differenceAccountServiceClient.revokeMerchantAccount(
					Long.parseLong(String.valueOf(paramJsonHashMap.get("merchantId"))), paramJsonHashMap.get("paymentId"), 
					paramJsonHashMap.get("transNo"), paramJsonHashMap.get("channelCode"), 
					(Double.valueOf(String.valueOf(paramJsonHashMap.get("requestAmount")))-Double.valueOf(String.valueOf(paramJsonHashMap.get("feeAmount"))))+"", paramJsonHashMap.get("feeAmount"));
			 utilsRecordAndLog_merchant(null,flag,msg,"商户侧充值接口撤账操作成功","商户侧充值接口撤账操作成功,revokeMerchantAccount",paramJsonHashMap,false); //不插入表，只记录日志
		}
		msg.put("flag", flag+"");
		logger.info("充值调账结果，flag：{}",  flag);
		return  JsonMapperUtil.nonDefaultMapper().toJson(msg);
	};
	/**二、消费
	1、调用接口查询 通道侧是否有记录，通道无记录 记为坏账，或通道有记录，但金额不一致 也记为坏账
	2、通道 有记录，金额一致 ，走正常的结算
	3、通道 有记录，但没成功，视为短款，走商户能撤账流程
 * 
 * @param accountingInfoHash
 * @return
 */
	IAjustMoneyService<HashMap<String,String>, String> handleAdjustMoney_Pay = paramJsonHashMap->{
		Integer flag = null;
		HashMap<String,String> msg = new HashMap<String,String>();
		if 	(paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_Y.getValue())){	//消费通道有记录	
			if(new BigDecimal(paramJsonHashMap.get("successAmount")).compareTo(new BigDecimal(paramJsonHashMap.get("requestAmount"))) !=0){ 
				//金额不一致 ，暂不做处理
				
			}else
			{
				logger.info("消费通道有记录,调账开始transNo:{}", paramJsonHashMap.get("transNo"));
				//通道差异记录是否存在，若不存在，则入库（可能存在第一次调账失败、二次调账）
				SettleDifferChannel settleDifferChannel = querySettleDifferChannelByTransNo(paramJsonHashMap.get("transNo"));
				if(settleDifferChannel == null){
					//走正常的 消费入账流程
					settleDifferChannel = setSettleDifferChannelObj(paramJsonHashMap);  //先入库
				}
				
				flag = differenceAccountServiceClient.supplementPayChannelAccount(paramJsonHashMap.get("paymentId"),paramJsonHashMap.get("transNo"),paramJsonHashMap.get("channelCode"),paramJsonHashMap.get("requestAmount"),"0");
				utilsRecordAndLog_channel(settleDifferChannel,flag,msg,"通道侧 已补账","通道侧 已补账,supplementPayChannelAccount",paramJsonHashMap,true);
				if (flag!=null && flag ==1000){
					if ("1".equals(paramJsonHashMap.get("settleCyc"))){  //周期结算，补商户账
						
						SettleDifferMerchant settleDifferMerchant = queryDifferMerchantByTransNo(paramJsonHashMap.get("transNo"));
						if(settleDifferMerchant == null){   //若不存在则入库
							settleDifferMerchant = setSettleDifferMerchantObj(paramJsonHashMap);  
						}
						
						flag = differenceAccountServiceClient.supplementPayMerchantAccount(
							Integer.valueOf(String.valueOf(paramJsonHashMap.get("merchantId"))),
							paramJsonHashMap.get("paymentId"),
							paramJsonHashMap.get("transNo"),
							paramJsonHashMap.get("channelCode"),
							(Double.valueOf(String.valueOf(paramJsonHashMap.get("requestAmount")))-Double.valueOf(String.valueOf(paramJsonHashMap.get("feeAmount"))))+"",paramJsonHashMap.get("feeAmount"));
						 utilsRecordAndLog_merchant(settleDifferMerchant,flag,msg,"商户侧 已补账","商户侧已补账,supplementPayMerchantAccount",paramJsonHashMap,true);
					}
				}				
			}
		}else if (paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_N.getValue())){	//消费通道无记录	
			
			logger.info("消费通道无记录调账开始，transNo:{}", paramJsonHashMap.get("transNo"));
			//走短款的流程			
			SettleDifferMerchant settleDifferMerchant = queryDifferMerchantByTransNo(paramJsonHashMap.get("transNo"));
			if(settleDifferMerchant == null){   //若不存在则入库
				settleDifferMerchant = setSettleDifferMerchantObj(paramJsonHashMap);  
			}
			if ("0".equals(paramJsonHashMap.get("settleCyc"))){ //时时结算，撤商户账
				
				logger.info("消费T+0,通道侧无记录，撤商户账，交易单号：{}", paramJsonHashMap.get("transNo"));				
				flag = differenceAccountServiceClient.revokePayMerchantAccount(
						Long.valueOf(String.valueOf(paramJsonHashMap.get("merchantId"))),
						paramJsonHashMap.get("paymentId"),
						paramJsonHashMap.get("transNo"),
						paramJsonHashMap.get("channelCode"),
						(Double.valueOf(paramJsonHashMap.get("requestAmount"))-Double.valueOf(paramJsonHashMap.get("feeAmount"))+""),
						paramJsonHashMap.get("feeAmount"));
				utilsRecordAndLog_merchant(settleDifferMerchant,flag,msg,"商户侧 已撤账","商户侧 已撤账,revokePayMerchantAccount",paramJsonHashMap,true);
			}else if ("1".equals(paramJsonHashMap.get("settleCyc"))){  //周期结算，撤日间待结算账务
				
				logger.info("消费T+1,通道侧无记录，撤日间待结算账务，交易单号：{}", paramJsonHashMap.get("transNo"));	
				flag = differenceAccountServiceClient.revokePayDayTimeAccount(
						Long.valueOf(String.valueOf(paramJsonHashMap.get("merchantId"))),
						paramJsonHashMap.get("paymentId"),
						paramJsonHashMap.get("transNo"),
						paramJsonHashMap.get("channelCode"),
						paramJsonHashMap.get("requestAmount"));
				utilsRecordAndLog_merchant(null,flag,msg,"日间待结算账务已撤账","日间待结算账务已撤账,revokePayDayTimeAccount",paramJsonHashMap,false);
			}
		}
		msg.put("flag", flag+"");
		return  JsonMapperUtil.nonDefaultMapper().toJson(msg);
	};
	/**
	 * //三、提现
		//1、调用接口查询 通道侧是否有记录，通道无记录 记为坏账，或通道有记录，但金额不一致 也记为坏账
		//2、通道 有记录，金额一致 ，走正常的结算
		//3、通道 有记录，但没成功，视为长款，走商户、通道 撤账流程
	 */
	IAjustMoneyService<HashMap<String,String>, String> handleAdjustMoney_Withdraw = paramJsonHashMap->{
		Integer flag = null;
		HashMap<String,String> msg = new HashMap<String,String>();
		if 	(paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_Y.getValue())){	//提现通道有记录	
			if(new BigDecimal(paramJsonHashMap.get("successAmount")).compareTo(new BigDecimal(paramJsonHashMap.get("requestAmount"))) !=0){ 
				//金额不一致 ，调用坏账处理
				
			}else{
				//走正常的 提现流程 ，暂不考虑
				
			}
		}else if (paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_N.getValue())){	//提现通道无记录	
			//走长款的流程，商户侧，通道侧撤账
			flag = differenceAccountServiceClient.supplementOutMerchantAccount(Integer.parseInt(String.valueOf(paramJsonHashMap.get("merchantId"))),paramJsonHashMap.get("transType"),paramJsonHashMap.get("paymentId"),
					paramJsonHashMap.get("transNo"),
					paramJsonHashMap.get("channelCode"),
					String.valueOf(paramJsonHashMap.get("requestAmount")),
					paramJsonHashMap.get("feeAmount"));
			utilsRecordAndLog_merchant(null,flag,msg,"商户侧已撤账","商户侧已撤账,supplementOutMerchantAccount",paramJsonHashMap,false);
			if (flag !=null && flag==1000)
			{
				flag = differenceAccountServiceClient.supplementOutChannelAccount(paramJsonHashMap.get("transType"),paramJsonHashMap.get("paymentId"),
					paramJsonHashMap.get("transNo"),paramJsonHashMap.get("channelCode"),paramJsonHashMap.get("successAmount"),paramJsonHashMap.get("chanelFeeAmount"));
				utilsRecordAndLog_channel(null,flag,msg,"通道侧分别撤账成功","通道侧分别撤账成功,supplementOutChannelAccount",paramJsonHashMap,false);
			}
		}
		
		msg.put("flag", flag+"");
		return JsonMapperUtil.nonDefaultMapper().toJson(msg);
	};
	
	/**银联扫码支付
	 * 
	 * @param accountingInfoHash
	 * @return
	 */
	IAjustMoneyService<HashMap<String,String>, String> handleAdjustMoney_UnionPay = paramJsonHashMap->{
		if 	(paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_Y.getValue())){	//银联扫码支付通道有记录	
			if(new BigDecimal(paramJsonHashMap.get("successAmount")).compareTo(new BigDecimal(paramJsonHashMap.get("requestAmount"))) !=0){ 
				//金额不一致 ，调用坏账处理
				
			}else
			{
				//走正常的 充值入账流程
			}
		}else if (paramJsonHashMap.get("handleMessage").equals(ErrorNoteType.CHANNEL_N.getValue())){	//银联扫码支付通道无记录	
			//走短款的流程，商户侧撤账
		}
	
		return null;
	};
	
	/**
	 * 业务描述
	 * 差异单处理分传统和银联两种
	 * 按交易类型，传统差异单分 充值，消费，提现，银联差异单为消费、代收(暂不考虑)、转账、提现
	 *  //successAmount  支付单金额（通道金额）
		//handleMessage  处理结果Y ,通道有该笔记录 N 通道无该笔记录
		//channle_no  通道流水（上游流水）
		//differ_type 差异类型（CK：长款   DK：短款   JE：金额   QT：其他   WZ:未知）
		//trans_type 交易类型
		//requestAmount 我方金额
		//payment_id 支付单号
		// feeAmount 手续费
		//feeWay 坐扣 外扣
	 */
	@Transactional()
	@Override
	public String updateAccounting(String accountingInfo) throws TException {
		// TODO Auto-generated method stub
		String rtnStr="";
		try{
			logger.info("调账参数：{}",accountingInfo);
			
			HashMap<String,String> accountingInfoHash =  JsonMapperUtil.nonDefaultMapper().fromJson(accountingInfo, HashMap.class);
			
			if (TransType.CHARGE.getValue().equals(accountingInfoHash.get("transType"))) {   //充值
				
				rtnStr =handleAdjustMoney_Charge.apply(accountingInfoHash);
				
			}else if (TransType.PAY.getValue().equals(accountingInfoHash.get("transType")) 
					  || TransType.UNIONPAY_QRCODE.getValue().equals(accountingInfoHash.get("transType"))){ //传统消费 、银联二维码
				
				rtnStr = handleAdjustMoney_Pay.apply(accountingInfoHash);
				
			}else if (TransType.WITHDRAW.getValue().equals(accountingInfoHash.get("transType")) 
					 || TransType.BATCHPAY.getValue().equals(accountingInfoHash.get("transType"))
					 ||	TransType.REFUND.getValue().equals(accountingInfoHash.get("transType"))){ //提现 、转账、退款
				
				rtnStr = handleAdjustMoney_Withdraw.apply(accountingInfoHash);
				
			}else if (TransType.UNIONPAY_QRCODE.getValue().equals(accountingInfoHash.get("transType"))){
				
				rtnStr = handleAdjustMoney_UnionPay.apply(accountingInfoHash);
			}
			//商户撤单接口 BillingClearAPIClient->cancelMerchantTrans
			
			return rtnStr;
		}catch(Exception e){
			logger.error("清结算系统调账异常：{}", accountingInfo, e);
		}
		return  rtnStr;

	}
	/**
	 * 差异账 调账 通道侧记录赋值
	 * @param accountingInfoHash
	 * @return
	 */
	public SettleDifferChannel setSettleDifferChannelObj(HashMap<String,String> accountingInfoHash){
		SettleDifferChannel settleDifferChannel = new SettleDifferChannel();
		settleDifferChannel.setErrorBath(SettleDifferFill.getRandomString(0));
		settleDifferChannel.setChannelCode(accountingInfoHash.get("channelCode"));
		settleDifferChannel.setChannelType(accountingInfoHash.get("channelType"));
		settleDifferChannel.setCurrency(accountingInfoHash.get("currency"));
		settleDifferChannel.setErrorDate(new Date(Long.parseLong(String.valueOf(accountingInfoHash.get("errorDate")))));
		settleDifferChannel.setDealTime(new Date());
		settleDifferChannel.setPaymentId(accountingInfoHash.get("paymentId"));
		settleDifferChannel.setTransNo(accountingInfoHash.get("transNo"));
		if 	(accountingInfoHash.get("handleMessage").equals(ErrorNoteType.CHANNEL_Y.getValue())){	
			settleDifferChannel.setSuccessAmount(new BigDecimal(String.valueOf(accountingInfoHash.get("successAmount"))));
		}
		settleDifferChannel.setBillType("FILL");
		settleDifferChannel.setErrorStatus("D");
		settleDifferChannel.setCheckStatus("N");
		settleDifferChannelDaoImpl.insert(settleDifferChannel);
		return settleDifferChannel;
	}
	
	/**
	 * 差异账 调账 商户侧记录赋值
	 * @param accountingInfoHash
	 * @return
	 */
	public SettleDifferMerchant setSettleDifferMerchantObj(HashMap<String,String> accountingInfoHash){
		SettleDifferMerchant settleDifferMerchant = new SettleDifferMerchant();
		settleDifferMerchant.setMerchantId(Integer.parseInt(String.valueOf(accountingInfoHash.get("merchantId"))));
		settleDifferMerchant.setTransType(accountingInfoHash.get("transType"));
		settleDifferMerchant.setCurrency(accountingInfoHash.get("currency"));
		settleDifferMerchant.setErrorBath(SettleDifferFill.getRandomString(1));
		settleDifferMerchant.setErrorDate(new Date(Long.parseLong(String.valueOf(accountingInfoHash.get("errorDate")))));
		settleDifferMerchant.setDealTime(new Date());
		settleDifferMerchant.setTransNo(accountingInfoHash.get("transNo"));
		settleDifferMerchant.setRequestAmount(new BigDecimal(accountingInfoHash.get("requestAmount")));
		settleDifferMerchant.setMerchantName(accountingInfoHash.get("merchantName"));
		
		settleDifferMerchant.setSettleAmountPlan(new BigDecimal(accountingInfoHash.get("settleAmountPlan")));

		settleDifferMerchant.setFee(new BigDecimal(accountingInfoHash.get("feeAmount")));
		settleDifferMerchant.setBillType("FILL");
		settleDifferMerchant.setErrorStatus("D");
		settleDifferMerchant.setCheckStatus("N");
		settleDifferMerchantDaoImpl.insert(settleDifferMerchant);	 
		return settleDifferMerchant;
	}
	/**
	 *  工具方法 用来记录 调用接口的返回判断
	 * @param flag
	 * @param msg
	 * @param remark
	 * @param loginfo
	 * @param paramJsonHashMap
	 * @param isInsert 是否插入数据库
	 */
	public void utilsRecordAndLog_channel(SettleDifferChannel settleDifferChannel,Integer flag,HashMap<String,String> msg,String remark,String loginfo,HashMap<String,String> paramJsonHashMap,boolean isInsert)
	{
		if (flag != null && flag ==1000){
			if (isInsert)
			{
				settleDifferChannelDaoImpl.updateErrorStatusByErrorBath(settleDifferChannel.getErrorBath());
			}
			msg.put("remark", remark)	;
			logger.info(loginfo);
		}else if (flag !=null && flag !=1000){
			msg.put("remark", "调用接口异常："+flag)	;
			logger.info("调用接口返回参数:{}",flag);
		}else if(flag==null){
			logger.info("调用接口报错,{}", msg);
		}
	}
	/**
	 *  工具方法 用来记录 调用接口的返回判断
	 * @param flag
	 * @param msg
	 * @param remark
	 * @param loginfo
	 * @param paramJsonHashMap
	 * @param isInsert 是否插入数据库
	 */
	public void utilsRecordAndLog_merchant(SettleDifferMerchant settleDifferMerchant,Integer flag,HashMap<String,String> msg,String remark,String loginfo,HashMap<String,String> paramJsonHashMap,boolean isInsert)
	{
		if (flag != null && flag ==1000){
			if (isInsert && settleDifferMerchant !=null)
			{
				settleDifferMerchantDaoImpl.updateErrorStatusByErrorBath(settleDifferMerchant.getErrorBath());
			}
			msg.put("remark", remark)	;
			logger.info(loginfo);
		}else if (flag !=null && flag !=1000){
			msg.put("remark", "调用接口异常："+flag)	;
			logger.info("调用接口返回参数:{}",flag);
		}else if(flag==null){
			logger.info("调用接口报错:{}", msg);
		}
	}
	
	/**
	 * 
	 * @方法说明：通过交易订单号查询商户差异记录
	 * @author xuangang
	 * @param transNo
	 * @return
	 * @时间：2017年5月26日下午2:58:17
	 */
	private SettleDifferMerchant queryDifferMerchantByTransNo(String transNo){
		
		return settleDifferMerchantDaoImpl.queryDifferMerchantByTransNo(transNo);		
	}
	/**
	 * 
	 * @方法说明：通过交易订单号查询通道差异记录
	 * @author xuangang
	 * @param transNo
	 * @return
	 * @时间：2017年5月26日下午3:29:52
	 */
	public SettleDifferChannel querySettleDifferChannelByTransNo(String transNo){
		return settleDifferChannelDaoImpl.querySettleDifferChannelByTransNo(transNo);		
	}
}
