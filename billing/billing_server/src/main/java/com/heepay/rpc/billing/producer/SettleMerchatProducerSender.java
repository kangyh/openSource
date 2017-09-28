package com.heepay.rpc.billing.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;


/***
 * 
* 
* 描    述：结算商户侧结算汇总写队列
*
* 创 建 者： xuangang
* 创建时间：  2016年9月26日下午1:48:17
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
public class SettleMerchatProducerSender {

	private Logger logger = LoggerFactory.getLogger(SettleMerchatProducerSender.class);

    @Autowired
    private AmqpTemplate hyBillingSettleMerchantqueueAmqpTemplate;
    @Autowired
    private AmqpTemplate hyBillingSettleChannelRecordDetailMqTemplate;
    @Autowired
    private AmqpTemplate hyBillingClearProfitDetailMqTemplate;   //分润数据发送队列

    public void sendDataToQueue(String param) {
        logger.info(" ---sendStringDataToQueue---- {}", param);
        hyBillingSettleMerchantqueueAmqpTemplate.convertAndSend(param);
    }
    
    public void sendDataToQueue(Object obj) {
    	String json = JsonMapperUtil.nonDefaultMapper().toJson(obj);
        logger.info(" ---sendStringDataToQueue---- {}", json);
        hyBillingSettleMerchantqueueAmqpTemplate.convertAndSend(json);
    }
    
    public void sendChannelDataToQueue(Object param) {
    	String json = JsonMapperUtil.nonDefaultMapper().toJson(param);
        logger.info(" ---sendStringDataToQueue---- {}", json);
        hyBillingSettleChannelRecordDetailMqTemplate.convertAndSend(json);
    }
    /**
     * 发送分润汇总信息到账务系统
     * @param param
     */
    public void sendProfitDataToQueue(Object param){
    	String json = JsonMapperUtil.nonDefaultMapper().toJson(param);
        logger.info(" ---sendProfitDataToQueue---- {}", json);
        hyBillingClearProfitDetailMqTemplate.convertAndSend(json);
    }
}
