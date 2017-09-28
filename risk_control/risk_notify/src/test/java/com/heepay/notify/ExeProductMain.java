package com.heepay.notify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

/**
 * 主函数入口
 * 
 * @author zc
 *
 */
@Component
public class ExeProductMain {

	public static void main(String[] args) throws Exception {
	 // System.out.println( Runtime.getRuntime().availableProcessors());
		ExecutorService exe = Executors.newFixedThreadPool(8000);
	  
		AtomicInteger transactionIndex = new AtomicInteger(1000);
	//	Producer.getDefaultMQProducerInstance("hy_pay_test");
        for (int i = 1; i <= 3000; i++) { 
          
           exe.execute(new ProductThread("Thread"+i,transactionIndex,i));  
        
        }  
        
       
        exe.shutdown();  
        while (true) {  
            if (exe.isTerminated()) { 
//              MQProducer.shoutdown();
              int value = transactionIndex.getAndIncrement();
              System.out.println("::::::"+value);
                System.out.println("结束了！");  
                break;  
            }  
            Thread.sleep(200);  
        }  
	}

}
