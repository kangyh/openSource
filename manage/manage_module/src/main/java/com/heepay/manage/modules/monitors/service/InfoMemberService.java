package com.heepay.manage.modules.monitors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.monitors.dao.InfoMemberDao;
import com.heepay.manage.modules.monitors.entity.InfoMember;

/***
 * 
 * 
 * 描    述：成员管理
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午4:09:20
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
@Service
@Transactional(readOnly = false)
public class InfoMemberService extends CrudService<InfoMemberDao, InfoMember> {

	@Autowired
	private InfoMemberDao infoMemberDao;

	/**
	 * @方法说明：更新操作
	 * @时间：20 Mar 201717:02:13
	 * @创建人：wangl
	 */
	public int updateEntity(InfoMember infoMember) {
		int updateEntity = 0;
		try {
			updateEntity = infoMemberDao.updateEntity(infoMember);
		} catch (Exception e) {
		}
		return updateEntity;
	}

	/**
	 * @方法说明：入库
	 * @时间：20 Mar 201717:02:18
	 * @创建人：wangl
	 */
	public int saveEntity(InfoMember infoMember) {
		int saveEntity = 0;
		try { 
			saveEntity = infoMemberDao.saveEntity(infoMember);
		} catch (Exception e) {
		}
		return saveEntity;
	}

	/**
	 * @方法说明：查询
	 * @时间：20 Mar 201717:03:14
	 * @创建人：wangl
	 */
	public InfoMember getEntity(Integer memberId) {
		InfoMember selectEntity = infoMemberDao.selectEntity(memberId);
		return selectEntity;
	}

	/**
	 * @方法说明：删除
	 * @时间：20 Mar 201717:03:29
	 * @创建人：wangl
	 */
	public int delete(Integer memberId) {
		int deleteEntity = 0;
		try {
			deleteEntity = infoMemberDao.deleteEntity(memberId);
		} catch (Exception e) {
		}
		return deleteEntity;
	}

	/**
	 * @方法说明：关联查询
	 * @时间：21 Mar 201710:12:23
	 * @创建人：wangl
	 */
	public List<InfoMember> getEntityByGroupId(Integer groupId) {
		
		return infoMemberDao.getEntityByGroupId(groupId);
	}
}
