package com.heepay.notify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主函数入口
 * 
 * @author zc
 *
 */

public class ExeProductMain2 {

	public static void main(String[] args) throws Exception {

		ExecutorService exe = Executors.newFixedThreadPool(100);  
        for (int i = 1; i <= 100; i++) {  
            //exe.execute(new ProductThread("Thread"+i,10));  
        }  
        exe.shutdown();  
        while (true) {  
            if (exe.isTerminated()) {  
                System.out.println("结束了！");  
                break;  
            }  
            Thread.sleep(200);  
        }  
	}

}
