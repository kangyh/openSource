package com.heepay.queue.consume;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.TransType;
import com.heepay.rpc.client.NotifyToCheckClient;
import com.rabbitmq.client.Channel;

/**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年8月17日下午3:56:59 
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
public class MqNotifyToCheckConsumeMain implements ChannelAwareMessageListener {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	NotifyToCheckClient notifyToCheckClient;
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String receiveMsg = null;
		//是否强行消费， false：强行消费，true：不消费.
		//只有往server端发送失败时，才保留该消息
		boolean consumeFlg = false;
	    try {
	      receiveMsg = new String(message.getBody());
	      
	      if(StringUtil.isBlank(receiveMsg)){	    	         
	          return;	    	  
	      }
	      
	      logger.info("网关通知对账消息队列开始处理:{}", receiveMsg);
	      Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(receiveMsg, Map.class);
	      
	      if(null == map){	    	           
		       return;
	      }
	      //校验通道侧是否存在空值
	      if(StringUtil.isBlank(map.get("channelPartner")) || StringUtil.isBlank(map.get("channelType")) ||
				  StringUtil.isBlank(map.get("ip")) || StringUtil.isBlank(map.get("path"))) {
				logger.info("网关通知对账存在空值，channelCode:{},channelType:{},remoteAdress:{},path:{}",
						map.get("channelPartner"),map.get("channelType"),map.get("ip"),map.get("path"));
				return;
			}
	     
	      String channelCode = map.get("channelPartner");
	      String channelType = map.get("channelType");
	      String remoteAdress = map.get("ip");
	      String path = map.get("path");
	      int indexOf = path.lastIndexOf("/");
	      String date = path.substring(indexOf - 10, indexOf);
	      
	      try{	    	  
	    	  notifyToCheckClient.notifyToCheck(channelCode, channelType, remoteAdress, date);
	      }catch(Exception ex){
	    	  logger.error("error-网关通知对账异常，该消息将不会被消费, {}", receiveMsg, ex);
	    	  //只有往server端发送失败时，才保留该消息
	    	  consumeFlg = true;	          
	          return;
	      }
	      
	    } catch (Exception e) {
	    	logger.error("网关通知对账数据异常, {}", receiveMsg, e);
	
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
		           logger.error("error-网关通知对账处理消息异常", e1);
		     }
	    }

	    logger.info("{}, MessageVO: {}", Thread.currentThread().getId(), receiveMsg);
	  }
		
	}


