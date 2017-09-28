package com.heepay.risk.dao.impl;


import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcTransCardPaymentAccountMapper;
import com.heepay.risk.entity.PbcTransCardPaymentAccount;
@Repository
public class PbcTransCardPaymentAccountDaoImpl extends BaseDaoImpl<PbcTransCardPaymentAccount> implements PbcTransCardPaymentAccountMapper  {

	// 默认构造方法设置命名空间
	public PbcTransCardPaymentAccountDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcTransCardPaymentAccountMapper");
	}

@Override
public void insert(PbcTransCardPaymentAccount entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}
}


