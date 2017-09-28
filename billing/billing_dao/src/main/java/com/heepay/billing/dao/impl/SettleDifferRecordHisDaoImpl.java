package com.heepay.billing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferRecordHisMapper;
import com.heepay.billing.entity.SettleDifferRecordHis;
import com.heepay.billing.entity.SettleDifferRecordHisExample;

/**
 * 
 *
 * 描    述：差异单记录历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月10日 下午3:18:10
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
public class SettleDifferRecordHisDaoImpl extends BaseDaoImpl<SettleDifferRecordHis, SettleDifferRecordHisExample>
		implements SettleDifferRecordHisMapper {
	// 默认构造方法设置命名空间
	public SettleDifferRecordHisDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleDifferRecordHisMapper");
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return super.getSqlSession().insert(this.getNs() + ".insertMap", map);
	}
	
}

