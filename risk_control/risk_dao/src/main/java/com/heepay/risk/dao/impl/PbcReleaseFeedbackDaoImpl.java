package com.heepay.risk.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcReleaseFeedbackMapper;
import com.heepay.risk.entity.PbcReleaseFeedback;
@Repository
public class PbcReleaseFeedbackDaoImpl extends BaseDaoImpl<PbcReleaseFeedback> implements PbcReleaseFeedbackMapper{
    
	// 默认构造方法设置命名空间
	public PbcReleaseFeedbackDaoImpl() {
		super.setNs("com.heepay.risk.dao.PbcReleaseFeedbackMapper");
	}
	@Override
	public void insert(PbcReleaseFeedback entity) {
		super.getSqlSession().insert(this.getNs()+".insert",entity);
		
	}
	@Override
	public PbcReleaseFeedback selectEntity(PbcReleaseFeedback entity) {
		return super.getSqlSession().selectOne(this.getNs()+".selectEntity",entity);
	}
	

}
