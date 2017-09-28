package com.heepay.boss.dao.impl;

import java.util.List;

import com.heepay.boss.entity.BossRuleExample;
import org.springframework.stereotype.Repository;
import com.heepay.boss.dao.BossRuleMapper;
import com.heepay.boss.entity.BossRule;

@Repository
public class BossRuleDaoImpl  extends BaseDaoImpl<BossRule,BossRuleExample> implements BossRuleMapper{
    
	// 默认构造方法设置命名空间
	public BossRuleDaoImpl() {
				super.setNs("com.heepay.boss.dao.BossRuleMapper");
			}
	@Override
	public int insert(BossRule record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public BossRule selectByPrimaryKey(Long ruleId) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimaryKey",ruleId);
	}

	@Override
	public int update(BossRule record) {
		return super.getSqlSession().insert(this.getNs()+".update",record);
	}

	@Override
	public List<BossRule> getlist() {
		return super.getSqlSession().selectList(this.getNs()+".getlist");
	}
 
}
