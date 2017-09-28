package com.heepay.billing.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.ClearMerhantRecordClient;

/**
 * T+0日结算到商户
 * 
 * 名称：2小时汇总查询发送
 * 
 * 创建者： wangl 
 * 创建时间：2016年9月7日 
 * 创建描述：
 * 
 * 修改者： 
 * 修改时间： 
 * 修改描述：
 * 
 *
 * 审核者： 
 * 审核时间： 
 * 审核描述：
 * 
 *
 */
@Service
public class BillingSettleToMerchantTimedJob extends AbstractSimpleElasticJob {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearMerhantRecordClient clearMerhantRecordClient;

	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		
		logger.info("2小时t+0商户汇总任务开始！");	
			clearMerhantRecordClient.batchQueryRecord();
		logger.info("2小时t+0商户汇总任务执行结束，等待下一次执行！");	
	}
	
	
}
