package com.heepay.rpc.billing.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleRuleControl;
import com.heepay.rpc.client.PayChannelCacheServiceClient;

/**
 * 
 * 
 * 描    述：解析对账文件，对账明细入库测试类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年10月20日下午1:42:42 
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class BillPutInStorageImplTest {

	@Autowired
	BillPutInStorageImpl billPutInStorageImpl;
	@Autowired
	SettleChannelManagerServiceImpl settleChannelManagerServiceImpl;
//	ChannelPatternAndChannelTypeClient client;
	@Autowired
	PayChannelCacheServiceClient client1;
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 
	 * @方法说明：账单入库
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @时间：2016年9月23日下午4:27:09
	 */
	@Test
	public void testBillPutInStorageImpl() {
		try {
			String channelCode = "DIRCON-306";
			String channelType = "B2CEBK";
			String checkBathno = "DZ201612100000020";
			String fileName = "C:\\Users\\Administrator\\Desktop\\真实对账文件\\财付通\\20170315-PAYMNT - 副本.txt";
			boolean b = billPutInStorageImpl.billPutInStorage(channelCode, channelType, checkBathno, fileName,"auto","COMM");
			if(b) {
				Assert.assertEquals(checkBathno, "测试成功");
			}else {
				Assert.assertEquals(checkBathno, "测试失败");
			}
		} catch (Exception e) {
			Assert.assertEquals("测试异常", e);
		}
		
	}
	
	@Test
	public void testSettleChannelManagerServiceImpl() {
		//checkBillRecordServiceImpl.billCompare("DZ201612100000020");
		//String channelByChannelCode = client1.getChannelByChannelCode("105BILPAY_10063");
		//String code = client.getChannelByChannelCode("ChannelType", "批付");
		//logger.info(code);
	}
	
	
	
	/**
	 * 
	 * @方法说明：读取对账文件，获取账单明细数据
	 * @author chenyanming
	 * @param file
	 * @return
	 * @时间：2016年9月25日上午10:24:14
	 */
	@Test
	public void testGetSettleBillRecordData() {
		try {
			String file = new String("C:\\Users\\Administrator\\Desktop\\SHOP.105110073991493.20151214.02.successcym5.txt");
			SettleRuleControl settleRuleControl = new SettleRuleControl();
			String channelCode = "CIBDXT";
			String channelType = "WECHAT"; 
			List<SettleBillRecord> settleBillRecordData = billPutInStorageImpl.getSettleBillRecordData(file, settleRuleControl,channelCode,channelType);
			Assert.assertEquals(settleBillRecordData, "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败", e);
		}
	}
	
	/**
	 * 
	 * @方法说明：获取指定文件的编码格式
	 * @author chenyanming
	 * @时间：2016年12月8日下午4:39:55
	 */
	@Test
	public void testGetFilecharset() {
		try {
			File sourceFile = new File("");
			String code = billPutInStorageImpl.getFilecharset(sourceFile);
			Assert.assertEquals( code, sourceFile, "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败", e);
		}
	}
	
	/**
	 * 
	 * @方法说明：农行B2C对账文件解析
	 * @author chenyanming
	 * @param lineTxt
	 * @param splitFlg
	 * @param settleRuleControl
	 * @return
	 * @时间：2016年12月12日下午4:10:08
	 */
	@Test
	public void testGetB2CData() {
		try {
			SettleRuleControl settleRuleControl = new SettleRuleControl();
			String splitFlg = "\\,`";
			String lineTxt = null;
			List<SettleBillRecord> list = new ArrayList<SettleBillRecord>();
			String channelCode = "CIBDXT";
			String channelType = "WECHAT"; 
			//billPutInStorageImpl.getB2CData(list , lineTxt, splitFlg, settleRuleControl, channelCode, channelType);
			Assert.assertEquals(list, "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败",e);
		}
	}
	
	/**
	 * 
	 * @方法说明：获取对账文件中SettleBillRecordList的集合
	 * @author chenyanming
	 * @param list
	 * @param data
	 * @时间：2016年12月12日下午4:26:37
	 */
	@Test
	public void testGetSettleBillRecordList() {
		try {
			SettleRuleControl settleRuleControl = new SettleRuleControl();
			String splitData = "";
			String[] data = splitData.split("");
			String channelType = "";
			String channelCode = "";
			List<SettleBillRecord> list = new ArrayList<SettleBillRecord>();
			//int end = billPutInStorageImpl.getSettleBillRecordList(list, data, settleRuleControl, channelCode, channelType);
			Assert.assertEquals(list, "测试成功");
		} catch (Exception e) {
			Assert.assertEquals("测试失败",e);
		}
	}
	
}














