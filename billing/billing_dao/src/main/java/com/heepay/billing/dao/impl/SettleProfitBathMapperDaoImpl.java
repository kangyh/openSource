package com.heepay.billing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleProfitBathMapper;
import com.heepay.billing.entity.SettleProfitBath;
import com.heepay.billing.entity.SettleProfitBathExample;


/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年11月9日下午4:26:45
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
public class SettleProfitBathMapperDaoImpl extends BaseDaoImpl<SettleProfitBath, SettleProfitBathExample> implements SettleProfitBathMapper{

	public SettleProfitBathMapperDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleProfitBathMapper");
	}
	
	@Override
	public int insert(SettleProfitBath record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}
	
	/**
	 * @author xuangang
	 * @描述 通过结算批次更新分润状态
	 * @param map
	 */
	@Override
	public void updateSettleProfitBatchBySettleNo(Map<String, Object> map){
		super.getSqlSession().update(this.getNs() + ".updateSettleProfitBatchBySettleNo", map);
	}

}
