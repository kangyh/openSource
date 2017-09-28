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

import com.heepay.manage.modules.pbc.entity.PbcAccountDetail;

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
public class PbcAccountDetailServiceTest extends TestCase{

	@Autowired
	private PbcAccountDetailService pbcAccountDetailService;
	
	//构造方法
	public PbcAccountDetailServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcAccountDetailServiceTest.class));
	}
	
	/**
	 * 
	 * @方法说明：获取账户主体详情List
	 * @时间：2016年12月26日 上午11:58:13
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListPbcAccountDetail() {
		try {
			PbcAccountDetail pbcAccountDetail = new PbcAccountDetail();
			pbcAccountDetail.setAccountOwnerId("1");
			List<PbcAccountDetail> riskPList = pbcAccountDetailService.findList(pbcAccountDetail);
			if(null != riskPList && riskPList.size() > 0){
				Assert.assertEquals("获取账户主体详情List", "成功");
			}else{
				Assert.assertEquals("获取账户主体详情List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：根据id获取实体
	 * @时间：2016年12月26日 上午11:59:59
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			Long pbcId = 111111L;
			PbcAccountDetail r = pbcAccountDetailService.getEntityById(pbcId);
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
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:01:59
	 * @创建人：wangdong
	 */
	@Test
	public void testGetFeeBackId() {
		try {
			String applicationId = "1";
			PbcAccountDetail r = pbcAccountDetailService.getFeeBackId(applicationId);
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
	 * @时间：2016年12月26日 下午12:02:25
	 * @创建人：wangdong
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testSaveMap() {
		try {
			Map map = new HashMap();
			int num = pbcAccountDetailService.saveMap(map);
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
