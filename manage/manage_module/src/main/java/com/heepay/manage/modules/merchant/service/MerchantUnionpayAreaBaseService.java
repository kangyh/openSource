/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantUnionpayAreaBase;

/**
 * 银联地区编码基础数据Service
 * @author ly
 * @version V1.0
 */
public interface MerchantUnionpayAreaBaseService {

	public MerchantUnionpayAreaBase get(String id);
	
	public List<MerchantUnionpayAreaBase> findList(MerchantUnionpayAreaBase merchantUnionpayAreaBase);
	
	public Page<MerchantUnionpayAreaBase> findPage(Page<MerchantUnionpayAreaBase> page, MerchantUnionpayAreaBase merchantUnionpayAreaBase);
	
	public void save(MerchantUnionpayAreaBase merchantUnionpayAreaBase, boolean flag);
	
	public void delete(MerchantUnionpayAreaBase merchantUnionpayAreaBase);
	
}