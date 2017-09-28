package com.heepay.boss.dao;

import com.heepay.boss.entity.BossRule;
import java.util.List;


public interface BossRuleMapper {
   
    int insert(BossRule record);
    BossRule selectByPrimaryKey(Long ruleId);
    int update(BossRule record);
   List<BossRule> getlist();
}