package com.heepay.payment.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.api.listener.ElasticJobListener;
/**
 * 监听器的配置 分为每个节点都执行的和单个某节点执行的
 * 可以在任务执行前执行，也可以在任务执行后执行
 * 
 * */
public class MyJobListener implements ElasticJobListener{
	private static final Logger logger = LogManager.getLogger();
	@Override
	public void beforeJobExecuted(JobExecutionMultipleShardingContext shardingContext) {
		logger.info("在定时任务"+shardingContext.getJobName()+"之前执行监听器 !");
		
	}

	@Override
	public void afterJobExecuted(JobExecutionMultipleShardingContext shardingContext) {
		logger.info("定时任务"+shardingContext.getJobName()+"之后执行的监听器  !");
		
	}

}
