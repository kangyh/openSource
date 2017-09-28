/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import java.util.List;

import com.heepay.manage.common.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantIndustryBaseDao;
import com.heepay.manage.modules.merchant.service.MerchantIndustryBaseCService;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;

/**
 * mcc基础数据Service
 * @author ly
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class MerchantIndustryBaseCServiceImpl extends CrudService<MerchantIndustryBaseDao, MerchantIndustryBase> implements MerchantIndustryBaseCService {

  @Autowired
  private MerchantIndustryBaseDao merchantIndustryBaseDao;
  
  @Override
  public List<MerchantIndustryBase> getIndustry() {
    return merchantIndustryBaseDao.getIndustry();
  }

  @Override
  public List<MerchantIndustryBase> industryChild(String id) {
    return merchantIndustryBaseDao.industryChild(id);
  }

  @Override
  public List<MerchantIndustryBase> industryMcc(String id) {
    return merchantIndustryBaseDao.industryMcc(id);
  }

  @Override
  public MerchantIndustryBase getMcc(String id) {
    return merchantIndustryBaseDao.getMcc(id);
  }

  @Override
  @Transactional(readOnly = false)
  public void save(MerchantIndustryBase merchantIndustryBase, boolean flag){
      //根据行业子类类查询行业子类id
      MerchantIndustryBase industryChild = merchantIndustryBaseDao.getIndustryChildId(merchantIndustryBase.getIndustryChildName());
      if(null != industryChild){
          merchantIndustryBase.setIndustryChildId(industryChild.getIndustryChildId());
          merchantIndustryBase.setChildParentId(industryChild.getIndustryChildId());
      }else{
          Integer count = merchantIndustryBaseDao.maxChildId() + Constants.LENGTH;
          merchantIndustryBase.setIndustryChildId(count.toString());
          merchantIndustryBase.setChildParentId(count.toString());
      }
      super.save(merchantIndustryBase,flag);
  }
}