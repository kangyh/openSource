package com.heepay.manage.modules.pbc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.pbc.entity.PbcReleaseFeedback;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试解除反馈Service
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
public class PbcReleaseFeedbackServiceTest extends TestCase{

	@Autowired
	private PbcReleaseFeedbackService pbcReleaseFeedbackService;
	
	//构造方法
	public PbcReleaseFeedbackServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcReleaseFeedbackServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:57:55
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListPbcReleaseFeedback() {
		try {
			PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
			pbcReleaseFeedback.setApplicationCode("1");
			List<PbcReleaseFeedback> riskPList = pbcReleaseFeedbackService.findList(pbcReleaseFeedback);
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
	 * @时间：2016年12月26日 下午12:58:45
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			int pbcId = 1;
			PbcReleaseFeedback r = pbcReleaseFeedbackService.getEntityById(pbcId);
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
	 * @时间：2016年12月26日 下午12:59:25
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdate() {
		try {
			PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
			pbcReleaseFeedbackService.update(pbcReleaseFeedback);
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
	 * @时间：2016年12月26日 下午12:59:25
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdateEntity() {
		try {
			PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
			pbcReleaseFeedbackService.updateEntity(pbcReleaseFeedback);
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
	 * @时间：2016年12月26日 下午1:05:51
	 * @创建人：wangdong
	 */
	@Test
	public void testSavePbcReleaseFeedback() {
		try {
			PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
			pbcReleaseFeedbackService.save(pbcReleaseFeedback);
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
	 * @时间：2016年12月26日 下午1:03:41
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveEntity() {
		try {
			PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
			pbcReleaseFeedbackService.saveEntity(pbcReleaseFeedback);
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
	 * @时间：2016年12月26日 下午1:01:15
	 * @创建人：wangdong
	 */
	@Test
	public void testGetFeeBackId() {
		try {
			Long pbcId = 1L;
			PbcReleaseFeedback r = pbcReleaseFeedbackService.getFeeBackId(pbcId);
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
	 * @方法说明：保存信息
	 * @时间：2016年12月26日 下午1:02:46
	 * @创建人：wangdong
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSaveMap() {
		try {
			Map map = new HashMap();
			int num = pbcReleaseFeedbackService.saveMap(map);
			if(num > 0){
				Assert.assertEquals("保存信息", "成功");
			}else{
				Assert.assertEquals("保存信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
