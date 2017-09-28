package com.heepay.billing.job;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import com.dangdang.ddframe.job.plugin.job.type.simple.AbstractSimpleElasticJob;
import com.heepay.billing.client.ChannelClearRecordClient;
import com.heepay.billing.client.ChannelSettleRecordeClient;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.vo.SettleChannelMessage;
import com.heepay.vo.SettleChannelRecordMessage;

/**
 * 
 * 
 * 描    述：通道侧定时任务
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月10日上午11:03:28 
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
public class ChannelSettleJob extends AbstractSimpleElasticJob {
	
	@Autowired
	ChannelSettleRecordeClient channelSettleRecordeClient;
	@Autowired
	ChannelClearRecordClient channelClearRecordClient;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void process(JobExecutionMultipleShardingContext shardingContext) {
		try {
			// 修改用户侧和通道侧清算单记录
			channelClearRecordClient.updateClearRecordStatus();
			
			String settleChannelRecordMessageJson = channelSettleRecordeClient.sendChannelSettleRecord();
			
			/*JsonMapperUtil json = new JsonMapperUtil();
			SettleChannelRecordMessage settleChannelRecordMessage = json.fromJson(settleChannelRecordMessageJson, SettleChannelRecordMessage.class);
			Map<String, List<SettleChannelMessage>> settleChannelRecordMap = settleChannelRecordMessage.getSettleChannelRecordMap();
			Set<String> keySet = settleChannelRecordMap.keySet();
			// 将结算批次和 清算明细逐条发送
			for (String channelCode : keySet) {
				List<SettleChannelMessage> messageList = settleChannelRecordMap.get(channelCode);
				for (SettleChannelMessage settleChannelMessage : messageList) {
					String settleChannelMessageJson = json.toJson(settleChannelMessage);
					queueProducerSender.sendDataToQueue(settleChannelMessageJson);
				}
			}
			*/
		} catch (Exception e) {
			logger.error(e);
		}
	}

}







