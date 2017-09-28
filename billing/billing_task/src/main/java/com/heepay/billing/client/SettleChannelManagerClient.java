package com.heepay.billing.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.SettleChannelManagerService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

/**
 * *
 * 
 * 
 * 描 述：下载对账文件客户端
 *
 * 创 建 者： wangjie 创建时间： 2016年9月25日下午3:38:14 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class SettleChannelManagerClient extends BaseClient {

	private static final String SERVICENAME = "SettleChannelManagerServiceImplWD";

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

	public SettleChannelManagerService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleChannelManagerService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	public void batchQueryRecord() {
		SettleChannelManagerService.Client client = this.getClient();

		try {

			client.query();
		} catch (Exception e) {
			logger.error("下载对账文件定时任务出错:" + e.getMessage());
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

}
