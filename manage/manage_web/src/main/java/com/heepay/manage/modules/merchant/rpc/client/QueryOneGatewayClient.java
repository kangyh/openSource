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
import com.heepay.route.thrift.quickpay.QueryOneRequestVO;
import com.heepay.route.thrift.quickpay.QueryOneResponseVO;
import com.heepay.route.thrift.quickpay.TQueryOneService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;


/**          
* 
* 描    述：网关快捷支付接口客户端
*
* 创 建 者： 刘栋  
* 创建时间： 2016年8月24日 上午11:43:36 
* 创建描述：调用网关快捷支付接口
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
public class QueryOneGatewayClient extends BaseClientDistribute {

	private static final String SERVICENAME = "queryOneService";
  
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
  
	private TQueryOneService.Client getClient() {
		this.setNodename();
		this.setServiceName();
		this.setTMultiplexedProtocol();
		return new TQueryOneService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
  
	public QueryOneResponseVO queryOne(QueryOneRequestVO requestVO) {
		try{
			return getClient().queryOne(requestVO);
		} catch(TException e) {
			log.error(e);
		} finally {
			this.close();
		}
		return null;
	}



}
