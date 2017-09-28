package com.heepay.manage.modules.settle.service;

import java.util.Date;
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

import com.heepay.date.DateUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试通道侧清算记录Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年10月20日 下午2:58:48
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
public class ClearingChannelRecordServiceTest extends TestCase{
	
	@Autowired
	private ClearingChannelRecordService clearingChannelRecordService;
	
	//构造方法
	public ClearingChannelRecordServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(ClearingChannelRecordServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询通道侧清算记录
	 * @时间：2016年10月20日 下午3:49:42
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			ClearingChannelRecord clearingChannelRecord = clearingChannelRecordService.get(1499L);
			if(null != clearingChannelRecord){
				Assert.assertEquals("根据主键查询通道侧清算记录", "成功");
			}else{
				Assert.assertEquals("根据主键查询通道侧清算记录", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询通道侧清算记录
	 * @时间：2016年10月20日 下午3:49:42
	 * @创建人：wangdong
	 */
	@Test
	public void testFindList() {
		try {
			ClearingChannelRecord clearingChannelRecord = new ClearingChannelRecord();
			clearingChannelRecord.setBeginChannelTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			clearingChannelRecord.setEndChannelTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			clearingChannelRecord.setChannelName("快钱");
			clearingChannelRecord.setChannelType("QUICKP");
			clearingChannelRecord.setPaymentId("1000483");
			clearingChannelRecord.setCheckStatus("Y");
			clearingChannelRecord.setSettleStatus("Y");
			clearingChannelRecord.setChannelCode("BILPAY");
			clearingChannelRecord.setCheckFlg("QSTS");
			clearingChannelRecord.setTransNo("181100001476946790");
			clearingChannelRecord.setSettleBath("19201610156171900");
			List<ClearingChannelRecord> clearingChannelRecordList = clearingChannelRecordService.findList(clearingChannelRecord);
			if(null != clearingChannelRecordList && clearingChannelRecordList.size() > 0){
				Assert.assertEquals("查询通道侧清算记录", "成功");
			}else{
				Assert.assertEquals("查询通道侧清算记录", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询通道侧清算记录的名称List
	 * @时间：2016年10月20日 下午3:49:42
	 * @创建人：wangdong
	 */
	@Test
	public void testFindChannelRecordName() {
		try {
			List<ClearingChannelRecord> clearingChannelRecordList = clearingChannelRecordService.findChannelRecordName();
			if(null != clearingChannelRecordList && clearingChannelRecordList.size() > 0){
				Assert.assertEquals("查询通道侧清算记录的名称List", "成功");
			}else{
				Assert.assertEquals("查询通道侧清算记录的名称List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询通道侧清算记录
	 * @时间：2016年11月17日 上午9:29:26
	 * @创建人：wangdong
	 */
	@Test
	public void testFindClearingChannelRecordPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<ClearingChannelRecord> page = new Page<ClearingChannelRecord>(request,response);
			ClearingChannelRecord clearingChannelRecord = new ClearingChannelRecord();
			model = clearingChannelRecordService.findClearingChannelRecordPage(page, clearingChannelRecord, model);
			if(null != model){
				Assert.assertEquals("查询通道侧清算记录", "成功");
			}else{
				Assert.assertEquals("查询通道侧清算记录", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：通道侧清算数据信息导出
	 * @时间：2016年11月17日 上午9:59:25
	 * @创建人：wangdong
	 */
	@Test
	public void testExportClearingChannelRecordExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			ClearingChannelRecord clearingChannelRecord = new ClearingChannelRecord();
			clearingChannelRecord.setChannelCode("BILPAY");
			clearingChannelRecordService.exportClearingChannelRecordExcel(clearingChannelRecord,request,response);
			Assert.assertEquals("通道侧清算数据信息导出", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals("通道侧清算数据信息导出", "失败");
		}
	}

}
