/**
 * 
 */
package com.heepay.rpc.risk.service.impl;

/**
 * @author Administrator
 *
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.common.util.StringUtil;
import com.heepay.enums.FeeDeductType;
import com.heepay.enums.TransType;
import com.heepay.risk.dao.RiskTransInfoMapper;
import com.heepay.risk.entity.RiskRuleDetail;
import com.heepay.risk.entity.TransInfoObj;

import com.heepay.risk.service.IRuleService;
import com.heepay.rpc.client.GateWayDataServiceClient;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.model.TransOrderRiskModel;

import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;


/**
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class TestGateWayThrift {
	
	
	@Autowired
	GateWayDataServiceClient gateWayDataServiceClient;

	@Test
	public void test2()
	{
		String fields = "transNo,merchantNo,merchantId,bankId,bankShortName,bankcardType,requestStatus,requestAmount,payAmount,payFinishTime,requestTime";
//		String fields = "trans_no,merchant_no,merchant_id,bank_id,bank_short_name,bankcard_type,request_status,request_amount,pay_amount,pay_finish_time,request_time";
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginTime", "1496419200998");
		map.put("endTime", "1496505599998");
		String equalParam = JSONObject.fromObject(map).toString();
		List<Map<String,String>> list = gateWayDataServiceClient.getGatewayRecordList(fields, equalParam);
		System.out.println("");
	}
	
	

}
