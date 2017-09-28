package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;

public interface MerchantBankCardCService {

  public MerchantBankCard get(String id);
  
  public List<MerchantBankCard> findList(MerchantBankCard merchantBankCard);
  
  public Page<MerchantBankCard> findPage(Page<MerchantBankCard> page, MerchantBankCard merchantBankCard);
  
  public void save(MerchantBankCard merchantBankCard, boolean flag);
  
  public void delete(MerchantBankCard merchantBankCard);
  
  public void status(MerchantBankCard merchantBankCard);
  
  public MerchantBankCard getMerchant(String merchantId);
   
  public void statusCard(MerchantBankCard merchantBankCard);
  
  public void updateBankNo(MerchantBankCard merchantBankCard);
}
