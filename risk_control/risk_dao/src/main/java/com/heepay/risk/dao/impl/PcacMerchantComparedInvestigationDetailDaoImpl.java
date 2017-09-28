package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PcacMerchantComparedInvestigationDetailMapper;
import com.heepay.risk.dao.PcacMerchantComparedInvestigationMapper;
import com.heepay.risk.entity.PcacMerchantComparedInvestigation;
import com.heepay.risk.entity.PcacMerchantComparedInvestigationDetail;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月6日 上午9:50:52
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
public class PcacMerchantComparedInvestigationDetailDaoImpl extends BaseDaoImpl<PcacMerchantComparedInvestigationDetail> implements PcacMerchantComparedInvestigationDetailMapper {
    
	public PcacMerchantComparedInvestigationDetailDaoImpl() {
		super.setNs("com.heepay.risk.dao.PcacMerchantComparedInvestigationDetailMapper");
	}
	@Override
	public int insert(PcacMerchantComparedInvestigationDetail record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}
}

 