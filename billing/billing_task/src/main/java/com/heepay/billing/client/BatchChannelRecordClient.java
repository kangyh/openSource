 package com.heepay.billing.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.billing.model.ClearChannelRecordModel;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.billing.model.SettleChannelRecordModel;
import com.heepay.rpc.billing.model.SettleMerchantRecordModel;
import com.heepay.rpc.billing.service.ClearChannelRecordService;
import com.heepay.rpc.billing.service.ClearMerchantRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;



/***
 * 
* 
* 描    述：定时任务查询
*
* 创 建 者：wangl
* 创建时间：  2016年9月8日下午5:36:41
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
public class BatchChannelRecordClient extends BaseClient{

	private static final String SERVICENAME = "ClearChannelRecordServiceW";

	private static final String NODENAME = "billing_rpc";

	private static final Logger logger = LogManager.getLogger();
	
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
		return new ClearChannelRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	

	public List<ClearChannelRecordModel> batchQueryRecord(ClearChannelRecordModel clearMerchantRecordModel) {
		ClearChannelRecordService.Client client = this.getClient();
		try {
//			
//			List<ClearChannelRecordModel> queryByConditions = client.queryByConditions(clearMerchantRecordModel);
//			
//			return queryByConditions;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		return null;
	}
	
	//用户侧的对账状态修改
	public void updateClearRecordStatus() {
		try {
			getClient().update(null);
		} catch (TException e) {
			logger.error(e);
		}
		
	}

	
	//定时汇总查询通道侧的汇总
		public List<ClearChannelRecordModel> sendChannelSettleRecord() {
		try {
			//ClearChannelRecordModel ClearChannelRecordModel=new ClearChannelRecordModel();
			List<ClearChannelRecordModel> saveSettleChannelRecord = getClient().query(null);
			return saveSettleChannelRecord;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
		// 修改结算单状态为结算中
		public void updateChannelStatus() {
			try {
				getClient().update(null);
			} catch (TException e) {
				logger.error(e);
			}
		}

}
