package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAccountDynamicQueryMapper;
import com.heepay.risk.entity.PbcAccountDynamic;
import com.heepay.risk.entity.PbcAccountDynamicQuery;
@Repository
public class PbcAccountDynamicQueryDaoImpl extends BaseDaoImpl<PbcAccountDynamicQuery> implements PbcAccountDynamicQueryMapper  {
   
	// 默认构造方法设置命名空间
		public PbcAccountDynamicQueryDaoImpl() {
			super.setNs("com.heepay.risk.dao.PbcAccountDynamicQueryMapper");
		}
	@Override
	public void insert(PbcAccountDynamicQuery entity) {		
		super.getSqlSession().insert(this.getNs()+".insert",entity);
	}
	@Override
	public List<PbcAccountDynamicQuery> selectByApplicationCode(String applicationCode) {
		return super.getSqlSession().selectList(this.getNs()+".selectByApplicationCode",applicationCode);
	}
}
