package com.heepay.queue.consume;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.client.MerchantResultClient;

/**
 * 
 * 
 * 描    述：用户侧核账结果通知
 *
 * 创 建 者：wangl
 * 创建时间： 2016年9月12日下午2:26:28 
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
public class MqMerchantResultconsumeMain implements MessageListener {

	@Autowired
	private MerchantResultClient merchantResultClient;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void onMessage(Message message) {
		String receiveMsg = null;
		try {
				receiveMsg = new String(message.getBody());
			    JSONObject myJsonObject = JSONObject.fromObject(receiveMsg);
				logger.info("消息队列开始处理通知结果:{}",myJsonObject);
		     
				
					String settleBath = myJsonObject.getString("settleId");
					if(settleBath != "") {  // 成功
						merchantResultClient.updateSettleStatus(settleBath);
			
				}else { // 失败
					return;
				}
		} catch (Exception e) {
			logger.error("receive message has an error, {}", e);
		}
	}

}
