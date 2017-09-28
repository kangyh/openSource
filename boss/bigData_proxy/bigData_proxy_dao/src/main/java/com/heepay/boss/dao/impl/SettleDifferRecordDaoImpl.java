package com.heepay.boss.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.boss.dao.SettleDifferRecordMapper;
import com.heepay.boss.entity.SettleDifferRecord;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:14:37
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
public class SettleDifferRecordDaoImpl extends BaseNewDaoImpl<SettleDifferRecord> implements SettleDifferRecordMapper {

	// 默认构造方法设置命名空间
	public SettleDifferRecordDaoImpl() {
		super.setNs("com.heepay.boss.dao.SettleDifferRecordMapper");
	}

	@Override
	public List<SettleDifferRecord> queryForBigData() {
		return super.getSqlSession().selectList(this.getNs() + ".queryForBigData");
	}

	@Override
	public SettleDifferRecord selectByTransNo(String transNo) {
		return super.getSqlSession().selectOne(this.getNs() + ".selectByTransNo",transNo);
	}

}
