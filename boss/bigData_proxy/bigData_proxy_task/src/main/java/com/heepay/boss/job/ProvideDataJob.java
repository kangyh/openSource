package com.heepay.boss.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.boss.client.ProvideDataClient;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日上午9:59:16
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
public class ProvideDataJob extends AbstractSimpleElasticJob {

	@Autowired
	ProvideDataClient provideDataClient;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {

		logger.info("提供数据源任务开始执行。");
		try {
			provideDataClient.provideData();
		} catch (Exception e) {
			logger.error("提供数据源任务出现异常：" + e);
		}

	}
}
