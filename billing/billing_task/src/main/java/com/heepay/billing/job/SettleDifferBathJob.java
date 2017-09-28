package com.heepay.billing.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.SettleDifferBathClient;

/**
 * 
 * 
 * 描    述：汇总差错记录，生成差错批次
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年10月27日下午3:44:50 
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
@Component
public class SettleDifferBathJob extends AbstractSimpleElasticJob {
	
	@Autowired
	SettleDifferBathClient settleDifferBathClient;
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		logger.info("汇总差错批次数据开始");
		settleDifferBathClient.getSettleDifferBath();
		
	}

}
