/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;

/**
 * 商户打款认证DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantBankCardAuthenticationDao extends CrudDao<MerchantBankCardAuthentication> {

  void status(MerchantBankCardAuthentication merchantBankCardAuthentication);
  
  void statusAut(MerchantBankCardAuthentication merchantBankCardAuthentication);
  
  MerchantBankCardAuthentication getByMerchantId(MerchantBankCardAuthentication merchantBankCardAuthentication);

}