/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;

/**
 * 技术签约DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantAutographInfoDao extends CrudDao<MerchantAutographInfo> {
	void status(MerchantAutographInfo merchantAutographInfo);

  MerchantAutographInfo exist(MerchantAutographInfo merchantAutographInfo);

  void resetKey(MerchantAutographInfo merchantAutographInfo);
	
}