/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;

/**
 * mcc基础数据DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantIndustryBaseDao extends CrudDao<MerchantIndustryBase> {

  List<MerchantIndustryBase> getIndustry();

  List<MerchantIndustryBase> industryChild(String id);

  List<MerchantIndustryBase> industryMcc(String id);
  
  MerchantIndustryBase getMcc(String id);

  MerchantIndustryBase getIndustryChildName(String id);

  MerchantIndustryBase getIndustryChildId(String industryChildName);

  Integer maxChildId();
}