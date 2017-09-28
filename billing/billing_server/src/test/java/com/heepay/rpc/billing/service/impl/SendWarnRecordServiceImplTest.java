/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年1月17日下午1:17:49
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * 创建时间：  2017年1月17日下午1:17:49
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
public class SendWarnRecordServiceImplTest {
	
	@Autowired
	SendWarnRecordServiceImpl sendWarnRecordServiceImpl;
	
	private static final Logger logger = LogManager.getLogger();
	@Test
	public void testIsCheckBillRecord() {
		try {
			sendWarnRecordServiceImpl.isCheckBillRecord();
		} catch (Exception e) {
			logger.error("----------", e);
		}
	}
	@Test
	public void testIsSettleRecord(){
		try {
			sendWarnRecordServiceImpl.isSettleRecord();
		} catch (Exception e) {
			logger.error("----------", e);
		}
	}

}
