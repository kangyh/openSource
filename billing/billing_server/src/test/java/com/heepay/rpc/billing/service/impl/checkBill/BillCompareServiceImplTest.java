/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年6月9日上午10:10:58
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
package com.heepay.rpc.billing.service.impl.checkBill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.rpc.billing.service.impl.checkbill.BillCompareServiceImpl;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年6月9日上午10:10:58
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
public class BillCompareServiceImplTest {

	@Autowired
	BillCompareServiceImpl billCompareServiceImpl;
	
	@Test
	public void testbillCompare(){
		billCompareServiceImpl.billCompare("DZ201612100000051");
	}
}





