package com.heepay.manage.modules.monitor.java.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.monitor.java.entity.MonitorGroup;

/***
 * 
* 
* 描    述：监控配置表
*
* 创 建 者： wangl
* 创建时间：  2017年1月20日上午10:02:18
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
public interface MonitorGroupDao  extends CrudDao<MonitorGroup>{

	/**
	 * 
	 * @方法说明：删除
	 * @时间：2017年1月20日上午10:35:33
	 * @创建人：wangl
	 */
    int deleteEntity(Integer groupid);

    /**
	 * 
	 * @方法说明：保存
	 * @时间：2017年1月20日上午10:35:33
	 * @创建人：wangl
	 */
    int save(MonitorGroup record);

    /**
	 * 
	 * @方法说明：保存
	 * @时间：2017年1月20日上午10:35:33
	 * @创建人：wangl
	 */
    int saveEntity(MonitorGroup record);


    /**
	 * 
	 * @方法说明：更加id获取对象
	 * @时间：2017年1月20日上午10:35:33
	 * @创建人：wangl
	 */
    MonitorGroup getEntityById(Integer groupid);

    /**
	 * 
	 * @方法说明：更新
	 * @时间：2017年1月20日上午10:35:33
	 * @创建人：wangl
	 */
    int update(MonitorGroup record);

    /**
	 * 
	 * @方法说明：更新
	 * @时间：2017年1月20日上午10:35:33
	 * @创建人：wangl
	 */
    int updateEntity(MonitorGroup record);

	/**
	 * @方法说明：
	 * @时间：2017年1月20日下午5:06:48
	 * @创建人：wangl
	 */
	List<MonitorGroup> getList();
}