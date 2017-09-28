/**
 *  
 */
package com.heepay.manage.modules.merchant.utils;

import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.merchant.dao.MerchantIndustryBaseDao;
import com.heepay.manage.modules.merchant.dao.ProductTrxTypeDao;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;
import com.heepay.manage.modules.merchant.vo.ProductTrxType;

/**
 * 产品交易类型工具类
 *  
 * @version 2014-11-7
 */
public class MccUtils {
	
	private static MerchantIndustryBaseDao merchantIndustryBaseDao = SpringContextHolder.getBean(MerchantIndustryBaseDao.class);

	public static String getMccName(String code){
		MerchantIndustryBase merchantIndustryBase = merchantIndustryBaseDao.getMcc(code);
		if(null != merchantIndustryBase){
			return merchantIndustryBase.getIndustryDescribe();
		}
		return "";
	}

	public static String getIndustryChildName(String code){
		MerchantIndustryBase merchantIndustryBase = merchantIndustryBaseDao.getIndustryChildName(code);
		if(null != merchantIndustryBase){
			return merchantIndustryBase.getIndustryChildName();
		}
		return "";
	}

}
