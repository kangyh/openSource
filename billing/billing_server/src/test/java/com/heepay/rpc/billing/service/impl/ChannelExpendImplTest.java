package com.heepay.rpc.billing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.rpc.billing.model.ClearChannelRecordModel;

/**
 * 
 * 
 * 描    述：通道侧清算明细数据入库测试类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月10日上午11:24:05 
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
public class ChannelExpendImplTest {
	@Autowired
	ChannelExpendImpl channelExpendImpl;
	
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;
	
	/**
	 * 
	 * @方法说明：清算明细数据入库
	 * @author chenyanming
	 * @时间：2016年11月10日上午11:29:39
	 */
	@Test
	public void testBillingHandle() {
		ClearChannelRecordModel model = new ClearChannelRecordModel();
		try {
			channelExpendImpl.billingHandle(model);
			Assert.assertEquals(model, "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败！" , e);
		}
		
	}
	
}















