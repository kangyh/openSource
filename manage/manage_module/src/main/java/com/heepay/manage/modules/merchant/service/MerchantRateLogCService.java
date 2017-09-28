/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantRateLog;

/**
 *
 * 描    述：费率操作日志Service
 *
 * 创 建 者： @author ly
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
public interface MerchantRateLogCService {

	public MerchantRateLog get(String id);
	
	public List<MerchantRateLog> findList(MerchantRateLog merchantRateLog);
	
	public Page<MerchantRateLog> findPage(Page<MerchantRateLog> page, MerchantRateLog merchantRateLog);
	
	public void save(MerchantRateLog merchantRateLog);
	
	public void delete(MerchantRateLog merchantRateLog);
	
}