package com.heepay.billing.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDicItemMapper;
import com.heepay.billing.entity.SettleDicItem;
import com.heepay.billing.entity.SettleDicItemExample;
import com.heepay.billing.entity.SettleDicType;

@Repository
public class SettleDicItemMapperImpl extends BaseDaoImpl<SettleDicItem, SettleDicItemExample> implements SettleDicItemMapper {

	// 默认构造方法设置命名空间
	public SettleDicItemMapperImpl() {
		super.setNs("com.heepay.billing.dao.SettleDicItemMapper");
	}

	@Override
	public SettleDicItem selectByPrimaryKey(SettleDicItem record) {
		
		return super.getSqlSession().selectOne(this.getNs()+".selecKey", record);
	}
    
	/**
	 * @描述 通过字典类型查询字典明细表
	 * @param record
	 * @return
	 */
	public SettleDicItem queryItemByTypeCode(SettleDicType record) {
		
		return super.getSqlSession().selectOne(this.getNs()+".querySettleItemByTypeCode", record);
	}

	@Override
	public List<SettleDicItem> queryItem(SettleDicType record) {
		
		return super.getSqlSession().selectList(this.getNs()+".queryItem", record);
	}

  
}
