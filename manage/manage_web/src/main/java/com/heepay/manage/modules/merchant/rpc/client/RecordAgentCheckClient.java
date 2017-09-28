/**     
*   Copyright © since 2008. All Rights Reserved 
*   汇元银通（北京）在线支付技术有限公司   www.heepay.com    
*/
    
package com.heepay.manage.modules.merchant.rpc.client;

import com.heepay.agent.common.constants.RpcConstants;
import com.heepay.agent.rpc.thrift.AgentCheckObject;
import com.heepay.agent.rpc.thrift.AgentCheckThrift;
import com.heepay.agent.rpc.thrift.AgentUserObject;
import com.heepay.agent.rpc.thrift.AgentUserThrift;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class RecordAgentCheckClient extends BaseClientDistribute {

	private static final String SERVICENAME = "AgentCheckThriftImpl";
  
	private static final Logger log = LogManager.getLogger();
  
	@Resource(name = "agentClient")
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
		ClientThreadLocal.getInstance().setNodename(RpcConstants.RPC_NODENAME_AGENT);
	}
  
	private AgentCheckThrift.Client getClient() {
		this.setNodename();
		this.setServiceName();
		this.setTMultiplexedProtocol();
		return new AgentCheckThrift.Client(ClientThreadLocal.getInstance().getProtocol());
	}

   // 保存审核信息
	public int insert(AgentCheckObject agentCheckObject) throws TException {
		try {
			return getClient().insert(agentCheckObject);
		} finally {
			this.close();
		}
	}



}
