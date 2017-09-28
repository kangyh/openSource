/**
 * 
 */
package com.heepay.risk.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.risk.MonitorObject;
import com.heepay.enums.risk.QuotaType;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.risk.cache.RuleRedisCache;
import com.heepay.risk.dao.RiskRulelistMapper;
import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.entity.RiskProductQuota;
import com.heepay.risk.entity.RiskRulelist;
import com.heepay.risk.vo.RiskMerchantProductQuotaVO;
import com.heepay.risk.vo.RiskProductQuotaVO;
import com.heepay.risk.vo.RiskRuleListVo;
import com.heepay.rpc.risk.service.RiskRuleService;
import com.heepay.rpc.service.RpcService;
/**
 * @author Administrator
 *
 */
@Component
@RpcService(name="RiskRuleServiceImpl",processor=com.heepay.rpc.risk.service.RiskRuleService.Processor.class)
public class RiskRuleServiceImpl implements RiskRuleService.Iface{
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private RiskRulelistMapper riskRulelistMapper;
	
	/**
	 * 初始化缓存接口
	 */
	@PostConstruct
	private void initRuleCache()
	{
		RiskRulelist record = null; 
		record = new RiskRulelist();
		record.setStatus(RiskMerChantStatus.RISK_MER_STATUS_ENABLE.getValue());
		List<RiskRulelist> rulelist =  riskRulelistMapper.selectByCondition( record );
		//清空缓存
		if(rulelist!=null && rulelist.size()>0) {
			RuleRedisCache.delRiskRuleList();
		}
		//重新载入缓存
		for(RiskRulelist rule:rulelist)
		{
			RiskRuleListVo  vo = new RiskRuleListVo();
			vo.setBuziType(rule.getBuziType());
			vo.setCreateTime(rule.getCreateTime());
			vo.setMonitorObject(rule.getMonitorObject());
			vo.setOtherItem(rule.getOtherItem());
			vo.setRiskControlAction(rule.getRiskControlAction());
			vo.setRiskControlFact(rule.getRiskControlFact());
			vo.setRuleDescription(rule.getRuleDescription());
			vo.setRuleId(rule.getRuleId());
			vo.setRulelistId(rule.getRulelistId());
			vo.setRuleType(rule.getRuleType());
			vo.setStatus(rule.getStatus());
			vo.setOperator(rule.getOperator());
			RuleRedisCache.setRiskRuleList(vo);
			logger.info( "更新风控规则缓存" +"RISK_RULE_"+rule.getRuleId()+"_"+rule.getMonitorObject());
		}
		
	}
	
	@Override
	public String addRiskRule(String riskRulelistStr)  {
		String result = "success";
		logger.info( "调用添加风控规则接口开始" );
		try {
			RiskRulelist rule = (RiskRulelist) JsonMapperUtil.nonEmptyMapper().fromJson(riskRulelistStr, RiskRulelist.class);
			riskRulelistMapper.insert(rule);
			RiskRuleListVo  vo = new RiskRuleListVo();
			vo.setBuziType(rule.getBuziType());
			vo.setCreateTime(rule.getCreateTime());
			vo.setMonitorObject(rule.getMonitorObject());
			vo.setOtherItem(rule.getOtherItem());
			vo.setRiskControlAction(rule.getRiskControlAction());
			vo.setRiskControlFact(rule.getRiskControlFact());
			vo.setRuleDescription(rule.getRuleDescription());
			vo.setRuleId(rule.getRuleId());
			vo.setRulelistId(rule.getRulelistId());
			vo.setRuleType(rule.getRuleType());
			vo.setStatus(rule.getStatus());
			vo.setOperator(rule.getOperator());
			RuleRedisCache.setRiskRuleList(vo);
		} catch (Exception e) {
			logger.error( "调用添加风控规则接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

	@Override
	public String editRiskRule(String riskRulelistStr) throws TException {
		String result = "success";
		logger.info( "调用修改风控规则接口开始" );
		try {
			RiskRulelist rule = (RiskRulelist) JsonMapperUtil.nonEmptyMapper().fromJson(riskRulelistStr, RiskRulelist.class);
			riskRulelistMapper.updateByPrimaryKey(rule);
			RiskRuleListVo  vo = new RiskRuleListVo();
			vo.setBuziType(rule.getBuziType());
			vo.setCreateTime(rule.getCreateTime());
			vo.setMonitorObject(rule.getMonitorObject());
			vo.setOtherItem(rule.getOtherItem());
			vo.setRiskControlAction(rule.getRiskControlAction());
			vo.setRiskControlFact(rule.getRiskControlFact());
			vo.setRuleDescription(rule.getRuleDescription());
			vo.setRuleId(rule.getRuleId());
			vo.setRulelistId(rule.getRulelistId());
			vo.setRuleType(rule.getRuleType());
			vo.setStatus(rule.getStatus());
			vo.setOperator(rule.getOperator());
			RuleRedisCache.setRiskRuleList(vo);
		} catch (Exception e) {
			logger.error( "调用添加风控规则接口异常"  );
			e.printStackTrace();
			return e.getMessage();
		}
		return result;
	}

}
