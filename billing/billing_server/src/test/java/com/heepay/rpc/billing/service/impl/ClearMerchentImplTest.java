/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年5月3日上午11:18:18
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

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.rpc.billing.service.impl.clear.ClearMerchentImpl;
import com.heepay.rpc.billing.service.impl.clear.MethodTransactional2;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年5月3日上午11:18:18
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
	ClearMerchentImpl clearMerchentImpl;
	
	@Autowired
	MethodTransactional2 methodTransactional2;
	
	@Test
	public void testgetClearMerchantRecord() throws TException{
		
		methodTransactional2.getClearMerchantRecordMessage();
	}

}
