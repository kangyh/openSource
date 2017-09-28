/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.MerchantUnionpayAreaBaseDao;
import com.heepay.manage.modules.merchant.service.MerchantUnionpayAreaBaseCService;
import com.heepay.manage.modules.merchant.vo.MerchantUnionpayAreaBase;

/**
 * 银联地区编码基础数据Service
 * @author ly
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class MerchantUnionpayAreaBaseCServiceImpl extends CrudService<MerchantUnionpayAreaBaseDao, MerchantUnionpayAreaBase> implements MerchantUnionpayAreaBaseCService{

  @Autowired
  private MerchantUnionpayAreaBaseDao merchantUnionpayAreaBaseDao;
  
  @Override
  public List<MerchantUnionpayAreaBase> UnionpayP() {
    return merchantUnionpayAreaBaseDao.UnionpayP();
  }

  @Override
  public List<MerchantUnionpayAreaBase> UnionpayCity(String id) {
    return merchantUnionpayAreaBaseDao.UnionpayCity(id);
  }
	
}