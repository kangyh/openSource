package com.heepay.rpc.billing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.dao.SettleDifferMerchantMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.rpc.billing.service.ISettleDifferFillAndWithdrawBillService;

/**
 * 
 * 
 * 描    述：汇总差错批次数据，撤账和补账测试用例
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月21日下午5:41:55 
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
public class SettleDifferFillAndWithdrawBillServiceImplTest {
	
	@Autowired
	ISettleDifferFillAndWithdrawBillService settleDifferFillAndWithdrawBillServiceDao;
	@Autowired
	SettleDifferFillAndWithdrawBillServiceImpl settleDifferFillAndWithdrawBillServiceImpl;
	@Autowired
	SettleDifferRecordMapper SettleDifferRecordDao;
	@Autowired
	SettleDifferMerchantMapper settleDifferMerchantDao;
	@Autowired
	SettleDifferChannelMapper settleDifferChannelDao;
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 
	 * @方法说明： 差错批次数据入库，补账和撤账逻辑
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @时间：2016年11月22日上午10:54:31
	 */
	@Test
	public void testFillAndWithdrawBillMethod() {
		try {
			SettleDifferRecord settleDifferRecord = SettleDifferRecordDao.selectByPrimaryKey((long) 3337);
			settleDifferFillAndWithdrawBillServiceDao.fillAndWithdrawBillMethod(settleDifferRecord);
			Assert.assertEquals("差错批次数据入库，补账和撤账逻辑","测试成功");
		} catch (Exception e) {
			Assert.assertEquals("差错批次数据入库，补账和撤账逻辑","测试失败", e);
		}
	}
	
	/**
	 * 
	 * @方法说明：出款类撤账,商户侧和通道侧
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @param settleDifferChannel
	 * @时间：2016年11月15日下午6:01:44
	 */
	@Test
	public void testDoMerchantWithDrawBill() {
		try {
			SettleDifferMerchant settleDifferMerchant = settleDifferMerchantDao.selectByPrimaryKey((long) 3337);
			SettleDifferChannel settleDifferChannel = settleDifferChannelDao.selectByPrimaryKey((long)3337);
			SettleDifferRecord settleDifferRecord = SettleDifferRecordDao.selectByPrimaryKey((long) 3337);
			settleDifferFillAndWithdrawBillServiceImpl.doMerchantWithDrawBill(settleDifferMerchant, settleDifferChannel, settleDifferRecord);
			Assert.assertEquals("出款类撤账,商户侧和通道侧","测试成功");
		} catch (Exception e) {
			Assert.assertEquals("出款类撤账,商户侧和通道侧","测试失败", e);
		}
	}
	
	/**
	 * 
	 * @方法说明：商户侧补账方法
	 * @author chenyanming
	 * @param settleDifferMerchant
	 * @param settleDifferRecord
	 * @时间：2016年11月15日下午5:59:18
	 */
	@Test
	public void testDoMerchantFillBill() {
		try {
			SettleDifferRecord settleDifferRecord = SettleDifferRecordDao.selectByPrimaryKey((long) 3337);
			SettleDifferMerchant settleDifferMerchant = settleDifferMerchantDao.selectByPrimaryKey((long) 3337);
			settleDifferFillAndWithdrawBillServiceImpl.doMerchantFillBill(settleDifferMerchant, settleDifferRecord);
			Assert.assertEquals("商户侧补账方法","测试成功");
		} catch (Exception e) {
			Assert.assertEquals("商户侧补账方法","测试失败", e);
		}
	}
	
	/**
	 * 
	 * @方法说明：通道侧补账方法
	 * @author chenyanming
	 * @param settleDifferChannel
	 * @param settleDifferRecord
	 * @时间：2016年11月15日下午5:47:39
	 */
	@Test
	public void testDoChannelFillBill() {
		try {
			SettleDifferRecord settleDifferRecord = SettleDifferRecordDao.selectByPrimaryKey((long) 3337);
			SettleDifferChannel settleDifferChannel = settleDifferChannelDao.selectByPrimaryKey((long)3337);
			settleDifferFillAndWithdrawBillServiceImpl.doChannelFillBill(settleDifferChannel, settleDifferRecord);
			Assert.assertEquals("通道侧补账方法","测试成功");
		} catch (Exception e) {
			Assert.assertEquals("通道侧补账方法","测试失败", e);
		}
	}
	
	/**
	 * 
	 * @方法说明：生成差错批次数据
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @param transStatus
	 * @return
	 * @时间：2016年10月28日下午2:31:35
	 */
	@Test
	public void testPutSettleDifferBathIntoStorage() {
		try {
			SettleDifferRecord settleDifferRecord = SettleDifferRecordDao.selectByPrimaryKey((long) 3337);
			settleDifferFillAndWithdrawBillServiceImpl.putSettleDifferBathIntoStorage(settleDifferRecord);
			Assert.assertEquals("生成差错批次数据","测试成功");
		} catch (Exception e) {
			Assert.assertEquals("生成差错批次数据","测试失败", e);
		}
	}
	
}














