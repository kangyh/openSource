package com.heepay.queue.consume;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 * 
 * 
 * 描 述：通道侧消息队列
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月8日上午10:56:11 
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
public class MqClearChannelConsumeMain implements ChannelAwareMessageListener {

	private static final Logger logger = LogManager.getLogger();

	@Override
	public void onMessage(Message message,Channel channel) {
		String receiveMsg = null;
		//只有往server端发送失败时，才保留该消息
		boolean consumeFlg = false;
		try {

			receiveMsg = new String(message.getBody());

			logger.info("通道侧消息队列只消费，不处理:{}", receiveMsg);

			try {
				
				//商户侧、通道侧队列合并，该队列仍然被消费，但是诗句不做任何处理
				//consumeClearChannelImpl.billingHandle(ob);
			} catch (Exception e) {
//				logger.error("error-通道侧thrift连接server异常，该消息将不会被消费, {}", ob, e);
		    	 //只有往server端发送失败时，才保留该消息
		    	 consumeFlg = true;	          
		         return;
			}

		} catch (Exception e) {
			logger.error("receive message has an error, {}", e);
		}finally{
	    	 try {
	    		 if(consumeFlg){
	    			  //该消息保留
			          channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
	    		 }
	    		 else if(!consumeFlg){
	    			  //强行消费
			          channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
	    		 }
		     } catch (IOException e1) {
		           logger.error("error-通道侧处理消息异常", e1);
		     }
	    }
		
		if (StringUtils.isBlank(receiveMsg)) {
			logger.error("receiveMsg is empty {}", receiveMsg);
			return;
		}
		logger.info("{}, MessageVO: {}", Thread.currentThread().getId(), receiveMsg);
	}
}
