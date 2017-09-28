package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAccountTransDetailMapper;
import com.heepay.risk.entity.PbcAccountTransDetail;


@Repository
public class PbcAccountTransDetailDaoImpl extends BaseDaoImpl<PbcAccountTransDetail> implements PbcAccountTransDetailMapper {

	// 默认构造方法设置命名空间
	public PbcAccountTransDetailDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcQueryInfoMapper");
	}

@Override
public void insert(PbcAccountTransDetail entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}

}
