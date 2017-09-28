package com.heepay.billing.client;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.SendEmailService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

/**
 * *
 * 
 * 
 * 描 述： 查找告警记录发送邮件客户端
 *
 * 创 建 者： wangjie 创建时间： 2016年9月25日下午3:38:14 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class SendEmailClient extends BaseClient {

	private static final String SERVICENAME = "SendEmailServiceImplWD";

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

	public SendEmailService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SendEmailService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	public void sendEmail() {
		SendEmailService.Client client = this.getClient();

		try {

			client.query();
			logger.debug("告警记录定时任务执行完成!");
		} catch (Exception e) {
			logger.error("告警记录定时任务出错" + e.getMessage());
		} finally {
			this.close();
		}
	}

}
