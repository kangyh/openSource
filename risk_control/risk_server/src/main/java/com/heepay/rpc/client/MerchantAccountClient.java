package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.common.util.Constants;
import com.heepay.rpc.payment.service.MerchantAccountService;

public class MerchantAccountClient extends BaseClientDistribute {

	private static final String SERVICENAME = "MerchantAccountServiceImpl";
	private static final Logger log = LogManager.getLogger();
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
  
	private MerchantAccountService.Client getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new MerchantAccountService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	

}
