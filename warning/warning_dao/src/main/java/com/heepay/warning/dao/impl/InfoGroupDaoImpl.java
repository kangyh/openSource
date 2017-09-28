package com.heepay.warning.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.warning.dao.InfoGroupMapper;
import com.heepay.warning.entity.InfoGroup;


/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月16日 上午10:47:10
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
public class InfoGroupDaoImpl  extends BaseDaoImpl<InfoGroup> implements InfoGroupMapper {
    
	// 默认构造方法设置命名空间
		public InfoGroupDaoImpl() {
			super.setNs("com.heepay.warning.dao.InfoGroupMapper");
		}

		@Override
		public List<InfoGroup> selectAll() {
			return super.getSqlSession().selectList(this.getNs()+".selectAll");
		}

		@Override
		public InfoGroup getByGroupCode(String groupCode) {
			return super.getSqlSession().selectOne(this.getNs()+".getByGroupCode",groupCode);
		}
         
		
	
}

 