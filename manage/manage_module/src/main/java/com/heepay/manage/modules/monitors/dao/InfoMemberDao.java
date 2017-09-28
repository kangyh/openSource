package com.heepay.manage.modules.monitors.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.monitors.entity.InfoMember;


/***
 * 
* 
* 描    述：成员管理
*
* 创 建 者： wangl
* 创建时间：  20 Mar 201714:19:07
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
public interface InfoMemberDao extends CrudDao<InfoMember>{

	/**
	 * 
	 * @方法说明：删除
	 * @时间：20 Mar 201714:50:28
	 * @创建人：wangl
	 */
    int deleteEntity(Integer memberId);

	/**
	 * 
	 * @方法说明：保存
	 * @时间：20 Mar 201714:50:28
	 * @创建人：wangl
	 */
    int saveEntity(InfoMember record);

	/**
	 * 
	 * @方法说明：查询
	 * @时间：20 Mar 201714:50:28
	 * @创建人：wangl
	 */
    InfoMember selectEntity(Integer memberId);

	/**
	 * 
	 * @方法说明：更新
	 * @时间：20 Mar 201714:50:28
	 * @创建人：wangl
	 */
    int updateEntity(InfoMember record);

	/**
	 * 
	 * @方法说明：更新
	 * @时间：20 Mar 201714:50:28
	 * @创建人：wangl
	 */
    int update(InfoMember record);

	/**
	 * @方法说明：关联查询
	 * @时间：21 Mar 201710:13:02
	 * @创建人：wangl
	 */
    List<InfoMember> getEntityByGroupId(Integer groupId);
}