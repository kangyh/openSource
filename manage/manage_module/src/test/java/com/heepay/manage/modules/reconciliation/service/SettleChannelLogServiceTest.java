package com.heepay.manage.modules.reconciliation.service;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;

/***
 * 
* 
* 描    述：SettleChannelLogService测试方法
*
* 创 建 者： wangl
* 创建时间：  2016年10月20日下午4:07:55
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
public class SettleChannelLogServiceTest {

	@Autowired
	private SettleChannelLogService settleChannelLogService;
	
	@Test
	public void testFindListSettleChannelLog() {
		List<SettleChannelLog> findList = settleChannelLogService.findList(new SettleChannelLog());
		if(findList.size()>0){
			for (SettleChannelLog settleChannelLog : findList) {
				Assert.assertEquals(findList,"成功");
				Assert.assertEquals(settleChannelLog,"成功");
			}
			
		}else{
			Assert.assertEquals(findList,"没有查找到数据");
		}
		
	}

	@Test
	public void testFindListExcel() {
		SettleChannelLog settleChannelLog = new SettleChannelLog();
		
		List<Map<String, Object>> findListExcel = settleChannelLogService.findListExcel(settleChannelLog);
		
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
	public void testGetLocalPath() {
		SettleChannelManager settleChannelManager = new SettleChannelManager();
		String localPath;
		try {
			localPath = settleChannelLogService.getLocalPath(settleChannelManager);
			Assert.assertEquals(localPath,"成功");
		} catch (Exception e) {
			Assert.assertEquals(e,"失败");
		}
	}

}
