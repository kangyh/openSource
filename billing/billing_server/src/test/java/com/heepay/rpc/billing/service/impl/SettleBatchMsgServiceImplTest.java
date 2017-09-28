package com.heepay.rpc.billing.service.impl;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.rpc.billing.model.SettleBatchMsgModel;

/**
 * 
 * 
 * 描    述：根据结算批次号查询结算明细数据测试类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年7月6日下午6:29:46 
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
public class SettleBatchMsgServiceImplTest {
	
	@Autowired
	SettleBatchMsgServiceImpl settleBatchMsgServiceImpl;
	
	@Test
	public void testSettleBatchMsgService() throws TException {
		SettleBatchMsgModel settleBatchMsgModel = settleBatchMsgServiceImpl.queryChannelSettleBatch("19201707060172730", -1, 2);
		System.out.println(settleBatchMsgModel.getSettleBatch());
	}

}
