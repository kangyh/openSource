package com.heepay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
public class ApplicationHelper implements ApplicationListener<ContextRefreshedEvent> {
	private final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);
    @Autowired
    AspectUtil aspectUtil;
    @Autowired
    DataHandleOut dataHandleOut;
    
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			
			/** 放入队列  **/
	        new Thread(){
	            @Override
	            public void run() {
	                while(true){
	                    aspectUtil.entrance();
	                }
	            }
	        }.start();
	        
	        /** 输出日志结果  **/
	        new Thread(){
	            @Override
	            public void run() {
	                while(true){
	                	logger.info("每隔1分钟，输出一次");
	                    try {
	                        Thread.currentThread().sleep(60000);
	                        dataHandleOut.HandAndPut(aspectUtil.getMinuteInvokeCostTime());
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }.start();
		}
	}
} 
