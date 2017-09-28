package com.heepay.rpc.billing.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * 描    述：结算记录和清算明细汇总客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月28日下午7:01:24 
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
public class SettleChannelProducerSender {
	
	private Logger logger = LoggerFactory.getLogger(SettleChannelProducerSender.class);

    /*@Autowired
    private AmqpTemplate rabbitMqAmqpTemplate;*/
    @Autowired
    private AmqpTemplate hyBillingSettleChannelRecordDetailMqTemplate;

    public void sendDataToQueue(String param) {
        logger.debug(" ---sendStringDataToQueue---- {}", param);
        hyBillingSettleChannelRecordDetailMqTemplate.convertAndSend(param);
    }

}
