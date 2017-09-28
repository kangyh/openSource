package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskProductQuotaMapper;
import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.entity.RiskProductQuota;
@Repository
public class RiskProductQuotaDaoImpl  extends BaseDaoImpl<RiskProductQuota> implements RiskProductQuotaMapper  {
    
	// 默认构造方法设置命名空间
		public RiskProductQuotaDaoImpl() {
			super.setNs("com.heepay.risk.dao.RiskProductQuotaMapper");
		}
	@Override
	public RiskProductQuota selectByProductCode(RiskMerchantProductQuota model) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByProductCode",model);
	}
	@Override
	public Integer insert(RiskProductQuota entity) {
		return super.getSqlSession().insert(this.getNs()+".insert",entity);
	}
	@Override
	public Integer update(RiskProductQuota entity) {
		return super.getSqlSession().update(this.getNs()+".update",entity);
	}
	@Override
	public List<RiskProductQuota> selectProductQuotaList() {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectList(this.getNs()+".selectProductQuotaList");
	}

}
