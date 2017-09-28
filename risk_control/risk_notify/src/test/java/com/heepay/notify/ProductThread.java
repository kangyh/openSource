package com.heepay.notify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.SinglePropertiesFactory;

public class ProductThread implements Runnable{
	String names ; 
	int t;
	AtomicInteger transactionIndex;
	DefaultMQProducer _defaultMQProducerInstance;
	ProductThread(String name,AtomicInteger transactionIndex,int t){
		this.names=name;
		this.t=t;
		this.transactionIndex=transactionIndex;
	}
	public void getEven() {
    synchronized (this) {
     
      for(int i = 1;i<=1000;i++){
        int value = transactionIndex.getAndIncrement();
      System.out.println(names+"::::::"+value);
      }
    }
}
	 public  void run(){
	   getEven2();
	 }
	public  void getEven2(){
	  synchronized(this){
	   
			try {
//				for(int i =0;i<=100;i++){	
//					Producer.getInstance().addMq("CID_thread0017"+names, "Topicthread00171"+names, j+"111", "key123"+i, j+"");
//				}
//				DefaultMQProducer _defaultMQProducerInstance  = new DefaultMQProducer("hy_pay_test");
//				SinglePropertiesFactory propFactory = SinglePropertiesFactory.getInstance("/rocketmq.properties");
//				_defaultMQProducerInstance.setNamesrvAddr(propFactory.getConfig("rocketmq.nameserver"));
//				_defaultMQProducerInstance.setInstanceName("Producer");		
//				_defaultMQProducerInstance.start(); 
				for(int i = 1;i<=1000;i++){
	        int value = transactionIndex.getAndIncrement();
					Map map = new HashMap();
					map.put("transNo",value);
					map.put("merchantId",value);
					map.put("merchantOrderNo","test"+value);
					if(value%2==0){
						map.put("succAmount",value);
					}else{
					  map.put("succAmount",value-1);
					}					
					map.put("payAmount",value);
					map.put("payResult",1);
					map.put("merchantCompany",value);
					map.put("notifyUrl",value);
					map.put("paymentId",value);
					JsonMapperUtil js = new JsonMapperUtil();
					//Message msg = new Message("hy_pay","tag"+value,"test"+value,(js.toJson(map)).getBytes());// body  
					//SendResult sendResult = _defaultMQProducerInstance.send(msg);  
//					SendResult sendResult = MQProducer.getInstance("hy_pay_test").addMq("hy_pay_new", "tag"+value, "test"+value, js.toJson(map));
//					System.out.println(names+":"+value+":::::::::::::::::::::"+sendResult.toString());
				}
				//_defaultMQProducerInstance.shutdown();
				
				//_defaultMQProducerInstance = null;
			
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
		
	}
}
