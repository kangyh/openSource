package com.heepay.manage.modules.monitors.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.monitors.entity.InfoGroup;

/***
 * 
* 
* 描    述：组管理
*
* 创 建 者： wangl
* 创建时间：  20 Mar 201714:17:22
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
public interface InfoGroupDao  extends CrudDao<InfoGroup>{

	/**
	 * 
	 * @方法说明：删除
	 * @时间：20 Mar 201714:49:53
	 * @创建人：wangl
	 */
    int deleteEntity(Integer groupId);

    /**
	 * 
	 * @方法说明：保存
	 * @时间：20 Mar 201714:49:53
	 * @创建人：wangl
	 */
    int saveEntity(InfoGroup record);
    /**
	 * 
	 * @方法说明：查询
	 * @时间：20 Mar 201714:49:53
	 * @创建人：wangl
	 */
    InfoGroup getEntity(Integer groupId);
    /**
	 * 
	 * @方法说明：更新
	 * @时间：20 Mar 201714:49:53
	 * @创建人：wangl
	 */
    int update(InfoGroup record);
    /**
	 * 
	 * @方法说明：更新
	 * @时间：20 Mar 201714:49:53
	 * @创建人：wangl
	 */
    int updateEntity(InfoGroup record);

	/**
	 * @方法说明：批量查询
	 * @时间：20 Mar 201717:43:26
	 * @创建人：wangl
	 */
	List<InfoGroup> getList(InfoGroup infoGroup);

	/**
	 * @方法说明：验重
	 * @时间：21 Mar 201711:31:47
	 * @创建人：wangl
	 */
	int countNum(String groupCode);
}