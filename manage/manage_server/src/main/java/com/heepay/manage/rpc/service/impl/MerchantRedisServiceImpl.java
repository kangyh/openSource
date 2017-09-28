package com.heepay.manage.rpc.service.impl;

import com.heepay.enums.RateBusinessType;
import com.heepay.manage.common.cache.PersonRedis;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.rpc.service.MerchantRedisService;
import com.heepay.rpc.service.RpcService;

@Service
@RpcService(name = "merchantRedisServiceImpl", processor = MerchantRedisService.Processor.class)
public class MerchantRedisServiceImpl implements MerchantRedisService.Iface {

    @Override
    public String getMerchantVO(String merchantId) throws TException {
        return RedisUtil.getRedisUtil().merchantRedis(merchantId, null, null, null, null, null, "merchant");
    }

    @Override
    public String getMerchantProductVO(String merchantId, String productCode) throws TException {
        if(productCode.indexOf("CP") < 0){
            return PersonRedis.getRedisUtil().getPersonProductVo(merchantId,productCode);
        }else{
            return RedisUtil.getRedisUtil().merchantRedis(merchantId, productCode, null, null, null,
                    RateBusinessType.INTERNETPAY.getValue(), "merchantProduct");
        }
    }

    @Override
    public String getMerchantFeeVO(String merchantId, String productCode, String bankCardType, String bankNo,String money)
            throws TException {
        if(productCode.indexOf("CP") < 0){
            return PersonRedis.getRedisUtil().getPersonFeeVo(merchantId,productCode,bankCardType,bankNo);
        }else{
            return RedisUtil.getRedisUtil().merchantRedis(merchantId, productCode, bankCardType, bankNo, money,
                    RateBusinessType.INTERNETPAY.getValue(), "fee");
        }
    }

    @Override
    public String getProductVo(String productCode) throws TException {
        return RedisUtil.getRedisUtil().merchantRedis(null, productCode, null, null, null, null, "product");
    }

    @Override
    public String getProductList(String trxType) throws TException {
        return RedisUtil.getRedisUtil().getProductList(trxType);
    }

    @Override
    public String getMerchantProduct(String merchantId, String productCode, String businessType) throws TException {
        if(productCode.indexOf("CP") < 0){
            return PersonRedis.getRedisUtil().getPersonProductVo(merchantId,productCode);
        }else{
            return RedisUtil.getRedisUtil().merchantRedis(merchantId, productCode, null, null, null, businessType, "merchantProduct");
        }
    }

    @Override
    public String getMerchantFee(String merchantId, String productCode, String bankCardType, String bankNo,
              String money, String businessType) throws TException {
        if(productCode.indexOf("CP") < 0){
            return PersonRedis.getRedisUtil().getPersonFeeVo(merchantId,productCode,bankCardType,bankNo);
        }else{
            return RedisUtil.getRedisUtil().merchantRedis(merchantId, productCode, bankCardType, bankNo, money, businessType, "fee");
        }
    }
}
