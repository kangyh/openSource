package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.merchant.dao.MerchantEmployeeFunctionsDao;
import com.heepay.manage.modules.merchant.dao.MerchantFuctionsDao;
import com.heepay.manage.modules.merchant.vo.MerchantEmployeeFunctions;
import com.heepay.manage.modules.merchant.vo.MerchantFuctions;
import com.heepay.manage.rpc.service.MerchantEmployeeFunctionsService;
import com.heepay.manage.rpc.service.MerchantEmployeeFunctionsThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：商户员工权限表
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-09-06-18:29
 * 创建描述：商户员工权限管理
 */
@Component
@RpcService(name = "employeePermissionServiceImpl", processor = MerchantEmployeeFunctionsService.Processor.class)
public class EmployeePermissionServiceImpl implements MerchantEmployeeFunctionsService.Iface{

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
        MerchantEmployeeFunctions employeeCondition = new MerchantEmployeeFunctions();
        employeeCondition.setEmployeeId(arg0.getEmployeeId());
        List<MerchantEmployeeFunctions> list = merchantEmployeeFunctionsDao.findList(employeeCondition);
        List<String> arrayList = new ArrayList<>();
       // merchantFuctionsDao
        for (MerchantEmployeeFunctions employee : list) {
            //通过员工的功能id查到功能表对象
            MerchantFuctions merchantFuctions = merchantFuctionsDao.get(String.valueOf(employee.getFunctionId()));
            //获取权限code标识
            String functionCode = merchantFuctions.getFunctionCode();
            arrayList.add(functionCode);
        }
        return arrayList;
    }
}
