/**
 * 
 */
package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskInternalMonitorChannelDao;
import com.heepay.risk.entity.RiskInternalMonitorChannel;
import com.heepay.risk.entity.RiskIpbase;

/**
 * @author Administrator
 *
 */
@Repository
public class RiskInternalMonitorChannelDaoImpl  extends BaseDaoImpl<RiskInternalMonitorChannel> implements RiskInternalMonitorChannelDao {
	
	public RiskInternalMonitorChannelDaoImpl() {
		super.setNs("com.heepay.risk.dao.RiskInternalMonitorChannelDao");
	}
	@Override
	public int deleteByPrimaryKey(String key) {
		return super.getSqlSession().delete(this.getNs()+".delete",key);
	}
	@Override
	public int insert(RiskInternalMonitorChannel record) throws Exception {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}
	@Override
	public int updateByPrimaryKey(RiskInternalMonitorChannel record) {
		return super.getSqlSession().update(this.getNs()+".update",record);
	}
	@Override
	public List<RiskInternalMonitorChannel> selectByCondition(RiskInternalMonitorChannel record) {
		return super.getSqlSession().selectList(this.getNs()+".selectByCondition",record);
	}

	@Override
	public RiskInternalMonitorChannel selectByPrimary(Integer internalChannelId) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimary",internalChannelId);
	}

}
