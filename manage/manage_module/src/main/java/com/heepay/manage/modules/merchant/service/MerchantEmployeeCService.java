/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantEmployee;

/**
 * merchantService
 * @author ly
 * @version V1.0
 */
public interface MerchantEmployeeCService{

	public MerchantEmployee get(String id);
	
	public List<MerchantEmployee> findList(MerchantEmployee merchantEmployee);
	
	public Page<MerchantEmployee> findPage(Page<MerchantEmployee> page, MerchantEmployee merchantEmployee);
	
	public void save(MerchantEmployee merchantEmployee, boolean flag);
	
	public void delete(MerchantEmployee merchantEmployee);
	
}