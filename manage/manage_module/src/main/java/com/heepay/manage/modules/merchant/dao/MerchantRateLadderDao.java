/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantRateLadder;

/**
 * merchantDAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantRateLadderDao extends CrudDao<MerchantRateLadder> {
	
}