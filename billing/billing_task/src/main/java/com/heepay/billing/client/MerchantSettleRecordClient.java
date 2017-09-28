package com.heepay.billing.client;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.billing.model.SettleMerchantMessageModel;
import com.heepay.rpc.billing.model.SettleMerchantRecordModel;
import com.heepay.rpc.billing.service.SettleMerchantRecordService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;


/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月10日上午11:21:40
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
public class MerchantSettleRecordClient extends BaseClient{

	private static final String SERVICENAME = "SettleMerchantRecordServiceImpl";

	private static final String NODENAME = Constants.Clear.BILLINGRPC;

	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
		;
	}

	public SettleMerchantRecordService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new SettleMerchantRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
	}
	/**
	 * @author Administrator
	 * @param model
	 * @汇总用户端清算流水
	 */
	public void SettleMerchantSummarizing(SettleMerchantRecordModel model) {
		try {
		  getClient().saveSettleMerchantRecord(model);
		} catch (TException e) {
		  logger.error(e);
		} 
	}
	
	public List<SettleMerchantMessageModel> querySettleMerchantList(SettleMerchantRecordModel model){
		try {
			  return getClient().querySettleMessage(model);
			} catch (TException e) {
			  logger.error(e);
			}
		return null;
	}


}
