package com.heepay.queue.consume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.rpc.billing.model.ClearShareProfitDetailModel;
import com.heepay.rpc.billing.model.ClearShareProfitModel;
import com.heepay.rpc.client.ClearProfitClient;
import com.rabbitmq.client.Channel;



/***
 * 
* 
* 描    述：商户侧获取交易系统分润数据
*
* 创 建 者： xuangang
* 创建时间：  2016年11月3日下午2:46:14
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
public class MqClearProfitMain implements ChannelAwareMessageListener{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearProfitClient clearProfitClient;
	
	@Override
	public void onMessage(Message message,Channel channel) {
		String receiveMsg = null;
		//是否强行消费， false：强行消费，true：不消费.
		//只有往server端发送失败时，才保留该消息
		boolean consumeFlg = false;
		
	    try {
	      receiveMsg = new String(message.getBody());
//	      JSONObject myJsonObject = JSONObject.fromObject(receiveMsg);
	      logger.info("商户侧获取交易系统分润数据:{}", receiveMsg);
	      
	      Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(receiveMsg, Map.class);	     
	     
	      ClearShareProfitModel clearShareProfitModel = new ClearShareProfitModel();
	      
	      List<Map<String, String>> list = (List<Map<String, String>>)map.get("shareDetailList");
	      
	      //分润明细列表
	      List<ClearShareProfitDetailModel> detailList = new ArrayList<ClearShareProfitDetailModel>();
	      
	      for(Map<String, String> vo : list){
	    	  ClearShareProfitDetailModel clearShareProfitDetailModel = new ClearShareProfitDetailModel();
	    	  clearShareProfitDetailModel.setMerchantId(vo.get("merchantId"));
	    	  clearShareProfitDetailModel.setProfitAmount(vo.get("profitAmount"));
	    	  clearShareProfitDetailModel.setTransNo(vo.get("transNo"));
	    	  clearShareProfitDetailModel.setShareDetailId(vo.get("shareDetailId"));
	    	  clearShareProfitDetailModel.setTransType(vo.get("transType"));
	    	  
	    	  detailList.add(clearShareProfitDetailModel);
	      }
	      
	      clearShareProfitModel.setClearShareProfitDetail(detailList);   //分润明细
	      clearShareProfitModel.setMerchantId(Integer.valueOf(map.get("merchantId").toString()));            //商户编码
	      clearShareProfitModel.setTransNo(map.get("transNo")==null?"":map.get("transNo").toString());       //交易单号
	      clearShareProfitModel.setIsShare(map.get("share")==null?"":map.get("share").toString());           //是否分润
	      clearShareProfitModel.setShareId(map.get("shareId")==null?"":map.get("shareId").toString());       //分润号
	       try{
	 	      clearProfitClient.saveShareProfitRecord(clearShareProfitModel);
		      
		      logger.info("商户侧交易系统分润数据发送到server端处理完毕:{}", receiveMsg);
	       }catch(Exception ex){
		    	 logger.error("error-分润thrift连接server异常，该消息将不会被消费, {}", clearShareProfitModel, ex);
		    	  //只有往server端发送失败时，才保留该消息
		    	  consumeFlg = true;	          
		          return;
		     }	

	      
	    } catch (Exception e) {
	      logger.error("商户侧获取交易系统分润数据异常, {}",receiveMsg, e);
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
	      logger.warn("商户侧获取交易系统分润数据为空！ {}", receiveMsg);
	      return;
	    }
	  
	  }
		
}


