/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantUnionpayAreaBase;

/**
 * 银联地区编码基础数据DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantUnionpayAreaBaseDao extends CrudDao<MerchantUnionpayAreaBase> {

  List<MerchantUnionpayAreaBase> UnionpayP();

  List<MerchantUnionpayAreaBase> UnionpayCity(String id);
	
}