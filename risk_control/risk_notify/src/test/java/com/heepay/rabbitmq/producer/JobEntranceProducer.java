/**
 *
 */
package com.heepay.rabbitmq.producer;

import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.InterfaceStatus;
import com.heepay.vo.MqBodyVO;


public class JobEntranceProducer {

    private static final String CORE_WORK_XML = "rabbitmq-producer.xml";

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JobEntranceProducer.class);

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CORE_WORK_XML)) {
            context.registerShutdownHook();
            context.start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    DemoQueueSender demoQueueSender = context.getBean("demoQueueSender", DemoQueueSender.class);
                    MqBodyVO mqBodyVO  = new MqBodyVO();
                    mqBodyVO.setSuccessAmount("100.0000");
                    mqBodyVO.setBankSerialNo("BANK112233445566");
                    mqBodyVO.setNotifyUrl("http://");
                    mqBodyVO.setPaymentId("2016083010000344");
//                    mqBodyVO.setResult(InterfaceStatus.success.getValue());
                    JsonMapperUtil jsonMapperUtil = new JsonMapperUtil();
//                    for (int i = 0; i < 10; i++){
                      //  String message = Thread.currentThread().getName()+" : hi rabbit."+i;
                        demoQueueSender.sendDataToQueue(jsonMapperUtil.toJson(mqBodyVO));
                        logger.info(jsonMapperUtil.toJson(mqBodyVO));
//                    }
                }
            }).start();

            logger.info("spring 容器启动成功! ");
            
            Object lock = new Object();
            synchronized (lock) {
                try {
                    while (true)
                        lock.wait();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("spring 容器启动错误", e);
        }

    }

}
