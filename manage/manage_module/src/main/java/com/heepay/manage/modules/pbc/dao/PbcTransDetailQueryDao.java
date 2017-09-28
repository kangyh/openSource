package com.heepay.manage.modules.pbc.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcTransDetailQuery;

/***
 * 
* 
* 描    述：交易流水号查询银行卡/支付帐号
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:21:00 PM
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
public interface PbcTransDetailQueryDao  extends CrudDao<PbcTransDetailQuery>{


	/**
	 * 插入
	 */
    int save(PbcTransDetailQuery record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcTransDetailQuery record);

    /**
     * 更新
     */
    int update(PbcTransDetailQuery record);

    /**
   	 * 更新默认使用这个
   	 */
    int updateEntity(PbcTransDetailQuery record);

    /**
     * 
     * @方法说明：根据id查询整个对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcTransDetailQuery getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据查询信息主键查询对应类型的信息
	 * @时间：2016年12月17日 下午3:45:50
	 * @创建人：wangdong
	 */
	PbcTransDetailQuery getQueryInfoId(String queryInfoId);
}