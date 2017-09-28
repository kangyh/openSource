package com.heepay.queue.consume;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.StringUtil;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.client.ChannelResultClient;
import com.heepay.rpc.client.ConsumeClearMerchantClient;
import com.rabbitmq.client.Channel;


/***
 * 
* 
* 描    述：接收账务系统返回的结算批次处理结果，更新清结算系统
*
* 创 建 者： xuangang
* 创建时间：  2016年9月28日下午4:26:21
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
public class MqClearMerchantResultMain implements ChannelAwareMessageListener {

	@Autowired
	ConsumeClearMerchantClient mqHandleClient;
	
	@Autowired
	ChannelResultClient channelResultClient; 
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void onMessage(Message message,Channel channel) {
		String receiveMsg = null;
		//是否强行消费， false：强行消费，true：不消费.
		//只有往server端发送失败时，才保留该消息
		boolean consumeFlg = false;
	    try {
	      receiveMsg = new String(message.getBody());
	      logger.info("消息队列开始处理结算批次通知结果:{}", receiveMsg);
	      if(StringUtil.isBlank(receiveMsg)){	    	         
	          return;	    	  
	      }
	      
	      JSONObject myJsonObject = JSONObject.fromObject(receiveMsg);
	      logger.info("消息队列开始处理结算批次通知结果:{}",myJsonObject);
	     
	      String settleBath = myJsonObject.getString("settleId");// 批次号
	      String settleSource = myJsonObject.getString("settleSource"); //标识     
	      
	      ClearMerchantRecordModel ob = new ClearMerchantRecordModel();
	      ob.setSettleBath(settleBath);
	      	      
	      if(settleBath == null) {
	    	  return;
	      }	      
	     if(settleSource == null){
	    	 return;
	     }
	     try{
	    	 if("merchant".equals(settleSource)){
		    	 mqHandleClient.updateClearMerchantRecord(ob);	 //商户侧更新结算
		     }
		     else if("channle".equals(settleSource)){
		    	 channelResultClient.updateSettleStatus(settleBath); //通道侧更新结算
		     }
		      
		     else if("profit".equals(settleSource)){
		    	 ob.setTransType("profit");                      //分润
		    	 mqHandleClient.updateClearMerchantRecord(ob);	 //商户侧更新结算
		     }
	     }
	     catch(Exception ex){
	    	 logger.error("error-商户侧thrift连接server异常，该消息将不会被消费, {}", ob, ex);
	    	  //只有往server端发送失败时，才保留该消息
	    	  consumeFlg = true;	          
	          return;
	     }	     
	      
	    } catch (Exception e) {
	      logger.error("消息队列开始处理结算批次通知结果异常, {}", receiveMsg, e);
	    }
	    finally{
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
		           e1.printStackTrace();  
		           logger.error("error-商户侧处理消息异常", e1);
		     }
	    }
	    if (StringUtils.isBlank(receiveMsg)) {
	      logger.warn("消息队列开始处理结算批次通知结果is empty {}", receiveMsg);
	      return;
	    }
	    logger.info("{}, MessageVO: {}", Thread.currentThread().getId(), receiveMsg);
	  }


	}





