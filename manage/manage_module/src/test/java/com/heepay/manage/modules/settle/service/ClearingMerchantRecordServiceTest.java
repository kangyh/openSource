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
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
* 
*
* 描    述：测试商户侧清算记录Service
*
* 创 建 者：   wangdong
* 创建时间：2016年10月20日 下午4:24:34
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
public class ClearingMerchantRecordServiceTest extends TestCase{

	@Autowired
	private ClearingMerchantRecordService clearingMerchantRecordService;
	
	//构造方法
	public ClearingMerchantRecordServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(ClearingMerchantRecordServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：根据主键查询商户侧清算记录
	 * @时间：2016年10月20日 下午4:27:52
	 * @创建人：wangdong
	 */
	@Test
	public void testGetString() {
		try {
			ClearingMerchantRecord clearingMerchantRecord = clearingMerchantRecordService.get(1333L);
			if(null != clearingMerchantRecord){
				Assert.assertEquals("根据主键查询商户侧清算记录", "成功");
			}else{
				Assert.assertEquals("根据主键查询商户侧清算记录", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：查询商户侧清算记录List
	 * @时间：2016年10月20日 下午4:27:52
	 * @创建人：wangdong
	 */
	@Test
	public void testFindListClearingMerchantRecord() {
		try {
			ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
			clearingMerchantRecord.setTransType("PAYMNT");
			clearingMerchantRecord.setCheckStatus("Y");
			clearingMerchantRecord.setSettleStatus("Y");
			clearingMerchantRecord.setMerchantId(100045);
			clearingMerchantRecord.setCheckFlg("QSTS");
			clearingMerchantRecord.setTransNo("181100001476946802");
			clearingMerchantRecord.setSettleBath("DZ201610151683680");
			List<ClearingMerchantRecord> clearingMerchantRecordList = clearingMerchantRecordService.findList(clearingMerchantRecord);
			if(null != clearingMerchantRecordList && clearingMerchantRecordList.size() > 0){
				Assert.assertEquals("查询商户侧清算记录List", "成功");
			}else{
				Assert.assertEquals("查询商户侧清算记录List", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：用于商户结算单据查询明细
	 * @时间：2016年10月20日 下午4:27:52
	 * @创建人：wangdong
	 */
	@Test
	public void testFindPageDetail(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Page<ClearingMerchantRecord> page = new Page<ClearingMerchantRecord>(request,response);
			ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
			clearingMerchantRecord.setTransType("PAYMNT");
			clearingMerchantRecord.setCheckStatus("Y");
			clearingMerchantRecord.setSettleStatus("Y");
			clearingMerchantRecord.setMerchantId(100045);
			clearingMerchantRecord.setCheckFlg("QSTS");
			clearingMerchantRecord.setTransNo("181100001476946802");
			clearingMerchantRecord.setSettleBath("DZ201610151683680");
			Page<ClearingMerchantRecord> clearingMerchantRecordList = clearingMerchantRecordService.findPageDetail(page, clearingMerchantRecord);
			if(null != clearingMerchantRecordList && clearingMerchantRecordList.getList().size() > 0){
				Assert.assertEquals("用于商户结算批次查询明细", "成功");
			}else{
				Assert.assertEquals("用于商户结算批次查询明细", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：用于商户侧清算数据信息查询
	 * @时间：2016年11月17日 上午10:19:00
	 * @创建人：wangdong
	 */
	@Test
	public void testFindClearingMerchantRecordPage(HttpServletRequest request, HttpServletResponse response,Model model) {
		try {
			Page<ClearingMerchantRecord> page = new Page<ClearingMerchantRecord>(request,response);
			ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
			model = clearingMerchantRecordService.findClearingMerchantRecordPage(page, clearingMerchantRecord, model);
			if(null != model){
				Assert.assertEquals("用于商户侧清算数据信息查询", "成功");
			}else{
				Assert.assertEquals("用于商户侧清算数据信息查询", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：用于商户侧清算数据信息导出
	 * @时间：2016年11月17日 上午10:20:59
	 * @创建人：wangdong
	 */
	@Test
	public void testExportClearingMerchantRecordExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
			clearingMerchantRecord.setMerchantId(100081);
			clearingMerchantRecordService.exportClearingMerchantRecordExcel(clearingMerchantRecord,response,request);
			Assert.assertEquals("通道侧清算数据信息导出", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertEquals("通道侧清算数据信息导出", "失败");
		}
	}

}
