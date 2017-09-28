package com.heepay.manage.modules.pbc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.pbc.entity.PbcBankInfo;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试银行信息Service
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
public class PbcBankInfoServiceTest extends TestCase{

	@Autowired
	private PbcBankInfoService pbcBankInfoService;
	
	//构造方法
	public PbcBankInfoServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcBankInfoServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:12:48
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListPbcBankInfo() {
		try {
			PbcBankInfo pbcBankInfo = new PbcBankInfo();
			pbcBankInfo.setApplicationCode("1");
			List<PbcBankInfo> riskPList = pbcBankInfoService.findList(pbcBankInfo);
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
	 * @时间：2016年12月26日 下午12:29:08
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			Long pbcId = 111111L;
			PbcBankInfo r = pbcBankInfoService.getEntityById(pbcId);
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
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:29:38
	 * @创建人：wangdong
	 */
	@Test
	public void testGetFeeBackId() {
		try {
			String applicationId = "1";
			PbcBankInfo r = pbcBankInfoService.getFeeBackId(applicationId);
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
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:31:08
	 * @创建人：wangdong
	 */
	@Test
	public void testFindPbcBankInfoPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<PbcBankInfo> page = new Page<PbcBankInfo>(request,response);
			PbcBankInfo pbcBankInfo = new PbcBankInfo();
			model = pbcBankInfoService.findPbcBankInfoPage(page, pbcBankInfo, model);
			if(null != model){
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
	 * @时间：2016年12月26日 下午12:30:15
	 * @创建人：wangdong
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testSaveMap() {
		try {
			Map map = new HashMap();
			int num = pbcBankInfoService.saveMap(map);
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
