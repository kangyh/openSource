package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskOrderMapper;
import com.heepay.risk.entity.RiskOrder;
@Repository
public class RiskOrderDaoImpl extends BaseDaoImpl<RiskOrder> implements RiskOrderMapper {
   
	// 默认构造方法设置命名空间
			public RiskOrderDaoImpl() {
				super.setNs("com.heepay.risk.dao.RiskOrderMapper");
			}
		
	@Override
	public void insert(RiskOrder entity) {
		// TODO Auto-generated method stub
	super.getSqlSession().insert(this.getNs()+".insert",entity);
	}

}
