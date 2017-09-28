package com.heepay.tpds.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.common.util.StringUtils;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 *
 * 描    述：客户充值提现
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月27日 上午10:09:05
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
public class CustomerCwServiceImplTest extends TestCase{

	@Autowired
	private CustomerCwServiceImpl customerCwServiceImpl;
	
	//构造方法
	public CustomerCwServiceImplTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(CustomerCwServiceImplTest.class));
	}
	
	/**
	 * 
	 * @方法说明：客户充值
	 * @时间：2017年2月27日 上午10:10:55
	 * @创建人：wangdong
	 */
	@Test
	public void testCustomerCharge(String requestHeader, String requestBody) {
		try {
			String ret = customerCwServiceImpl.customerCharge(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("客户充值","成功");
			}else{
				Assert.assertEquals("客户充值","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：客户提现
	 * @时间：2017年2月27日 上午10:11:51
	 * @创建人：wangdong
	 */
	@Test
	public void testCustomerWithdraw(String requestHeader, String requestBody) {
		try {
			String ret = customerCwServiceImpl.customerWithdraw(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("客户提现","成功");
			}else{
				Assert.assertEquals("客户提现","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

}
