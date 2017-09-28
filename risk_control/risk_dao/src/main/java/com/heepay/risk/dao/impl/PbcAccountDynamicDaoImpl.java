package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAccountDynamicMapper;
import com.heepay.risk.entity.PbcAccountDynamic;

@Repository
public class PbcAccountDynamicDaoImpl extends BaseDaoImpl<PbcAccountDynamic> implements PbcAccountDynamicMapper  {
   
	// 默认构造方法设置命名空间
	public PbcAccountDynamicDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcAccountDynamicMapper");
	}
	@Override
	public void insert(com.heepay.risk.entity.PbcAccountDynamic entity) {		
		super.getSqlSession().insert(this.getNs()+".insert",entity);
	}
	

}
