/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantEmployeeFunctions;

/**
 * merchantService
 * @author ly
 * @version V1.0
 */
public interface MerchantEmployeeFunctionsCService{

	public MerchantEmployeeFunctions get(String id);
	
	public List<MerchantEmployeeFunctions> findList(MerchantEmployeeFunctions merchantEmployeeFunctions);
	
	public Page<MerchantEmployeeFunctions> findPage(Page<MerchantEmployeeFunctions> page, MerchantEmployeeFunctions merchantEmployeeFunctions);
	
	public void save(MerchantEmployeeFunctions merchantEmployeeFunctions, boolean flag);

	public void delete(MerchantEmployeeFunctions merchantEmployeeFunctions);
	
}