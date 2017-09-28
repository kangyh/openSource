package com.heepay.manage.modules.pbc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.pbc.entity.PbcAccountDetailSubject;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试账户主体详情Service
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
public class PbcAccountDetailSubjectServiceTest extends TestCase{

	@Autowired
	private PbcAccountDetailSubjectService pbcAccountDetailSubjectService;
	
	//构造方法
	public PbcAccountDetailSubjectServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcAccountDetailSubjectServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据id获取实体
	 * @时间：2016年12月26日 下午1:41:33
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			int pbcId = 1;
			PbcAccountDetailSubject r = pbcAccountDetailSubjectService.getEntityById(pbcId);
			if(null != r){
				Assert.assertEquals("根据id获取实体", "成功");
			}else{
				Assert.assertEquals("根据id获取实体", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：更新实体信息
	 * @时间：2016年12月26日 下午1:43:16
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdateEntity() {
		try {
			PbcAccountDetailSubject pbcAccountDetailSubject = new PbcAccountDetailSubject();
			pbcAccountDetailSubjectService.updateEntity(pbcAccountDetailSubject);
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
	 * @时间：2016年12月26日 下午1:43:41
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveEntity() {
		try {
			PbcAccountDetailSubject pbcAccountDetailSubject = new PbcAccountDetailSubject();
			pbcAccountDetailSubjectService.saveEntity(pbcAccountDetailSubject);
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
	 * @时间：2016年12月26日 下午1:44:28
	 * @创建人：wangdong
	 */
	@Test
	public void testGetFeeBackId() {
		try {
			Long feedBackId = 1L;
			PbcAccountDetailSubject r = pbcAccountDetailSubjectService.getFeeBackId(feedBackId);
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

	@Test
	public void testFindRiskProductQuotaPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetListByMerchantNumber() {
		fail("Not yet implemented");
	}

}
