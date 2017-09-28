package com.heepay.manage.modules.account.rpc;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.service.AccountRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class AccountOpClient extends BaseClient {

	private static final String SERVICENAME = "AccountRecordServiceImpl";

	private static final Logger logger = LogManager.getLogger();

  @Override
  public void setServiceName(){
    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
  }
  
  @Override
  public void setNodename() {
    ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
  }
  

	private AccountRecordService.Client getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new AccountRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 刷新内部账户缓存
	 * @return
	 */
	public String refRefreshInterAccount() {
		try {
			logger.info("刷新内部账户缓存");
			getClient().refRefreshInterAccount();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return "";
	}
}
