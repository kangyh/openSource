package com.heepay.billing.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.service.ClearMerchantRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

/***
 * 
 * 
 * 描 述：定时任务查询2小时跑批
 *
 * 创 建 者：wangl 创建时间： 2016年9月8日下午5:36:41 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class ClearMerhantRecordClient extends BaseClient {

	private static final String SERVICENAME = "ClearMerchantRecordServiceL";

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

	public ClearMerchantRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ClearMerchantRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 
	 * @方法说明：定时任务查询2小时跑批
	 * @时间：Nov 8, 2016
	 * @创建人：wangl
	 */
	public void batchQueryRecord() {
		ClearMerchantRecordService.Client client = this.getClient();
		try {
			client.getClearMerchantRecord();
		} catch (Exception e) {
			logger.error("数据库没有查询数据异常------->", e);
		} finally {
			this.close();
		}
	}

}
