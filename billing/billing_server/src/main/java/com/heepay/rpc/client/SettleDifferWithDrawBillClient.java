package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.rpc.payment.service.AccountRecordService;
import com.heepay.rpc.payment.service.AccountRecordService.Client;

/**
 * 
 * 
 * 描    述：通道侧和用户侧撤账客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月8日上午10:14:58 
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
public class SettleDifferWithDrawBillClient extends BaseClientDistribute {
	private static final String SERVICENAME = "AccountRecordServiceImpl";
	
	private static final String NODENAME = "payment_rpc";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "paymentServerClient")
	private ThriftClientProxy clientProxy;
  
	
	 @Override
	  public void setServiceName(){
	    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	  }
	  
	  @Override
	  public void setNodename() {
	    ClientThreadLocal.getInstance().setNodename(NODENAME);
	  }
	  
	  @Override
	  public ThriftClientProxy getClientProxy() {
	    return clientProxy;
	  }
	  public AccountRecordService.Client getClient(){
		    this.setServiceName();
		    this.setNodename();
		    this.setTMultiplexedProtocol();
		    return new AccountRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	 }


	/**
	 * 
	 * @方法说明：商户侧撤账,通道侧撤账一块处理
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @return
	 * @时间：2016年11月8日上午10:21:06
	 */
	public int doMerchantWithDrawBill(long merchantId, String transNo, String transType, String payType) {
		AccountRecordService.Client client = this.getClient();
		try {
			int flag = client.revokeAccount(merchantId, transNo, transType, payType);
			return flag;
		} catch (Exception e) {
			log.error("商户侧撤账异常！", transNo, e);
		} finally {
			this.close();
		}
		return 0;
	}
}
