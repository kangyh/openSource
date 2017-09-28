package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.BlackorwhiteListMapper;
import com.heepay.risk.dao.PbcAccountDetailSubjectMapper;
import com.heepay.risk.entity.BlackorwhiteList;
import com.heepay.risk.entity.PbcAccountDetailSubject;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年4月21日 上午10:00:47
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
public class BlackorwhiteListDaoImpl extends BaseDaoImpl<BlackorwhiteList> implements BlackorwhiteListMapper {
    
	// 默认构造方法设置命名空间
		public BlackorwhiteListDaoImpl() {
			super.setNs("com.heepay.risk.dao.BlackorwhiteListMapper");
		}

	@Override
	public int insert(BlackorwhiteList record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public int update(BlackorwhiteList record) {
		// TODO Auto-generated method stub
		return super.getSqlSession().insert(this.getNs()+".update",record);
	}

	@Override
	public List<BlackorwhiteList> getlist() {
		return super.getSqlSession().selectList(this.getNs()+".getlist");
	}

	@Override
	public int delete(BlackorwhiteList record) {
		// TODO Auto-generated method stub
		return super.getSqlSession().delete(this.getNs()+".delete",record);
	}

	@Override
	public BlackorwhiteList get(Integer blackId) {
		return super.getSqlSession().selectOne(this.getNs()+".get",blackId);
	}
}
	


 