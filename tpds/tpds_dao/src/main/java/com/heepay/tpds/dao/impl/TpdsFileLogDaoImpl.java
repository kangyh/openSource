package com.heepay.tpds.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsFileLogMapper;
import com.heepay.tpds.entity.TpdsFileLog;
import com.heepay.tpds.entity.TpdsFileLogExample;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年5月2日下午4:12:11
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
public class TpdsFileLogDaoImpl extends BaseNewDaoImpl<TpdsFileLog> implements TpdsFileLogMapper{
	
	// 默认构造方法设置命名空间
	public TpdsFileLogDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsFileLogMapper");
	}
			
	@Override
	public int countByExample(TpdsFileLogExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(TpdsFileLogExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer logId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TpdsFileLog record) {
		return this.getSqlSession().insert(this.getNs()+".insert",record);
	}

	@Override
	public int insertSelective(TpdsFileLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TpdsFileLog> selectByExample(TpdsFileLogExample example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TpdsFileLog selectByPrimaryKey(Integer logId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(TpdsFileLog record, TpdsFileLogExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(TpdsFileLog record, TpdsFileLogExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(TpdsFileLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(TpdsFileLog record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer selectMaxId() {
		return this.getSqlSession().selectOne(this.getNs()+".selectMaxId");
	}

}
