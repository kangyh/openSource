package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAttachmentQueryMapper;
import com.heepay.risk.entity.PbcAttachmentQuery;

@Repository
public class PbcAttachmentQueryDaoImpl extends BaseDaoImpl<PbcAttachmentQuery> implements PbcAttachmentQueryMapper {

	// 默认构造方法设置命名空间
	public PbcAttachmentQueryDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcAttachmentQueryMapper");
	}

@Override
public void insert(PbcAttachmentQuery entity) {
// TODO Auto-generated method stub
super.getSqlSession().insert(this.getNs()+".insert",entity);
}
}
