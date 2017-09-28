package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskRulelistMapper;
import com.heepay.risk.entity.RiskRulelist;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年7月27日下午3:02:41
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
@Repository
public class RiskRulelistDaoImpl extends BaseDaoImpl< RiskRulelist> implements  RiskRulelistMapper{
	
	// 默认构造方法设置命名空间
	public RiskRulelistDaoImpl() {
		super.setNs("com.heepay.risk.dao.RiskRulelistMapper");
	}
	
	@Override
	public int deleteByPrimaryKey(Integer rulelistId) {
		return super.getSqlSession().delete(this.getNs()+".deleteByPrimaryKey",rulelistId);
	}

	@Override
	public int insert(RiskRulelist record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public RiskRulelist selectByPrimaryKey(Integer rulelistId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RiskRulelist> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(RiskRulelist record) {

		return super.getSqlSession().update(this.getNs()+".updateByPrimaryKey",record);
	}
	@Override
	public List<RiskRulelist> selectByCondition(RiskRulelist record) {
		return super.getSqlSession().selectList(this.getNs()+".selectByCondition",record);
	}

}
