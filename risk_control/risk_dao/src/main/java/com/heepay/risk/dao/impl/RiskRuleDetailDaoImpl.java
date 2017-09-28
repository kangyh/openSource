package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskRuleDetailMapper;
import com.heepay.risk.entity.RiskRuleDetail;
@Repository
public class RiskRuleDetailDaoImpl extends BaseDaoImpl<RiskRuleDetail> implements RiskRuleDetailMapper{
    
	// 默认构造方法设置命名空间
				public RiskRuleDetailDaoImpl() {
					super.setNs("com.heepay.risk.dao.RiskRuleDetailMapper");
				}

				@Override
				public List<RiskRuleDetail> getRuleDetailList(Long ruleId) {
					List<RiskRuleDetail> list= super.getSqlSession().selectList(this.getNs()+".getRuleDetailList",ruleId);
					return list;
				}

				@Override
				public RiskRuleDetail getRuleDetailByDetailCode(String detailCode) {
					return super.getSqlSession().selectOne(this.getNs()+".getRuleDetailByDetailCode",detailCode);
				}


}
