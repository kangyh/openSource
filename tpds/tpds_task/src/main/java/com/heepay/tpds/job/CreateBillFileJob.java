package com.heepay.tpds.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.tpds.client.CreateBillFileClient;

/**
 * *
 * 
* 
* 描    述：资金存管生成对账文件定时任务
*
* 创 建 者： wangjie
* 创建时间：  2016年11月28日下午2:30:29
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
public class CreateBillFileJob extends AbstractSimpleElasticJob {

	@Autowired
	CreateBillFileClient createBillFileClient;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		
		logger.info("生成对账文件任务开始执行。");
		try {
			createBillFileClient.createFile();
		} catch (Exception e) {
			logger.error("生成对账文件任务出现异常："+e);
		}

	}

}
