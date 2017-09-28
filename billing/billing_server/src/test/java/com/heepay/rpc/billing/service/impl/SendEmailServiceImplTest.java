/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年6月27日下午2:38:15
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

import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.rpc.billing.service.ISendMailService;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年6月27日下午2:38:15
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
public class SendEmailServiceImplTest {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	SendEmailServiceImpl sendEmailServiceImpl;
	
	@Autowired
	ISendMailService settleDifferRecordImpl;

	@Test
	public void testquery() {
		try {
			sendEmailServiceImpl.query();
			
//			ClearChannelRecord cRecord = new ClearChannelRecord();
//			
//			cRecord.setTransNo("13954100001498098060");
//			cRecord.setPaymentId("1065282");
//			
//			ClearMerchantRecord mRecord = new ClearMerchantRecord();
//			
//			mRecord.setTransNo("13954100001498098060");
//			
//			settleDifferRecordImpl.settleDifferRecord(cRecord, mRecord);
			
			
		} catch (Exception e) {
			logger.error("----------", e);
		}
	}
}
