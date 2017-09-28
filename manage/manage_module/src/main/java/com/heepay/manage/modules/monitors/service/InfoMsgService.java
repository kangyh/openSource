package com.heepay.manage.modules.monitors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.monitors.dao.InfoMsgDao;
import com.heepay.manage.modules.monitors.entity.InfoMsg;

/***
 * 
 * 
 * 描    述：消息管理
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
public class InfoMsgService extends CrudService<InfoMsgDao, InfoMsg> {

	@Autowired
	private InfoMsgDao infoMsgDao;

	/**
	 * @方法说明：查询对象
	 * @时间：21 Mar 201710:10:03
	 * @创建人：wangl
	 */
	public InfoMsg selectEntity(Integer msgId) {
		
		return infoMsgDao.selectEntity(msgId);
	}

	/**
	 * @方法说明：更新
	 * @时间：21 Mar 201710:27:27
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int update(InfoMsg info) {
		int update = 0;
		try {
			update = infoMsgDao.update(info);
		} catch (Exception e) {
		}
		return update;
	}

	
}
