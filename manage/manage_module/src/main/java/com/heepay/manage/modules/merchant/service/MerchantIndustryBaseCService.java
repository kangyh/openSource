package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;

public interface MerchantIndustryBaseCService {

  public MerchantIndustryBase get(String id);
  
  public List<MerchantIndustryBase> findList(MerchantIndustryBase merchantIndustryBase);
  
  public Page<MerchantIndustryBase> findPage(Page<MerchantIndustryBase> page, MerchantIndustryBase merchantIndustryBase);
  
  public void save(MerchantIndustryBase merchantIndustryBase, boolean flag);
  
  public void delete(MerchantIndustryBase merchantIndustryBase);

  public List<MerchantIndustryBase> getIndustry();

  public List<MerchantIndustryBase> industryChild(String id);

  public List<MerchantIndustryBase> industryMcc(String id);
  
  public MerchantIndustryBase getMcc(String id);
}
