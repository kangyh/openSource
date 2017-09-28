package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.hgms.dao.HgmsMerchantFuctionsDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantFuctions;
import com.heepay.manage.rpc.service.HgmsMerchantFuctionsService;
import com.heepay.manage.rpc.service.HgmsMerchantFuctionsThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 描    述：商户员工权限表
 * <p>
 * 创 建 者： guozx@9186.com
 * 创建时间： 2017-08-07 09:45:30
 * 创建描述： 商户员工权限管理
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
@RpcService(name = "hgmsMerchantFunctionsServiceImpl", processor = HgmsMerchantFuctionsService.Processor.class)
public class HgmsMerchantFuctionsServiceImpl implements HgmsMerchantFuctionsService.Iface {

    @Autowired
    private HgmsMerchantFuctionsDao merchantFuctionsDao;

    @Override
    public HgmsMerchantFuctionsThrift get(String arg0) throws TException {
        return null;
    }

    @Override
    public HgmsMerchantFuctionsThrift save(HgmsMerchantFuctionsThrift arg0) throws TException {
        return null;
    }

    @Override
    public List<HgmsMerchantFuctionsThrift> findList(HgmsMerchantFuctionsThrift arg0) throws TException {
        HgmsMerchantFuctions fuctions = new HgmsMerchantFuctions();
        List<HgmsMerchantFuctions> list = merchantFuctionsDao.findList(fuctions);
        List<HgmsMerchantFuctionsThrift> listNew = new ArrayList<>();
        for (HgmsMerchantFuctions merchantFuction : list) {
            HgmsMerchantFuctionsThrift newone = new HgmsMerchantFuctionsThrift();
            newone.setId(merchantFuction.getId());
            newone.setFunctionCode(merchantFuction.getFunctionCode());
            newone.setFunctionName(merchantFuction.getFunctionName());
            newone.setParentFunctionCode(merchantFuction.getParentFunctionCode());
            listNew.add(newone);
        }
        return listNew;
    }
}
