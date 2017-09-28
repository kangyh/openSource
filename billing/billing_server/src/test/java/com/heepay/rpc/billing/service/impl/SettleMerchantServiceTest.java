/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年10月21日上午10:48:27
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

import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2016年10月21日上午10:48:27
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
public class SettleMerchantServiceTest {

	@Autowired
	SettleMerchantService settleMerchantService;
	
	@Autowired
	SettleMerchantRecordServiceImpl settleMerchantRecordServiceImpl;
	
	@Autowired
	
	@Test
	public void testSaveSettleMerchantRecord(){
		
		try {
			settleMerchantService.saveAndSendSettleMsg(null, null,null);
			System.out.println("test---------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test---------");
	}
	@Test
	public void testsettleMerchantRecordServiceImpl()
	{
		try {
			settleMerchantRecordServiceImpl.querySettleMessage(null);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
