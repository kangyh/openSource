package com.heepay.engine.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

import com.heepay.engine.AbstractRiskFact;
import com.heepay.engine.IRuleEngineService;
import com.heepay.engine.entity.MerchantRiskFact;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月11日 下午5:20:52
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
@Component
public class DroolsTemplateRuleEngineService implements IRuleEngineService {

	KieServices ks = null;
	KieContainer kContainer = null;
	KieSession kSession = null;
	
	@PostConstruct
	public void initDroolsEngine()
	{
		ks = KieServices.Factory.get();
	    kContainer = ks.getKieClasspathContainer();
    	
	}
	@Override
	public <T extends AbstractRiskFact> void handleRuleFact(T fact)
	{
		kSession = kContainer.newKieSession("ksession-rules"); //代表一次会话
		//kSession.setGlobal( "logger", logger );  
		kSession.insert(fact);
        kSession.fireAllRules();
        kSession.destroy();
        System.out.println(fact.getMessage());
	}
	
	@PreDestroy
	public void closeDroolsEngine()
	{
		ks = null;
		kContainer = null;
		kSession = null;
	}
}


