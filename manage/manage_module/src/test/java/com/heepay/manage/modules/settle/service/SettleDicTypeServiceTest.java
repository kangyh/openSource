package com.heepay.manage.modules.settle.service;

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
import com.heepay.manage.modules.settle.entity.SettleDicItem;
import com.heepay.manage.modules.settle.entity.SettleDicType;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试类型Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年10月20日 下午5:08:27
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
public class SettleDicTypeServiceTest extends TestCase{

	@Autowired
	private SettleDicTypeService settleDicTypeService;
	
	//构造方法
	public SettleDicTypeServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(SettleDicTypeServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询类型
	 * @时间：2016年10月20日 下午5:08:46
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			SettleDicType settleDicType = settleDicTypeService.get(3);
			if(null != settleDicType){
				Assert.assertEquals("根据主键查询类型", "成功");
			}else{
				Assert.assertEquals("根据主键查询类型", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询类型List
	 * @时间：2016年10月20日 下午5:08:46
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListSettleDicType() {
		try {
			SettleDicType settleDicType = new SettleDicType();
			settleDicType.setCode("SETTLE_AREA");
			settleDicType.setText("结算区间");
			settleDicType.setStatus("Y");
			List<SettleDicType> settleDicTypeList = settleDicTypeService.findList(settleDicType);
			if(null != settleDicTypeList && settleDicTypeList.size() > 0){
				Assert.assertEquals("查询类型List", "成功");
			}else{
				Assert.assertEquals("查询类型List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：保存类型信息
	 * @时间：2016年10月20日 下午5:08:46
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveSettleDicType(HttpServletRequest request) {
		SettleDicType settleDicType = new SettleDicType();
		try {
			settleDicType.setCode("SETTLE_AREA");
			settleDicType.setText("结算区间");
			settleDicType.setStatus("Y");
			settleDicTypeService.saveSettleDicType(settleDicType,request);
			Assert.assertEquals("保存类型信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存类型信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询是否存在相同的类型
	 * @时间：2016年10月20日 下午5:08:46
	 * @创建人：wangdong
	 */
	@Test
	public void testFindCodeTextBySettleDicTypeExist() {
		SettleDicType settleDicType = new SettleDicType();
		try {
			settleDicType.setCode("SETTLE_AREA");
			settleDicType.setText("结算区间");
			settleDicType.setStatus("Y");
			settleDicTypeService.findCodeTextBySettleDicTypeExist(settleDicType);
			Assert.assertEquals("查询是否存在相同的类型", "成功");
		} catch (Exception e) {
			Assert.assertEquals("查询是否存在相同的类型", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询去重的类型List
	 * @时间：2016年11月17日 上午11:00:46
	 * @创建人：wangdong
	 */
	@Test
	public void testFindDistinctType() {
		try {
			List<SettleDicType> settleDicTypeList = settleDicTypeService.findDistinctType();
			if(null != settleDicTypeList && settleDicTypeList.size() > 0){
				Assert.assertEquals("查询去重的类型List", "成功");
			}else{
				Assert.assertEquals("查询去重的类型List", "失败");
			}
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询类型根据typeId和text去重
	 * @时间：2016年11月17日 上午10:57:32
	 * @创建人：wangdong
	 */
	@Test
	public void testFindDistinctTypeIdType() {
		try {
			SettleDicItem settleDicItem = new SettleDicItem();
			List<SettleDicType> settleDicTypeList = settleDicTypeService.findDistinctTypeIdType(settleDicItem);
			if(null != settleDicTypeList && settleDicTypeList.size() > 0){
				Assert.assertEquals("查询类型根据typeId和text去重", "成功");
			}else{
				Assert.assertEquals("查询类型根据typeId和text去重", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询类型信息
	 * @时间：2016年11月17日 上午10:55:52
	 * @创建人：wangdong
	 */
	@Test
	public void testFindSettleDicTypePage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<SettleDicType> page = new Page<SettleDicType>(request,response);
			SettleDicType settleDicType = new SettleDicType();
			model = settleDicTypeService.findSettleDicTypePage(page, settleDicType, model);
			if(null != model){
				Assert.assertEquals("查询类型信息", "成功");
			}else{
				Assert.assertEquals("查询类型信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：跳转类型添加页面
	 * @时间：2016年11月17日 上午10:54:43
	 * @创建人：wangdong
	 */
	@Test
	public void testGoToSettleDicTypeAddJsp(Model model) {
		try {
			SettleDicType settleDicType = new SettleDicType();
			model = settleDicTypeService.goToSettleDicTypeAddJsp(settleDicType, model);
			if(null != model){
				Assert.assertEquals("跳转类型添加页面", "成功");
			}else{
				Assert.assertEquals("跳转类型添加页面", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
