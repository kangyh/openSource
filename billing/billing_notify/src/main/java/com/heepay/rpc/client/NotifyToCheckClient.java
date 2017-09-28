package com.heepay.rpc.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.NotifyToCheckService;
import com.heepay.rpc.billing.service.NotifyToCheckService.Client;

/**
 * 
 * 
 * 描    述：网关通知对账客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年8月17日下午4:06:31 
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
public class NotifyToCheckClient extends BaseClient {
	
	private static final String SERVICENAME = "NotifyToCheckServiceServiceImpl";

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

	public NotifyToCheckService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new NotifyToCheckService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}

	/**
	 * 
	 * @方法说明：网关通知对账
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @param remoteAdress
	 * @param date
	 * @时间：2017年8月17日下午4:13:29
	 */
	public void notifyToCheck(String channelCode, String channelType, String remoteAdress, String date) {
		NotifyToCheckService.Client client = this.getClient();
		try {
			client.notifyToCheck(channelCode, channelType, remoteAdress, date);
		} catch (TException e) {
			logger.error("网关通知对账调用billing服务出错,{},{},{},{}", channelCode, channelType, remoteAdress, date);
		}finally {
			this.close();
		}
	}
}
