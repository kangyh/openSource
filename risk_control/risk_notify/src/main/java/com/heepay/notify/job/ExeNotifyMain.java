package com.heepay.notify.job;

import java.math.BigDecimal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
/**
 * 定时查询入口
 * @author zc
 *
 */
public class ExeNotifyMain {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
		  
        EPAdministrator admin = epService.getEPAdministrator();  
  
        String product = Apple.class.getName();  
        String epl = "select avg(price) from " + product ;  
  
        EPStatement state = admin.createEPL(epl);  
        state.addListener(new AppleListener());  
  
        EPRuntime runtime = epService.getEPRuntime();  
  
        Apple apple1 = new Apple();  
        apple1.setId(1);  
        apple1.setPrice(5);  
        runtime.sendEvent(apple1);  
  
        Apple apple2 = new Apple();  
        apple2.setId(2);  
        apple2.setPrice(2);  
        runtime.sendEvent(apple2);  
  
        Apple apple3 = new Apple();  
        apple3.setId(3);  
        apple3.setPrice(5);  
        runtime.sendEvent(apple3);  
	}
}
