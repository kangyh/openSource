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
import com.heepay.manage.modules.pbc.entity.PbcMeasureInfo;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试措施信息Service
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
public class PbcMeasureInfoServiceTest extends TestCase{

	@Autowired
	private PbcMeasureInfoService pbcMeasureInfoService;
	
	//构造方法
	public PbcMeasureInfoServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcMeasureInfoServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:43:33
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListPbcMeasureInfo() {
		try {
			PbcMeasureInfo pbcMeasureInfo = new PbcMeasureInfo();
			pbcMeasureInfo.setApplicationCode("1");
			List<PbcMeasureInfo> riskPList = pbcMeasureInfoService.findList(pbcMeasureInfo);
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
	 * @时间：2016年12月26日 下午12:44:04
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			Long pbcId = 111111L;
			PbcMeasureInfo r = pbcMeasureInfoService.getEntityById(pbcId);
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
	 * @时间：2016年12月26日 下午12:44:42
	 * @创建人：wangdong
	 */
	@Test
	public void testGetFeeBackId() {
		try {
			String applicationId = "1";
			PbcMeasureInfo r = pbcMeasureInfoService.getFeeBackId(applicationId);
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
	 * @时间：2016年12月26日 下午12:45:45
	 * @创建人：wangdong
	 */
	@Test
	public void testFindPbcMeasureInfoPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<PbcMeasureInfo> page = new Page<PbcMeasureInfo>(request,response);
			PbcMeasureInfo pbcMeasureInfo = new PbcMeasureInfo();
			model = pbcMeasureInfoService.findPbcMeasureInfoPage(page, pbcMeasureInfo, model);
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
	 * @时间：2016年12月26日 下午12:46:01
	 * @创建人：wangdong
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testSaveMap() {
		try {
			Map map = new HashMap();
			int num = pbcMeasureInfoService.saveMap(map);
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
