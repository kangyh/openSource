/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantOperationLog;

/**
 * merchantService
 * @author ly
 * @version V1.0
 */
public interface MerchantOperationLogCService{

	public MerchantOperationLog get(String id);
	
	public List<MerchantOperationLog> findList(MerchantOperationLog merchantOperationLog);
	
	public Page<MerchantOperationLog> findPage(Page<MerchantOperationLog> page, MerchantOperationLog merchantOperationLog);
	
	public void save(MerchantOperationLog merchantOperationLog, boolean flag);
	
	public void delete(MerchantOperationLog merchantOperationLog);
	
}