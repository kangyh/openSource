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
import com.heepay.manage.modules.settle.entity.SettleChannelRecord;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
* 
*
* 描    述：测试通道侧结算记录Service
*
* 创 建 者：   wangdong
* 创建时间：2016年10月20日 下午4:48:11
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
public class SettleChannelRecordServiceTest extends TestCase{

	@Autowired
	private SettleChannelRecordService settleChannelRecordService;
	
	//构造方法
	public SettleChannelRecordServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(SettleChannelRecordServiceTest.class));
	}
	
	/**
	 * 
	 * @方法说明：根据主键查询通道侧结算记录
	 * @时间：2016年10月20日 下午4:48:49
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			SettleChannelRecord settleChannelRecord = settleChannelRecordService.get(245L);
			if(null != settleChannelRecord){
				Assert.assertEquals("根据主键查询通道侧结算记录", "成功");
			}else{
				Assert.assertEquals("根据主键查询通道侧结算记录", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询通道侧结算记录List
	 * @时间：2016年10月20日 下午4:48:49
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListSettleChannelRecord() {
		try {
			SettleChannelRecord settleChannelRecord = new SettleChannelRecord();
			settleChannelRecord.setBeginSettleTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			settleChannelRecord.setEndSettleTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			settleChannelRecord.setChannelName("快钱");
			settleChannelRecord.setChannelType("QUICKP");
			settleChannelRecord.setSettleStatus("Y");
			settleChannelRecord.setChannelCode("BILPAY");
			settleChannelRecord.setSettleBath("19201610156171900");
			List<SettleChannelRecord> settleChannelRecordList = settleChannelRecordService.findList(settleChannelRecord);
			if(null != settleChannelRecordList && settleChannelRecordList.size() > 0){
				Assert.assertEquals("查询通道侧结算记录List", "成功");
			}else{
				Assert.assertEquals("查询通道侧结算记录List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询通道侧结算信息
	 * @时间：2016年11月17日 上午10:25:48
	 * @创建人：wangdong
	 */
	@Test
	public void testFindSettleChannelRecordPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<SettleChannelRecord> page = new Page<SettleChannelRecord>(request,response);
			SettleChannelRecord settleChannelRecord = new SettleChannelRecord();
			model = settleChannelRecordService.findSettleChannelRecordPage(page, settleChannelRecord, model);
			if(null != model){
				Assert.assertEquals("查询通道侧结算信息", "成功");
			}else{
				Assert.assertEquals("查询通道侧结算信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：用于通道侧结算信息详细
	 * @时间：2016年11月17日 上午10:29:03
	 * @创建人：wangdong
	 */
	@Test
	public void testFindSettleChannelRecordDetailPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<ClearingChannelRecord> page = new Page<ClearingChannelRecord>(request,response);
			ClearingChannelRecord clearingChannelRecord = new ClearingChannelRecord();
			model = settleChannelRecordService.findSettleChannelRecordDetailPage(clearingChannelRecord, page, model);
			if(null != model){
				Assert.assertEquals("用于通道侧结算信息详细", "成功");
			}else{
				Assert.assertEquals("用于通道侧结算信息详细", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：通道侧结算数据导出
	 * @时间：2016年11月17日 上午10:30:36
	 * @创建人：wangdong
	 */
	@Test
	public void testExportSettleChannelRecordExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			SettleChannelRecord settleChannelRecord = new SettleChannelRecord();
			settleChannelRecord.setChannelCode("BILPAY");
			settleChannelRecordService.exportSettleChannelRecordExcel(settleChannelRecord,response,request);
			Assert.assertEquals("通道侧清算数据信息导出", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals("通道侧清算数据信息导出", "失败");
		}
	}

}
