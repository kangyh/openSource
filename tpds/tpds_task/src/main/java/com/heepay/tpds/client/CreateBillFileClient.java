package com.heepay.tpds.client;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.tpds.service.CreateBillFileService;



/**
 * *
 * 
* 
* 描    述：生成队长文件定时任务客户端
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
public class CreateBillFileClient extends BaseClientDistribute {

	private static final String SERVICENAME = "CreateBillFileServiceImpl";

	private static final String NODENAME = "tpds_rpc";

	private static final Logger logger = LogManager.getLogger();
	
	@Resource(name = "tpdsClient")
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

	public CreateBillFileService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new CreateBillFileService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	public void  createFile() {
		CreateBillFileService.Client client = this.getClient();
		try {
			client.query();
		} catch (TException e) {
			logger.error("资金存管生产对账文件出现异常{}",e);
		} finally {
			this.close();
		}
		
	}


}
