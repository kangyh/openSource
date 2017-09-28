package com.heepay.manage.modules.payment.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.mq.rabbit.HeepayRabbitTemplate;

/**
 * Created by Administrator on 2016/8/17.
 */
@Service
public class QueuePorducerSender {

    private Logger logger = LoggerFactory.getLogger(QueuePorducerSender.class);

    @Autowired
    private HeepayRabbitTemplate hyBillingClearMerchantQueueMqTemplate;
    
    @Autowired
    private HeepayRabbitTemplate hyBillingClearChannelQueueMqTemplate;
    
    @Autowired
    private HeepayRabbitTemplate hyNotifyQueueMqTemplate;
    

    public void sendDataToQueueHyBillingClearMerchantqueue(String param) {
      logger.debug(" ---sendDataToQueueHyBillingClearMerchantqueue---- {}", param);
      hyBillingClearMerchantQueueMqTemplate.send(param);
    }
    
    public void sendDataToQueueHyBillingClearChannelqueue(String param) {
      logger.debug(" ---sendDataToQueueHyBillingChannelMerchantqueue---- {}", param);
      hyBillingClearChannelQueueMqTemplate.send(param);
    }

	public void sendDataToQueueHyPayResultqueue(String json) {
		logger.debug(" ---sendDataToQueueHyBillingChannelMerchantqueue---- {}", json);
        hyNotifyQueueMqTemplate.send(json);
	}

}
