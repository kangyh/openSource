package com.heepay.rpc.risk.service.impl;

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
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.model.TransOrderRiskModel;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Assert;


/**
 * 
 * 
 * 描    述：风控限额测试
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2016.12.29
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
public class ProductQuotaServiceTest {
	
	
	/*@Autowired
	 RiskTransInfoMapper riskTransInfoMapper;
*/
	/**
	 * 
	 * @方法说明：测试风控提现每笔限额
	 * @author dongzhengjiang
	 * @return
	 * @时间：2016.12.29
	 */
	/**@Test
	public void testWZDRAWQuota() {
		RiskRuleDetail detail=new  RiskRuleDetail();
		detail.setDetailCode("10001");
		detail.setStatus("ENABLE");
		detail.setRuleId(1L);
		detail.setLevel(8);
		TransOrderRiskModel vo =new TransOrderRiskModel();
		vo.setProductType("CP05");
		vo.setMerchantId("100007");
		vo.setTransAmount("10000");
		vo.setBankCardOwnerType("1");
		vo.setCreateTime("20161221204000");
		//vo.setBankCardType("SAVING");
		vo.setProductName("提现");
		vo.setMerchantCompany("阿里巴巴");
		vo.setTransType("WZDRAW");
		vo.setMerchantOrderNo("1111");
		vo.setTransNo("2222");
		vo.setFeeType(FeeDeductType.OUTER.getValue());
		vo.setFeeAmount("1.00");
		IRuleService service=ruleFactory.GetInstance(detail);
		AsyncMsgVO result=service.ExecuteRule(vo);
		Assert.assertEquals(8001, result.getStatus());
	}*/
	@Test
	public void test2()
	{
		boolean b="BATPAY".equals(TransType.BATCHPAY.getValue())&&!StringUtil.isBlank("400")&& new BigDecimal("400").compareTo(BigDecimal.ZERO)>0;
		Assert.assertTrue(b);
	}
	
	

}
