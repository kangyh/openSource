/**
 * 
 */
package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskInternalMonitorMerchantDao;
import com.heepay.risk.entity.RiskInternalMonitorMerchant;

/**
 * @author Administrator
 *
 */
@Repository
public class RiskInternalMonitorMerchantDaoImpl extends BaseDaoImpl<RiskInternalMonitorMerchant> implements RiskInternalMonitorMerchantDao {

	public RiskInternalMonitorMerchantDaoImpl() {
		super.setNs("com.heepay.risk.dao.RiskInternalMonitorMerchantDao");
	}
	@Override
	public int deleteByPrimaryKey(String key) {
		return super.getSqlSession().delete(this.getNs()+".delete",key);
	}
	@Override
	public int insert(RiskInternalMonitorMerchant record) throws Exception {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}
	@Override
	public int updateByPrimaryKey(RiskInternalMonitorMerchant record) {
		return super.getSqlSession().update(this.getNs()+".update",record);
	}
	@Override
	public List<RiskInternalMonitorMerchant> selectByCondition(RiskInternalMonitorMerchant record) {
		return super.getSqlSession().selectList(this.getNs()+".selectByCondition",record);
	}

	@Override
	public RiskInternalMonitorMerchant selectByPrimary(Integer internalMerchantId) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimary",internalMerchantId);
	}

}
