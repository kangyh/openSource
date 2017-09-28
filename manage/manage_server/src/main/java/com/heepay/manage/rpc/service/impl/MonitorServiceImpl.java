/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.manage.modules.merchant.dao.ManageMonitorDao;
import com.heepay.manage.rpc.service.MonitorService;
import com.heepay.rpc.service.RpcService;

/**
 *
 * 描述：监控服务service
 *
 * 创建者  B.HJ
 * 创建时间 2016-12-10-9:58
 * 创建描述：监控服务service
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
@RpcService(name = "monitorServiceImpl", processor = MonitorService.Processor.class)
public class MonitorServiceImpl implements MonitorService.Iface{

    @Autowired
    private ManageMonitorDao monitorDao;

    @Override
    public boolean available() throws TException {
        return monitorDao.getMonitor("9003") == null ? false : true;
    }
}
