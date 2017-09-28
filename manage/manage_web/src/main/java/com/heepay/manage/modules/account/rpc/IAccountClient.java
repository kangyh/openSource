package com.heepay.manage.modules.account.rpc;

import com.heepay.common.util.Constants;
import com.heepay.rpc.account.model.PayMentSplDayTimeModel;
import com.heepay.rpc.account.service.AccountRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.MerchantLogModel;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**          
* 
* 描    述：
* 创 建 者： zhangjx
* 创建时间： 2017年2月27日 上午10:30:56
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
public class IAccountClient  extends BaseClient {

	private static final String SERVICENAME = "AccountServiceImpl";

  @Override
  public void setServiceName(){
    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
  }
  
  @Override
  public void setNodename() {
    ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.ACCOUNTRPC);
  }
  

	private AccountRecordService.Client getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new AccountRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 刷新关联账户
	 * @paramml
	 * @return
	 */
	public String refreshRelationAccount() {
		try {
			int returnCode = getClient().refreshRelationAccount();
			return String.valueOf(returnCode);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}

	/**
	 *
	 * @return
	 */
	public String recordTransfer(long sponsorMerchantId, long acceptMerchantId, String paymentId, String transNo, String transferAmount, String feeAmount, boolean isSign, String opResources) {
		try {
			int returnCode = getClient().recordTransfer(sponsorMerchantId, acceptMerchantId, paymentId, transNo, transferAmount, feeAmount, isSign, opResources);
			return String.valueOf(returnCode);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}

	/**
	 * 签约内转
	 * @param sponsorMerchantId
	 * @param acceptMerchantId
	 * @param paymentId
	 * @param transNo
	 * @param transferAmount
	 * @param feeAmount
	 * @param opResources
	 * @return
	 */
	public String recordTransferSign(long sponsorMerchantId, long acceptMerchantId, String paymentId, String transNo, String transferAmount, String feeAmount,boolean isSign, String opResources) {
		try {
			int returnCode = getClient().recordTransferSign(sponsorMerchantId, acceptMerchantId, paymentId, transNo, transferAmount, feeAmount, isSign, opResources);
			return String.valueOf(returnCode);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}

	/**
	 * 鉴权补商户账
	 * @param merchantId
	 * @param transType
	 * @param paymentId
	 * @param transNo
	 * @param settleId
	 * @param channelCode
	 * @param feeAmount
	 * @param channelFeeAmount
	 * @param accountMark
	 * @param operationSource
	 * @return
	 */
	public String authentication(long merchantId, String transType, String paymentId, String transNo, String settleId, String channelCode, String feeAmount, String channelFeeAmount, String accountMark, String operationSource) {
		try {
			int returnCode = getClient().authentication(merchantId, transType, paymentId, transNo, settleId, channelCode, feeAmount, channelFeeAmount, accountMark, operationSource);
			return String.valueOf(returnCode);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}


	/**
	 * 补日间账务
	 * @param payMentSplDayTimeModel
	 * @return
	 */
	public String splPaymentDaytime(PayMentSplDayTimeModel payMentSplDayTimeModel){
		try {
			int returnCode = getClient().splPaymentDaytime(payMentSplDayTimeModel);
			return String.valueOf(returnCode);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}

	/**
	 * 刷新内部账户缓存
	 * @return
	 */
	public String refRefreshInterAccount() {
		try {
			int returnCode =  getClient().refRefreshInterAccount();
			return String.valueOf(returnCode);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}


}
