package com.heepay.billing.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.HangAccountService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;


/**
 * *
 * 
* 
* 描    述：挂账定时任务客户端
*
* 创 建 者： wangjie
* 创建时间：  2016年11月28日下午2:29:19
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
public class HangAccountClient extends BaseClient {

	private static final String SERVICENAME = "HangAccountServiceImplWJ";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);

	}

	public HangAccountService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new HangAccountService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	public void  hangAccount() {
		HangAccountService.Client client = this.getClient();
		
		try {
			client.queryHangAccountRecord();
		} catch (TException e) {
			logger.error("挂账任务出现异常:" + e);
		} finally {
			this.close();
		}
		
	}

}
