package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;
import com.heepay.risk.dao.PcacRiskHintInfoMapper;
import com.heepay.risk.entity.PcacRiskHintInfo;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 下午1:55:46
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
public class PcacRiskHintInfoDaoImpl extends BaseDaoImpl<PcacRiskHintInfo> implements PcacRiskHintInfoMapper  {
    
	// 默认构造方法设置命名空间
		public PcacRiskHintInfoDaoImpl() {
			super.setNs("com.heepay.risk.dao.PcacRiskHintInfoMapper");
		}
	@Override
	public int insert(PcacRiskHintInfo record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}
	@Override
	public PcacRiskHintInfo selectByBatchNo(String batchNo) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByBatchNo",batchNo);
	}

}

 