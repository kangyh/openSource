package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcTransSerialCardPaymentAccountQueryMapper;
import com.heepay.risk.dao.PcacBlackListMapper;
import com.heepay.risk.entity.PbcTransSerialCardPaymentAccountQuery;
import com.heepay.risk.entity.PcacBlackList;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 下午1:52:56
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
public class PcacBlackListDaoImpl extends BaseDaoImpl<PcacBlackList> implements PcacBlackListMapper {
    

	// 默认构造方法设置命名空间
	public PcacBlackListDaoImpl() {
		super.setNs("com.heepay.risk.dao.PcacBlackListMapper");
	}
	@Override
	public int insert(PcacBlackList record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}
	@Override
	public PcacBlackList selectByBatchNo(String batchNo) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByBatchNo",batchNo);
	}
	@Override
	public PcacBlackList selectByCondition(PcacBlackList entity) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByCondition",entity);
	}
	@Override
	public int update(PcacBlackList entity) {
		return super.getSqlSession().update(this.getNs()+".update",entity);
	}
	

}

 