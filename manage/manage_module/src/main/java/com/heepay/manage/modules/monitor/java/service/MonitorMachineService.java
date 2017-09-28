package com.heepay.manage.modules.monitor.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.monitor.java.dao.MonitorMachineDao;
import com.heepay.manage.modules.monitor.java.entity.MonitorMachine;

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
public class MonitorMachineService extends CrudService<MonitorMachineDao, MonitorMachine> {

	@Autowired
	private MonitorMachineDao monitorMachineDao;
	

	/**
	 * @方法说明：显示分页
	 * @author wangl
	 * @时间：2016年11月18日17:27:53
	 */
	public List<MonitorMachine> findList(MonitorMachine monitorMachine) {
		return super.findList(monitorMachine);
	}
	public MonitorMachine getEntityById(int machineId)
	{
		return monitorMachineDao.getEntityById(machineId);
	}
	public List<MonitorMachine> getAll()
	{
		return monitorMachineDao.getAll();
	}
	/**
	 * @方法说明：显示分页
	 * @时间：2017年1月20日下午1:33:10
	 * @创建人：wangl
	 */
	public Model findListPage(Page<MonitorMachine> page, MonitorMachine monitorMachine, Model model) {
		
		Page<MonitorMachine> group = super.findPage(page, monitorMachine);
		
		model.addAttribute("monitorMachine",monitorMachine);
		model.addAttribute("page",group);
		return model;
	}


	/**
	 * @方法说明：删除操作
	 * @时间：2017年1月20日下午4:43:27
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteEntity(Integer groupId) {
		
		return monitorMachineDao.deleteEntity(groupId);
	}


	/**
	 * @方法说明：根据主键查询对象
	 * @时间：2017年1月20日下午4:43:44
	 * @创建人：wangl
	 */
	public MonitorMachine getEntityById(Integer groupId) {
		
		return monitorMachineDao.getEntity(groupId);
	}


	/**
	 * @方法说明：更新操作
	 * @时间：2017年1月20日下午4:44:34
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(MonitorMachine monitorMachine) {
		monitorMachineDao.updateEntity(monitorMachine);
	}


	/**
	 * @方法说明：保存入库
	 * @时间：2017年1月20日下午4:44:40
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(MonitorMachine monitorMachine) {
		monitorMachineDao.saveEntity(monitorMachine);
	}


	/**
	 * @方法说明：根据id查询组类型编号是否存在
	 * @时间：2017年1月22日上午9:03:35
	 * @创建人：wangl
	 */
	public int getEntityByMachineGroupId(Integer machineGroupId) {
		
		return monitorMachineDao.getEntityByMachineGroupId(machineGroupId);
	}
}
