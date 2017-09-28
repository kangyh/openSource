package com.heepay.rpc.client;

import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.ClearChannelRecordModel;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.model.SettleMerchantRecordModel;
import com.heepay.rpc.billing.service.ClearChannelRecordService;
import com.heepay.rpc.billing.service.ClearMerchantRecordService;
import com.heepay.rpc.billing.service.SettleChannelRecordService;
import com.heepay.rpc.billing.service.SettleChannelRecordService.Client;
import com.heepay.rpc.billing.service.SettleMerchantRecordService;


/***
 * 
* 
* 描    述：用户侧等待消息 订阅账务的核账结果12
*
* 创 建 者：wangl
* 创建时间：  2016年9月21日上午9:39:25
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
public class MerchantResultClient extends BaseClient {

	private static final String SERVICENAME = "SettleMerchantRecordServiceW";
	private static final String NODENAME = Constants.Clear.BILLINGRPC;
	
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
		
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
		
	}
	public SettleMerchantRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleMerchantRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 接受消息 修改结算记录状态
	 */
	public void updateSettleStatus(String settleBath) {
		SettleMerchantRecordService.Client client = this.getClient();
		try {
			SettleMerchantRecordModel settleMerchant=new SettleMerchantRecordModel();
			//将状态设置进模型对象参数中
			settleMerchant.setSettleBath(settleBath);
			
			client.updateSettleMerchantRecord(settleMerchant);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.close();
		}
	}
}
