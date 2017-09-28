package com.heepay.manage.modules.monitor.java.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.monitor.java.entity.MonitorMachine;

/***
 * 
* 
* 描    述：
*
* 创 建 者： wangl
* 创建时间：  2017年1月20日上午10:06:17
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
public interface MonitorMachineDao  extends CrudDao<MonitorMachine>{

	/**
	 * 
	 * @方法说明：
	 * @时间：2017年1月20日上午10:38:42
	 * @创建人：wangl
	 */
    int deleteEntity(Integer machineid);

    /**
	 * 
	 * @方法说明：
	 * @时间：2017年1月20日上午10:38:42
	 * @创建人：wangl
	 */
    int save(MonitorMachine record);

    /**
	 * 
	 * @方法说明：
	 * @时间：2017年1月20日上午10:38:42
	 * @创建人：wangl
	 */
    int saveEntity(MonitorMachine record);


    /**
	 * 
	 * @方法说明：
	 * @时间：2017年1月20日上午10:38:42
	 * @创建人：wangl
	 */
    MonitorMachine getEntity(Integer machineid);
    MonitorMachine getEntityById(int machineid);
    List<MonitorMachine> getAll();


    /**
	 * 
	 * @方法说明：
	 * @时间：2017年1月20日上午10:38:42
	 * @创建人：wangl
	 */
    int update(MonitorMachine record);

    /**
	 * 
	 * @方法说明：
	 * @时间：2017年1月20日上午10:38:42
	 * @创建人：wangl
	 */
    int updateEntity(MonitorMachine record);

	/**
	 * @方法说明：根据id查询组类型编号是否存在
	 * @时间：2017年1月22日上午9:03:54
	 * @创建人：wangl
	 */
	int getEntityByMachineGroupId(Integer machineGroupId);
}