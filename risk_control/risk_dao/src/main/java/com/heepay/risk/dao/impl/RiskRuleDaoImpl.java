package com.heepay.risk.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskRuleMapper;
import com.heepay.risk.entity.RiskRule;

@Repository
public class RiskRuleDaoImpl extends BaseDaoImpl<RiskRule> implements RiskRuleMapper {
	   
	// 默认构造方法设置命名空间
			public RiskRuleDaoImpl() {
				super.setNs("com.heepay.risk.dao.RiskRuleMapper");
			}

			@Override
			public List<RiskRule> getRuleList(String triggerType) {
				// TODO Auto-generated method stub
				List<RiskRule> list= super.getSqlSession().selectList(this.getNs()+".getRuleList",triggerType);
				return list;
			}
		


}

