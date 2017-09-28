package com.heepay.rpc.payment.service.impl;

import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.rpc.billing.service.impl.DoneSettleDifferRecordServiceImpl;
import com.heepay.rpc.billing.service.impl.InsertLogAndAdultServiceImpl;
import com.heepay.rpc.billing.service.impl.SendEmailServiceImpl;
import com.heepay.rpc.billing.service.impl.SendWarnRecordServiceImpl;
import com.heepay.rpc.billing.service.impl.SettleChannelManagerServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")

public class SettleChannelManagerServiceImplTest {
	
	@Autowired
	SettleChannelManagerServiceImpl settleChannelManagerServiceImpl;
	
	@Autowired
	SendEmailServiceImpl sendEmailServiceImpl;
	
	@Autowired
	InsertLogAndAdultServiceImpl insertLogAndAdultServiceImpl;
	
	@Autowired
	DoneSettleDifferRecordServiceImpl doneSettleDifferRecordServiceImpl;
	
	@Autowired
	SendWarnRecordServiceImpl sendWarnRecordServiceImpl;
	
	private static final Logger logger = LogManager.getLogger();
	
	/*public SettleChannelManagerServiceImplTest(String name) {
		super(name);
	}
		
		//测试套件
		public static void main(String[] args) {
			junit.textui.TestRunner.run(new TestSuite(SettleChannelManagerServiceImplTest.class));
		}*/
	@Test	
	public void testDoneload() {
		try {
			settleChannelManagerServiceImpl.downChannelManager();
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}	
	
		//ftp下载测试
		@Test
		public void testdownloadFileFtp() {
			try {
				boolean flag = settleChannelManagerServiceImpl.downloadFileFtp("BILPAY", "QUICKP", "ftp192.168.4.31", "wangjie",
						"123QWEasd", "home/billing/AAA/CCC/BILPAY/QUICKP/","","","");
				if(true == flag){
					Assert.assertEquals("FTP文件下载","成功");
				}else{
					Assert.assertEquals("FTP文件下载","失败");
				}
			} catch (Exception e) {
				fail("Not yet implemented");
			}
		}
		
		
		//sftp下载测试
		@Test
		public void testdownloadFileSFtp() {
			try {
				boolean flag = settleChannelManagerServiceImpl.downloadFileSftp("BILPAY", "QUICKP", "sftp192.168.162.81", "jenkins",
						"jenkins", "/home/billing/aaa/","","","","");
				if(true == flag){
					Assert.assertEquals("SFTP文件下载","成功");
				}else{
					Assert.assertEquals("SFTP文件下载","失败");
				}
			} catch (Exception e) {
				fail("Not yet implemented");
			}
		}
		
		
		//sftp告警测试
		@Test
		public void testSendEmailTest() {
			try {
				 sendEmailServiceImpl.query();
				logger.info("告警测试成功");
			} catch (Exception e) {
				logger.info("告警测试失败");
				fail("Not yet implemented");
			}
		}
		
		
		//写入日记测试
		@Test
		public void testInsertLog(){
			try {
				insertLogAndAdultServiceImpl.insertAndAdult("BILPAY", "QUICKP", "hello.txt", "/aaa/vvv/ddd/","","", null,"","");
				logger.info("插入日记测试成功");
			} catch (Exception e) {
				logger.info("插入日记测试失败");
				fail("Not yet implemented");
			}
		}
		
		
		//写入日记测试
		@Test
		public void testDifferRecord(){
			try {
				doneSettleDifferRecordServiceImpl.getSettleDifferRecord("WZ");
				logger.info("插入日记测试成功");
			} catch (Exception e) {
				logger.info("插入日记测试失败");
				fail("Not yet implemented");
			}
		}
		
		//手动对账测试
		@Test
		public void testHandleRecord(){
			try {
				SettleChannelManager settleChannelManager = new SettleChannelManager();
				settleChannelManager.setChannelCode("UNOPAY");
				settleChannelManager.setChannelType("QRCODE");
				sendEmailServiceImpl.checkHandleChannel(settleChannelManager);
				logger.info("手动对账测试成功");
			} catch (Exception e) {
				logger.info("手动对账测试失败");
				fail("Not yet implemented");
			}
		}
		
		
		
		//未对账测试
		@Test
		public void testNoCheckReocrd(){
			try {
				sendWarnRecordServiceImpl.isCheckBillRecord();
				logger.info("未对账测试成功");
			} catch (Exception e) {
				logger.info("未对账测试失败");
				fail("Not yet implemented");
			}
		}
		
		
		//对账数据统计测试
		@Test
		public void testTotalCheckRecord(){
			try {
				sendWarnRecordServiceImpl.reconciliationCycleRecord();
				logger.info("对账数据统计测试成功");
			} catch (Exception e) {
				logger.info("对账数据统计测试失败");
				fail("Not yet implemented");
			}
		}
		
}
