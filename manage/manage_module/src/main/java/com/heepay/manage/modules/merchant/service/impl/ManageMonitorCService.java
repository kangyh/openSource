package com.heepay.manage.modules.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.enums.AuthenticationStatus;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.ManageMonitorDao;
import com.heepay.manage.modules.merchant.vo.Monitor;

@Service
public class ManageMonitorCService extends CrudService<ManageMonitorDao, Monitor>{
    
    @Autowired
    private ManageMonitorDao monitorDao;
    
    public boolean getMonitor(){
        String portId = "9003";
        Monitor monitor = monitorDao.getMonitor(portId);
        if(null != monitor){
            if(AuthenticationStatus.SUCCESS.getValue().equals(monitor.getValue())){
                return true;
            }
        }
        return false;
    }
    
}
