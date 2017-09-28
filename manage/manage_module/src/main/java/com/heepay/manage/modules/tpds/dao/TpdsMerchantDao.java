package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsMerchant;


/***
 * 
* 
* 描    述：商户信息表
*
* 创 建 者： wangl
* 创建时间：  9 Feb 201710:16:13
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
public interface TpdsMerchantDao extends CrudDao<TpdsMerchant> {

	/**
	 * 
	 * @方法说明：根据主键删除
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int deleteEntity(Integer merchantId);

    /**
	 * 
	 * @方法说明：保存入库
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int save(TpdsMerchant record);

    /**
	 * 
	 * @方法说明：保存入库
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int saveEntity(TpdsMerchant record);


    /**
     * 
     * @方法说明：根据条件查询这个对象
     * @时间：9 Feb 201710:33:09
     * @创建人：wangl
     */
    TpdsMerchant selectEntity(TpdsMerchant record);

    /**
	 * 
	 * @方法说明：更新对象
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int update(TpdsMerchant record);

    /**
	 * 
	 * @方法说明：更新对象
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int updateEntity(TpdsMerchant record);

	/**
	 * @方法说明：根据id查询对象
	 * @时间：9 Feb 201713:06:20
	 * @创建人：wangl
	 */
	TpdsMerchant getEntityById(Integer merchantId);
}