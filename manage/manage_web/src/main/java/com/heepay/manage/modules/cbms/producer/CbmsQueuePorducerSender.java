package com.heepay.manage.modules.cbms.producer;

import com.heepay.mq.rabbit.HeepayRabbitTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生产者
 */
@Service
public class CbmsQueuePorducerSender {

    private Logger logger = LoggerFactory.getLogger(CbmsQueuePorducerSender.class);

    @Autowired
    private HeepayRabbitTemplate hyCbmsNotifyQueueMqTemplate;

    /**
     *
     * @param param JSON格式数据
     */
    public void send(String param) {
        logger.debug(" ---send---- {}", param);
        hyCbmsNotifyQueueMqTemplate.send(param);
    }



}
