package com.heepay.manage.rpc.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings({"unused", "resource"})
public class Run {

	/**
	 * 定义全局日志
	 */
	private static Logger logger = LogManager.getLogger();

	public static void main(String[] args) {

		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml")) {
			context.registerShutdownHook();
			context.start();

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