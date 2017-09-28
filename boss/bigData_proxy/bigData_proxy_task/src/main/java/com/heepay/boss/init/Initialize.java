package com.heepay.boss.init;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日上午10:00:23
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
@SuppressWarnings({"unused", "resource"})
public class Initialize {

    public static void main(final String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    }
}
