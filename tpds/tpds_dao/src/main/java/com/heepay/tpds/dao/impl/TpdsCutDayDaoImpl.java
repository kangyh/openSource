package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.enums.tpds.CutDay;
import com.heepay.tpds.dao.TpdsCutDayMapper;
import com.heepay.tpds.entity.TpdsCutDay;
import com.heepay.tpds.enums.Customerstatus;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月16日 下午6:10:23
 * 创建描述：
 * 
 * 修 改 者： 
 * 修改时间： 
 * 修改描述：
 * 
 * 审 核 者： 
 * 审核时间： 
 * 审核描述：
 *
 */
@Repository
public class TpdsCutDayDaoImpl  extends BaseNewDaoImpl<TpdsCutDay> implements TpdsCutDayMapper {

	// 默认构造方法设置命名空间
		public TpdsCutDayDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsCutDayMapper");
		}

	@Override
	public TpdsCutDay selectByCutType(String CutType) {
		return this.getSqlSession().selectOne(this.getNs()+".selectByCutType",CutType);
	}

	@Override
	public int insert(TpdsCutDay entity) {
		return this.getSqlSession().insert(this.getNs()+".insert",entity);
	}

	@Override
	public int update(TpdsCutDay entity) {
		return this.getSqlSession().update(this.getNs()+".update",entity);
	}
	
	/*
	 * 查询日切点
	 * (non-Javadoc)
	 * @see com.heepay.tpds.dao.TpdsCutDayMapper#selectCutTypeByCutType(com.heepay.tpds.entity.TpdsCutDay)
	 */
	@Override
	public TpdsCutDay selectCutTypeByCutType(TpdsCutDay tpdsCutDay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cutType", CutDay.BANK.getValue());
		map.put("busiNo", tpdsCutDay.getBusiNo());
		map.put("status", Customerstatus.ENABLE.getValue());
		return super.getSqlSession().selectOne(this.getNs()+".selectCutTypeByCutType",map);
	}

	/**
	 * 
	 * @方法说明： 根据商户编码获取日切点
	 * @author chenyanming
	 * @param merchantId
	 * @param status
	 * @return
	 * @时间：2017年2月18日下午4:05:03
	 */
	@Override
	public TpdsCutDay getCutTimeByBusiNo(String merchantId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busiNo", merchantId);
		map.put("status", status);
		return super.getSqlSession().selectOne(this.getNs()+".getCutTimeByBusiNo",map);
	}
}

	

 