package com.heepay.warning.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.warning.dao.InfoMemberMapper;
import com.heepay.warning.entity.InfoMember;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月16日 上午10:36:58
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
public class InfoMemberDaoImpl extends BaseDaoImpl<InfoMember> implements InfoMemberMapper {
    
	// 默认构造方法设置命名空间
		public InfoMemberDaoImpl() {
			super.setNs("com.heepay.warning.dao.InfoMemberMapper");
		}

		@Override
		public List<InfoMember> selectByGroupId(Integer groupId) {
			return super.getSqlSession().selectList(this.getNs()+".selectByGroupId",groupId);
		}
	
}

 