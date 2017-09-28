package com.heepay.boss.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.boss.dao.ClearChannelRecordMapper;
import com.heepay.boss.entity.ClearChannelRecord;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:14:26
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
public class ClearChannelRecordDaoImpl extends BaseNewDaoImpl<ClearChannelRecord> implements ClearChannelRecordMapper {
	// 默认构造方法设置命名空间
	public ClearChannelRecordDaoImpl() {
		super.setNs("com.heepay.boss.dao.ClearChannelRecordMapper");
	}

	@Override
	public List<ClearChannelRecord> queryForBigData() {
		return super.getSqlSession().selectList(this.getNs() + ".queryForBigData");
	}

	@Override
	public ClearChannelRecord selectByTranNo(String transNo) {
		return super.getSqlSession().selectOne(this.getNs() + ".selectByTranNo",transNo);
	}

	
}
