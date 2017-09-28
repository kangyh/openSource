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
import com.heepay.manage.modules.risk.entity.RiskMerchantProductQuota;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试商户产品限额Service
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
public class RiskMerchantProductQuotaServiceTest extends TestCase{
	
	@Autowired
	private RiskMerchantProductQuotaService riskMerchantProductQuotaService;
	
	//构造方法
	public RiskMerchantProductQuotaServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(RiskMerchantProductQuotaServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询商户产品限额信息
	 * @时间：2016年11月22日 下午1:29:58
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			RiskMerchantProductQuota riskMerchantProductQuota = riskMerchantProductQuotaService.get(1499L);
			if(null != riskMerchantProductQuota){
				Assert.assertEquals("根据主键查询商户产品限额信息", "成功");
			}else{
				Assert.assertEquals("根据主键查询商户产品限额信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询商户产品限额信息list
	 * @时间：2016年11月22日 下午1:30:41
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListRiskMerchantProductQuota() {
		try {
			RiskMerchantProductQuota riskMerchantProductQuota = new RiskMerchantProductQuota();
			riskMerchantProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			List<RiskMerchantProductQuota> riskPList = riskMerchantProductQuotaService.findList(riskMerchantProductQuota);
			if(null != riskPList && riskPList.size() > 0){
				Assert.assertEquals("查询商户产品限额信息list", "成功");
			}else{
				Assert.assertEquals("查询商户产品限额信息list", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询商户产品限额信息page
	 * @时间：2016年11月22日 下午1:31:42
	 * @创建人：wangdong
	 */
	@Test
	public void testFindRiskMerchantProductQuotaPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<RiskMerchantProductQuota> page = new Page<RiskMerchantProductQuota>(request,response);
			RiskMerchantProductQuota riskMerchantProductQuota = new RiskMerchantProductQuota();
			model = riskMerchantProductQuotaService.findRiskMerchantProductQuotaPage(page, riskMerchantProductQuota, model);
			if(null != model){
				Assert.assertEquals("查询商户产品限额信息page", "成功");
			}else{
				Assert.assertEquals("查询商户产品限额信息page", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：跳转添加/修改页面
	 * @时间：2016年11月22日 下午1:33:02
	 * @创建人：wangdong
	 */
	@Test
	public void testGoToRiskMerchantProductQuotaEditJsp(Model model) {
		try {
			RiskMerchantProductQuota riskMerchantProductQuota = new RiskMerchantProductQuota();
			model = riskMerchantProductQuotaService.goToRiskMerchantProductQuotaEditJsp(riskMerchantProductQuota, model);
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
	 * @方法说明：保存/更新商户产品限额信息
	 * @时间：2016年11月22日 下午1:33:56
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveRiskMerchantProductQuota(HttpServletRequest request) {
		try {
			RiskMerchantProductQuota riskMerchantProductQuota = new RiskMerchantProductQuota();
			riskMerchantProductQuota.setProductCode("CP01");
			riskMerchantProductQuota.setProductName("ceshi");
			riskMerchantProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			riskMerchantProductQuotaService.saveRiskMerchantProductQuota(riskMerchantProductQuota,request);
			Assert.assertEquals("保存/更新商户产品限额信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存/更新商户产品限额信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：验证是否存在相同商户号，产品编码，账户类型，银行卡类型
	 * @时间：2016年11月22日 下午1:35:13
	 * @创建人：wangdong
	 */
	@Test
	public void testFindRiskMerchantProductQuotaExist() {
		try {
			RiskMerchantProductQuota riskMerchantProductQuota = new RiskMerchantProductQuota();
			riskMerchantProductQuota.setProductCode("CP01");
			riskMerchantProductQuota.setProductName("ceshi");
			riskMerchantProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			Integer count = riskMerchantProductQuotaService.findRiskMerchantProductQuotaExist(riskMerchantProductQuota);
			if(null != count && count > 0){
				Assert.assertEquals("验证是否存在相同商户号，产品编码，账户类型，银行卡类型", "成功");
			}else{
				Assert.assertEquals("验证是否存在相同商户号，产品编码，账户类型，银行卡类型", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：存在相同商户号，产品编码，账户类型，银行卡类型
	 * @时间：2016年11月22日 下午1:36:34
	 * @创建人：wangdong
	 */
	@Test
	public void testRiskMerchantProductQuotaExist(Model model) {
		try {
			RiskMerchantProductQuota riskMerchantProductQuota = new RiskMerchantProductQuota();
			riskMerchantProductQuota.setProductCode("CP01");
			riskMerchantProductQuota.setProductName("ceshi");
			riskMerchantProductQuota.setAccountType(AccountType.PRIVAT.getValue());
			model = riskMerchantProductQuotaService.riskMerchantProductQuotaExist(riskMerchantProductQuota,model);
			if(null != model){
				Assert.assertEquals("存在相同商户号，产品编码，账户类型，银行卡类型", "成功");
			}else{
				Assert.assertEquals("存在相同商户号，产品编码，账户类型，银行卡类型", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}
}
