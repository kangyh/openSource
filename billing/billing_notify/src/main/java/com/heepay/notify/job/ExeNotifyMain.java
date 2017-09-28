package com.heepay.notify.job;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 定时查询入口
 * @author zc
 *
 */
public class ExeNotifyMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("quartz-config.xml");
	}
}
