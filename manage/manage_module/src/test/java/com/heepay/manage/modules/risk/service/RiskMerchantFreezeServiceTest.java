package com.heepay.manage.modules.risk.service;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;

/***
 * 
* 
* 描    述：商户账户冻结的service测试
*
* 创 建 者： wangl
* 创建时间：  Nov 28, 20169:21:25 AM
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
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class RiskMerchantFreezeServiceTest {

	
	@Autowired
	private RiskMerchantFreezeService riskMerchantFreezeService;
	
	/**
	 * 
	 * @方法说明：测试页面显示的方法
	 * @时间：Nov 28, 2016
	 * @创建人：wangl
	 */
	@Test
	public void testFindRiskMerchantProductQuotaPage() {
		
		List<RiskMerchantFreeze> findList = riskMerchantFreezeService.findList(new RiskMerchantFreeze());
		if(findList.size()>0){
			for (RiskMerchantFreeze riskMerchantFreeze : findList) {
				Assert.assertEquals(findList,"成功");
				Assert.assertEquals(riskMerchantFreeze.toString(),"成功");
			}
			
		}else{
			Assert.assertEquals(findList,"没有查找到数据");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：商户冻结规则添加
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@Test
	public void testSaveEntity() {
		
		RiskMerchantFreeze  riskMerchantFreeze=new RiskMerchantFreeze();
		int saveEntity = riskMerchantFreezeService.saveEntity(riskMerchantFreeze);
		if(saveEntity!=0){
			Assert.assertEquals(saveEntity,"保存商户冻结规则添加成功");
		}else{
			Assert.assertEquals(saveEntity,"保存商户冻结规则添加失败------->{数据库代码执行异常了}");
			fail("Not yet implemented");
		}
	}

	
	/**
	 * 
	 * @方法说明：冻结和取消操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@Test
	public void testUpdateEntity() {
		RiskMerchantFreeze  riskMerchantFreeze=new RiskMerchantFreeze();
		riskMerchantFreeze.setCreateDate(new Date());
		riskMerchantFreeze.setMerchantCode("10086");
		riskMerchantFreeze.setFreezeId(12);
		riskMerchantFreeze.setDefreezeNo("20161128121025");
		int updateEntity = riskMerchantFreezeService.updateEntity(riskMerchantFreeze);
		if(updateEntity!=0){
			Assert.assertEquals(riskMerchantFreeze,"保存商户冻结规则更新成功");
		}else{
			Assert.assertEquals(riskMerchantFreeze,"保存商户冻结规则更新失败------->{数据库代码执行异常了}");
			fail("Not yet implemented");
		}
	}

	
	/**
	 * 
	 * @方法说明：根据id查询整个对象发给交易
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	@Test
	public void testGetEntity() {
		RiskMerchantFreeze entity = riskMerchantFreezeService.getEntity(23);
		if(entity !=null){
			Assert.assertEquals(entity.toString(),"根据id查询整个对象发给交易成功");
		}else{
			Assert.assertEquals("根据id查询整个对象发给交易为空--------->{空}", null);
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：文件导出查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Test
	public void testFindListExcel() {
		
		List<Map<String, Object>> findListExcel = riskMerchantFreezeService.findListExcel(new RiskMerchantFreeze());
		if(findListExcel.size()>0){
			for (Map<String, Object> map : findListExcel) {
				Assert.assertEquals(map,"成功");
			}
			
		}else{
			Assert.assertEquals(findListExcel,"没有查找到数据");
			fail("Not yet implemented");
		}
	}

}
