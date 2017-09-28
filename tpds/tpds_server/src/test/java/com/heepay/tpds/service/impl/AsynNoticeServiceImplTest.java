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
 * 描    述：异步通知的测试类
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月27日 上午9:56:37
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
public class AsynNoticeServiceImplTest extends TestCase{
	
	@Autowired
	private AsynNoticeServiceImpl asynNoticeServiceImpl;
	
	//构造方法
	public AsynNoticeServiceImplTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(AsynNoticeServiceImplTest.class));
	}

	/**
	 * 
	 * @方法说明：客户充值的异步通知
	 * @时间：2017年2月27日 上午9:58:18
	 * @创建人：wangdong
	 */
	@Test
	public void testCustomerChargeAsynNotice(String requestHeader, String requestBody) {
		try {
			String ret = asynNoticeServiceImpl.customerChargeAsynNotice(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("客户充值的异步通知","成功");
			}else{
				Assert.assertEquals("客户充值的异步通知","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：客户提现的异步通知
	 * @时间：2017年2月27日 上午10:00:49
	 * @创建人：wangdong
	 */
	@Test
	public void testCustomerWithdrawAsynNotice(String requestHeader, String requestBody) {
		try {
			String ret = asynNoticeServiceImpl.customerWithdrawAsynNotice(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("客户提现的异步通知","成功");
			}else{
				Assert.assertEquals("客户提现的异步通知","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：文件上传异步通知
	 * @时间：2017年2月27日 上午10:02:17
	 * @创建人：wangdong
	 */
	@Test
	public void testFileAsynNotice(String requestHeader, String requestBody) {
		try {
			String ret = asynNoticeServiceImpl.fileAsynNotice(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("文件上传异步通知","成功");
			}else{
				Assert.assertEquals("文件上传异步通知","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：银行提现异步通知
	 * @时间：2017年2月27日 上午10:04:00
	 * @创建人：wangdong
	 */
	@Test
	public void testBankWithdrawAsynNotice(String requestHeader, String requestBody) {
		try {
			String ret = asynNoticeServiceImpl.bankWithdrawAsynNotice(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("银行提现异步通知","成功");
			}else{
				Assert.assertEquals("银行提现异步通知","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：日切通知
	 * @时间：2017年2月27日 上午10:04:50
	 * @创建人：wangdong
	 */
	@Test
	public void testCutDayAsynNotice(String requestHeader, String requestBody) {
		try {
			String ret = asynNoticeServiceImpl.cutDayAsynNotice(requestHeader, requestBody);
			if(StringUtils.isNotBlank(ret)){
				Assert.assertEquals("日切通知","成功");
			}else{
				Assert.assertEquals("日切通知","失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	@Test
	public void testSetPasswordAsynNotice(String requestHeader, String requestBody) {
		fail("Not yet implemented");
	}

	@Test
	public void testEditPasswordAsynNotice(String requestHeader, String requestBody) {
		fail("Not yet implemented");
	}

	@Test
	public void testResetPasswordAsynNotice(String requestHeader, String requestBody) {
		fail("Not yet implemented");
	}

}
