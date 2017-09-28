package com.heepay.manage.modules.account.rpc;

import com.heepay.common.util.Constants;
import com.heepay.rpc.account.service.MerchantAccountService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**          
* 
* 描    述：
* 创 建 者： zhangjx
* 创建时间： 2017年4月6日 上午10:59:56
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
public class MerchantAccountClient extends BaseClient {

	private static final String SERVICENAME = "MerchantAccountServiceImpl";

  @Override
  public void setServiceName(){
    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
  }
  
  @Override
  public void setNodename() {
    ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.ACCOUNTRPC);
  }
  

	private MerchantAccountService.Client getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new MerchantAccountService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 创建商户账户
	 * @param merchantId
	 * @param manchantLonginName
	 * @param accountName
	 * @param opSystemResouce
	 * @return
	 */
	public Boolean createMerchanAccount(long merchantId, String manchantLonginName, String accountName, String opSystemResouce) {
		try {
			return getClient().createMerchanAccount(merchantId, manchantLonginName, accountName, opSystemResouce);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return false;
	}
}
