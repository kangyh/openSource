package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcTransSerialCardPaymentAccountQueryMapper;
import com.heepay.risk.entity.PbcTransSerialCardPaymentAccountQuery;
@Repository
public class PbcTransSerialCardPaymentAccountQueryDaoImpl extends BaseDaoImpl<PbcTransSerialCardPaymentAccountQuery> implements PbcTransSerialCardPaymentAccountQueryMapper  {

	// 默认构造方法设置命名空间
	public PbcTransSerialCardPaymentAccountQueryDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcTransSerialCardPaymentAccountQueryMapper");
	}

@Override
public void insert(PbcTransSerialCardPaymentAccountQuery entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
} 
}
