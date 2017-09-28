package com.heepay.boss.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.boss.service.ProvidedDataService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
/**
 * *
 * 
* 
*
* 创 建 者： wangjie
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
public class ProvideDataClient extends BaseClientDistribute{
	

	private static final String SERVICENAME = "ProvideDataServiceImpl";

	private static final String NODENAME = "proxy_rpc";

	private static final Logger logger = LogManager.getLogger();
	@Resource(name = "bossClient")
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

	public  ProvidedDataService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new  ProvidedDataService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	public void  provideData() {
		ProvidedDataService.Client client = this.getClient();
		try {
			client.queryDate();
		} catch (TException e) {
			logger.error("提供数据定时任务出现异常{}",e);
		} finally {
			this.close();
		}
	}



}
