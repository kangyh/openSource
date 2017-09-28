package com.heepay.manage.modules.pbc.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountQuery;

/***
 * 
* 
* 描    述：查询信息主键
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:20:26 PM
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
public interface PbcPaymentAccountQueryDao  extends CrudDao<PbcPaymentAccountQuery>{

	/**
	 * 插入
	 */
	int save(PbcPaymentAccountQuery record);

	/**
	 * 插入默认使用这个
	 */
	int saveEntity(PbcPaymentAccountQuery record);

	 /**
     * 更新
     */
	int update(PbcPaymentAccountQuery record);

	/**
	 * 更新默认使用这个
	 */
	int updateEntity(PbcPaymentAccountQuery record);

	/**
	 * 
	 * @方法说明：根据id查询关联全支付账号查询
	 * @时间：2016年12月26日 下午3:20:50
	 * @创建人：wangdong
	 */
	PbcPaymentAccountQuery getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:55:21
	 * @创建人：wangdong
	 */
	PbcPaymentAccountQuery getQueryInfoId(String string);
}