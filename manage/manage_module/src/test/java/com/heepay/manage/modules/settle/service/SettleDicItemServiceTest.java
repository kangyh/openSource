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

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.settle.entity.SettleDicItem;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试字典类型明细Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年10月20日 下午4:56:35
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
public class SettleDicItemServiceTest extends TestCase{

	@Autowired
	private SettleDicItemService settleDicItemService;
	
	//构造方法
	public SettleDicItemServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(SettleDicItemServiceTest.class));
	}
	
	/**
	 * 
	 * @方法说明：根据主键查询类型明细
	 * @时间：2016年10月20日 下午4:58:31
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			SettleDicItem settleDicItem = settleDicItemService.get(9L);
			if(null != settleDicItem){
				Assert.assertEquals("根据主键查询类型明细", "成功");
			}else{
				Assert.assertEquals("根据主键查询类型明细", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询类型明细List
	 * @时间：2016年10月20日 下午4:58:49
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListSettleDicItem() {
		try {
			SettleDicItem settleDicItem = new SettleDicItem();
//			settleDicItem.setItemId(9L);
//			settleDicItem.setTypeId(9);
			settleDicItem.setStatus("Y");
			List<SettleDicItem> settleDicItemList = settleDicItemService.findList(settleDicItem);
			if(null != settleDicItemList && settleDicItemList.size() > 0){
				Assert.assertEquals("查询类型明细List", "成功");
			}else{
				Assert.assertEquals("查询类型明细List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：保存类型明细信息
	 * @时间：2016年10月20日 下午4:58:49
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveSettleDicItem(HttpServletRequest request) {
		SettleDicItem settleDicItem = new SettleDicItem();
		try {
			settleDicItem.setText("支付");
			settleDicItem.setValue("PAY");
			settleDicItem.setStatus("Y");
			settleDicItem.setTypeId(9);
			settleDicItemService.saveSettleDicItem(settleDicItem,request);
			Assert.assertEquals("保存类型明细信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存类型明细信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询是否存在相同的类型明细
	 * @时间：2016年10月20日 下午4:58:49
	 * @创建人：wangdong
	 */
	@Test
	public void testFindBySettleDicItemExist() {
		SettleDicItem settleDicItem = new SettleDicItem();
		try {
			settleDicItem.setText("支付");
			settleDicItem.setValue("PAY");
			settleDicItem.setStatus("Y");
			settleDicItem.setTypeId(9);
			settleDicItemService.findBySettleDicItemExist(settleDicItem);
			Assert.assertEquals("查询是否存在相同的类型明细", "成功");
		} catch (Exception e) {
			Assert.assertEquals("查询是否存在相同的类型明细", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询明细根据编码，名称，类型去重
	 * @时间：2016年10月25日20:33:30
	 * @创建人：wangdong
	 */
	@Test
	public void testFindDistinctItem(){
		try {
			List<SettleDicItem> itemList = settleDicItemService.findDistinctItem();
			if(null != itemList && itemList.size() > 0){
				Assert.assertEquals("查询明细根据编码，名称，类型去重", "成功");
			}else{
				Assert.assertEquals("查询明细根据编码，名称，类型去重", "失败");
			}
		} catch (Exception e) {
			Assert.assertEquals("查询明细根据编码，名称，类型去重", "失败");
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：用于查询类型明细
	 * @时间：2016年11月17日 上午10:42:03
	 * @创建人：wangdong
	 */
	@Test
	public void testFindSettleDicItemPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<SettleDicItem> page = new Page<SettleDicItem>(request,response);
			SettleDicItem settleDicItem = new SettleDicItem();
			model = settleDicItemService.findSettleDicItemPage(page, settleDicItem, model);
			if(null != model){
				Assert.assertEquals("用于查询类型明细", "成功");
			}else{
				Assert.assertEquals("用于查询类型明细", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：跳转类型明细添加页面
	 * @时间：2016年11月17日 上午10:43:25
	 * @创建人：wangdong
	 */
	@Test
	public void testGoToSettleDicItemAddJsp(Model model) {
		try {
			SettleDicItem settleDicItem = new SettleDicItem();
			model = settleDicItemService.goToSettleDicItemAddJsp(settleDicItem, model);
			if(null != model){
				Assert.assertEquals("跳转类型明细添加页面", "成功");
			}else{
				Assert.assertEquals("跳转类型明细添加页面", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询结算区间是否存在明细
	 * @时间：2016年11月17日 上午10:44:48
	 * @创建人：wangdong
	 */
	@Test
	public void testChechTypeSettleTle() {
		try {
			SettleDicItem settleDicItem = new SettleDicItem();
			String flg = settleDicItemService.chechTypeSettleTle(settleDicItem);
			if(StringUtils.isNotBlank(flg) && StringUtils.equals(flg.split(",")[0],"true")){
				Assert.assertEquals("查询结算区间是否存在明细", "不存在该区间");
			}else{
				Assert.assertEquals("查询结算区间是否存在明细", "存在该区间");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：存在相同的类型明细
	 * @时间：2016年11月17日 上午10:48:01
	 * @创建人：wangdong
	 */
	@Test
	public void testSettleDicItemExist(Model model) {
		try {
			SettleDicItem settleDicItem = new SettleDicItem();
			model = settleDicItemService.settleDicItemExist(settleDicItem,model);
			if(null != model){
				Assert.assertEquals("存在相同的类型明细", "成功");
			}else{
				Assert.assertEquals("存在相同的类型明细", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}
}
