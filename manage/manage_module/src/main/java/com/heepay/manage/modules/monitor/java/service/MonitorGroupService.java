package com.heepay.manage.modules.monitor.java.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.monitor.java.dao.MonitorGroupDao;
import com.heepay.manage.modules.monitor.java.entity.MonitorGroup;

/***
 * 
 * 
 * 描    述：
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
public class MonitorGroupService extends CrudService<MonitorGroupDao, MonitorGroup> {

	//日志打印
	private static final Logger log = LogManager.getLogger();
		
	@Autowired
	private MonitorGroupDao monitorGroupDao;
	

	/**
	 * @方法说明：显示分页
	 * @author wangl
	 * @时间：2016年11月18日17:27:53
	 */
	public List<MonitorGroup> findList(MonitorGroup monitorGroup) {
		
		return super.findList(monitorGroup);
	}
	
	/**
	 * @方法说明：
	 * @时间：2017年1月20日下午12:45:50
	 * @创建人：wangl
	 */
	public Model findListPage(Page<MonitorGroup> page,MonitorGroup monitorGroup,Model model) {
		
		log.info("组信息查询开始--->{条件}"+monitorGroup);
		Page<MonitorGroup> pageMonitorGroup = findPage(page, monitorGroup);
		
		model.addAttribute("page",pageMonitorGroup);
		model.addAttribute("monitorGroup",monitorGroup);
		log.info("组信息查询开始--->{结束}");
		return model;
	}

	/**
	 * @方法说明：
	 * @时间：2017年1月20日下午3:25:36
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteEntity(Integer groupId) {
		
		return monitorGroupDao.deleteEntity(groupId);
	}

	/**
	 * @方法说明：根据id查对象
	 * @时间：2017年1月20日下午3:33:29
	 * @创建人：wangl
	 */
	public MonitorGroup getEntityById(Integer groupId) {
		
		return monitorGroupDao.getEntityById(groupId);
	}

	/**
	 * @方法说明：修改方法
	 * @时间：2017年1月20日下午3:56:35
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(MonitorGroup monitorGroup) {
		
		return monitorGroupDao.updateEntity(monitorGroup);
	}

	/**
	 * @方法说明：新增保存入库
	 * @时间：2017年1月20日下午3:59:39
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(MonitorGroup monitorGroup) {
		monitorGroupDao.saveEntity(monitorGroup);
	}

	/**
	 * @方法说明：查询所有数据
	 * @时间：2017年1月20日下午5:06:18
	 * @创建人：wangl
	 */
	public List<MonitorGroup> getList() {
		
		return monitorGroupDao.getList();
	}
}
