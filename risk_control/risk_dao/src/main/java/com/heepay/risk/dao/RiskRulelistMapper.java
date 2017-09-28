package com.heepay.risk.dao;

import com.heepay.risk.entity.RiskRulelist;
import java.util.List;

public interface RiskRulelistMapper {
    int deleteByPrimaryKey(Integer rulelistId);

    int insert(RiskRulelist record);

    RiskRulelist selectByPrimaryKey(Integer rulelistId);

    List<RiskRulelist> selectAll();

    int updateByPrimaryKey(RiskRulelist record);
    
    List<RiskRulelist> selectByCondition(RiskRulelist record);
}