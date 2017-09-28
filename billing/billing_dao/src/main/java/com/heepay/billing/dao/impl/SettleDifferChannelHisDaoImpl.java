package com.heepay.billing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferChannelHisMapper;
import com.heepay.billing.entity.SettleDifferChannelHis;
import com.heepay.billing.entity.SettleDifferChannelHisExample;

/**
 * 
 *
 * 描    述：通道侧差异账汇总历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月10日 下午3:16:35
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
public class SettleDifferChannelHisDaoImpl extends BaseDaoImpl<SettleDifferChannelHis, SettleDifferChannelHisExample>
		implements SettleDifferChannelHisMapper {
	// 默认构造方法设置命名空间
	public SettleDifferChannelHisDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleDifferChannelHisMapper");
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return super.getSqlSession().insert(this.getNs() + ".insertMap", map);
	}
	
}

