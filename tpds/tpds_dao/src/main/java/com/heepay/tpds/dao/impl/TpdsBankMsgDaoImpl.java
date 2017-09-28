package com.heepay.tpds.dao.impl;

import org.springframework.stereotype.Repository;
import com.heepay.tpds.dao.TpdsBankMsgMapper;
import com.heepay.tpds.entity.TpdsBankMsg;
import com.heepay.tpds.entity.TpdsBankMsgExample;


/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月16日 上午9:38:25
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
public class TpdsBankMsgDaoImpl extends BaseDaoImpl<TpdsBankMsg,TpdsBankMsgExample> implements TpdsBankMsgMapper {

	// 默认构造方法设置命名空间
	public TpdsBankMsgDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsBankMsgMapper");
	}

	@Override
	public int insert(TpdsBankMsg record) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TpdsBankMsg selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}

 