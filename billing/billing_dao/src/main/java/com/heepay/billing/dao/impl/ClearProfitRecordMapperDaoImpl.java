package com.heepay.billing.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearProfitRecordMapper;
import com.heepay.billing.entity.ClearProfitRecord;
import com.heepay.billing.entity.ClearProfitRecordExample;

@Component
public class ClearProfitRecordMapperDaoImpl extends BaseDaoImpl<ClearProfitRecord, ClearProfitRecordExample> implements ClearProfitRecordMapper {

	// 默认构造方法设置命名空间
	public ClearProfitRecordMapperDaoImpl() {
		super.setNs("com.heepay.billing.dao.ClearProfitRecordMapper");
	}
	
	/*
	 * 分润明细入库
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.ClearProfitRecordMapper#insertClearProfitRecord(com.heepay.billing.entity.ClearProfitRecord)
	 */
	@Override
	public int insertClearProfitRecord(ClearProfitRecord clearProfitRecord) {
		return super.getSqlSession().insert(this.getNs() + ".insert", clearProfitRecord);
	}

	/**
	 * 通过订单号，查询分润明细
	 * @param map
	 * @return
	 */
	@Override
	public List<ClearProfitRecord> queryByTransNo(Map map) {
		// TODO Auto-generated method stub
		
		return super.getSqlSession().selectList(this.getNs() + ".queryByTransNo", map);		 
	}
	/**
	 * 清算明细结算后，更新结果
	 * @param map
	 */
	@Override
	public void updateAfterSettle(Map map) {
		// TODO Auto-generated method stub		
		super.getSqlSession().update(this.getNs() + ".updateAfterSettleByTransNo", map);		
	}

	/**
	 * 
	 * @方法说明：判断是否重复发送的数据
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public int getBooleanExist(int shareDetailId) {
		// TODO Auto-generated method stub
	 return super.getSqlSession().selectOne(this.getNs() + ".getBooleanExist", shareDetailId);
	}

}
