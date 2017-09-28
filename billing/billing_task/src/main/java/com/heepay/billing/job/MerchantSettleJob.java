package com.heepay.billing.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.BillingClearAPIClient;
import com.heepay.billing.client.MerchantSettleRecordClient;
import com.heepay.rpc.billing.model.SettleMerchantRecordModel;

/**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月10日上午11:09:41 
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
public class MerchantSettleJob extends AbstractSimpleElasticJob{

	@Autowired
	MerchantSettleRecordClient merchantSettleRecordClient;
	
	@Autowired
	BillingClearAPIClient billingClearAPIClient;
							  
	@Override
	public void process(JobExecutionMultipleShardingContext arg0) {
//		// TODO Auto-generated method stub
		SettleMerchantRecordModel settleMerchantRecordModel = new SettleMerchantRecordModel();
//		settleMerchantRecordModel.setCheckTime("2016-09-26");
		merchantSettleRecordClient.querySettleMerchantList(settleMerchantRecordModel);   //汇总用户侧清结算
		

	}

}
