package com.heepay.manage.modules.pbc.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.pbc.entity.PbcQueryInfo;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试查询信息Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月22日11:45:53
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
public class PbcQueryInfoServiceTest extends TestCase{

	@Autowired
	private PbcQueryInfoService pbcQueryInfoService;
	
	//构造方法
	public PbcQueryInfoServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcQueryInfoServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:51:15
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListPbcQueryInfo() {
		try {
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			pbcQueryInfo.setApplicationId("1");
			List<PbcQueryInfo> riskPList = pbcQueryInfoService.findList(pbcQueryInfo);
			if(null != riskPList && riskPList.size() > 0){
				Assert.assertEquals("获取实体信息", "成功");
			}else{
				Assert.assertEquals("获取实体信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:51:58
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			int pbcId = 1;
			PbcQueryInfo r = pbcQueryInfoService.getEntityById(pbcId);
			if(null != r){
				Assert.assertEquals("获取实体信息", "成功");
			}else{
				Assert.assertEquals("获取实体信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：更新实体信息
	 * @时间：2016年12月26日 下午12:52:52
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdate() {
		try {
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			pbcQueryInfoService.update(pbcQueryInfo);
			Assert.assertEquals("更新实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("更新实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：更新实体信息
	 * @时间：2016年12月26日 下午12:52:52
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdateEntity() {
		try {
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			pbcQueryInfoService.updateEntity(pbcQueryInfo);
			Assert.assertEquals("更新实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("更新实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：保存实体信息
	 * @时间：2016年12月26日 下午12:52:52
	 * @创建人：wangdong
	 */
	@Test
	public void testSavePbcQueryInfo() {
		try {
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			pbcQueryInfoService.save(pbcQueryInfo);
			Assert.assertEquals("保存实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：保存实体信息
	 * @时间：2016年12月26日 下午12:52:52
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveEntity() {
		try {
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			pbcQueryInfoService.saveEntity(pbcQueryInfo);
			Assert.assertEquals("保存实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:56:07
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityByApplicationId() {
		try {
			String pbcId = "1";
			PbcQueryInfo r = pbcQueryInfoService.getEntityByApplicationId(pbcId);
			if(null != r){
				Assert.assertEquals("获取实体信息", "成功");
			}else{
				Assert.assertEquals("获取实体信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
