/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;

/**
 * 商家费率DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantRateNewDao extends CrudDao<MerchantRateNew> {

  void status(MerchantRateNew merchantRate);
  
  MerchantRateNew exist(MerchantRateNew merchantRate);
  
  MerchantRateNew existDefault(MerchantRateNew merchantRate);
  
  MerchantRateNew existNoId(MerchantRateNew merchantRate);

  MerchantRateNew existNoIdForCheck(MerchantRateNew merchantRateNew);

  List<MerchantRateNew> findPageOnly(MerchantRateNew merchantRate);
  
  List<MerchantRateNew> findPageNotDefaulOnly(MerchantRateNew merchantRate);

  List<MerchantRateNew> getDefault(MerchantRateNew merchantRate);

  List<MerchantRateNew> findListDefault(MerchantRateNew merchantRateNew);

  List<MerchantRateNew> findListMerchant(MerchantRateNew merchantRate);

  MerchantRateNew existNoBank(MerchantRateNew merchantRateNew);
    
  List<MerchantRateNew> findMerchantProductAndRate(String merchantId);

  /**
   * @方法说明：merchant_rate_bank_check和merchant_product_rate_check组合查询
   * @时间： 2017-08-08 11:14
   * @创建人：wangl
   */
  List<MerchantRateNew> checkFindList(MerchantRateNew merchantRate);

  MerchantRateNew existNoBankCheck(MerchantRateNew merchantRateNew);

}