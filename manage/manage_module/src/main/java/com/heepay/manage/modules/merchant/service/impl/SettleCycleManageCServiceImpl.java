/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.SettleCycleManageDao;
import com.heepay.manage.modules.merchant.service.SettleCycleManageCService;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;

/**
 *
 * 描    述：结算周期管理
 *
 * 创 建 者：ly
 * 创建时间：2016-08-23
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：ljh
 * 审核时间：2016-09-01
 * 审核描述：41/45/49如果直接使用父类方法，子类中可不用重写，直接调用父类。本类中所有调用super的方法可不用重写；
 * 			 删除无用注释代码
 *
 */
@Service
@Transactional(readOnly = true)
public class SettleCycleManageCServiceImpl extends CrudService<SettleCycleManageDao, SettleCycleManage> implements SettleCycleManageCService{

  @Autowired
  private SettleCycleManageDao settleCycleManageDao;

  @Transactional(readOnly = false)
  public void save(SettleCycleManage settleCycleManage, boolean flag) {
    super.save(settleCycleManage, flag);
//    //写费率(商家产品)
//    RedisUtil.getRedisUtil().saveMerchantProductRedis(settleCycleManage.getMerchantId(),
//        settleCycleManage.getProductCode(), false, false);
  }
  
	@Transactional(readOnly = false)
  public void status(SettleCycleManage settleCycleManage) {
    settleCycleManageDao.status(settleCycleManage);
  }

  public SettleCycleManage exsit(SettleCycleManage settleCycleManage) {
    return settleCycleManageDao.exsit(settleCycleManage);
  }
	
}