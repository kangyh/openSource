package com.heepay.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/8/17.
 */
@Component
public class DemoQueueSender {

    private Logger logger = LoggerFactory.getLogger(DemoQueueSender.class);

    @Autowired
    private AmqpTemplate demoAmqpTemplate;

    public void sendDataToQueue(String param) {
        logger.debug(" ---sendStringDataToQueue---- {}", param);
        demoAmqpTemplate.convertAndSend(param);
    }

}
