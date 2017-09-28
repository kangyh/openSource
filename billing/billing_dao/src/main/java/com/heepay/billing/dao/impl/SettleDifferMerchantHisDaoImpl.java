package com.heepay.billing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleDifferMerchantHisMapper;
import com.heepay.billing.entity.SettleDifferMerchantHis;
import com.heepay.billing.entity.SettleDifferMerchantHisExample;

/**
 * 
 *
 * 描    述：商户侧差错账汇总历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月10日 下午3:16:58
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
public class SettleDifferMerchantHisDaoImpl extends BaseDaoImpl<SettleDifferMerchantHis, SettleDifferMerchantHisExample>
		implements SettleDifferMerchantHisMapper {
	// 默认构造方法设置命名空间
	public SettleDifferMerchantHisDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleDifferMerchantHisMapper");
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return super.getSqlSession().insert(this.getNs() + ".insertMap", map);
	}
	
}

