/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;

/**
 * 结算周期DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface SettleCycleManageDao extends CrudDao<SettleCycleManage> {

  void status(SettleCycleManage settleCycleManage);

  SettleCycleManage getByMerchantId(SettleCycleManage settleCycleManageFind);

  SettleCycleManage exsit(SettleCycleManage settleCycleManage);
	
}