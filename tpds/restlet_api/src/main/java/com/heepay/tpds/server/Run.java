package com.heepay.tpds.server;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.heepay.tpds.resource.CustomerResource;


/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月9日 下午2:13:40
 * 创建描述：
 * 
 * 修 改 者：  
 * 修改时间：
 * 修改描述： 
 * 
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class Run {
		
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		Component component = ((Component) context.getBean("restlet"));
		
		component.getClients().add(Protocol.CLAP); 
        component.getClients().add(Protocol.FILE);
        component.getClients().add(Protocol.HTTP);
        component.getClients().add(Protocol.HTTPS);
        component.start();
        
		//((Component) context.getBean("restlet")).start();
		/*Component component = (Component) context.getBean("restlet_mockbank");*/
		//component.start();
	}
}
