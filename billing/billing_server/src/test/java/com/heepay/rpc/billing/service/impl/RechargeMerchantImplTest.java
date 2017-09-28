package com.heepay.rpc.billing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;


/***
 * 
* 
* 描    述：充值、消费测试类
*
* 创 建 者： xuangang
* 创建时间：  2016年11月14日下午2:56:38
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
public class RechargeMerchantImplTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearMerchantRecordMapper ClearMerchantRecordDaoImpl;
	
	@Test
	public void billingHandle(ClearMerchantRecordModel model){
		ClearMerchantRecord clearParam = new ClearMerchantRecord();
		
		ClearMerchantRecordDaoImpl.insert(clearParam);           //清算记录入库
		
		logger.info("清算记录入库成功!");
	}
}
