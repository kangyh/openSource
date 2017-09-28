package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcTransDetailQueryMapper;
import com.heepay.risk.entity.PbcTransDetailQuery;
@Repository
public class PbcTransDetailQueryDaoImpl extends BaseDaoImpl<PbcTransDetailQuery> implements PbcTransDetailQueryMapper{

	// 默认构造方法设置命名空间
	public PbcTransDetailQueryDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcTransDetailQueryMapper");
	}

@Override
public void insert(PbcTransDetailQuery entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}

}
