package com.heepay.manage.rpc.service.impl;

import java.util.List;

import com.heepay.codec.Md5;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.BankcardType;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.CostType;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.utils.AmountChangeUtil;
import com.heepay.manage.common.utils.DateUtil;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.MerchantRateNewDao;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.rpc.service.MerchantProductService;
import com.heepay.manage.rpc.service.MerchantProductThrift;
import com.heepay.rpc.service.RpcService;
import com.heepay.vo.MerchantProductVO;

@Service
@RpcService(name = "merchantProductServiceImpl", processor = MerchantProductService.Processor.class)
public class MerchantProductServiceImpl implements MerchantProductService.Iface {

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private MerchantRateNewDao merchantRateNewDao;

    private JsonMapperUtil mapper = new JsonMapperUtil();

      
    /**     
    * @discription 获取商户签约产品信息
    * @author ly     
    * @created 2016年12月6日 下午9:19:09     
    * @param merchantId
    * @return
    * @throws TException     
    */
    public List<MerchantProductThrift> getMerchantProduct(String merchantId) throws TException {
        List<MerchantProductThrift> merchantProductThrifts = Lists.newArrayList();
        List<MerchantRateNew> findMerchantProductAndRate = merchantRateNewDao.findMerchantProductAndRate(merchantId);
        List<String> merchantDefaultProduct = Lists.newArrayList();
        merchantDefaultProduct.add("CP01");// 充值
        merchantDefaultProduct.add("CP04");// 提现
        merchantDefaultProduct.add("CP05");// 退款
        if (null != findMerchantProductAndRate && !findMerchantProductAndRate.isEmpty()) {
            for (MerchantRateNew merchantRateNew : findMerchantProductAndRate) {
                if (com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_PRODUCT
                        .indexOf(merchantRateNew.getProductCode()) != -1) {
                    merchantDefaultProduct.remove(merchantRateNew.getProductCode());
                }
                // 转化thrift类
                String beginDate;
                String endDate;
                if(null == merchantRateNew.getRuleBeginTime()){
                    beginDate = DateUtil.stampToDate(DateUtil.getNowTime(), DateUtil.DATE_FORMAT_YYYYMMDD);
                    endDate = DateUtil.stampToDate(DateUtil.getNowTimeAfterYear(100)-1, DateUtil.DATE_FORMAT_YYYYMMDD);
                }else{
                    beginDate = DateUtil.dateToString(merchantRateNew.getRuleBeginTime(), DateUtil.DATE_FORMAT_YYYYMMDD);
                    endDate = DateUtil.dateToString(merchantRateNew.getRuleEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD);
                }
                MerchantProductThrift thrift = getMerchantProductThrift(merchantId, beginDate, endDate, merchantRateNew);
                merchantProductThrifts.add(thrift);
            }
        }
        // 增加默认开通产品
        for (String productCode : merchantDefaultProduct) {
            String json = RedisUtil.getRedisUtil().merchantRedis(merchantId, productCode, null, null,
                    null,"","merchantProduct");
            MerchantProductVO vo = mapper.fromJson(json, MerchantProductVO.class);
            // 获取商户费率默认
            MerchantRateNew rateNew = new MerchantRateNew();
            MerchantRateNew merchantFee = new MerchantRateNew();
            merchantFee.setMerchantId(merchantId);
            merchantFee.setProductCode(productCode);
            List<MerchantRateNew> listMerchant = merchantRateNewDao.findListDefault(merchantFee);
            if (null != listMerchant && !listMerchant.isEmpty()) {
                rateNew = listMerchant.get(0);
            }
            // 转化thrift类
            String beginDate = DateUtil.stampToDate(vo.getValidityDateBegin(), DateUtil.DATE_FORMAT_YYYYMMDD);
            String endDate = DateUtil.stampToDate(vo.getValidityDateEnd()-1, DateUtil.DATE_FORMAT_YYYYMMDD);
            rateNew.setProductCode(productCode);
            rateNew.setSettleType(vo.getSettleCyc());
            MerchantProductThrift thrift = getMerchantProductThrift(merchantId, beginDate, endDate, rateNew);
            merchantProductThrifts.add(thrift);
        }
        return merchantProductThrifts;
    }

      
    /**     
    * @discription  转化MerchantProductThrift类
    * @author ly     
    * @created 2016年12月8日 下午4:43:56     
    * @param merchantId
    * @param beginDate
    * @param endDate
    * @param rateNew
    * @return     
    */
    private MerchantProductThrift getMerchantProductThrift(String merchantId, String beginDate,
            String endDate, MerchantRateNew rateNew) {
        Product product = productDao.selectByCode(rateNew.getProductCode());
        MerchantProductThrift thrift = new MerchantProductThrift();
        thrift.setMerchantId(merchantId);
        thrift.setProductCode(rateNew.getProductCode());
        thrift.setBusinessType(rateNew.getBusinessType());
        thrift.setTrxType(product.getTrxType());
        // 拼产品名称和卡类型
        if (StringUtils.isNotBlank(rateNew.getBankCardType())) {
            thrift.setProductName(product.getName() + BankcardType.labelOf(rateNew.getBankCardType()));
        } else {
            thrift.setProductName(product.getName());
        }
        thrift.setValidityDateBegin(beginDate);
        thrift.setValidityDateEnd(endDate);
        // 拼费率手续费扣除方式结算周期
        // 拼接费率
        String fee = "";
        if (StringUtils.isNotBlank(rateNew.getChargeType())) {
            if (CostType.COUNT.getValue().equals(rateNew.getChargeType())) {
                fee = null == rateNew.getChargeFee() ? "" : AmountChangeUtil.change(rateNew.getChargeFee()) + "元/笔";
            } else if (CostType.RATE.getValue().equals(rateNew.getChargeType())) {
                fee = null == rateNew.getChargeRatio() ? "" : AmountChangeUtil.change(rateNew.getChargeRatio()) + "%";
            }
        }
        if (StringUtils.isNotBlank(rateNew.getChargeDeductType())) {
            fee = fee == "" ? ChargeDeductType.labelOf(rateNew.getChargeDeductType())
                    : fee + "-" + ChargeDeductType.labelOf(rateNew.getChargeDeductType());
        }
        if (StringUtils.isNotBlank(rateNew.getSettleType())) {
            fee = fee == "" ? "T+" + rateNew.getSettleType() : fee + "-T+" + rateNew.getSettleType();
        }
        thrift.setFee(fee);
        // 判断是否退还手续费
        if (StringUtils.isNotBlank(rateNew.getIsRefundable())) {
            if (CommonStatus.YES.getValue().equals(rateNew.getIsRefundable())) {
                thrift.setRemark("退还手续费");
            } else if (CommonStatus.NO.getValue().equals(rateNew.getIsRefundable())) {
                thrift.setRemark("不退还手续费");
            }
        } else {
            thrift.setRemark("无");
        }
        return thrift;
    }

