package com.heepay.tpds.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsBankCerMapper;
import com.heepay.tpds.dao.TpdsCutDayMapper;
import com.heepay.tpds.entity.TpdsBankCer;
import com.heepay.tpds.entity.TpdsCutDay;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月17日 下午3:03:38
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
public class TpdsBankCerDaoImpl extends BaseNewDaoImpl<TpdsBankCer> implements TpdsBankCerMapper {

	// 默认构造方法设置命名空间
		public TpdsBankCerDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsBankCerMapper");
		}

	

	@Override
	public int insert(TpdsBankCer entity) {
		return this.getSqlSession().insert(this.getNs()+".insert",entity);
	}

	@Override
	public int update(TpdsBankCer entity) {
		return this.getSqlSession().update(this.getNs()+".update",entity);
	}



	@Override
	public List<TpdsBankCer> getlist() {
		return this.getSqlSession().selectList(this.getNs()+".getlist");
	}



	@Override
	public int deleteByBankNo(String bankNo) {
		return this.getSqlSession().delete(this.getNs()+".deleteByBankNo",bankNo);
	}
}

 