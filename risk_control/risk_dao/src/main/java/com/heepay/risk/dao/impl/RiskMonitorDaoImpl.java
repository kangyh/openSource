package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.RiskMerchantProductQuotaMapper;
import com.heepay.risk.dao.RiskMonitorMapper;
import com.heepay.risk.entity.RiskMerchantProductQuota;
import com.heepay.risk.entity.RiskMonitorObj;
import com.heepay.risk.entity.RiskOrder;

/**
 * 描    述：风控系统  服务类
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2016年12月10日 下午6:26:51
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
public class RiskMonitorDaoImpl extends BaseDaoImpl<RiskMonitorObj> implements RiskMonitorMapper  {
	// 默认构造方法设置命名空间
				public RiskMonitorDaoImpl() {
					super.setNs("com.heepay.risk.dao.RiskMonitorMapper");
				}

				@Override
				public RiskMonitorObj queryMonitorInfo() {
					// TODO Auto-generated method stub
					return super.getSqlSession().selectOne(this.getNs()+".queryMonitorInfo");
				}
}
