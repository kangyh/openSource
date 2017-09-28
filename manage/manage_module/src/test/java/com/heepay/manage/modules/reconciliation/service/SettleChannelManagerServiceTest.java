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

/***
 * 
* 
* 描    述：SettleChannelManagerService测试类
*
* 创 建 者： wangl
* 创建时间：  2016年10月20日下午4:27:44
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
public class SettleChannelManagerServiceTest {

	@Autowired
	private SettleChannelManagerService SettleChannelManagerService;
	private static final Logger logger = LogManager.getLogger();
	 
	@Test
	public void testFindListSettleChannelManager() {
		List<SettleChannelManager> findList = SettleChannelManagerService.findList(new SettleChannelManager());
		if(findList.size()>0){
			Assert.assertEquals(findList,"成功");
		for (SettleChannelManager settleChannelManager : findList) {
			logger.info(settleChannelManager);
			Assert.assertEquals(settleChannelManager,"成功");
		}
		}else{
			Assert.assertEquals(findList,"没有查找到数据");
		}
	}

	@Test
	public void testFindListExcel() {
		
		List<Map<String, Object>> findListExcel = SettleChannelManagerService.findListExcel(new SettleChannelManager());
		
		if(findListExcel.size()>0){
			for (Map<String, Object> map : findListExcel) {
				Assert.assertEquals(findListExcel,"成功");
				Assert.assertEquals(map,"成功");
			}
			
		}else{
			Assert.assertEquals(findListExcel,"没有查找到数据");
		}
	}

	@Test
	public void testGetEntity() {
		SettleChannelManager entity = SettleChannelManagerService.getEntity(100);
		Assert.assertEquals(entity,"消息打印成功");
	}

	@Test
	public void testDeleteEntity() {
		SettleChannelManagerService.deleteEntity(1000);
	
	}

	@Test
	public void testSaveEntity() {
		SettleChannelManagerService.saveEntity(new SettleChannelManager());
	}

	@Test
	public void testAddEntity() {
		SettleChannelManagerService.addEntity(new SettleChannelManager());
	}

	@Test
	public void testGetChannel() {
		SettleChannelManagerService.getChannel();
	}

}
