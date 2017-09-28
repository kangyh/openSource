package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAccountDetailSubjectMapper;
import com.heepay.risk.entity.PbcAccountDetailSubject;
@Repository
public class PbcAccountDetailSubjectDaoImpl extends BaseDaoImpl<PbcAccountDetailSubject> implements PbcAccountDetailSubjectMapper {
    
	// 默认构造方法设置命名空间
		public PbcAccountDetailSubjectDaoImpl() {
			super.setNs("com.heepay.risk.dao.PbcAccountDetailSubjectMapper");
		}
	@Override
	public void insert(PbcAccountDetailSubject entity) {
		super.getSqlSession().insert(this.getNs()+".insert",entity);
		
	}

}
