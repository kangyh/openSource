package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcPaymentAccountQueryMapper;
import com.heepay.risk.entity.PbcPaymentAccountQuery;
@Repository
public class PbcPaymentAccountQueryDaoImpl extends BaseDaoImpl<PbcPaymentAccountQuery> implements PbcPaymentAccountQueryMapper {
	// 默认构造方法设置命名空间
	public PbcPaymentAccountQueryDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcPaymentAccountQueryMapper");
	}

@Override
public void insert(PbcPaymentAccountQuery entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}

}
