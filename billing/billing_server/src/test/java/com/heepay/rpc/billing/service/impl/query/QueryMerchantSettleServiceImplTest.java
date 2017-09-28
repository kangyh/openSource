/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年7月7日下午1:41:41
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
package com.heepay.rpc.billing.service.impl.query;

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
 * 创建时间：  2017年7月7日下午1:41:41
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
public class QueryMerchantSettleServiceImplTest {
	
	@Autowired
	QueryMerchantSettleServiceImpl queryMerchantSettleServiceImpl;
	
	@Test
	public void testQueryMerchantSettleServiceImpl(){
		
		queryMerchantSettleServiceImpl.querySettleBathMsg("19201704040015721", -1, 100);
	}

}
