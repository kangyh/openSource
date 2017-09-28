package com.heepay.warning.dao.impl;




import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.warning.dao.InfoMsgMapper;
import com.heepay.warning.entity.InfoMsg;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月16日 上午10:53:35
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
public class InfoMsgDaoImpl extends BaseDaoImpl<InfoMsg> implements InfoMsgMapper {
    
	// 默认构造方法设置命名空间
		public InfoMsgDaoImpl() {
			super.setNs("com.heepay.warning.dao.InfoMsgMapper");
		}

		@Override
		public int insert(InfoMsg record) {
			return super.getSqlSession().insert(this.getNs()+".insert",record);
		}

		@Override
		public List<InfoMsg> getList() {
			return super.getSqlSession().selectList(this.getNs()+".getList");
		}

		@Override
		public int update(InfoMsg record) {
			return super.getSqlSession().update(this.getNs()+".update",record);
		}

	@Override
	public InfoMsg getBodyByMsgId(Integer msgId) {
		return super.getSqlSession().selectOne(this.getNs()+".getBodyByMsgId",msgId);
	}

}

 