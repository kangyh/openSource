package com.heepay.risk.service;

import com.heepay.rpc.risk.model.TransOrderRiskModel;

public  abstract class   IRuleService {
	public  abstract com.heepay.rpc.risk.model.AsyncMsgVO ExecuteRule(TransOrderRiskModel vo);
	public Long getRuleDetailId() {
		return ruleDetailId;
	}
	public void setRuleDetailId(Long ruleDetailId) {
		this.ruleDetailId = ruleDetailId;
	}
	private   Long  ruleDetailId;

}
