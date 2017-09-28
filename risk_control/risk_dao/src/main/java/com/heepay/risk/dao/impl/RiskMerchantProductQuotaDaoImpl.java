package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskMerchantProductQuotaMapper;
import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.vo.MerchantProductSelectVO;
@Repository
public class RiskMerchantProductQuotaDaoImpl  extends BaseDaoImpl<RiskMerchantProductQuota> implements RiskMerchantProductQuotaMapper  {
   
	// 默认构造方法设置命名空间
	public RiskMerchantProductQuotaDaoImpl() {
		super.setNs("com.heepay.risk.dao.RiskMerchantProductQuotaMapper");
	}
	@Override
	public List<RiskMerchantProductQuota> selectProductQuotaList(MerchantProductSelectVO vo) {
		return super.getSqlSession().selectList(this.getNs()+".selectProductQuotaList",vo);
	}
	@Override
	public Integer insert(RiskMerchantProductQuota entity) {
		return super.getSqlSession().insert(this.getNs()+".insert",entity);
		
	}
	@Override
	public Integer update(RiskMerchantProductQuota entity) {
		return super.getSqlSession().update(this.getNs()+".update",entity);
	}
	@Override
	public List<RiskMerchantProductQuota> selectMerchantProductQuotaList() {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectList(this.getNs()+".selectMerchantProductQuotaList");
	}

}
