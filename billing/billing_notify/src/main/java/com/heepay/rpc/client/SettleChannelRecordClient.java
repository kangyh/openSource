package com.heepay.rpc.client;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.ClearChannelRecordModel;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.service.ClearChannelRecordService;
import com.heepay.rpc.billing.service.ClearMerchantRecordService;


/***
 * 
* 
* 描    述：修改通道侧的清算记录
*
* 创 建 者：wangl
* 创建时间：  2016年9月20日下午5:54:30
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
public class SettleChannelRecordClient extends BaseClient {

	private static final String SERVICENAME = "ClearChannelRecordServiceW";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;
	
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
		
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
		
	}
	public ClearChannelRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new ClearChannelRecordService.Client(ClientThreadLocal
				.getInstance().getProtocol());
	}
	
	public void updateSettleStatus(String settleBath) {
		ClearChannelRecordService.Client client = this.getClient();
		ClearChannelRecordModel clearChannel=new ClearChannelRecordModel();
		clearChannel.setSettleBath(settleBath);
		
		try {
			client.update(clearChannel);
			
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}

}
