package com.heepay.manage.modules.reconciliation.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;

/***
 * 
* 
* 描    述：
*
* 创 建 者： wangl
* 创建时间：  2016年10月20日下午6:14:47
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
public class SettleDifferRecordServiceTest {

	
	@Autowired
	private SettleDifferRecordService settleDifferRecordService;
	private static final Logger logger = LogManager.getLogger();
	
	@Test
	public void testFindListSettleDifferRecord() {
		List<SettleDifferRecord> findList = settleDifferRecordService.findList(new SettleDifferRecord());
		if(findList.size()>0){
			for (SettleDifferRecord SettleDifferRecord : findList) {
				Assert.assertEquals(findList,"成功");
				logger.info("查找到数据："+SettleDifferRecord);
			}
			
		}else{
			Assert.assertEquals(findList,"成功");
			logger.info("没有查找到数据");
		}
		
	}

	@Test
	public void testGetBatchName() {

		List<SettleChannelManager> findListExcel = settleDifferRecordService.getBatchName();
		

		if(findListExcel.size()>0){
			for (SettleChannelManager map : findListExcel) {
				Assert.assertEquals(findListExcel,"成功");
				logger.info("查找到数据："+map);
			}
			
		}else{
			Assert.assertEquals(findListExcel,"没有查找到数据");
		}
	}
	
	
	@Test
	public void testFindListExcel() {
		SettleDifferRecord SettleDifferRecord = new SettleDifferRecord();
		List<Map<String, Object>> findListExcel = settleDifferRecordService.findListExcel(SettleDifferRecord);
		
		if(findListExcel.size()>0){
			Assert.assertEquals(findListExcel,"成功");
			for (Map<String, Object> map : findListExcel) {
				logger.info("查找到数据："+map);
			}
			
		}else{
			Assert.assertEquals(findListExcel,"没有查找到数据");
		}
		
	
	}

	@Test
	public void testGetEntityById() {
		SettleDifferRecord entityById = settleDifferRecordService.getEntityById(100);
		Assert.assertEquals(entityById,"消息打印成功");
	}

	@Test
	public void testUpdateTime() {
		settleDifferRecordService.updateTime(new  SettleDifferRecord());
	}

}
