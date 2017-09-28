package com.heepay.manage.modules.monitors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.monitors.dao.InfoGroupDao;
import com.heepay.manage.modules.monitors.entity.InfoGroup;

/***
 * 
 * 
 * 描    述：组管理
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
@Transactional(readOnly = true)
public class InfoGroupService extends CrudService<InfoGroupDao, InfoGroup> {

	@Autowired
	private InfoGroupDao infoGroupDao;

	/**
	 * @方法说明：获取对象
	 * @时间：20 Mar 201716:06:24
	 * @创建人：wangl
	 */
	public InfoGroup getEntity(Integer groupId) {
		
		return infoGroupDao.getEntity(groupId);
	}

	/**
	 * @方法说明：更新操作
	 * @时间：20 Mar 201716:31:03
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(InfoGroup infoGroup) {
		
		int updateEntity = 0;
		try {
			updateEntity = infoGroupDao.updateEntity(infoGroup);
		} catch (Exception e) {
		}
	
		return updateEntity;
	}

	/**
	 * @方法说明：入库
	 * @时间：20 Mar 201716:34:31
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntity(InfoGroup infoGroup) {
		
		int saveEntity = 0;
		try {
			saveEntity = infoGroupDao.saveEntity(infoGroup);
		} catch (Exception e) {
		}
		return saveEntity;
	}

	/**
	 * @方法说明：删除操作
	 * @时间：20 Mar 201716:51:14
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int delete(Integer groupId) {
		int deleteEntity = 0;
		try {
			deleteEntity = infoGroupDao.deleteEntity(groupId);
		} catch (Exception e) {
		}
		return deleteEntity;
	}

	/**
	 * @方法说明：批量查询
	 * @时间：20 Mar 201717:43:04
	 * @创建人：wangl
	 */
	public List<InfoGroup> getList(InfoGroup infoGroup) {
		
		return infoGroupDao.getList(infoGroup);
	}

	/**
	 * @方法说明：验重
	 * @时间：21 Mar 201711:31:11
	 * @创建人：wangl
	 */
	public int countNum(String groupCode) {
		
		int num = 0;
		try {
			num = infoGroupDao.countNum(groupCode);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return num;
	}
}
