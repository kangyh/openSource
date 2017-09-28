package com.heepay.manage.modules.tpds.web.rpc;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.tpds.service.FileAdviceService;
import com.heepay.rpc.tpds.service.FileAdviceService.Client;

/**
 * *
 * 
* 
* 描    述：补发对账通知接口
*
* 创 建 者： wangjie
* 创建时间：  2017年7月18日下午2:55:13
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
public class TpdsAdviceClient extends BaseClientDistribute {

	private static final String SERVICENAME = "FileAdviceServiceImpl";
	private static final String NODENAME = "tpds_rpc";
	private static final Logger logger = LogManager.getLogger();
	
	
	@Resource(name = "tpdsAccountClient")
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

	public Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new FileAdviceService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	
	public String httpAdvice(String adviceUrl, String systemNo, String fileName) {
		
		FileAdviceService.Client client = this.getClient();
		try {
			return client.adviceHttp(adviceUrl, systemNo, fileName);
		} catch (TException e) {
			logger.error("出现异常{}", e.getMessage());
			return null;
		} finally {
			this.close();
		}
	}
}