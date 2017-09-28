package com.heepay.notify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.SinglePropertiesFactory;

public class ChargeMqProductTest{
	
  public static void main(String[] args)  { 
	 
	   
			try {

					Map map = new HashMap();
					map.put("paymentId","2016082610000285");
					map.put("bankSerialNo","bankSerialNo231");
					map.put("merchantId",123);		
					map.put("succAmount",1000);
					map.put("payResult",100);
					map.put("notifyUrl",1);
					JsonMapperUtil js = new JsonMapperUtil();
					//Message msg = new Message("hy_pay","tag"+value,"test"+value,(js.toJson(map)).getBytes());// body  
					//SendResult sendResult = _defaultMQProducerInstance.send(msg);  
	//				SendResult sendResult = MQProducer.getInstance("hy_pay").addMq("ChargeResults", "charge", "test", js.toJson(map));
	//				System.out.println(sendResult);
			
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	
}
