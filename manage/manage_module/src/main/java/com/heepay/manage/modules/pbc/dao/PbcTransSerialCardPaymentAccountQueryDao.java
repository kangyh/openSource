package com.heepay.manage.modules.pbc.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcTransSerialCardPaymentAccountQuery;

/***
 * 
* 
* 描    述：
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:21:05 PM
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
public interface PbcTransSerialCardPaymentAccountQueryDao  extends CrudDao<PbcTransSerialCardPaymentAccountQuery>{

	/**
	 * 插入
	 */
	int save(PbcTransSerialCardPaymentAccountQuery record);

	/**
	 * 插入默认使用这个
	 */
	int saveEntity(PbcTransSerialCardPaymentAccountQuery record);

	 /**
     * 更新
     */
	int update(PbcTransSerialCardPaymentAccountQuery record);

	/**
	 * 更新默认使用这个
	 */
	int updateEntity(PbcTransSerialCardPaymentAccountQuery record);

	/**
	 * 
	 * @方法说明：根据id查询对象
	 * @时间：Dec 16, 2016
	 * @创建人：wangl
	 */
	PbcTransSerialCardPaymentAccountQuery getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:51:05
	 * @创建人：wangdong
	 */
	PbcTransSerialCardPaymentAccountQuery getQueryInfoId(String string);
}