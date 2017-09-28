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
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
* 
*
* 描    述：商户侧结算记录Service
*
* 创 建 者：   wangdong
* 创建时间：2016年10月20日 下午6:09:44
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
public class SettleMerchantRecordServiceTest extends TestCase{

	@Autowired
	private SettleMerchantRecordService settleMerchantRecordService;
	
	//构造方法
	public SettleMerchantRecordServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(SettleMerchantRecordServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询商户侧结算记录
	 * @时间：2016年10月20日 下午6:10:37
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			SettleMerchantRecord settleMerchantRecord = settleMerchantRecordService.get(3L);
			if(null != settleMerchantRecord){
				Assert.assertEquals("根据主键查询商户侧结算记录", "成功");
			}else{
				Assert.assertEquals("根据主键查询商户侧结算记录", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询商户侧结算记录List
	 * @时间：2016年10月20日 下午6:11:51
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListSettleMerchantRecord() {
		try {
			SettleMerchantRecord settleMerchantRecord = new SettleMerchantRecord();
			settleMerchantRecord.setBeginCheckTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			settleMerchantRecord.setEndCheckTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			settleMerchantRecord.setBeginSettleTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			settleMerchantRecord.setEndSettleTime(DateUtils.getStrDate(new Date(),"yyyy-MM-dd"));
			settleMerchantRecord.setTransType("BATPAY");
			settleMerchantRecord.setMerchantId(100015);
			settleMerchantRecord.setSettleStatus("Y");
			settleMerchantRecord.setSettleBath("18201610181402655851");
			List<SettleMerchantRecord> settleMerchantRecordList = settleMerchantRecordService.findList(settleMerchantRecord);
			if(null != settleMerchantRecordList && settleMerchantRecordList.size() > 0){
				Assert.assertEquals("查询商户侧结算记录List", "成功");
			}else{
				Assert.assertEquals("查询商户侧结算记录List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询商户侧结算数据信息
	 * @时间：2016年11月17日 下午3:35:58
	 * @创建人：wangdong
	 */
	@Test
	public void testFindSettleMerchantRecordPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<SettleMerchantRecord> page = new Page<SettleMerchantRecord>(request,response);
			SettleMerchantRecord settleMerchantRecord = new SettleMerchantRecord();
			model = settleMerchantRecordService.findSettleMerchantRecordPage(page, settleMerchantRecord, model);
			if(null != model){
				Assert.assertEquals("查询商户侧结算数据信息", "成功");
			}else{
				Assert.assertEquals("查询商户侧结算数据信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：用于商户侧结算信息详细
	 * @时间：2016年11月17日 上午10:35:26
	 * @创建人：wangdong
	 */
	@Test
	public void testFindSettleMerchantRecordDetailPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<ClearingMerchantRecord> page = new Page<ClearingMerchantRecord>(request,response);
			ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
			model = settleMerchantRecordService.findSettleMerchantRecordDetailPage(page, clearingMerchantRecord, model);
			if(null != model){
				Assert.assertEquals(model, "成功");
			}else{
				Assert.assertEquals(model, "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：商户侧结算数据信息导出
	 * @时间：2016年11月17日 上午10:38:05
	 * @创建人：wangdong
	 */
	@Test
	public void testExportSettleMerchantRecordExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			SettleMerchantRecord settleMerchantRecord = new SettleMerchantRecord();
			settleMerchantRecord.setMerchantId(100085);
			settleMerchantRecordService.exportSettleMerchantRecordExcel(settleMerchantRecord,response,request);
			Assert.assertEquals("通道侧清算数据信息导出", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals("通道侧清算数据信息导出", "失败");
		}
	}

}
