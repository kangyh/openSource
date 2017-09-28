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
 * 描    述：银行提现
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月27日 上午10:06:17
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
public class BankWQServiceImplTest extends TestCase{
	
	@Autowired
	private BankWQServiceImpl bankWQServiceImpl;
	
	//构造方法
	public BankWQServiceImplTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(BankWQServiceImplTest.class));
	}

	/**
	 * 
	 * @方法说明：银行提现
	 * @时间：2017年2月27日 上午10:07:38
	 * @创建人：wangdong
	 */
	@Test
	public void testBankWithdraw(String requestHeader, String requestBody) {
		try {
			String ret = bankWQServiceImpl.bankWithdraw(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("银行提现","成功");
			}else{
				Assert.assertEquals("银行提现","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：提现交易查询
	 * @时间：2017年2月27日 上午10:08:29
	 * @创建人：wangdong
	 */
	@Test
	public void testBankStatusQuery(String requestHeader, String requestBody) {
		try {
			String ret = bankWQServiceImpl.bankStatusQuery(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("提现交易查询","成功");
			}else{
				Assert.assertEquals("提现交易查询","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

}
