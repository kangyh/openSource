package com.heepay.manage.modules.risk.service;

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

import com.heepay.enums.AccountType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.risk.entity.RiskProductQuota;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试产品限额Service
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
public class RiskProductQuotaServiceTest extends TestCase{

	@Autowired
	private RiskProductQuotaService riskProductQuotaService;
	
	//构造方法
	public RiskProductQuotaServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(RiskProductQuotaServiceTest.class));
	}
	
	/**
	 * 
	 * @方法说明：根据主键获取产品限额信息
	 * @时间：2016年11月22日 上午11:47:18
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			RiskProductQuota riskProductQuota = riskProductQuotaService.get(1499L);
			if(null != riskProductQuota){
				Assert.assertEquals("根据主键获取产品限额信息", "成功");
			}else{
				Assert.assertEquals("根据主键获取产品限额信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：根据条件查询产品限额信息
	 * @时间：2016年11月22日 上午11:54:33
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListRiskProductQuota() {
		try {
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			riskProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			List<RiskProductQuota> riskPList = riskProductQuotaService.findList(riskProductQuota);
			if(null != riskPList && riskPList.size() > 0){
				Assert.assertEquals("根据条件查询产品限额信息", "成功");
			}else{
				Assert.assertEquals("根据条件查询产品限额信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：根据条件查询产品限额信息page
	 * @时间：2016年11月22日 上午11:55:15
	 * @创建人：wangdong
	 */
	@Test
	public void testFindRiskProductQuotaPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<RiskProductQuota> page = new Page<RiskProductQuota>(request,response);
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			model = riskProductQuotaService.findRiskProductQuotaPage(page, riskProductQuota, model);
			if(null != model){
				Assert.assertEquals("根据条件查询产品限额信息page", "成功");
			}else{
				Assert.assertEquals("根据条件查询产品限额信息page", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：跳转添加/修改页面
	 * @时间：2016年11月22日 上午11:56:30
	 * @创建人：wangdong
	 */
	@Test
	public void testGoToRiskProductQuotaAddOrEditJsp(Model model) {
		try {
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			model = riskProductQuotaService.goToRiskProductQuotaAddOrEditJsp(riskProductQuota, model);
			if(null != model){
				Assert.assertEquals("跳转添加/修改页面", "成功");
			}else{
				Assert.assertEquals("跳转添加/修改页面", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：保存/修改产品限额信息
	 * @时间：2016年11月22日 上午11:58:00
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveRiskProductQuota(HttpServletRequest request) {
		try {
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			riskProductQuota.setProductCode("CP01");
			riskProductQuota.setProductName("ceshi");
			riskProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			riskProductQuotaService.saveRiskProductQuota(riskProductQuota,request);
			Assert.assertEquals("保存/修改产品限额信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存/修改产品限额信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询是否存在相同产品和银行卡类型
	 * @时间：2016年11月22日 下午12:02:50
	 * @创建人：wangdong
	 */
	@Test
	public void testFindByRiskProductQuotaExist() {
		try {
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			riskProductQuota.setProductCode("CP01");
			riskProductQuota.setProductName("ceshi");
			riskProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			Integer count = riskProductQuotaService.findByRiskProductQuotaExist(riskProductQuota);
			if(null != count && count > 0){
				Assert.assertEquals("查询是否存在相同产品和银行卡类型", "成功");
			}else{
				Assert.assertEquals("查询是否存在相同产品和银行卡类型", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：存在相同产品和银行卡类型
	 * @时间：2016年11月22日 下午12:05:36
	 * @创建人：wangdong
	 */
	@Test
	public void testRiskProductQuotaExist(Model model) {
		try {
			RiskProductQuota riskProductQuota = new RiskProductQuota();
			riskProductQuota.setProductCode("CP01");
			riskProductQuota.setProductName("ceshi");
			riskProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			model = riskProductQuotaService.riskProductQuotaExist(riskProductQuota,model);
			if(null != model){
				Assert.assertEquals("存在相同产品和银行卡类型", "成功");
			}else{
				Assert.assertEquals("存在相同产品和银行卡类型", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
