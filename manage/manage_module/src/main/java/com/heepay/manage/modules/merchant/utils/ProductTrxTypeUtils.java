/**
 *  
 */
package com.heepay.manage.modules.merchant.utils;

import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.merchant.dao.ProductTrxTypeDao;
import com.heepay.manage.modules.merchant.vo.ProductTrxType;

/**
 * 产品交易类型工具类
 *  
 * @version 2014-11-7
 */
public class ProductTrxTypeUtils {
	
	private static ProductTrxTypeDao productTrxTypeDao = SpringContextHolder.getBean(ProductTrxTypeDao.class);

	public static String getProductTrxTypeName(String code){
		ProductTrxType productTrxType = productTrxTypeDao.getByCode(code);
		if(null != productTrxType){
			return productTrxType.getName();
		}
		return "";
	}

}
