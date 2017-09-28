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

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.risk.entity.RiskRuleDetail;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试风险订单Service
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
public class RiskRuleDetailServiceTest extends TestCase{
	
	@Autowired
	private RiskRuleDetailService riskRuleDetailService;
	
	//构造方法
	public RiskRuleDetailServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(RiskRuleDetailServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询规则明细信息
	 * @时间：2016年12月1日 上午10:32:31
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			RiskRuleDetail riskRuleDetail = riskRuleDetailService.get(1499L);
			if(null != riskRuleDetail){
				Assert.assertEquals("根据主键查询规则明细信息", "成功");
			}else{
				Assert.assertEquals("根据主键查询规则明细信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询规则明细信息List
	 * @时间：2016年12月1日 上午10:32:31
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListRiskRuleDetail() {
		try {
			RiskRuleDetail riskRuleDetail = new RiskRuleDetail();
			List<RiskRuleDetail> riskRuleDetailList = riskRuleDetailService.findList(riskRuleDetail);
			if(null != riskRuleDetailList && riskRuleDetailList.size() > 0){
				Assert.assertEquals("查询规则明细信息List", "成功");
			}else{
				Assert.assertEquals("查询规则明细信息List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询规则明细信息page
	 * @时间：2016年12月1日 上午10:32:31
	 * @创建人：wangdong
	 */
	@Test
	public void testFindRiskRuleDetailPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<RiskRuleDetail> page = new Page<RiskRuleDetail>(request,response);
			RiskRuleDetail riskRuleDetail = new RiskRuleDetail();
			model = riskRuleDetailService.findRiskRuleDetailPage(page, riskRuleDetail, model);
			if(null != model){
				Assert.assertEquals("查询规则明细信息page", "成功");
			}else{
				Assert.assertEquals("查询规则明细信息page", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
