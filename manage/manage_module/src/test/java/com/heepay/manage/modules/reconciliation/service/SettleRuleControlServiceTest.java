package com.heepay.manage.modules.reconciliation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;

/***
 * 
* 
* 描    述：SettleRuleControlService测试类
*
* 创 建 者： wangl
* 创建时间：  2016年10月20日下午6:22:43
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

@RunWith(SpringJUnit4ClassRunner.class) //用于配置spring中测试的环境
@ContextConfiguration(locations = {"classpath*:/spring-context.xml"}) //用于指定配置文件所在的位置
public class SettleRuleControlServiceTest {

	
	@Autowired
	private SettleRuleControlService settleRuleControlService;

	@Autowired
	private SettleRegexControlService settleRegexControlService;

	private static final Logger logger = LogManager.getLogger();
	
	@Test
	public void testFindListSettleRuleControl() {
		List<SettleRuleControl> findList = settleRuleControlService.findList(new SettleRuleControl());
		if(findList.size()>0){
			for (SettleRuleControl SettleDifferRecord : findList) {
				Assert.assertEquals(findList,"成功");
				logger.info("查找到数据："+SettleDifferRecord);
			}
			
		}else{
			Assert.assertEquals(findList,"失败");
		}
		
	}

	@Test
	public void testGetChannelName() {
		String channelName = settleRuleControlService.getChannelName("SETTLE_AREA");
		Assert.assertEquals(channelName,"消息打印成功");
	}

	@Test
	public void testGetEntity() {
		SettleRuleControl entity = settleRuleControlService.getEntity(100);
		Assert.assertEquals(entity,"消息打印成功");
	}

	@Test
	public void testSaveEntity() {
		SettleRuleControl settleRuleControl=new SettleRuleControl();
		settleRuleControl.setCreateDate(new Date());
		settleRuleControlService.saveEntity(settleRuleControl);
	}

	@Test
	public void testAddEntity() {
		SettleRuleControl settleRuleControl=new SettleRuleControl();
		settleRuleControl.setChannelName("EBANKP");
		settleRuleControlService.saveEntity(settleRuleControl);
		
	}

	@Test
	public void testGetBatchName() {
		List<SettleChannelManager> batchName = settleRuleControlService.getBatchName();
		if(batchName.size()>0){
			for (SettleChannelManager SettleDifferRecord : batchName) {
				Assert.assertEquals(batchName,"成功");
				Assert.assertEquals(SettleDifferRecord,"消息打印成功");
			}
			
		}else{
			Assert.assertEquals(batchName,"没有查找到数据");
		}
	}

	@Test
	public void testAddLog() {
		SettleChannelLog settleChannelLog=new SettleChannelLog();
		settleChannelLog.setBankNo("7");
		settleChannelLog.setChannelType("WITHHO");
		settleChannelLog.setChannelCode("EBANKP");
		
		settleRuleControlService.addLog(settleChannelLog);
	}

	@Test
	public void testGetChannelType() {
		String channelName = settleRuleControlService.getChannelName("EBANKP");
		Assert.assertEquals(channelName,"没有查找到数据");
	}

	@Test
	public void testUpdateList() {
		List<SettleRegexControl> list = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			SettleRegexControl regex = new SettleRegexControl();
			regex.setRuleId((long)i+1);
			regex.setRuleKey((long)i*10);
			regex.setRegexName("姓名"+i);
			regex.setRegexShow("([成][功])"+i);
			regex.setCreateTime(new Date());
			regex.setCreateAuthor("老王");
			list.add(regex);
		}
		settleRegexControlService.insetList(list);
	}

}
