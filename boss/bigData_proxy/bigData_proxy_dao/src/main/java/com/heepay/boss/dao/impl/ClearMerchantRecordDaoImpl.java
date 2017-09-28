package com.heepay.boss.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.boss.dao.ClearMerchantRecordMapper;
import com.heepay.boss.entity.ClearMerchantRecord;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:14:32
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
public class ClearMerchantRecordDaoImpl extends BaseNewDaoImpl<ClearMerchantRecord> implements ClearMerchantRecordMapper {

	// 默认构造方法设置命名空间
	public ClearMerchantRecordDaoImpl() {
		super.setNs("com.heepay.boss.dao.ClearMerchantRecordMapper");
	}

	@Override
	public List<ClearMerchantRecord> queryForBigData(Date startTime, Date endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return super.getSqlSession().selectList(this.getNs() + ".queryForBigData",map);
	}

}