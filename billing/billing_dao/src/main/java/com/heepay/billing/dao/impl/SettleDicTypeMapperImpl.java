package com.heepay.billing.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDicTypeMapper;
import com.heepay.billing.entity.SettleDicType;
import com.heepay.billing.entity.SettleDicTypeExample;

@Repository
public class SettleDicTypeMapperImpl extends BaseDaoImpl<SettleDicType, SettleDicTypeExample> implements SettleDicTypeMapper {

	// 默认构造方法设置命名空间
	public SettleDicTypeMapperImpl() {
		super.setNs("com.heepay.billing.dao.SettleDicTypeMapper");
	}

	@Override
	public int deleteByPrimaryKey(Integer typeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SettleDicType selectByPrimaryKey(Integer typeId) {
		// TODO Auto-generated method stub
		return null;
	}
	//查询结账区间右侧时间L
	@Override
	public SettleDicType selectByPrimaryKey(SettleDicType record) {
		
		return super.getSqlSession().selectOne(this.getNs()+".selectType", record);
	}
	
	

}
