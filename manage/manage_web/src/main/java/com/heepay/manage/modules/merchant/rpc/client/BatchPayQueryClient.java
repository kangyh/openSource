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
import com.heepay.route.thrift.quickpay.BatchPayQueryRequestVO;
import com.heepay.route.thrift.quickpay.BatchPayQueryResponseVO;
import com.heepay.route.thrift.quickpay.TBatchPayQueryService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;


/**          
* 
* 描    述：批付查询client
*
* 创 建 者： yanxb
* 创建时间： 2017年1月5日 上午10:21:36 
* 创建描述：批付查询接口
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
public class BatchPayQueryClient extends BaseClientDistribute {

	private static final String SERVICENAME = "batchPayQueryService";
  
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
  
	private TBatchPayQueryService.Client getClient() {
		this.setNodename();
		this.setServiceName();
		this.setTMultiplexedProtocol();
		return new TBatchPayQueryService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
  
	/**
	 * 
	* @discription 批付银行单笔查询
	* @author yanxb       
	* @created 2017年1月6日 下午1:13:39     
	* @param requestVO
	* @return
	 */
	public BatchPayQueryResponseVO batchPayQuery(BatchPayQueryRequestVO requestVO) {
		try{
			return getClient().batchPayQuery(requestVO);
		} catch(TException e) {
			log.error(e);
		} finally {
			this.close();
		}
		return null;
	}



}
