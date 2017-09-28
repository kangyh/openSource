package com.heepay.rpc.billing.service.impl.profit.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.rpc.billing.model.ClearShareProfitDetailModel;
import com.heepay.rpc.billing.model.ClearShareProfitModel;
import com.heepay.rpc.billing.service.impl.profit.service.ClearingProfitDetailServiceTransactionalImpl;

import junit.framework.TestCase;
import junit.framework.TestSuite;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class ClearingProfitDetailServiceTransactionalImplTest extends TestCase{
	
	public ClearingProfitDetailServiceTransactionalImplTest(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(ClearingProfitDetailServiceTransactionalImplTest.class));
	}

	@Autowired
	private ClearingProfitDetailServiceTransactionalImpl clearingProfitDetailServiceTransactionalImpl;

	/**
	 * 
	 * @方法说明：分润明细入库和更新商户侧清算记录是否分润字段  总方法
	 * @时间：2016年11月3日 下午2:53:41
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveClearShareProfitRecord() {
		try {
		      ClearShareProfitModel clearShareProfitModel = new ClearShareProfitModel();
		      ClearShareProfitDetailModel clearShareProfitDetailModel = new ClearShareProfitDetailModel();
		      List<ClearShareProfitDetailModel> profitDetailList = new ArrayList<ClearShareProfitDetailModel>();
		      clearShareProfitDetailModel.setShareDetailId("1000022");
		      clearShareProfitDetailModel.setTransType("PAYMNT");
		      clearShareProfitDetailModel.setProfitAmount("23546.0022");
		      profitDetailList.add(clearShareProfitDetailModel);
		      clearShareProfitModel.setMerchantId(100085);     //商户编码
		      clearShareProfitModel.setTransNo("807100001478155346");//交易单号
		      clearShareProfitModel.setIsShare("true");           //是否分润
		      clearingProfitDetailServiceTransactionalImpl.saveClearingProfit(clearShareProfitModel);
			Assert.assertEquals("分润明细入库和更新商户侧清算记录是否分润字段","成功");
		} catch (Exception e) {
			Assert.assertEquals("分润明细入库和更新商户侧清算记录是否分润字段","失败");
		}
	}

	/**
	 * 
	 * @方法说明：更新商户侧清算记录是否分润字段
	 * @时间：2016年11月3日 下午2:53:31
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdateClearMerChantRecordIsProfitByMerChantIdAndTransNo() {
		try {
			ClearShareProfitModel clearShareProfitVo = new ClearShareProfitModel();
			clearShareProfitVo.setIsShare("true");
			ClearMerchantRecord clearMerchantRecord = new ClearMerchantRecord();
			clearMerchantRecord.setMerchantId(100085);
			clearMerchantRecord.setTransNo("807100001478155346");
			clearingProfitDetailServiceTransactionalImpl.updateClearMerChantRecordIsProfitByMerChantIdAndTransNo(clearShareProfitVo, clearMerchantRecord);
			Assert.assertEquals("更新商户侧清算记录是否分润字段","成功");
		} catch (Exception e) {
			Assert.assertEquals("更新商户侧清算记录是否分润字段","失败");
		}
	}
	
	/**
	 * 
	 * @方法说明：分润明细入库
	 * @时间：2016年11月3日 下午4:40:15
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveClearingProfitDetail(){
		try {
			ClearShareProfitDetailModel clearShareProfitDetailModel = new ClearShareProfitDetailModel();
			//解析获取分润明细
			clearShareProfitDetailModel.setShareDetailId("1000022");
			clearShareProfitDetailModel.setTransNo("807100001478155346");
		    clearShareProfitDetailModel.setTransType("PAYMNT");
		    clearShareProfitDetailModel.setProfitAmount("23546.0022");
		    clearingProfitDetailServiceTransactionalImpl.saveClearingProfitDetail(clearShareProfitDetailModel,"11111111");
			Assert.assertEquals("分润明细入库","成功");
		} catch (Exception e) {
			Assert.assertEquals("分润明细入库","失败");
		}
	}

}
