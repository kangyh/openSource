package com.heepay.manage.modules.pbc.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcQueryInfo;

/***
 * 
* 
* 描    述：查询信息主表
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:20:33 PM
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
public interface PbcQueryInfoDao  extends CrudDao<PbcQueryInfo>{


	/**
	 * 插入
	 */
    int save(PbcQueryInfo record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcQueryInfo record);

    /**
     * 更新
     */
    int update(PbcQueryInfo record);

    /**
	 * 更新默认使用这个
	 */
    int updateEntity(PbcQueryInfo record);

    /**
     * 
     * @方法说明：根据id获取对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcQueryInfo getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据业务申请编码获取查询主表信息
	 * @时间：2016年12月17日 下午2:59:56
	 * @创建人：wangdong
	 */
	PbcQueryInfo getEntityByApplicationId(String applicationId);
}