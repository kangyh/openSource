package com.heepay.risk.service.impl;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import com.heepay.rpc.risk.model.MerchantProductModel;
import com.heepay.rpc.risk.service.MerchantService;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.client.MerchantProductServiceClient;
import com.heepay.rpc.client.MerchantRedisServiceClient;
/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年11月22日 下午6:13:48
* 类说明
* 商户基本信息 及 产品的信息
* 
* 
*/


@Component
@RpcService(name="MerchantServiceImpl",processor=com.heepay.rpc.risk.service.MerchantService.Processor.class)
public class MerchantServiceImpl implements MerchantService.Iface {

    @Autowired
    MerchantProductServiceClient  merchantProductServiceClient ;
    @Autowired
    MerchantRedisServiceClient merchantRedisServiceClient;
    
    /*
     * 给出 商户编号 返回 该商户下的 所有支付类产品
     * 
     */
    @Override
    public String getMerchantProduct(String merchantId) throws TException {
        // TODO Auto-generated method stub
        return merchantProductServiceClient.getMerchantProduct(merchantId);
    }
    /*
     * 给出商户编号 返回 商户基本信息，以json 串形程现
     * 
     */
    @Override
    public String getMerchantVO(String merchantId) throws TException {
        // TODO Auto-generated method stub
        return merchantRedisServiceClient.getMerchantVO(merchantId);
    }
    /**
     * 根据交易类型，获取产品列表信息
     */
    @Override
    public String getProductList(String trxType)
    {
        return merchantRedisServiceClient.getProductList(trxType);
    }

}
