/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;

/**
 * mcc基础数据Service
 * @author ly
 * @version V1.0
 */
public interface MerchantIndustryBaseService {

	public MerchantIndustryBase get(String id);
	
	public List<MerchantIndustryBase> findList(MerchantIndustryBase merchantIndustryBase);
	
	public Page<MerchantIndustryBase> findPage(Page<MerchantIndustryBase> page, MerchantIndustryBase merchantIndustryBase);
	
	public void save(MerchantIndustryBase merchantIndustryBase, boolean flag);
	
	public void delete(MerchantIndustryBase merchantIndustryBase);
	
}