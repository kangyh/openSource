package com.heepay.risk.dao.impl;


import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcFeedBackMapper;
import com.heepay.risk.entity.PbcFeedBack;
@Repository
public class PbcFeedBackDaoImpl  extends BaseDaoImpl<PbcFeedBack> implements PbcFeedBackMapper {

	// 默认构造方法设置命名空间
	public PbcFeedBackDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcFeedBackMapper");
	}

@Override
public void insert(PbcFeedBack entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}

@Override
public PbcFeedBack GetFeedBack(String applicationCode) {
	return super.getSqlSession().selectOne(this.getNs()+".GetFeedBack",applicationCode);
}
}

