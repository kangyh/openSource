package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.BlackorwhiteItemListMapper;
import com.heepay.risk.dao.BlackorwhiteListMapper;
import com.heepay.risk.entity.BlackorwhiteItemList;
import com.heepay.risk.entity.BlackorwhiteList;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年4月21日 上午9:59:18
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
public class BlackorwhiteItemListDaoImpl extends BaseDaoImpl<BlackorwhiteItemList> implements BlackorwhiteItemListMapper {
    
	// 默认构造方法设置命名空间
		public BlackorwhiteItemListDaoImpl() {
			super.setNs("com.heepay.risk.dao.BlackorwhiteItemListMapper");
		}

	@Override
	public int insert(BlackorwhiteItemList record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public int update(BlackorwhiteItemList record) {
		// TODO Auto-generated method stub
		return super.getSqlSession().insert(this.getNs()+".update",record);
	}

	@Override
	public List<BlackorwhiteItemList> getlist() {
		return super.getSqlSession().selectList(this.getNs()+".getlist");
	}

	@Override
	public int delete(BlackorwhiteItemList record) {
		return  super.getSqlSession().update(this.getNs()+".delete",record);
	}

	@Override
	public List<BlackorwhiteItemList> selectByBlackId(BlackorwhiteList record) {
		return  super.getSqlSession().selectList(this.getNs()+".selectByBlackId",record);
	}

	@Override
	public int deleteByBlackId(BlackorwhiteItemList record) {
		return super.getSqlSession().update(this.getNs()+".deleteByBlackId",record);
	}

	@Override
	public BlackorwhiteItemList get(Integer blackItemId) {
		return super.getSqlSession().selectOne(this.getNs()+".get",blackItemId);
	}
}

 