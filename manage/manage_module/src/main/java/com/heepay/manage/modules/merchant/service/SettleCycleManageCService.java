/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;

/**
 * 结算周期Service
 * @author ly
 * @version V1.0
 */
public interface SettleCycleManageCService {

	public SettleCycleManage get(String id) ;
	
	public List<SettleCycleManage> findList(SettleCycleManage settleCycleManage);
	
	public Page<SettleCycleManage> findPage(Page<SettleCycleManage> page, SettleCycleManage settleCycleManage);
	
	public void save(SettleCycleManage settleCycleManage, boolean flag);
	
  public void status(SettleCycleManage settleCycleManage);

  public SettleCycleManage exsit(SettleCycleManage settleCycleManage);
	
}