/**     
*   Copyright © since 2008. All Rights Reserved 
*   汇元银通（北京）在线支付技术有限公司   www.heepay.com    
*/
    
package com.heepay.manage.modules.merchant.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.gateway.thrift.system.THeartbeat;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;


/**          
* 
* 描    述：网关service监控
*
* 创 建 者： ly
* 创建时间： 2016年12月12日 下午1:42:51 
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
public class GatewayServiceClient extends BaseClientDistribute {

	private static final String SERVICENAME = "heartbeatService";
  
	private static final Logger log = LogManager.getLogger();
  
	@Resource(name = "gatewayClient")
	private ThriftClientProxy clientProxy;

	@Override
	public ThriftClientProxy getClientProxy() {
	  
  		return clientProxy;
	}  
  
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.GATEWAYRPC);
	}
  
	private THeartbeat.Client getClient() {
		this.setNodename();
		this.setServiceName();
		this.setTMultiplexedProtocol();
		return new THeartbeat.Client(ClientThreadLocal.getInstance().getProtocol());
	}
  
	public String queryGatewayService() {
		try{
			return getClient().heartbeat();
		} catch(TException e) {
			log.error(e);
		} finally {
			this.close();
		}
		return null;
	}



}
