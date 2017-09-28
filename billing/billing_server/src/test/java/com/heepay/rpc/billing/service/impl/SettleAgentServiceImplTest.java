/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月14日下午5:05:07
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
package com.heepay.rpc.billing.service.impl;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.rpc.billing.service.impl.agent.SettleAgentServiceImpl;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月14日下午5:05:07
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class SettleAgentServiceImplTest {

	@Autowired
	SettleAgentServiceImpl settleAgentServiceImpl;
	
	@Test
	public void testagentSettle(){
		settleAgentServiceImpl.agentSettle();
	}
	
	public static void main(String args[]){
		File file =new File("F:/file/2017/03/04/");    
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("//不存在");  
		    file .mkdir();    
		}
		
	}
}
