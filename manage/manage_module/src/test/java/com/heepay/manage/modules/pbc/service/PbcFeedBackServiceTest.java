package com.heepay.manage.modules.pbc.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试反馈信息Service
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
public class PbcFeedBackServiceTest extends TestCase{

	@Autowired
	private PbcFeedBackService pbcFeedBackService;
	
	//构造方法
	public PbcFeedBackServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcFeedBackServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:33:06
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListPbcFeedBack() {
		try {
			PbcFeedBack pbcFeedBack = new PbcFeedBack();
			pbcFeedBack.setApplicationCode("1");
			List<PbcFeedBack> riskPList = pbcFeedBackService.findList(pbcFeedBack);
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
	 * @方法说明：保存实体信息
	 * @时间：2016年12月26日 下午12:35:37
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveEntityPbcFeedBack() {
		try {
			PbcFeedBack pbcFeedBack = new PbcFeedBack();
			pbcFeedBack.setApplicationCode("1");
			pbcFeedBackService.saveEntity(pbcFeedBack);
			Assert.assertEquals("保存实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：更新实体信息
	 * @时间：2016年12月26日 下午12:36:49
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdateEntity() {
		try {
			PbcFeedBack pbcFeedBack = new PbcFeedBack();
			pbcFeedBack.setApplicationCode("1");
			int num = pbcFeedBackService.updateEntity(pbcFeedBack);
			if(num > 0){
				Assert.assertEquals("更新实体信息", "成功");
			}else{
				Assert.assertEquals("更新实体信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:37:44
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			Long pbcId = 111111L;
			PbcFeedBack r = pbcFeedBackService.getEntityById(pbcId);
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
	 * @时间：2016年12月26日 下午12:38:41
	 * @创建人：wangdong
	 */
	@Test
	public void testFindPbcFeedBackPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<PbcFeedBack> page = new Page<PbcFeedBack>(request,response);
			PbcFeedBack pbcFeedBack = new PbcFeedBack();
			model = pbcFeedBackService.findPbcFeedBackPage(page, pbcFeedBack, model);
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
	 * @方法说明：导出信息
	 * @时间：2016年12月26日 下午12:40:05
	 * @创建人：wangdong
	 */
	@Test
	public void testExport(HttpServletRequest request, HttpServletResponse response) {
		try {
			PbcFeedBack pbcFeedBack = new PbcFeedBack();
			pbcFeedBackService.export(pbcFeedBack, request, response);
			Assert.assertEquals("导出实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("导出实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：上传文件和入库
	 * @时间：2016年12月26日 下午3:05:00
	 * @创建人：wangdong
	 */
	@Test
	public void testUpLoadFileAndInsert(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		try {
			pbcFeedBackService.upLoadFileAndInsert(file, request, response);
			Assert.assertEquals("导出实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("导出实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	public void testSaveEntityPbcFeedBackHttpServletRequestRedirectAttributesModelStringStringMultipartFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveEntityByPaymentAccountPbcPaymentAccount() {
		fail("Not yet implemented");
	}
	
	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午12:40:42
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityByApplicationCode() {
		try {
			String applicationId = "1111";
			PbcFeedBack r = pbcFeedBackService.getEntityByApplicationCode(applicationId);
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
	public void testSaveEntityByPaymentAccountPbcAccountTransDetail() {
		fail("Not yet implemented");
	}

}
