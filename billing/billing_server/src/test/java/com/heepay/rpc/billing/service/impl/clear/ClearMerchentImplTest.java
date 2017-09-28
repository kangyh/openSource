package com.heepay.rpc.billing.service.impl.clear;

import static org.junit.Assert.fail;

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
* 描    述：t+0的测试方法
*
* 创 建 者： wangl
* 创建时间：  2016年10月20日下午3:36:57
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
public class ClearMerchentImplTest {

	@Autowired
	private MethodTransactional methodTransactional;
	
	/**
	 * 
	 * @方法说明：汇总出t+0的数据然后发送给消息队列
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Test
	public void testGetClearMerchantRecordMessage() {
		
		try {
			methodTransactional.getClearMerchantRecordMessage();
			Assert.assertEquals("调取服务","成功");
		} catch (TException e) {
			fail("没有查找到数据");
		}
	}

}
