package com.heepay.rpc.billing.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.TestCase;

/**
 * 
 *
 * 描    述：数据库备份（清结算）
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月15日 上午11:29:37
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
public class BillingDataBackUpServiceImplTest extends TestCase{

	@Autowired
	private BillingDataBackUpServiceImpl billingDataBackUpServiceImpl;
	
	//构造方法
	public BillingDataBackUpServiceImplTest(String name) {
		super(name);
	}
	
	/**
	 * 
	 * @方法说明：数据库备份主方法
	 * @时间：2017年3月15日 上午11:30:48
	 * @创建人：wangdong
	 */
	@Test
	public void testBillingDataBackUp() {
		try {
			billingDataBackUpServiceImpl.billingDataBackUp();
				Assert.assertEquals("数据库备份主方法","成功");
		} catch (Exception e) {
			Assert.assertEquals("数据库备份主方法","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：通道侧清算记录历史表
	 * @时间：2017年3月15日 上午11:32:12
	 * @创建人：wangdong
	 */
	@Test
	public void testClearingChannelRecordHis() {
		try {
			billingDataBackUpServiceImpl.clearingChannelRecordHis();
				Assert.assertEquals("通道侧清算记录历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("通道侧清算记录历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：商户侧清算记录历史表
	 * @时间：2017年3月15日 上午11:32:41
	 * @创建人：wangdong
	 */
	@Test
	public void testClearingMerchantRecordHis() {
		try {
			billingDataBackUpServiceImpl.clearingMerchantRecordHis();
				Assert.assertEquals("商户侧清算记录历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("商户侧清算记录历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：账单明细历史表
	 * @时间：2017年3月15日 上午11:33:03
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleBillRecordHis() {
		try {
			billingDataBackUpServiceImpl.settleBillRecordHis();
				Assert.assertEquals("账单明细历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("账单明细历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：通道侧结算记录历史表
	 * @时间：2017年3月15日 上午11:33:28
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleChannelRecordHis() {
		try {
			billingDataBackUpServiceImpl.settleChannelRecordHis();
				Assert.assertEquals("通道侧结算记录历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("通道侧结算记录历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：通道侧差错账汇总历史表
	 * @时间：2017年3月15日 上午11:34:01
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleDifferChannelHis() {
		try {
			billingDataBackUpServiceImpl.settleDifferChannelHis();
				Assert.assertEquals("通道侧差错账汇总历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("通道侧差错账汇总历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：商户侧差错账汇总历史表
	 * @时间：2017年3月15日 上午11:34:26
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleDifferMerchantHis() {
		try {
			billingDataBackUpServiceImpl.settleDifferMerchantHis();
				Assert.assertEquals("商户侧差错账汇总历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("商户侧差错账汇总历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：差异单历史表
	 * @时间：2017年3月15日 上午11:35:02
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleDifferRecordHis() {
		try {
			billingDataBackUpServiceImpl.settleDifferRecordHis();
				Assert.assertEquals("差异单历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("差异单历史表","失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：商户侧结算记录历史表
	 * @时间：2017年3月15日 上午11:35:50
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleMerchantRecordHis() {
		try {
			billingDataBackUpServiceImpl.settleMerchantRecordHis();
				Assert.assertEquals("商户侧结算记录历史表","成功");
		} catch (Exception e) {
			Assert.assertEquals("商户侧结算记录历史表","失败");
			fail("Not yet implemented");
		}
	}

}
