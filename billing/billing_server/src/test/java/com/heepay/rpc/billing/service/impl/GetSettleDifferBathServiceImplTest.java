package com.heepay.rpc.billing.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.entity.SettleDifferRecord;

/**
 * 
 * 
 * 描    述：通道侧和用户侧的撤账和补账实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月4日下午2:44:56 
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
public class GetSettleDifferBathServiceImplTest {
	
	@Autowired
	GetSettleDifferBathServiceImpl getSettleDifferBathServiceImpl;
	@Autowired
	SettleDifferRecordMapper SettleDifferRecordDao;
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 
	 * @方法说明： 汇总差错批次数据，做撤账和补账操作
	 * @author chenyanming
	 * @时间：2016年11月10日下午2:11:18
	 */
	@Test
	public void testGetSettleDifferBath() {
		try {
			getSettleDifferBathServiceImpl.getSettleDifferBath();
			Assert.assertEquals("", "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败" , e);
		}
	}
	
	/**
	 * 
	 * @方法说明：出款类差错批次数据处理，只处理审核通过的数据
	 * @author chenyanming
	 * @时间：2016年11月10日下午2:12:08
	 */
	@Test
	public void testDoExpendDifferBath() {
		try {
			getSettleDifferBathServiceImpl.doExpendDifferBath();
			Assert.assertEquals("", "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败" , e);
		}
		
	}
	
}

















