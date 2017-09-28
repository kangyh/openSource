package com.heepay.billing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleBillRecordHisMapper;
import com.heepay.billing.entity.SettleBillRecordHis;
import com.heepay.billing.entity.SettleBillRecordHisExample;

/**
 * 
 *
 * 描    述：账单明细记录历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月10日 下午3:11:23
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
public class SettleBillRecordHisDaoImpl extends BaseDaoImpl<SettleBillRecordHis, SettleBillRecordHisExample>
		implements SettleBillRecordHisMapper {
	// 默认构造方法设置命名空间
	public SettleBillRecordHisDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleBillRecordHisMapper");
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return super.getSqlSession().insert(this.getNs() + ".insertMap", map);
	}
	
}

