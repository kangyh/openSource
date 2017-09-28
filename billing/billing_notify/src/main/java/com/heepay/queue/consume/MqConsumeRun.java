package com.heepay.queue.consume;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MqConsumeRun {
  private static final String CORE_WORK_XML = "spring.xml";

  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    try (
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(CORE_WORK_XML)) {
        context.registerShutdownHook();
        context.start();

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
