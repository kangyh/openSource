package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskLoginBlacklistMapper;
import com.heepay.risk.entity.RiskLoginBlacklist;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年7月27日下午3:02:35
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
public class RiskLoginBlacklistDaoImpl extends BaseDaoImpl< RiskLoginBlacklist> implements  RiskLoginBlacklistMapper{
	
	// 默认构造方法设置命名空间
	public RiskLoginBlacklistDaoImpl() {
		super.setNs("com.heepay.risk.dao.RiskLoginBlacklistMapper");
	}
			
	@Override
	public int deleteByPrimaryKey(Integer blackId) {
		return super.getSqlSession().delete(this.getNs()+".deleteByPrimaryKey",blackId);
	}

	@Override
	public int insert(RiskLoginBlacklist record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}



	@Override
	public RiskLoginBlacklist selectByPrimaryKey(Integer blackId) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimaryKey", blackId);
	}

	@Override
	public List<RiskLoginBlacklist> selectAll() {
		return super.getSqlSession().selectList(this.getNs()+".selectAll");
	}

	@Override
	public int updateByPrimaryKey(RiskLoginBlacklist record) {
		return super.getSqlSession().update(this.getNs()+".update",record);
	}

	@Override
	public int selectCount(RiskLoginBlacklist record) {
		return super.getSqlSession().selectOne(this.getNs()+".selectCount",record);
	}

}
