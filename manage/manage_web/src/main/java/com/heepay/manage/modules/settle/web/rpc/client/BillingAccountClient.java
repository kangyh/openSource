package com.heepay.manage.modules.settle.web.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.account.model.ChannelParamModel;
import com.heepay.rpc.account.model.MerchantParamModel;
import com.heepay.rpc.account.service.DepositoryAccountService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

/**
 * 
 * 
 * 描    述：存管充值补账接口
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年8月15日上午10:29:31 
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
public class BillingAccountClient extends BaseClientDistribute {

	private static final String SERVICENAME = "DepositoryAccountServiceImpl";
	
	private static final String NODENAME = "account_rpc";

	private static final Logger log = LogManager.getLogger();
	@Resource(name = "accountClient")
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

	public DepositoryAccountService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new DepositoryAccountService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 
	 * @方法说明：存管充值通道补账
	 * @author chenyanming
	 * @param channelCode
	 * @param paymentId
	 * @param transNo
	 * @param errorBath
	 * @param object
	 * @param string
	 * @param feeAmount
	 * @return
	 * @时间：2017年8月15日上午10:35:13
	 */
	public int doDepositPayChannelFillBill(String channelCode, String paymentId, String transNo, String errorBath,
			Object object, String amount, String feeAmount) {
		DepositoryAccountService.Client client = this.getClient();
		try {
			ChannelParamModel channelParamModel = new ChannelParamModel();
			channelParamModel.setPaymentId(paymentId);
			channelParamModel.setChannelCode(channelCode);
			channelParamModel.setTransNo(transNo);
			channelParamModel.setSettleId(errorBath);
			channelParamModel.setSplAmount(amount);
			channelParamModel.setFeeAmount(feeAmount);
			int flag = client.recordDptLongSupplementChannelAccount(channelParamModel);
			return flag;
		} catch (TException e) {
			log.error("存管充值通道侧补账异常！支付单号为:", paymentId, e);
		} finally {
			this.close();
		}
		return 0;
	}

	/**
	 * 
	 * @方法说明：存管充值商户补账
	 * @author chenyanming
	 * @param merchantId
	 * @param channelCode
	 * @param paymentId
	 * @param transNo
	 * @param errorBath
	 * @param transType
	 * @param object
	 * @param string
	 * @param string2
	 * @return
	 * @时间：2017年8月15日上午10:45:10
	 */
	public int doDepositPayMerchantFillBill(Integer merchantId, String channelCode, String paymentId, String transNo,
			String errorBath, String transType, Object object, String amount, String feeAmount) {
		DepositoryAccountService.Client client = this.getClient();
		try {
			MerchantParamModel merchantParamModel = new MerchantParamModel();
			merchantParamModel.setMerchantId(merchantId);
			merchantParamModel.setChannelCode(channelCode);
			merchantParamModel.setPaymentId(paymentId);
			merchantParamModel.setTransNo(transNo);
			merchantParamModel.setSettleId(errorBath);
			merchantParamModel.setSplAmount(amount);
			merchantParamModel.setFeeAmount(feeAmount);
			int flag = client.recordDptLongSupplementMercahntAccount(merchantParamModel);
			return flag;
		} catch (TException e) {
			log.error("存管充值商户侧补账异常！支付单号为:", paymentId, e);
		} finally {
			this.close();
		}
		return 0;
	}

}
