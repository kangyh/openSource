package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsCustomChargeMapper;
import com.heepay.tpds.entity.TpdsCustomCharge;
import com.heepay.tpds.entity.TpdsCustomChargeExample;

@Repository
public class TpdsCustomChargeDaoImpl extends BaseDaoImpl<TpdsCustomCharge,TpdsCustomChargeExample> implements TpdsCustomChargeMapper{
	
	// 默认构造方法设置命名空间
	public TpdsCustomChargeDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsCustomChargeMapper");
	}
		
	@Override
	public TpdsCustomCharge selectByBusinessOrderNo(String transNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessOrderNo", transNo);
		return super.getSqlSession().selectOne(this.getNs()+".selectByBusinessOrderNo",map);
	}
	
	@Override
	public List<TpdsCustomCharge> selectByBusinessSeqNo(String transNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessSeqNo", transNo);
		return super.getSqlSession().selectList(this.getNs()+".selectByBusinessSeqNo",map);
	}

}
