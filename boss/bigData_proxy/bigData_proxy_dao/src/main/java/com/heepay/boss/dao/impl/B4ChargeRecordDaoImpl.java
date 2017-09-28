package com.heepay.boss.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.boss.dao.B4ChargeRecordMapper;
import com.heepay.boss.entity.B4ChargeRecord;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:14:00
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
public class B4ChargeRecordDaoImpl extends BaseNewDaoImpl<B4ChargeRecord> implements B4ChargeRecordMapper {
	// 默认构造方法设置命名空间
	public B4ChargeRecordDaoImpl() {
		super.setNs("com.heepay.boss.dao.B4ChargeRecordMapper");
	}

	@Override
	public int deleteByPrimaryKey(Long b4Id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(B4ChargeRecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public B4ChargeRecord selectByPrimaryKey(Long b4Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<B4ChargeRecord> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(B4ChargeRecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
