/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日上午9:51:16
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
package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.tpds.service.FundTransService;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月16日上午9:51:16
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
@Component
public class FundTransClient extends BaseClientDistribute {
	
	private static final String SERVICENAME = "FundTransServiceImpl";

	private static final String NODENAME = "tpds_rpc";

	private static final Logger log = LogManager.getLogger();
	
	@Resource(name = "tpdsClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
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

	public FundTransService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new FundTransService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	/**
	 * 资金交易T00002
	 * @param reqHeader
	 * @param body
	 */
	public String fundTrans(String reqHeader, String body) {
		
		FundTransService.Client client = this.getClient();
		try {
			 return client.fundTrans(reqHeader, body);
			 
		} catch (TException e) {
			e.printStackTrace();
			log.info("资金交易T00002异常---->{}", e);
		} finally {
			this.close();
		}
		return "";
	}
	
}