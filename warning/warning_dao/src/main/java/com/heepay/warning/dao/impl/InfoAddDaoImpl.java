package com.heepay.warning.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.warning.dao.InfoAddMapper;
import com.heepay.warning.dao.InfoMemberMapper;
import com.heepay.warning.entity.InfoAdd;
import com.heepay.warning.entity.InfoMember;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月24日 下午12:34:55
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
public class InfoAddDaoImpl extends BaseDaoImpl<InfoAdd> implements InfoAddMapper {
    
	// 默认构造方法设置命名空间
		public InfoAddDaoImpl() {
			super.setNs("com.heepay.warning.dao.InfoAddMapper");
		}

	@Override
	public int insert(InfoAdd record) {
		return super.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public InfoAdd getByMsgId(Integer msgId) {
		return super.getSqlSession().selectOne(this.getNs()+".getByMsgId",msgId);
	}

}

 