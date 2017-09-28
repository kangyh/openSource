package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcPaymentAccountMapper;
import com.heepay.risk.entity.PbcPaymentAccount;

@Repository
public class PbcPaymentAccountDaoImpl extends BaseDaoImpl<PbcPaymentAccount> implements PbcPaymentAccountMapper  {

	// 默认构造方法设置命名空间
	public PbcPaymentAccountDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcPaymentAccountMapper");
	}

@Override
public void insert(PbcPaymentAccount entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}

}
