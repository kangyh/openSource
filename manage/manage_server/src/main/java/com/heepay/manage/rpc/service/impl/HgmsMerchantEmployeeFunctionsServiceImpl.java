package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.hgms.dao.HgmsMerchantEmployeeFunctionsDao;
import com.heepay.manage.modules.hgms.dao.HgmsMerchantFuctionsDao;
import com.heepay.manage.rpc.service.HgmsMerchantEmployeeFunctionsService;
import com.heepay.manage.rpc.service.HgmsMerchantEmployeeFunctionsThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 描    述：商户员工功能表
 * <p>
 * 创 建 者： guozx@9186.com
 * 创建时间： 2017-08-07 09:45:30
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Component
@RpcService(name = "hgmsMerchantEmployeeFunctionsServiceImpl", processor = HgmsMerchantEmployeeFunctionsService.Processor.class)
public class HgmsMerchantEmployeeFunctionsServiceImpl implements HgmsMerchantEmployeeFunctionsService.Iface {

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private HgmsMerchantEmployeeFunctionsDao merchantEmployeeFunctionsDao;
    @Autowired
    private HgmsMerchantFuctionsDao merchantFuctionsDao;

    @Override
    public HgmsMerchantEmployeeFunctionsThrift get(String arg0) throws TException {
        return null;
    }

    @Override
    public String save(HgmsMerchantEmployeeFunctionsThrift arg0) throws TException {
        return null;
    }

    @Override
    public List<String> findList(HgmsMerchantEmployeeFunctionsThrift arg0) throws TException {
        long employeeId = arg0.getEmployeeId();
        List<String> list = new ArrayList<>();
        List<String> numberList = merchantEmployeeFunctionsDao.queryPermissionByEmployeeId(employeeId);
        for (String str : numberList) {
            String permission = merchantFuctionsDao.queryPermissionCodeById(Integer.valueOf(str));
            list.add(permission);
        }
        logger.info("员工ID为：{}，权限列表：{}", employeeId, list.toString());
        return list;
    }

}
