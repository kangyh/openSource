package com.heepay.manage.modules.monitors.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.monitors.entity.InfoMsg;



/***
 * 
* 
* 描    述：消息管理
*
* 创 建 者： wangl
* 创建时间：  20 Mar 201714:19:17
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
public interface InfoMsgDao extends CrudDao<InfoMsg>{

	/**
	 * 
	 * @方法说明：删除
	 * @时间：20 Mar 201714:49:11
	 * @创建人：wangl
	 */
    int deleteEntity(Integer msgId);

    /**
	 * 
	 * @方法说明：保存
	 * @时间：20 Mar 201714:49:11
	 * @创建人：wangl
	 */
    int saveEntity(InfoMsg record);

    /**
	 * 
	 * @方法说明：查询
	 * @时间：20 Mar 201714:49:11
	 * @创建人：wangl
	 */
    InfoMsg selectEntity(Integer msgId);

    /**
	 * 
	 * @方法说明：更新
	 * @时间：20 Mar 201714:49:11
	 * @创建人：wangl
	 */
    int update(InfoMsg record);

    /**
	 * 
	 * @方法说明：更新
	 * @时间：20 Mar 201714:49:11
	 * @创建人：wangl
	 */
    int updateEntity(InfoMsg record);
}