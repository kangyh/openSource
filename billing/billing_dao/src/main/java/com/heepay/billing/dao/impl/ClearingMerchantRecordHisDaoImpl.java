package com.heepay.billing.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.ClearingMerchantRecordHisMapper;
import com.heepay.billing.entity.ClearingMerchantRecordHis;
import com.heepay.billing.entity.ClearingMerchantRecordHisExample;

/**
 * 
 *
 * 描    述：商户侧清结算记录历史表
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月10日 下午3:11:00
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
public class ClearingMerchantRecordHisDaoImpl extends BaseDaoImpl<ClearingMerchantRecordHis, ClearingMerchantRecordHisExample>
		implements ClearingMerchantRecordHisMapper {
	// 默认构造方法设置命名空间
	public ClearingMerchantRecordHisDaoImpl() {
		super.setNs("com.heepay.billing.dao.ClearingMerchantRecordHisMapper");
	}

	@Override
	public int insertMap(Map<String, Object> map) {
		return super.getSqlSession().insert(this.getNs() + ".insertMap", map);
	}
	
}

