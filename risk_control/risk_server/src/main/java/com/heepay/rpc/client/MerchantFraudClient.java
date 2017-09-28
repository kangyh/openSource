package com.heepay.rpc.client;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.manage.rpc.service.AntifraudService;
import com.heepay.manage.rpc.service.AntifraudThrift;

public class MerchantFraudClient extends BaseClientDistribute {

	private static final String SERVICENAME = "antifraudServiceImpl";
	private static final String NODENAME = "manager_server";
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "managerClient")
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
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}
  
	private AntifraudService.Client getClient(){
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new AntifraudService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	public List<AntifraudThrift> getAntifraudInfo(List<String> merchantIds)
	{
		try
		{
			//return this.getClient().getAntifraudInfo(merchantIds);
			return null;
		}
		catch(Exception ex )
		{
			log.error("获取账户信息异常:{}",ex.getMessage());
			return null;
		}
	}
	}
