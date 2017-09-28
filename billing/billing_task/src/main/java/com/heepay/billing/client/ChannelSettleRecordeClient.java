package com.heepay.billing.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.SettleChannelRecordModel;
import com.heepay.rpc.billing.service.ClearChannelRecordService;
import com.heepay.rpc.billing.service.SettleChannelRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.payment.service.BatchCollectionRecordDetailService;

/**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月10日上午11:04:11 
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
public class ChannelSettleRecordeClient extends BaseClient {
	
	private static final String SERVICENAME = "SettleChannelRecordServiceImpl";

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
	// 通道侧
	public SettleChannelRecordService.Client getChannelClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleChannelRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	
	public String sendChannelSettleRecord() {
		try {
			String settleChannelRecordMessage = getChannelClient().getSettleChannelRecordMessage();
			return settleChannelRecordMessage;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

}



