package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsCutDay;
import com.heepay.manage.modules.tpds.entity.TpdsMerchant;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantAccount;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:27:20
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
@MyBatisDao
public interface TpdsMerchantAccountDao extends CrudDao<TpdsMerchantAccount>{

	TpdsMerchantAccount getEntityById(Integer merchantId);

	int saveEntity(TpdsMerchantAccount record);

	int updateEntity(TpdsMerchantAccount record);

	TpdsMerchantAccount selectByMerchantNo(String merchantNo);

	TpdsMerchantAccount selectBySystemNo(String systemNo);
    
}