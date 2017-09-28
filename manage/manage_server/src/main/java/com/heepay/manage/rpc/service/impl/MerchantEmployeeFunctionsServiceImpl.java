package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.merchant.dao.MerchantEmployeeFunctionsDao;
import com.heepay.manage.modules.merchant.dao.MerchantFuctionsDao;
import com.heepay.manage.rpc.service.MerchantEmployeeFunctionsService;
import com.heepay.manage.rpc.service.MerchantEmployeeFunctionsThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 名称：商户员工功能表
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-08-30-17:13
 * 创建描述：商户员工功能表
 */
@Component
@RpcService(name = "merchantEmployeeFunctionsCService", processor = MerchantEmployeeFunctionsService.Processor.class)
public class MerchantEmployeeFunctionsServiceImpl implements MerchantEmployeeFunctionsService.Iface{

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private MerchantEmployeeFunctionsDao merchantEmployeeFunctionsDao;
    @Autowired
    private MerchantFuctionsDao merchantFuctionsDao;


    @Override
    public MerchantEmployeeFunctionsThrift get(String arg0) throws TException {
        return null;
    }

    @Override
    public String save(MerchantEmployeeFunctionsThrift arg0) throws TException {
        return null;
    }

    @Override
    public List<String> findList(MerchantEmployeeFunctionsThrift arg0) throws TException {
        long employeeId = arg0.getEmployeeId();
        List<String> list = new ArrayList<>();
        List<String> numberList = merchantEmployeeFunctionsDao.queryPermissionByEmployeeId(employeeId);
        for (String str : numberList) {
            String permission = merchantFuctionsDao.queryPermissionCodeById(Integer.valueOf(str));
            list.add(permission);
        }
        return list;
    }
}
