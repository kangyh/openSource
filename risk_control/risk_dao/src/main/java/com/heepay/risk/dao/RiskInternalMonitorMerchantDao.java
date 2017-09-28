/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.RiskInternalMonitorChannel;
import com.heepay.risk.entity.RiskInternalMonitorMerchant;

/**
 *
 * 描    述：内部监控商户配制表DAO接口
 *
 * 创 建 者： @author lizhen
 * 创建时间：
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
public interface RiskInternalMonitorMerchantDao   {
	
	public int deleteByPrimaryKey(String key) ;

	public int insert(RiskInternalMonitorMerchant record) throws Exception ;

	public int updateByPrimaryKey(RiskInternalMonitorMerchant record) ;
	
	List<RiskInternalMonitorMerchant> selectByCondition(RiskInternalMonitorMerchant arg0);
	RiskInternalMonitorMerchant selectByPrimary(Integer internalMerchantId);
	
}