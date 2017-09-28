package com.heepay.warning.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.warning.client.MonitorClient;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月16日 下午6:04:32
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
public class WarningSendJob extends AbstractSimpleElasticJob {

	@Autowired
	MonitorClient monitorClient;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		
		logger.info("监控读消息开始");
		try {
			monitorClient.sendMsg();
		} catch (Exception e) {
			logger.error("监控读消息异常："+e);
		}
		logger.info("监控读消息结束");
	}

}

 