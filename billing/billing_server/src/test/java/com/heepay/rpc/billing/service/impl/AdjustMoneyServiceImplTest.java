/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年5月26日下午5:14:09
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

import com.heepay.billing.dao.impl.SettleDifferRecordDaoImpl;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.common.util.JsonMapperUtil;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年5月26日下午5:14:09
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
public class AdjustMoneyServiceImplTest {

	@Autowired
	AdjustMoneyServiceImpl adjustMoneyServiceImpl;
	
	@Autowired
	SettleDifferRecordDaoImpl settleDifferRecordDaoImpl;	
	
	@Test
	public void testupdateAccounting(){
		
		try {
			SettleDifferRecord sr = settleDifferRecordDaoImpl.querySettleDifferRecordByTransNo("13281100001493369881");
			
			//JsonMapperUtil.nonDefaultMapper().toJson(sr);
			String flag = adjustMoneyServiceImpl.updateAccounting(JsonMapperUtil.nonDefaultMapper().toJson(sr));
			System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
