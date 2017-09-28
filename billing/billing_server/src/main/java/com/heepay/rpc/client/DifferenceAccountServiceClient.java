package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.account.service.DifferenceAccountService;

/**
 * 
 * 
 * 描 述：清结算系统
 *
 * 创 建 者：王英雷 E-mail:wangyl@9186.com 创建时间： 2017年5月16日 下午2:38:08 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Service
public class DifferenceAccountServiceClient extends BaseClientDistribute {
	private static final String SERVICENAME = "DifferenceAccountServiceImpl";
												
	private static final String NODENAME = "account_rpc";

	private static final Logger log = LogManager.getLogger();
	@Resource(name = "differenceAccountClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
		;
	}

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	public DifferenceAccountService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new DifferenceAccountService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 其他差异-充值挂账-商户侧撤账
	 * 
	 * @param merchantId
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount 不含手续费
	 * @param feeAmount
	 * @return
	 */
	public Integer revokeMerchantAccount(long merchantId, String paymentId, String transNo, String channelCode,
			String adjustAmount, String feeAmount) {
		try {
			return getClient().revokeMerchantAccount(merchantId, paymentId, transNo, channelCode, adjustAmount,
					feeAmount);
		} catch (TException e) {
			log.error("其他差异-充值挂账-商户侧撤账发生异常:{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}

	/**
	 * 其他差异-消费挂账-T0-商户撤账
	 * @param merchantId
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param feeAmount
	 * @return
	 */
	public Integer revokePayMerchantAccount(long merchantId,String paymentId,String transNo,String channelCode,String adjustAmount,String feeAmount){
		
		try {
			return getClient().revokePayMerchantAccount(merchantId, paymentId, transNo, channelCode, adjustAmount,feeAmount);
		} catch (TException e) {
			log.error("其他差异-消费挂账-T0-商户撤账异常：{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	
	
	
	/**
	 * 其他差异-消费挂账-T1-撤日间待结算账务
	 * @param merchantId
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param feeAmount
	 * @return
	 */
	public Integer revokePayDayTimeAccount(long merchantId,String paymentId,String transNo,String channelCode,String adjustAmount){
		
		try {
			return getClient().revokePayDayTimeAccount(merchantId, paymentId, transNo, channelCode, adjustAmount);
		} catch (TException e) {
			log.error("其他差异-消费挂账-T1-撤日间待结算账务异常：{},{}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	
	
	
	
	/**
	 * 其他差异-充值挂账-通道补账
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount 订单金额（含手续费）
	 * @param chanelBackAmount 通道金额
	 * @param chanelFeeAmount 通道手续费
	 * @return
	 */
	public Integer supplementChargeChannelAccount(String paymentId, String transNo, String channelCode,
			String adjustAmount, String chanelBackAmount, String chanelFeeAmount) {
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.supplementChargeChannelAccount(paymentId, transNo, channelCode, adjustAmount,
					chanelBackAmount, chanelFeeAmount);
		} catch (TException e) {
			log.error("其他差异-充值挂账-通道补账异常：{}，{}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 其他差异-提现挂账-补通道账务
	 * @param transType
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param chanelFeeAmount
	 * @return
	 */
	public Integer supplementOutChannelAccount(String transType,String paymentId,String transNo,String channelCode,
			String adjustAmount,String chanelFeeAmount){
		
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.supplementOutChannelAccount(transType,paymentId,transNo,channelCode,adjustAmount,chanelFeeAmount);
		} catch (TException e) {
			log.error("其他差异-提现挂账-补通道账务异常:{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	/**
	 * 其他差异-挂账-补商户账务
	 * @param merchantId
	 * @param transType
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param feeAmount
	 * @return
	 */
	public Integer supplementOutMerchantAccount(int merchantId,String transType,String paymentId,String transNo,
			String channelCode,String adjustAmount,String feeAmount){
		
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.supplementOutMerchantAccount(merchantId,transType,paymentId,transNo,channelCode,adjustAmount,feeAmount);
		} catch (TException e) {
			log.error("其他差异-挂账-补商户账务异常：{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	/**
	 * 其他差异-消费挂账-T0-商户撤账
	 * @param merchantId
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param feeAmount
	 * @return
	 */
	public Integer revokePayMerchantAccount(Integer merchantId,String paymentId,String transNo,String channelCode,String adjustAmount,String feeAmount){
		
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.revokePayMerchantAccount(merchantId,paymentId,transNo,channelCode,adjustAmount,feeAmount);
		} catch (TException e) {
			log.error("其他差异-消费挂账-T0-商户撤账异常：{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	/**
	 * 其他差异-消费挂账-T1-撤日间待结算账务
	 * @param merchantId
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @return
	 */
	public Integer revokePayDayTimeAccount(Integer merchantId,String paymentId,String transNo,String channelCode,String adjustAmount){
			
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.revokePayDayTimeAccount(merchantId,paymentId,transNo,channelCode,adjustAmount);
		} catch (TException e) {
			log.error("其他差异-消费挂账-T1-撤日间待结算账务：{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	} 
	/**
	 * 其他差异-消费挂账-补商户账务
	 * @param merchantId
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param merFeeAmount
	 * @return
	 */
	public Integer supplementPayMerchantAccount(Integer merchantId,String paymentId,String transNo,String channelCode,String adjustAmount,String merFeeAmount){
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.supplementPayMerchantAccount(merchantId,paymentId,transNo,channelCode,adjustAmount,merFeeAmount);
		} catch (TException e) {
			log.error("其他差异-消费挂账-补商户账务:{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}
	/**
	 * 其他差异-消费挂账-补通道账务
	 * @param paymentId
	 * @param transNo
	 * @param channelCode
	 * @param adjustAmount
	 * @param chanelFeeAmount
	 * @return
	 */
	public Integer supplementPayChannelAccount(String paymentId,String transNo,String channelCode,String adjustAmount,String chanelFeeAmount){
		try {
			DifferenceAccountService.Client client =  getClient();
			return client.supplementPayChannelAccount(paymentId,transNo,channelCode,adjustAmount,chanelFeeAmount);
		} catch (TException e) {
			log.error("其他差异-消费挂账-补通道账务:{}, {}", transNo, paymentId, e);
		} finally {
			this.close();
		}
		return null;
	}

	
}
