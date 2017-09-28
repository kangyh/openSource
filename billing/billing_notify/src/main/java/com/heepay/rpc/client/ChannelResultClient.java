package com.heepay.rpc.client;

import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.SettleChannelRecordModel;
import com.heepay.rpc.billing.service.ClearChannelRecordService;
import com.heepay.rpc.billing.service.SettleChannelRecordService;
import com.heepay.rpc.billing.service.SettleChannelRecordService.Client;

/**
 * 
 * 
 * 描    述：通道侧核账结果客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月12日下午2:36:11 
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
public class ChannelResultClient extends BaseClient {

	private static final String SERVICENAME = "SettleChannelRecordServiceImpl";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;
	
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
		
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
		
	}
	public SettleChannelRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleChannelRecordService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}

	/**
	 * 修改结算记录状态
	 */
	public void updateSettleStatus(String settleBath) {
		SettleChannelRecordService.Client client = this.getClient();
		try {
			SettleChannelRecordModel settleChannelRecordModel = new SettleChannelRecordModel();
			settleChannelRecordModel.setSettleBath(settleBath);
			client.updateSettleChannelRecord(settleChannelRecordModel);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.close();
		}
	}

}
