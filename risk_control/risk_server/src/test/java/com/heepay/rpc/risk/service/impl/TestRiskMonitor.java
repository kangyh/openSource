/**
 * 
 */
package com.heepay.rpc.risk.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.heepay.common.util.DateUtil;
import com.heepay.risk.entity.RiskInternalMonitorChannel;
import com.heepay.risk.entity.RiskInternalMonitorMerchant;
import com.heepay.rpc.client.MonitorOrderServiceClient;
import com.heepay.rpc.client.RiskMoniConfClient;

/**
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class TestRiskMonitor {
	
	@Autowired
	RiskMoniConfClient riskMoniConfClient;
	@Autowired
	MonitorOrderServiceClient monitorOrderServiceClient;
	
	@Test
	public void test2(){
//		TestModel vo = new TestModel();	
//		vo.setMerchantOrderNo( "20170814112545" ) ;
//		vo.setTransNo( "20170814094242111"  ) ;
//		vo.setProductType( "1231" ) ;
//		vo.setProductName( "1231" ) ;
//		vo.setMerchantId( "8wu83243" ) ;
//		vo.setMerchantCompany( "345345") ;
//		vo.setTransType( "PAYMNT" ) ;
//		vo.setTransAmount( new BigDecimal(100.00) );
//		vo.setBankCardType( "45646" ) ;
//		vo.setBankCardNo( "8797" );
//		vo.setBankCardOwnerName( "45646") ;
//		vo.setBankCardOwnerIdCard( "1223") ;
//		vo.setBankCardOwnerMobile( "124443") ;
//		vo.setChannelCode( "67676") ;
//		vo.setChannelName( "657576") ;
//		vo.setChannelTransType( "2131") ;
//		vo.setFeeType( "12312") ;
//		vo.setBankCardOwnerType( "123123") ;
//		vo.setCreateTime( DateUtil.getDate().getTime()) ; 
//		vo.setTransferBatchAmount( new BigDecimal(100.00) ) ;
//		vo.setFeeAmount( new BigDecimal(10.00) ) ;
//		vo.setReqIp( "192.168.4.142") ;
//		
		try {
			String result1 = monitorOrderServiceClient.getMonitorOrderInfo("bankCardType,bankCardNo,bankCardOwnerName,bankCardOwnerIdCard,bankCardOwnerMobile,channelTransType,feeType,bankCardOwerType,feeAmount,reqIp", "AV3gR3JUZTioQrGjhm1k");
			String x = "{\"pagefrom\":\"0\",\"pagesize\":\"10\"}";
//			 Map<String, List<Map<String, String>>> list = monitorOrderServiceClient.getMonitorOrderList("bankCardNo,bankCardOwerType",x);
			System.out.println(""+result1);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//		try {
//			String result4 = riskMoniConfClient.addChannelMonitorConfig(JSONObject.toJSONString(channel));
//			String result5 = riskMoniConfClient.addMerchantMonitorConfig(JSONObject.toJSONString(merchant));
//			String result4 = riskMoniConfClient.editChannelMonitorConfig(JSONObject.toJSONString(channel));
//			String result5 = riskMoniConfClient.editMerchantMonitorConfig(JSONObject.toJSONString(merchant));
//			String result4 = riskMoniConfClient.delChannelMonitorConfig(JSONObject.toJSONString(channel));
//			String result5 = riskMoniConfClient.delMerchantMonitorConfig(JSONObject.toJSONString(merchant));
//			System.out.println("$$$$$$$====="+result4);
//			System.out.println("$$$$$$$====="+result5);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
