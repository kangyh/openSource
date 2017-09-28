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
* 描    述：商户侧清算记录接口根据清算流水号查询结算状态
*
* 创 建 者： wangl
* 创建时间：  Nov 3, 201611:56:00 AM
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
public class ClearingChannelRecordServiceImplTest {

	@Autowired
	ClearingChannelRecordServiceImpl clearingChannelRecordServiceImpl;
	/**
	 * 
	 * @方法说明：商户侧清算记录接口根据清算流水号查询结算状态
	 * @时间：Nov 3, 2016
	 * @创建人：wangl
	 */
	@Test
	public void testQueryChannelSettleStatusByTransNo() {
		try {
			String queryMerchantSettleStatusByTransNo = clearingChannelRecordServiceImpl.queryChannelSettleStatusByTransNo("165100001479094051");
			Assert.assertEquals("调取服务",queryMerchantSettleStatusByTransNo);
		} catch (TException e) {
			fail("没有查找到数据");
		}
	}

}
