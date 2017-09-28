/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantOperationLogDao;
import com.heepay.manage.modules.merchant.service.MerchantOperationLogCService;
import com.heepay.manage.modules.merchant.vo.MerchantOperationLog;

/**
 *
 * 描    述：商户
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
 * 审核描述：如果直接使用父类方法，子类中可不用重写，直接调用父类。本类中所有调用super的方法可不用重写
 *
 */
@Service
@Transactional(readOnly = true)
public class MerchantOperationLogCServiceImpl extends CrudService<MerchantOperationLogDao, MerchantOperationLog> implements MerchantOperationLogCService {
  
}