/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.RiskInternalMonitorChannel;

/**
 *
 * 描    述：内部监控通道配置DAO接口
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
public interface RiskInternalMonitorChannelDao  {

	public int deleteByPrimaryKey(String key) ;

	public int insert(RiskInternalMonitorChannel record) throws Exception ;

	public int updateByPrimaryKey(RiskInternalMonitorChannel record) ;
	
	List<RiskInternalMonitorChannel> selectByCondition(RiskInternalMonitorChannel arg0);
	RiskInternalMonitorChannel selectByPrimary(Integer internalChannelId);
	
}