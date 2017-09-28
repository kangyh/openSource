package com.heepay.rpc.billing.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.entity.SettleChannelRecordVo;

/**
 * 
 * 
 * 描    述：将结算批次数据入库，并将结算批次和明细数据发给账务系统
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月24日下午2:11:15 
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
public class SettleChannelRecordAndSendTest {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	SettleChannelRecordAndSend settleChannelRecordAndSendDao;
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDao;
	
	/**
	 * 
	 * @方法说明： 将结算批次数据入库，并将结算批次和明细数据发给账务系统
	 * @author chenyanming
	 * @时间：2016年11月23日下午4:14:58
	 */
	@Test
	public void testGetSettleChannelRecordAndSend() {
		try {
			List<SettleChannelRecordVo> settleChannelRecordVoList = clearChannelRecordDao.queryClearChannelRecordVo();
			for (SettleChannelRecordVo settleChannelRecordVo : settleChannelRecordVoList) {
				settleChannelRecordAndSendDao.getSettleChannelRecordAndSend(settleChannelRecordVo );
			}
			Assert.assertEquals("将结算批次数据入库，并将结算批次和明细数据发给账务系统","测试成功");
		} catch (Exception e) {
			Assert.assertEquals("将结算批次数据入库，并将结算批次和明细数据发给账务系统","测试失败", e);
		}
	}
	
}



