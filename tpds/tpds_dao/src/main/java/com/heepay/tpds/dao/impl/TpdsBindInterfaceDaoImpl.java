package com.heepay.tpds.dao.impl;

import org.springframework.stereotype.Repository;
import com.heepay.tpds.dao.TpdsBindInterfaceMapper;
import com.heepay.tpds.entity.TpdsBindInterface;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月17日 下午3:06:08
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
public class TpdsBindInterfaceDaoImpl extends BaseNewDaoImpl<TpdsBindInterface> implements TpdsBindInterfaceMapper {

	// 默认构造方法设置命名空间
		public TpdsBindInterfaceDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsBindInterfaceMapper");
		}

	@Override
	public int insert(TpdsBindInterface record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public int update(TpdsBindInterface record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(this.getNs()+".update",record);
	}
	
}

 