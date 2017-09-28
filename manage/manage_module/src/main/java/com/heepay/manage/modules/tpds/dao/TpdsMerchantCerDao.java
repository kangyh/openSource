package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsBankCer;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantCer;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:27:24
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
public interface TpdsMerchantCerDao extends CrudDao<TpdsMerchantCer>{
   
	/**
	 * 
	 * @方法说明：保存入库
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int saveEntity(TpdsMerchantCer record);
    
    
    /**
	 * 
	 * @方法说明：更新对象
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int updateEntity(TpdsMerchantCer record);
    
    
    /**
	 * @方法说明：根据id查询对象
	 * @时间：9 Feb 201713:06:20
	 * @创建人：wangl
	 */
    TpdsMerchantCer getEntityById(TpdsMerchantCer tpdsBankId);


	/**
	 * @方法说明：
	 * @时间：18 Feb 201716:22:15
	 * @创建人：wangl
	 */
	TpdsMerchantCer getEntityById(Integer tpdsBankId);


	/**
	 * @方法说明：
	 * @时间：18 Feb 201716:22:22
	 * @创建人：wangl
	 */
	int saveEntity(TpdsBankCer tpdsBankCer);


	/**
	 * @方法说明：
	 * @时间：18 Feb 201716:22:27
	 * @创建人：wangl
	 */
	int changeValue(String merchantNo);


	/**
	 * @方法说明：
	 * @时间：18 Feb 201716:22:33
	 * @创建人：wangl
	 */
	int deleteEntity(Integer tpdsBankId);
}