    @Override
    public String getProductKey(String merchantId, String productCode, String businessType) throws TException {
        switch (productCode) {
        case "CP01":// 充值
            String productKey = Constants.RedisKey.CHARGE_PRODUCT_KEY + merchantId;
            return Md5.encode(productKey);
        case "CP04":// 退款
            String productKey1 = Constants.RedisKey.REFUND_PRODUCT_KEY + merchantId;
            return Md5.encode(productKey1);
        case "CP05":// 提现
            String productKey2 =  Constants.RedisKey.WITHDRAW_PRODUCT_KEY + merchantId;
            return Md5.encode(productKey2);
        default:
            MerchantProductRate merchantProductRate = new MerchantProductRate();
            merchantProductRate.setMerchantId(merchantId);
            merchantProductRate.setProductCode(productCode);
            merchantProductRate.setBusinessType(businessType);
            merchantProductRate = merchantProductRateDao.exist(merchantProductRate);
            if (null != merchantProductRate) {
                return merchantProductRate.getAutographKey();
            }
            return "";
        }
    }

    @Override
    public String resetProductKey(String merchantId, String productCode, String key, String businessType) throws TException {
        MerchantProductRate merchantProductRate = new MerchantProductRate();
        merchantProductRate.setMerchantId(merchantId);
        merchantProductRate.setProductCode(productCode);
        merchantProductRate.setAutographKey(key);
        merchantProductRate.setBusinessType(businessType);
        merchantProductRateDao.resetKey(merchantProductRate);
        return Boolean.TRUE.toString();
    }

    @Override
    public String getResetProductKey(String merchantId, String productCode, String type) throws TException {
        return RandomUtil.getKey(merchantId, productCode, type);
    }

}
