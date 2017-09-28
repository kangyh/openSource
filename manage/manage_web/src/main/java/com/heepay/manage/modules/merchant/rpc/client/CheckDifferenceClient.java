package com.heepay.manage.modules.merchant.rpc.client;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.service.CheckDifferenceService;

/**
 * 
* 
* 描    述：
*
* 创 建 者：   杨春龙  
* 创建时间： 2017年9月5日 下午4:36:51 
* 创建描述： 账务数据和 交易数据日终校验客户端
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
public class CheckDifferenceClient extends BaseClientDistribute{
	
	private static final String SERVICENAME = "CheckDifferenceServiceImpl";


	@Resource(name = "paymentClient")
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
		ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);		
	}

	public CheckDifferenceService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new CheckDifferenceService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
		
	public int checkDifferencePaymentAndAccount(String accountDate ){
		
		CheckDifferenceService.Client client=getClient();
		
		try {
			return client.checkDifferencePaymentAndAccount(accountDate);
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return 0;				
	}	
}
