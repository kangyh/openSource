package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PcacMerchantComparedInvestigationMapper;
import com.heepay.risk.dao.PcacRiskHintInfoMapper;
import com.heepay.risk.entity.PcacMerchantComparedInvestigation;
import com.heepay.risk.entity.PcacRiskHintInfo;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 下午5:53:34
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
public class PcacMerchantComparedInvestigationDaoImpl extends BaseDaoImpl<PcacMerchantComparedInvestigation> implements PcacMerchantComparedInvestigationMapper {
    
	public PcacMerchantComparedInvestigationDaoImpl() {
		super.setNs("com.heepay.risk.dao.PcacMerchantComparedInvestigationMapper");
	}
	@Override
	public int insert(PcacMerchantComparedInvestigation record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public PcacMerchantComparedInvestigation selectByBatchNo(String batchNo) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByBatchNo",batchNo);
	}

}

 