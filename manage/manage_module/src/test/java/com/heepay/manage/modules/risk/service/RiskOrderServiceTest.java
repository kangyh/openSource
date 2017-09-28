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
import com.heepay.manage.modules.risk.entity.RiskOrder;

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
public class RiskOrderServiceTest extends TestCase{
	
	@Autowired
	private RiskOrderService riskOrderService;
	
	//构造方法
	public RiskOrderServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(RiskOrderServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询风险订单信息
	 * @时间：2016年11月24日 下午1:36:18
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			RiskOrder riskOrder = riskOrderService.get(1499L);
			if(null != riskOrder){
				Assert.assertEquals("根据主键查询风险订单信息", "成功");
			}else{
				Assert.assertEquals("根据主键查询风险订单信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询风险订单信息List
	 * @时间：2016年11月24日 下午1:40:07
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListRiskOrder() {
		try {
			RiskOrder riskOrder = new RiskOrder();
			List<RiskOrder> riskPList = riskOrderService.findList(riskOrder);
			if(null != riskPList && riskPList.size() > 0){
				Assert.assertEquals("查询风险订单信息List", "成功");
			}else{
				Assert.assertEquals("查询风险订单信息List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询风险订单信息page
	 * @时间：2016年11月24日 下午1:42:49
	 * @创建人：wangdong
	 */
	@Test
	public void testFindRiskOrderPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<RiskOrder> page = new Page<RiskOrder>(request,response);
			RiskOrder riskOrder = new RiskOrder();
			model = riskOrderService.findRiskOrderPage(page, riskOrder, model);
			if(null != model){
				Assert.assertEquals("查询风险订单信息page", "成功");
			}else{
				Assert.assertEquals("查询风险订单信息page", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：风险订单信息导出
	 * @时间：2016年11月24日 下午1:44:15
	 * @创建人：wangdong
	 */
	@Test
	public void testExportRiskOrderExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			RiskOrder riskOrder = new RiskOrder();
			riskOrderService.exportRiskOrderExcel(riskOrder,request,response);
			Assert.assertEquals("风险订单信息导出", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals("风险订单信息导出", "失败");
		}
	}

}
