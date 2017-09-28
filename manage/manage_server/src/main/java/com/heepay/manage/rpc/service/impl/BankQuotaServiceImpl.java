/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.heepay.manage.modules.merchant.dao.BankQuotaDao;
import com.heepay.manage.modules.merchant.vo.BankQuota;
import com.heepay.manage.rpc.service.BankQuotaThrift;
import com.heepay.rpc.service.RpcService;

/**
 *
 * 描 述：银行信息查询接口
 *
 * 创 建 者： M.Z 创建时间： 2016/10/11 14:56 创建描述：银行信息缓存出现异常时，手动查询接口
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
@RpcService(name = "bankQuotaServiceImpl", processor = com.heepay.manage.rpc.service.BankQuotaService.Processor.class)
public class BankQuotaServiceImpl implements com.heepay.manage.rpc.service.BankQuotaService.Iface {

    @Autowired
    private BankQuotaDao bankQuotaDao;

      
    /** 
    * @discription 获取银行限额List
    * @author ly       
    * @created 2016年12月5日 下午4:59:18      
    * @param bankQuotaThrifts
    */  
        
    @Override
    public List<BankQuotaThrift> getBankQuotaList(List<BankQuotaThrift> bankQuotaThrifts) throws TException {
        List<BankQuotaThrift> list = Lists.newArrayList();
        if (null != bankQuotaThrifts && !bankQuotaThrifts.isEmpty()) {
            for (BankQuotaThrift thrift : bankQuotaThrifts) {
                BankQuota bankQuotaFind = new BankQuota();
                bankQuotaFind.setBankCardType(thrift.getBankCardType());
                bankQuotaFind.setBankId(thrift.getBankId());
                BankQuota bankQuota = bankQuotaDao.getBankQuota(bankQuotaFind);
                BankQuotaThrift bankQuotaThrift = changeThrift(bankQuota);
                list.add(bankQuotaThrift);
            }
        }
        return list;
    }

    private BankQuotaThrift changeThrift(BankQuota bankQuota) {
        BankQuotaThrift bankQuotaThrift = new BankQuotaThrift();
        if (null != bankQuota) {
            bankQuotaThrift.setBankName(bankQuota.getBankName());
            bankQuotaThrift.setBankId(bankQuota.getBankId());
            bankQuotaThrift.setBankCardType(bankQuota.getBankCardType());
            bankQuotaThrift.setDaylimitAmount(bankQuota.getDaylimitAmount() == null ? "":bankQuota.getDaylimitAmount().toString());
            bankQuotaThrift.setMonlimitAmount(bankQuota.getMonlimitAmount() == null ? "":bankQuota.getMonlimitAmount().toString());
            bankQuotaThrift.setPerlimitAmount(bankQuota.getPerlimitAmount() == null ? "":bankQuota.getPerlimitAmount().toString());
        }
        return bankQuotaThrift;
    }


    /** 
    * @discription  根据卡类型和银行id获取限额
    * @author ly       
    * @created 2016年12月5日 下午4:59:43      
    * @param bankCardType
    * @param bankId
    */
        
    @Override
    public BankQuotaThrift getBankQuota(String bankCardType, String bankId) throws TException {
        BankQuota bankQuotaFind = new BankQuota();
        bankQuotaFind.setBankCardType(bankCardType);
        bankQuotaFind.setBankId(bankId);
        BankQuota bankQuota = bankQuotaDao.getBankQuota(bankQuotaFind);
        BankQuotaThrift bankQuotaThrift = changeThrift(bankQuota);
        return bankQuotaThrift;
    }

}
