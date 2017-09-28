package com.heepay.billing.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.SettleDifferBathClient;

/**
 * 
 * 
 * 描    述：出款类差错批次处理，撤账和补账需要审核
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月15日上午10:54:10 
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
public class ExpendDifferBathJob extends AbstractSimpleElasticJob {

	@Autowired
	SettleDifferBathClient settleDifferBathClient;
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		logger.info("出款类差错批次数据撤账和补账开始");
		settleDifferBathClient.doExpendDifferBath();
	}

}
