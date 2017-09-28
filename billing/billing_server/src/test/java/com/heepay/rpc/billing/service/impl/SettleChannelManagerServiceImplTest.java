/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月13日上午10:23:50
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
package com.heepay.rpc.billing.service.impl;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.rpc.client.BillingCommonClient;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月13日上午10:23:50
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
public class SettleChannelManagerServiceImplTest {
	
	@Resource(name = "billingCommonClient")
	BillingCommonClient billingCommonClient;
	@Autowired
	SettleChannelManagerServiceImpl settleChannelManagerServiceImpl;
	
	@Test
	public void testdownloadFileFtp(){
		
		
		String channelCode = "CIBDXT";  //兴业点芯
		String channelType = "WECHAT"; 
		String remoteAdress = "ftp192.168.162.81";
		String remoteUserName = "jenkins";
		
		String remotePassword = "jenkins";
		String localFilePath = "F:/checkFile";
		
//		
//		settleChannelManagerServiceImpl.downloadFileFtp(channelCode, channelType, remoteAdress, remoteUserName,
//				 remotePassword,  localFilePath);
	}
	@Test
	public void testbillCompare() throws TException{
		settleChannelManagerServiceImpl.query();
	}

}
