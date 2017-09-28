package com.heepay.manage.modules.merchant.dao;

import org.apache.ibatis.annotations.Param;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.Monitor;

@MyBatisDao
public interface ManageMonitorDao extends CrudDao<Monitor>{
    
    Monitor getMonitor(@Param(value="portId")String portId);
}
