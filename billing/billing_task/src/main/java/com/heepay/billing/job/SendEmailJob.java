package com.heepay.billing.job;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.SendEmailClient;

/**
 * *
 * 
 * 
 * 描 述： 查找告警记录发送邮件
 *
 * 创 建 者： wangjie 创建时间： 2016年9月27日下午6:59:17 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Service
public class SendEmailJob extends AbstractSimpleElasticJob {

	@Autowired
	SendEmailClient sendEmailClient;

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		logger.info("查询告警记录定时任务开始。。。");
		sendEmailClient.sendEmail();

	}
}
