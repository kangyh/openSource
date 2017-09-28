/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardDao;
import com.heepay.manage.modules.merchant.service.MerchantBankCardCService;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;

/**
 *
 * 描    述：merchantBankCardCService
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
public class MerchantBankCardCServiceImpl extends CrudService<MerchantBankCardDao, MerchantBankCard> implements MerchantBankCardCService {
  @Autowired
  private MerchantBankCardDao merchantBankCardDao;
  
  @Transactional(readOnly = false)
  public void status(MerchantBankCard merchantBankCard){
    merchantBankCardDao.status(merchantBankCard);
  }

  public MerchantBankCard getMerchant(String merchantId) {
    return merchantBankCardDao.getMerchant(merchantId);
  }

  @Transactional(readOnly = false)
  public void statusCard(MerchantBankCard merchantBankCard) {
    merchantBankCardDao.statusCard(merchantBankCard);
  }

  @Transactional(readOnly = false)
  public void updateBankNo(MerchantBankCard merchantBankCard) {
    merchantBankCardDao.updateBankNo(merchantBankCard);
  }
}