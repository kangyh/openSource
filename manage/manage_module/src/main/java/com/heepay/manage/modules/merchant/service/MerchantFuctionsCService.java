/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantFuctions;

/**
 *
 * 描    述：商户权限Service
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
public interface MerchantFuctionsCService {

	public MerchantFuctions get(String id);
	
	public List<MerchantFuctions> findList(MerchantFuctions merchantFuctions);
	
	public Page<MerchantFuctions> findPage(Page<MerchantFuctions> page, MerchantFuctions merchantFuctions);
	
	public void save(MerchantFuctions merchantFuctions, boolean flag);
	
	public void delete(MerchantFuctions merchantFuctions);
	
}