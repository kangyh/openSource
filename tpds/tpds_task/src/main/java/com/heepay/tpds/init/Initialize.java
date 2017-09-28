package com.heepay.tpds.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings({"unused", "resource"})
public class Initialize {

    public static void main(final String[] args) {
    
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    }
	
}
