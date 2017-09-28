package com.heepay.manage.modules.differences.service;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.differences.entity.ClearingProfitRecord;

/***
 * 
* 
* 描    述：分润明细service
*
* 创 建 者： wangl
* 创建时间：  Nov 8, 20165:04:21 PM
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
public class ClearingProfitRecordServiceTest {

	@Autowired
	private ClearingProfitRecordService clearingProfitRecordService;
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Test
	public void testFindListClearingProfitRecord() {
		List<ClearingProfitRecord> findList = clearingProfitRecordService.findList(new ClearingProfitRecord());
		if(findList.size()>0){
			for (ClearingProfitRecord settleChannelLog : findList) {
				Assert.assertEquals(findList,"成功");
				Assert.assertEquals(settleChannelLog,"成功");
			}
			
		}else{
			Assert.assertEquals(findList,"没有查找到数据");
		}
	}

	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Test
	public void testFindListExcel() {
		
		List<Map<String, Object>> findListExcel = clearingProfitRecordService.findListExcel(new ClearingProfitRecord());
		if(findListExcel.size()>0){
			for (Map<String, Object> map : findListExcel) {
				Assert.assertEquals(findListExcel,"成功");
				Assert.assertEquals(map,"成功");
			}
			
		}else{
			Assert.assertEquals(findListExcel,"没有查找到数据");
		}
	}

}
