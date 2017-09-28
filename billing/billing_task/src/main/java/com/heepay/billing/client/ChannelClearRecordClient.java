package com.heepay.billing.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.ClearChannelRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

/**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月12日下午5:37:17 
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
public class ChannelClearRecordClient extends BaseClient {

	private static final String SERVICENAME = "ClearChannelRecordServiceImpl";

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
	public ClearChannelRecordService.Client getClearClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ClearChannelRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	
	// 修改清算单状态为已对账
	public void updateClearRecordStatus() {
		try {
			getClearClient().update(null);
		} catch (TException e) {
			logger.error(e);
		}
	}

}
