package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.RiskRule;

public interface RiskRuleMapper {
	  List<RiskRule> getRuleList(String triggerType);
}
