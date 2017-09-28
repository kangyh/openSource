package com.heepay.engine.server;

import java.math.BigDecimal;
import java.util.Date;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.heepay.engine.entity.MerchantRiskFact;
import com.heepay.engine.entity.RegisterAndLoginFact;
import com.heepay.engine.impl.DroolsTemplateRuleEngineService;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.RegLoginType;
import com.heepay.risk.cache.RuleRedisCache;
import com.heepay.risk.util.SpringContextHolder;
import com.heepay.risk.vo.RiskMerchantProductQuotaVO;
import com.heepay.risk.vo.RiskRuleListVo;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月13日 上午10:12:20
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
public class run {

	public static void main(String[] args) {
		//droolsTest();
		//redisTest();
		droolsClientInvoke();
//		System.out.println(TransType.BATCHPAY.getValue());
	}
	
	public static void droolsClientInvoke()
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
//		 MerchantRiskFact merchantRiskFact = new MerchantRiskFact();
//         merchantRiskFact.setMerchantId("100007");
//         merchantRiskFact.setAccountType("PRIVAT");
//         merchantRiskFact.setBankCardType("SAVING");
//         merchantRiskFact.setProductCode("CP02");
//         merchantRiskFact.setPerItemAmount(new BigDecimal(100));
		RiskRuleListVo riskRulelist = new RiskRuleListVo();
		riskRulelist.setBuziType("注册/登录");
		riskRulelist.setCreateTime(new Date());
		riskRulelist.setMonitorObject("MERCHANT");
		riskRulelist.setOtherItem("item");
		riskRulelist.setRiskControlAction("BLOCK");
		riskRulelist.setRiskControlFact("工商异常企业黑名单");
		riskRulelist.setRuleDescription("阻断在工商登记中异常企业的商户注册");
		riskRulelist.setRuleId("01010101");
		riskRulelist.setRulelistId(25);
		riskRulelist.setRuleType("枚举类规则");
		riskRulelist.setStatus("ENABLE");
		RuleRedisCache.setRiskRuleList(riskRulelist) ;
		RegisterAndLoginFact regfact = new RegisterAndLoginFact();
		regfact.setBuziCode(null);
		regfact.setCompanyName(null);
		regfact.setOwnerId(null);
		regfact.setRegLoginType(RegLoginType.MER_REG.getValue());
		
		
         
         DroolsTemplateRuleEngineService droolsTemplateRuleEngineService = (DroolsTemplateRuleEngineService) applicationContext.getBean("droolsTemplateRuleEngineService");
         droolsTemplateRuleEngineService.handleRuleFact(regfact);
  /*       merchantRiskFact = new MerchantRiskFact();
         merchantRiskFact.setMerchantId("2323232");
         merchantRiskFact.setAccountType("private");
         merchantRiskFact.setBankCardType("SAVING");
         merchantRiskFact.setProductCode("P08");
         merchantRiskFact.setPerItemAmount(new BigDecimal(8000));
         droolsTemplateRuleEngineService.handleRuleFact(merchantRiskFact);
         merchantRiskFact = new MerchantRiskFact();
         merchantRiskFact.setMerchantId("2323232");
         merchantRiskFact.setAccountType("private");
         merchantRiskFact.setBankCardType("SAVING");
         merchantRiskFact.setProductCode("P08");
         merchantRiskFact.setPerItemAmount(new BigDecimal(8000));
         droolsTemplateRuleEngineService.handleRuleFact(merchantRiskFact);*/
	}
	
	public static void droolsTest()
	{
		 try {
	            // load up the knowledge base
		        KieServices ks = KieServices.Factory.get();
	    	    KieContainer kContainer = ks.getKieClasspathContainer();
	        	KieSession kSession = kContainer.newKieSession("ksession-rules");
	            MerchantRiskFact merchantRiskFact = new MerchantRiskFact();
	            merchantRiskFact.setMerchantId("2323232");
	            merchantRiskFact.setAccountType("private");
	            merchantRiskFact.setBankCardType("SAVING");
	            merchantRiskFact.setProductCode("P081");
	            merchantRiskFact.setPerItemAmount(new BigDecimal(8000));
	        	kSession.insert(merchantRiskFact);
	            kSession.fireAllRules();
	            kSession.retract(kSession.getFactHandle(merchantRiskFact));
	            System.out.println("fact数量："+kSession.getFactCount());
	            merchantRiskFact = new MerchantRiskFact();
	            merchantRiskFact.setMerchantId("2323232");
	            merchantRiskFact.setAccountType("private");
	            merchantRiskFact.setBankCardType("SAVING");
	            merchantRiskFact.setProductCode("P081");
	            merchantRiskFact.setPerItemAmount(new BigDecimal(8000));
	            System.out.println(merchantRiskFact.getMessage());
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	}

	
	public static void redisTest()
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
		//RuleRedisCache.setQuotaMerchantCache("risk_control", "hello");
		//SpringContextHolder springContextHolder = (SpringContextHolder) applicationContext.getBean("springContextHolder");
		/*RiskMerchantProductQuotaVO riskMerchantProductQuotaVO = new RiskMerchantProductQuotaVO();
		riskMerchantProductQuotaVO.setMerchantCode(2323232);
		riskMerchantProductQuotaVO.setAccountType("private");
		riskMerchantProductQuotaVO.setBankcardType("SAVING");
		riskMerchantProductQuotaVO.setProductCode("P08");
		riskMerchantProductQuotaVO.setPerDay(new BigDecimal(1000));
		riskMerchantProductQuotaVO.setPerItem(new BigDecimal(1000));
		riskMerchantProductQuotaVO.setPerMonth(new BigDecimal(1000));*/
		
		//applicationContext.getBean(RuleRedisCache.class).setQuotaMerchantCache(riskMerchantProductQuotaVO);
		//applicationContext.getBean(RuleRedisCache.class).delQuotaMerchantCache("risk_control");
		//applicationContext.getBean(RuleRedisCache.class).delQuotaRule();
		applicationContext.getBean(RuleRedisCache.class).getAllQuotaRule();
	}
	
	
	
	
}



