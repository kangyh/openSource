package com.heepay.queue.consume;


import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.TransType;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.rabbitmq.client.Channel;

/**
 * 
 * 
 * 描    述：获取商户侧清算消息队列
 *
 * 创 建 者：xuangang
 * 创建时间： 2016年9月8日上午10:56:11 
 * 创建描述：
 * 
 * 修 改 者：  xuangang
 * 修改时间：2016-09-12
 * 修改描述： 接口变动
 * 
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Component
public class MqClearMerchantConsumeMain implements ChannelAwareMessageListener {
	
	@Autowired
	IMqMerchantClearing consumeClearingMerchantImpl;
	
	private static final Logger logger = LogManager.getLogger();
	
	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(Message message,Channel channel) {
		String receiveMsg = null;
		//是否强行消费， false：强行消费，true：不消费.
		//只有往server端发送失败时，才保留该消息
		boolean consumeFlg = false;
	    try {
	      receiveMsg = new String(message.getBody());
	      
	      if(StringUtil.isBlank(receiveMsg)){	    	         
	          return;	    	  
	      }
	      
	      logger.info("商户侧清算数据消息队列开始处理:{}", receiveMsg);
	      Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(receiveMsg, Map.class);
	      
	      if(null == map){	    	           
		       return;
	      }
	      boolean flag = true;
	      //校验商户侧是否存在空值
	      if(StringUtil.isBlank(map.get("merchantId"))||StringUtil.isBlank(map.get("productCode"))
	    		  ||StringUtil.isBlank(map.get("transNo"))||StringUtil.isBlank(map.get("successTime"))
	    		  ||StringUtil.isBlank(map.get("transType"))||StringUtil.isBlank(map.get("settleCyc"))){
	    	  
	    	  logger.info("商户侧接收交易系统数据存在空值，merchantId:{},productCode:{},transNo:{},successTime:{}, transType:{}, settleCyc:{}",
	    			  map.get("merchantId"), map.get("productCode"), map.get("transNo"), map.get("successTime"), map.get("transType"), map.get("settleCyc"));	    	  
	    	     	  
	    	  flag = false;
	      }
	      //校验通道侧是否存在空值
	      if(StringUtil.isBlank(map.get("channelCode")) || StringUtil.isBlank(map.get("currency")) ||
				  StringUtil.isBlank(map.get("payTime")) || StringUtil.isBlank(map.get("successAmount")) ||
				  StringUtil.isBlank(map.get("paymentId"))  || StringUtil.isBlank(map.get("costWay"))) {
				logger.info("通道侧接受交易系统数据存在空值，channelCode:{},currency:{},payTime:{},successAmount:{},costWay:{}",
						map.get("channelCode"),map.get("currency"),map.get("payTime"),map.get("successAmount"),
						map.get("costWay"),map.get("paymentId"));
				flag = false;
			}
	      
			// 如果是退款，则应有原支付单号和原交易单号
			if (TransType.REFUND.getValue().equals(map.get("transType"))) {
				if(StringUtil.isBlank(map.get("transNoOld"))) {
					logger.info("传入数据有误，退款类型transNoOld不能为null或空串:{}" , map.get("transNoOld"));
					flag = false;
				}			
				
				if(StringUtil.isBlank(map.get("paymentIdOld"))) {
					logger.info("传入数据有误，退款类型paymentIdOld不能为null或空串:{}" , map.get("paymentIdOld"));
					flag = false;
				}
			}
			
		 if(!flag){
			 consumeClearingMerchantImpl.saveClearExceptionData(receiveMsg);
			 return;
		 }
	     
	      try{	    	  
	    	  consumeClearingMerchantImpl.settleDataSave(receiveMsg);
	      }catch(TException ex){
	    	  logger.error("error-商户侧thrift连接server异常，该消息将不会被消费, {}", receiveMsg, ex);
	    	  //只有往server端发送失败时，才保留该消息
	    	  consumeFlg = true;	          
	          return;
	      }catch(Exception e){
	    	  logger.error("清算数据处理异常：{}", receiveMsg, e);
	      }
	      
	    } catch (Exception e) {
	    	logger.error("接收交易系统商户侧数据异常, {}", receiveMsg, e);
	
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
		           e1.printStackTrace();  
		           logger.error("error-商户侧处理消息异常", e1);
		     }
	    }

	    logger.info("{}, MessageVO: {}", Thread.currentThread().getId(), receiveMsg);
	  }
	}
