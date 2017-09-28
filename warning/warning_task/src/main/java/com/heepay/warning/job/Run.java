package com.heepay.warning.job;


import com.heepay.warning.client.RiskWarningClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Run {
	
	public static void main(String[] args) throws InterruptedException {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		//RiskWarningClient client2=(RiskWarningClient)context.getBean("riskWarningClient");
		//String str=client2.getOrderSuccessRateWarning();
		//SendSms.sendSMS("13120254844", str, "46");
	}
}
