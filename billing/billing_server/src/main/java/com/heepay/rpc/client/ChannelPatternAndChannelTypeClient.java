/*package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.manage.rpc.service.ChannelPatternAndChannelTypeService;

*//**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年5月22日下午2:54:15 
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
 *//*
@Service
public class ChannelPatternAndChannelTypeClient extends BaseClientDistribute {
	
    private static final String SERVICENAME = "channelPatternAndChannelTypeServiceImpl";
	
	private static final String NODENAME = "manager_server";
	
	private static final Logger log = LogManager.getLogger();

	@Resource(name = "manageServerClient")
	private ThriftClientProxy clientProxy;
	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
		
	}
	
	public ChannelPatternAndChannelTypeService.Client getClient(){
	    this.setServiceName();
	    this.setNodename();
	    this.setTMultiplexedProtocol();
	    return new ChannelPatternAndChannelTypeService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	
	// 查询channelpartern和channelType
	public String getChannelByChannelCode(String type, String label){
		ChannelPatternAndChannelTypeService.Client client = this.getClient();
		  try {
			  	String type1 = client.queryChannelPatternChannelTypeByType(type, label);
			  	return type1;
			} catch (TException e) {
				log.info("获取ChannelPatternAndChannelType信息异常{},{}", type, label, e);
			} finally {
				this.close();
			}
			return null;
	  }

}
*/