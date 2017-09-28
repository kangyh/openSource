package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.merchant.dao.MerchantFuctionsDao;
import com.heepay.manage.modules.merchant.vo.MerchantFuctions;
import com.heepay.manage.rpc.service.MerchantFuctionsService;
import com.heepay.manage.rpc.service.MerchantFuctionsThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：权限表
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-09-06-18:26
 * 创建描述：给商户赋权限
 */
@Component
@RpcService(name = "merchantFunctionsServiceImpl", processor = MerchantFuctionsService.Processor.class)
public class MerchantPermissionServiceImpl implements MerchantFuctionsService.Iface{

    @Autowired
    private MerchantFuctionsDao merchantFuctionsDao;

    @Override
    public MerchantFuctionsThrift get(String arg0) throws TException {
        return null;
    }

    @Override
    public MerchantFuctionsThrift save(MerchantFuctionsThrift arg0) throws TException {
        return null;
    }

    @Override
    public List<MerchantFuctionsThrift> findList(MerchantFuctionsThrift arg0) throws TException {
        MerchantFuctions fuctions = new MerchantFuctions();
        List<MerchantFuctions> list = merchantFuctionsDao.findList(fuctions);
        List<MerchantFuctionsThrift> listNew = new ArrayList<>();
        for (MerchantFuctions merchantFuction:list) {
            MerchantFuctionsThrift newone = new MerchantFuctionsThrift();
            newone.setId(merchantFuction.getId());
            newone.setFunctionCode(merchantFuction.getFunctionCode());
            newone.setFunctionName(merchantFuction.getFunctionName());
            newone.setParentFunctionCode(merchantFuction.getParentFunctionCode());
            listNew.add(newone);
        }
        return listNew;
    }
}
