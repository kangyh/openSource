package com.heepay.manage.modules.payment.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.TransType;
import com.heepay.rpc.payment.model.MqHandleModel;
import com.heepay.vo.ClearChannelRecordVO;
import com.heepay.vo.ClearMerchantRecordVO;



@Service
public class ConsumeChargeResultsImpl implements IMqConsume{
  private static final Logger logger = LogManager.getLogger();
  @Autowired
  QueuePorducerSender queuePorducerSender;
  
  /**
   * 
  * @discription 充值写队列
  * @author yanxb       
  * @created 2016年11月10日 下午2:41:51      
  * @param clearChannelRecordVO
  * @param clearMerchantRecordVO     
  * @see com.heepay.manage.modules.payment.producer.IMqConsume#chargeHandle(com.heepay.vo.ClearChannelRecordVO, com.heepay.vo.ClearMerchantRecordVO)
   */
  @Override
  public void chargeHandle(ClearChannelRecordVO clearChannelRecordVO,ClearMerchantRecordVO clearMerchantRecordVO) {    
  	writeToQuene(TransType.CHARGE.getContent(),clearChannelRecordVO,clearMerchantRecordVO);
  }
  
  /**
   * 
  * @discription 提现写队列
  * @author yanxb       
  * @created 2016年11月10日 下午2:42:12      
  * @param clearChannelRecordVO
  * @param clearMerchantRecordVO     
  * @see com.heepay.manage.modules.payment.producer.IMqConsume#withdrawHandle(com.heepay.vo.ClearChannelRecordVO, com.heepay.vo.ClearMerchantRecordVO)
   */
	@Override
	public void withdrawHandle(String transType,ClearChannelRecordVO clearChannelRecordVO, ClearMerchantRecordVO clearMerchantRecordVO) {
		writeToQuene(TransType.getContentByValue(transType),clearChannelRecordVO,clearMerchantRecordVO);
	}
	
	/**
	 * 
	* @discription 转账写队列
	* @author yanxb       
	* @created 2016年11月10日 下午2:42:28      
	* @param clearChannelRecordVO
	* @param clearMerchantRecordVO     
	* @see com.heepay.manage.modules.payment.producer.IMqConsume#batchPayHandle(com.heepay.vo.ClearChannelRecordVO, com.heepay.vo.ClearMerchantRecordVO)
	 */
	@Override
	public void batchPayHandle(ClearChannelRecordVO clearChannelRecordVO, ClearMerchantRecordVO clearMerchantRecordVO) {
		writeToQuene(TransType.BATCHPAY.getContent(),clearChannelRecordVO,clearMerchantRecordVO);
	}
	
	/**
	 * 
	* @discription 写队列
	* @author yanxb       
	* @created 2016年11月10日 下午2:44:21     
	* @param transType
	* @param clearChannelRecordVO
	* @param clearMerchantRecordVO
	 */
	private void writeToQuene(String transType,ClearChannelRecordVO clearChannelRecordVO, ClearMerchantRecordVO clearMerchantRecordVO){
		JsonMapperUtil json = new JsonMapperUtil();
	    try{
	    	logger.info("异常处理,通知清结算,交易类型={}",transType);
	    	logger.info(transType + "异常处理,通知清结算,通道侧参数，clearChannelRecordVO={}",clearChannelRecordVO.toString());
		    logger.info(transType + "异常处理,通知清结算,商户侧参数，clearMerchantRecordVO={}",clearMerchantRecordVO.toString());
	    	queuePorducerSender.sendDataToQueueHyBillingClearChannelqueue(json.toJson(clearChannelRecordVO));
		    queuePorducerSender.sendDataToQueueHyBillingClearMerchantqueue(json.toJson(clearMerchantRecordVO));
		    logger.info(transType + "异常处理,通知清结算完成");
	    }catch(Exception e){
	    	logger.error(transType + "异常处理,通知清结算出错，错误信息={}",e.getMessage());
	    }
	}

	/**
	 * 
	* @discription 消费写队列
	* @author yanxb       
	* @created 2017年3月7日 下午4:35:47      
	* @param mqHandleModel     
	* @see com.heepay.manage.modules.payment.producer.IMqConsume#payHandle(com.heepay.rpc.payment.model.MqHandleModel)
	 */
	@Override
	public void payHandle(MqHandleModel mqHandleModel) {
		JsonMapperUtil json = new JsonMapperUtil();
	    try{
	    	logger.info("消费{}异常处理,写通知队列,参数={}",mqHandleModel.getTransNo(),json.toJson(mqHandleModel));
		    queuePorducerSender.sendDataToQueueHyPayResultqueue(json.toJson(mqHandleModel));
		    logger.info("消费{}异常处理,写通知队列,写队列完成",mqHandleModel.getTransNo(),json.toJson(mqHandleModel));
	    }catch(Exception e){
	    	logger.error("消费{}异常处理,写通知队列出错，错误信息={}",e.getMessage());
	    }
	}
}
