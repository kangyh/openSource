/**
 * 
 */
package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskIpbaseMapper;
import com.heepay.risk.entity.RiskIpbase;

/**
 * @author Administrator
 *
 */
@Repository
public class RiskIpbaseDaoImpl extends BaseDaoImpl<RiskIpbase> implements RiskIpbaseMapper {

	public RiskIpbaseDaoImpl() {
		super.setNs("com.heepay.risk.dao.RiskIpbaseMapper");
	}

	@Override
	public int deleteByPrimaryKey(String ip) {
		return 0;
	}
	public int insertBatch(List<RiskIpbase> list) throws Exception {
		return super.getSqlSession().insert(this.getNs()+".insertBatch",list);
	}

	@Override
	public int insert(RiskIpbase record) throws Exception {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public RiskIpbase selectByPrimaryKey(long ipNum) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimaryKey",ipNum);
	}

	@Override
	public List<RiskIpbase> selectAll() {
		return null;
	}
	@Override
	public int updateByPrimaryKey(RiskIpbase record) {
		return 0;
	}

}
