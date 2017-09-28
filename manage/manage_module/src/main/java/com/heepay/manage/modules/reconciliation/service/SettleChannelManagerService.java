package com.heepay.manage.modules.reconciliation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelManagerDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;

/***
 * 
 * 
 * 描    述：对账参数批量查询
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
public class SettleChannelManagerService extends CrudService<SettleChannelManagerDao, SettleChannelManager> {

	@Autowired
	SettleChannelManagerDao settleChannelManagerDao;
	
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> findList(SettleChannelManager settleChannelManager) {
		return super.findList(settleChannelManager);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(SettleChannelManager settleChannelManager) {
		return settleChannelManagerDao.findListExcel(settleChannelManager);
	}
	
	/**
	 * 
	 * @方法说明：//更新时的查询
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public SettleChannelManager getEntity(int channelManangeId) {
		SettleChannelManager entity = settleChannelManagerDao.getEntity(channelManangeId);
		return entity;
	}
	
	/**
	 * 
	 * @方法说明：//删除操作
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void deleteEntity(int channelManangeId) {
		settleChannelManagerDao.deleteEntity(channelManangeId);
	}
	
	/**
	 * 
	 * @方法说明：根据参数查该对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(SettleChannelManager settleChannelManager) {
		settleChannelManagerDao.saveEntity(settleChannelManager);
	}
	
	/**
	 * 
	 * @方法说明：根据参数查该对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void addEntity(SettleChannelManager settleChannelManager) {
		settleChannelManagerDao.addEntity(settleChannelManager);
	}
	
	/**
	 * 
	 * @方法说明：查询所有记录List
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> getChannel() {
		return settleChannelManagerDao.getChannel();
	}


	/**
	 *
	 * @方法说明：更新操作
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(SettleChannelManager settleChannelManager) {
		settleChannelManagerDao.updateEntity(settleChannelManager);
	}

	/**
	 *
	 * @方法说明：删除操作
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void deleteEntityById(Integer channelManangeId) {

		settleChannelManagerDao.deleteEntityById(channelManangeId);
	}

	/**
	 * 
	 * @方法说明：将用户名和密码加密更新
	 * @author chenyanming
	 * @param username 
	 * @param username
	 * @param password
	 * @时间：2017年7月26日下午2:34:20
	 */
	@Transactional(readOnly = false)
	public void updateAll(String remoteUserName, String remotePassword, String username) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("remoteUserName", remoteUserName);
		map.put("remotePassword", remotePassword);
		map.put("userName", username);
		settleChannelManagerDao.updateAll(map);
		
	}
}
