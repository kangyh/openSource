/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日上午9:57:56
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

import com.heepay.rpc.tpds.service.CustomerCwService;

/**
 * 
 *
 * 描    述：客户的充值和提现的客户端
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月16日 上午10:18:30
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
public class CustomerClient extends BaseClientDistribute{

	private static final String SERVICENAME = "CustomerCwServiceImpl";

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

	public CustomerCwService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new CustomerCwService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	
	/**
	 * 
	 * @方法说明：客户充值
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String customerCharge(String reqHeader, String body) {
		CustomerCwService.Client client = this.getClient();
		try {
			 String charge = client.customerCharge(reqHeader, body);
			 return charge;
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户充值RPC服务异常---->{}", e);
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：客户提现
	 * @时间：2017年2月16日 上午10:31:51
	 * @创建人：wangdong
	 */
	public String customerWithdraw(String reqHeader, String body) {
		CustomerCwService.Client client = this.getClient();
		try {
			 String withdraw = client.customerWithdraw(reqHeader, body);
			 return withdraw;
		} catch (TException e) {
			e.printStackTrace();
			log.info("客户提现RPC服务异常---->{}", e);
		} finally {
			this.close();
		}
		return null;
	}
}
