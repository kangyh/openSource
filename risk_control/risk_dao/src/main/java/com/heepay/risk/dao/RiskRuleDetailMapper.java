package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.RiskRuleDetail;

public interface RiskRuleDetailMapper {
       List<RiskRuleDetail> getRuleDetailList(Long ruleId);
       RiskRuleDetail getRuleDetailByDetailCode(String detailCode);
}
