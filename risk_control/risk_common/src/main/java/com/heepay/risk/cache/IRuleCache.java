package com.heepay.risk.cache;

import java.util.List;

import com.heepay.risk.vo.BlackItemModel;
import com.heepay.risk.vo.RiskMerchantProductQuotaVO;
import com.heepay.risk.vo.RiskProductQuotaVO;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月16日 上午11:17:26
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
public interface IRuleCache {

	
	RiskMerchantProductQuotaVO getQuotaMerchantCache(String merchantQuotakey);
	Boolean setQuotaMerchantCache(RiskMerchantProductQuotaVO riskMerchantProductQuotaVO); //没有就新增，有就修改
	Boolean delQuotaMerchantCache(RiskMerchantProductQuotaVO riskMerchantProductQuotaVO);
	
	RiskProductQuotaVO getQuotaProductCache(String productQuotakey);
	Boolean setQuotaProductCache(RiskProductQuotaVO riskProductQuotaVO); //没有就新增，有就修改
	Boolean delQuotaProductCache(RiskProductQuotaVO riskProductQuotaVO);
}


