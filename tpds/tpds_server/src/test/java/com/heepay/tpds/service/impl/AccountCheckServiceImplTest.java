package com.heepay.tpds.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.TransType;
import com.heepay.tpds.util.HttpClientUtils;
import com.heepay.tpds.util.TpdsDataUtils;
import com.heepay.tpds.vo.CommonRequestHeaderMessage;
import com.heepay.tpds.vo.RequestMessage;
import com.heepay.utils.http.WithoutAuthHttpsClient;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * 
 * 描    述：生成对账文件测试类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年3月15日下午3:37:34 
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
public class AccountCheckServiceImplTest extends TestCase{
	
	/*//构造方法
	public AccountCheckServiceImplTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(AccountCheckServiceImplTest.class));
	}*/
	
	@Autowired
	AccountCheckServiceImpl accountCheckServiceImpl;
	
	@Resource(name="httpsClient")
	WithoutAuthHttpsClient httpsClient;
	
	@Test
	public void testChargeAccountCheck() {
		String settleStatus;
		String transType;
		String settleTime;
		String settleBath;
		String merchantId;
		accountCheckServiceImpl.chargeAccountCheck("100005","JCT",null, null, TransType.DEPOSIT_PAY.getValue(), "Y");
		
		/*RequestMessage requestMessage = new RequestMessage();
		CommonRequestHeaderMessage reqHeader = new CommonRequestHeaderMessage();
		Map<String, Object> inBody = new TreeMap<String, Object>();
		
		reqHeader.setVersion("1.0");
		reqHeader.setMerchantCode("jct");
		reqHeader.setTxnType("R00001");
		reqHeader.setClientSn(TpdsDataUtils.businessOrderNo());
		reqHeader.setClientDate(DateUtil.getCurrentDate(new Date()));
		reqHeader.setClientTime(new SimpleDateFormat("HHmmss").format(new Date()));
		reqHeader.setFileName("");
		reqHeader.setSignTime(String.valueOf(new Date().getTime()));
		reqHeader.setSignature("xxx");

		inBody.put("businessSeqNo", TpdsDataUtils.businessSeqNo("jct"));
		inBody.put("liquDate", DateUtil.getCurrentDate(new Date()));
		inBody.put("fileName", "");
		
		requestMessage.setReqHeader(reqHeader);
		requestMessage.setInBody(inBody);
		
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		String aaa = jsonUtil.toJson(requestMessage);
		try {
			String msg = httpsClient.send("https://36.110.98.254:19101/p2pwg/hy/", jsonUtil.toJson(requestMessage), "UTF-8");
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
		
	}
	
	
	@Test
	public void testChargeAccountCheck1() {
		/*String settleStatus;
		String transType;
		String settleTime;
		String settleBath;
		String merchantId;
		accountCheckServiceImpl.chargeAccountCheck("100005", "jct",null, null, TransType.DEPOSIT_PAY.getValue(), "Y");
		
		jSONObject.getJSONObject("reqHeader").put("signature", RSA.sign(jSONObject.getJSONObject("inBody").toString().getBytes(),strSign,""));
		*/
		
		RequestMessage requestMessage = new RequestMessage();
		CommonRequestHeaderMessage reqHeader = new CommonRequestHeaderMessage();
		Map<String, Object> inBody = new TreeMap<String, Object>();
		
		reqHeader.setVersion("1.0");
		reqHeader.setMerchantCode("jct");
		reqHeader.setTxnType("R00001");
		reqHeader.setClientSn(TpdsDataUtils.businessOrderNo());
		reqHeader.setClientDate(DateUtil.getCurrentDate(new Date()));
		reqHeader.setClientTime(new SimpleDateFormat("HHmmss").format(new Date()));
		reqHeader.setFileName("");
		reqHeader.setSignTime(String.valueOf(new Date().getTime()));
		reqHeader.setSignature("xxx");

		inBody.put("businessSeqNo", TpdsDataUtils.businessSeqNo("jct"));
		inBody.put("liquDate", DateUtil.getCurrentDate(new Date()));
		inBody.put("fileName", "");
		inBody.put("status", "01");
		inBody.put("errorMsg", "");
		
		requestMessage.setReqHeader(reqHeader);
		requestMessage.setInBody(inBody);
		
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		String aaa = jsonUtil.toJson(requestMessage);
		try {
			String msg = httpsClient.send("https://36.110.98.254:19101/p2pwg/hy/", jsonUtil.toJson(requestMessage), "UTF-8");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}






