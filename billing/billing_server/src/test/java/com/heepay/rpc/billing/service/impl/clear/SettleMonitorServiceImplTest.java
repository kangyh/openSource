package com.heepay.rpc.billing.service.impl.clear;

import static org.junit.Assert.*;

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
* 描    述：服务状态监控表根据id查询对象
*
* 创 建 者： wangl
* 创建时间：  Dec 10, 20166:54:14 PM
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
public class SettleMonitorServiceImplTest {

	@Autowired
	SettleMonitorServiceImpl SettleMonitorServiceImpl;
	
	
	@Test
	public void testGetValueById() throws TException {
		
		try {
			String valueById = SettleMonitorServiceImpl.getValueById(9001);
			Assert.assertEquals("调取服务",valueById);
		} catch (Exception e) {
			fail("没有查找到数据");
		}
		
	}
	
}
