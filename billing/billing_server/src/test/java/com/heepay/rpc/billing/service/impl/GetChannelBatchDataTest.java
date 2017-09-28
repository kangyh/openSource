package com.heepay.rpc.billing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 
 * 描    述：获取结算批次和清算明细测试类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年10月20日下午2:17:24 
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
public class GetChannelBatchDataTest {

	@Autowired
	GetChannelBatchData getChannelBatchData;
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 
	 * @方法说明：生成结算数据和清算明细数据
	 * @author chenyanming
	 * @param checkBathno
	 * @return
	 * @throws TException
	 * @时间：2016年10月20日下午2:23:03
	 */
	@Test
	public void testGetSettleChannelRecordMessage() {
		try {
			getChannelBatchData.getSettleChannelRecordMessage();
			Assert.assertEquals("", "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("失败", e);
		}
	}
	
	
}











