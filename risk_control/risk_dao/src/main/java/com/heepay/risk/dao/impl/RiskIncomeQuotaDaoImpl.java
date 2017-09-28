package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAccountDetailSubjectMapper;
import com.heepay.risk.dao.RiskIncomeQuotaMapper;
import com.heepay.risk.entity.PbcAccountDetailSubject;
import com.heepay.risk.entity.RiskIncomeQuota;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年6月1日 下午3:06:48
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
public class RiskIncomeQuotaDaoImpl extends BaseDaoImpl<RiskIncomeQuota> implements RiskIncomeQuotaMapper {
    
	// 默认构造方法设置命名空间
		public RiskIncomeQuotaDaoImpl() {
			super.setNs("com.heepay.risk.dao.RiskIncomeQuotaMapper");
		}
	@Override
	public  int insert(RiskIncomeQuota entity) {
		return super.getSqlSession().insert(this.getNs()+".insert",entity);
		
	}
	@Override
	public int update(RiskIncomeQuota record) {
		return super.getSqlSession().update(this.getNs()+".update",record);
	}
	@Override
	public List<RiskIncomeQuota> getlist() {
		return super.getSqlSession().selectList(this.getNs()+".getlist");
	}
	@Override
	public RiskIncomeQuota get(Long id) {
		return super.getSqlSession().selectOne(this.getNs()+".get",id);
	}
}


 