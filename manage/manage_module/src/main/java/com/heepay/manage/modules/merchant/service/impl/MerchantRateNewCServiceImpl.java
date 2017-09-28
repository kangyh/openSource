/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import com.google.common.collect.Lists;
import com.heepay.common.util.SendMailUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.manage.common.enums.ContractType;
import com.heepay.manage.modules.merchant.dao.check.MerchantProductRateCheckDao;
import com.heepay.manage.modules.merchant.dao.check.MerchantRateBankCheckDao;
import com.heepay.manage.modules.merchant.service.check.MerchantProductRateCheckService;
import com.heepay.manage.modules.notification.dao.NotificationEmailDao;
import com.heepay.manage.modules.route.dao.CertifyChannelDao;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.enums.OperationType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.AmountChangeUtil;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.DateUtil;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.merchant.dao.*;
import com.heepay.manage.modules.merchant.service.MerchantRateNewCService;
import com.heepay.manage.modules.merchant.vo.*;
import com.heepay.manage.modules.notification.dao.NotificationEmailDao;
import com.heepay.manage.modules.route.dao.CertifyChannelDao;
import com.heepay.manage.modules.route.dao.ProductDetailDao;
import com.heepay.manage.modules.route.entity.ProductDetail;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 描 述：商户费率管理
 *
 * 创 建 者：ly 创建时间：2016-08-23 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Service
@Transactional(readOnly = true)
public class MerchantRateNewCServiceImpl extends CrudService<MerchantRateNewDao, MerchantRateNew>
        implements MerchantRateNewCService {

    @Value("#{task['update.rate.email']}")
    private String environment;
    @Autowired
    private MerchantRateNewDao merchantRateNewDao;
    
    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private CertifyChannelDao certifyChannelDao;

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Autowired
    private MerchantRateBankDao merchantRateBankDao;
    
    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private MerchantRateLogDao merchantRateLogDao;

    @Autowired
    private NotificationEmailDao notificationEmailDao;

    @Autowired
    private MerchantProductRateCheckDao merchantProductRateCheckDao;

    @Autowired
    private MerchantRateBankCheckDao merchantRateBankCheckDao;


    /**
     * @discription 保存商户签约产品
     * @author ly
     * @created 2016年12月2日 下午2:47:11
     * @param merchantRateNewVo
     * @param flag
     * @return
     */
    @Transactional(readOnly = false)
    public String saveVo(MerchantRateNewVo merchantRateNewVo, boolean flag) {
        List<String> list = merchantRateNewVo.getBankCardType();
        //获取通道支持银行(包含银行卡类型,银行id)是否存在
        List<ProductDetail> productDetails = productDetailDao.getProductDetailBankNo(merchantRateNewVo.getProductCode());
        Product product = productDao.selectByCode(merchantRateNewVo.getProductCode());
        boolean isProductDetails = RedisUtil.getRedisUtil().isJudgeChannel(productDetails, product);
        // 判断产品是否有通道
        if (isProductDetails) {
            // 卡类型不存在
            if (null == list || list.isEmpty()) {
                MerchantRateNew merchantRateNew = changeMerchantRateSaving(merchantRateNewVo, "");
                //判断成本
//                boolean isOverCost = isOverCost(merchantRateNew);
//                if(isOverCost){
//                    return "产品费率小于通道费率，请重新配置";
//                }
                MerchantRateBank merchantRateBank = new MerchantRateBank();
                switch (product.getTrxType()) {
                case "BATPAY":// 转账
                case "BATCOL":// 代收
                case "PAYMNT":// 支付 无卡类型(B2B)
                case "DPTPAY":// 存管支付
                    if (product.getName().indexOf(Constants.WECHAT) >= 0 || product.getName().indexOf(Constants.ALIPAY) >= 0 ||
                            product.getName().indexOf(Constants.UNOPAY) >= 0 || product.getName().indexOf(Constants.QUAGRP) >= 0) {
                        merchantRateBank.setRateId(getRateId(merchantRateNew, flag));
                        saveMerchantRateBank(merchantRateNew, flag, merchantRateBank);
                    } else {
                        // 代收转账B2B网银支付 区分银行 没有卡类型
                        saveMerchantRate(flag, merchantRateNew);
                    }
                    break;
                default:// 其他交易类型无银行
                    merchantRateBank.setRateId(getRateId(merchantRateNew, flag));
                    saveMerchantRateBank(merchantRateNew, flag, merchantRateBank);
                    break;
                }
                String businessType = merchantRateNew.getBusinessType();
                if(Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,
                        Constants.SYS_COMMON,""))){
                    businessType = RateBusinessType.INTERNETPAY.getValue();
                }
                // 修改商家费率缓存
                RedisUtil.getRedisUtil().saveProductFeeVORedis(merchantRateNew.getMerchantId(),
                        merchantRateNew.getProductCode(), businessType);
                return Boolean.TRUE.toString();
            }
            // 卡类型存在(支付B2C)
            //获取通道支持的卡类型
            List<String> productDetailsBankCardTypes = productDetailDao.getProductDetailBankCardType(merchantRateNewVo.getProductCode());
            if(null != productDetailsBankCardTypes && !productDetailsBankCardTypes.isEmpty()){
                boolean flagCardType = true;//判断卡类型与通道中卡类型是否相同
                for(String bankCardType : list){
                    flagCardType = flagCardType && productDetailsBankCardTypes.contains(bankCardType);
                }
                if(flagCardType){
                    for (String bankCardType : list) {
                        // 借记卡
                        if (RateBankcardType.DEBITCARD.getValue().equals(bankCardType)) {
                            MerchantRateNew merchantRateNew = changeMerchantRateSaving(merchantRateNewVo, bankCardType);
                            //判断成本
//                            boolean isOverCost = isOverCost(merchantRateNew);
//                            if(isOverCost){
//                                return "产品费率小于通道费率，请重新配置";
//                            }
                            saveMerchantRate(flag, merchantRateNew);
                        }
                        // 贷记卡
                        if (RateBankcardType.CREDITCARD.getValue().equals(bankCardType)) {
                            MerchantRateNew merchantRateNew = changeMerchantRateCredit(merchantRateNewVo, bankCardType);
                            //判断成本
//                            boolean isOverCost = isOverCost(merchantRateNew);
//                            if(isOverCost){
//                                return "产品费率小于通道费率，请重新配置";
//                            }
                            saveMerchantRate(flag, merchantRateNew);
                        }
                    }
                    return Boolean.TRUE.toString();
                }else{
                    return "只在路由配置中找到该产品对应的"+RateBankcardType.labelOf(productDetailsBankCardTypes.get(0))+"通道";
                }
            }
        }
        return "未在路由配置中找到该产品对应的通道";
    }


    /**     
    * @discription  保存商户费率
    * @author ly     
    * @created 2016年12月2日 下午2:46:49     
    * @param flag
    * @param merchantRateNew     
    */
    private void saveMerchantRate(boolean flag, MerchantRateNew merchantRateNew) {
        MerchantRateBank merchantRateBank = new MerchantRateBank();
        merchantRateBank.setRateId(getRateId(merchantRateNew, flag));
        merchantRateNew.setBankNo(Constants.MERCHANT_RATE_ALL_BANK);
        saveMerchantRateBank(merchantRateNew, flag, merchantRateBank);
        String businessType = merchantRateNew.getBusinessType();
        if(!Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,
                Constants.SYS_COMMON,""))){
            businessType = RateBusinessType.INTERNETPAY.getValue();
        }
        // 修改商家费率缓存
        RedisUtil.getRedisUtil().saveProductFeeVORedis(merchantRateNew.getMerchantId(),
                merchantRateNew.getProductCode(), businessType);
    }
    
      
    /**     
    * @discription 转换merchantRate借记卡
    * @author ly     
    * @created 2016年11月30日 下午5:40:18     
    * @param merchantRateNewVo
    * @param bankCardType
    * @return     
    */
    private MerchantRateNew changeMerchantRateSaving(MerchantRateNewVo merchantRateNewVo, String bankCardType) {
        MerchantRateNew merchantRateNew = changeMerchantRateProduct(merchantRateNewVo);
        merchantRateNew.setChargeFee(merchantRateNewVo.getChargeFeeSaving());
        merchantRateNew.setChargeFee2(merchantRateNewVo.getChargeFeeSaving2());
        merchantRateNew.setChargeFee3(merchantRateNewVo.getChargeFeeSaving3());
        merchantRateNew.setChargeRatio(merchantRateNewVo.getChargeRatioSaving());
        merchantRateNew.setChargeRatio2(merchantRateNewVo.getChargeRatioSaving2());
        merchantRateNew.setChargeRatio3(merchantRateNewVo.getChargeRatioSaving3());
        //阶梯范围
        if(CostType.RATE.getValue().equals(merchantRateNewVo.getChargeTypeSaving())){
            merchantRateNew.setChargeMax(merchantRateNewVo.getChargeMaxSavingRatiod());
            merchantRateNew.setChargeMax2(merchantRateNewVo.getChargeMaxSavingRatiod2());
            merchantRateNew.setChargeMax3(merchantRateNewVo.getChargeMaxSavingRatiod3());
            merchantRateNew.setChargeMin(merchantRateNewVo.getChargeMinSavingRatiod());
            merchantRateNew.setChargeMin2(merchantRateNewVo.getChargeMinSavingRatiod2());
            merchantRateNew.setChargeMin3(merchantRateNewVo.getChargeMinSavingRatiod3());
        }else{
            merchantRateNew.setChargeMax(merchantRateNewVo.getChargeMaxSavingCountd());
            merchantRateNew.setChargeMax2(merchantRateNewVo.getChargeMaxSavingCountd2());
            merchantRateNew.setChargeMax3(merchantRateNewVo.getChargeMaxSavingCountd3());
            merchantRateNew.setChargeMin(merchantRateNewVo.getChargeMinSavingCountd());
            merchantRateNew.setChargeMin2(merchantRateNewVo.getChargeMinSavingCountd2());
            merchantRateNew.setChargeMin3(merchantRateNewVo.getChargeMinSavingCountd3());
        }
        merchantRateNew.setChargeType(merchantRateNewVo.getChargeTypeSaving());
        merchantRateNew.setMaxFee(merchantRateNewVo.getMaxFeeSaving());
        merchantRateNew.setMinFee(merchantRateNewVo.getMinFeeSaving());
        merchantRateNew.setBankCardType(bankCardType);
        return merchantRateNew;
    }
    
      
    /**     
    * @discription 转换merchantRate贷记卡
    * @author ly     
    * @created 2016年11月30日 下午5:40:42     
    * @param merchantRateNewVo
    * @param bankCardType
    * @return     
    */
    private MerchantRateNew changeMerchantRateCredit(MerchantRateNewVo merchantRateNewVo, String bankCardType) {
        MerchantRateNew merchantRateNew = changeMerchantRateProduct(merchantRateNewVo);
        merchantRateNew.setChargeFee(merchantRateNewVo.getChargeFeeCredit());
        merchantRateNew.setChargeFee2(merchantRateNewVo.getChargeFeeCredit2());
        merchantRateNew.setChargeFee3(merchantRateNewVo.getChargeFeeCredit3());
        merchantRateNew.setChargeRatio(merchantRateNewVo.getChargeRatioCredit());
        merchantRateNew.setChargeRatio2(merchantRateNewVo.getChargeRatioCredit2());
        merchantRateNew.setChargeRatio3(merchantRateNewVo.getChargeRatioCredit3());
        //阶梯范围
        if(CostType.RATE.getValue().equals(merchantRateNewVo.getChargeTypeCredit())){
            merchantRateNew.setChargeMax(merchantRateNewVo.getChargeMaxCreditRatiod());
            merchantRateNew.setChargeMax2(merchantRateNewVo.getChargeMaxCreditRatiod2());
            merchantRateNew.setChargeMax3(merchantRateNewVo.getChargeMaxCreditRatiod3());
            merchantRateNew.setChargeMin(merchantRateNewVo.getChargeMinCreditRatiod());
            merchantRateNew.setChargeMin2(merchantRateNewVo.getChargeMinCreditRatiod2());
            merchantRateNew.setChargeMin3(merchantRateNewVo.getChargeMinCreditRatiod3());
        }else{
            merchantRateNew.setChargeMax(merchantRateNewVo.getChargeMaxCreditCountd());
            merchantRateNew.setChargeMax2(merchantRateNewVo.getChargeMaxCreditCountd2());
            merchantRateNew.setChargeMax3(merchantRateNewVo.getChargeMaxCreditCountd3());
            merchantRateNew.setChargeMin(merchantRateNewVo.getChargeMinCreditCountd());
            merchantRateNew.setChargeMin2(merchantRateNewVo.getChargeMinCreditCountd2());
            merchantRateNew.setChargeMin3(merchantRateNewVo.getChargeMinCreditCountd3());
        }
        merchantRateNew.setChargeType(merchantRateNewVo.getChargeTypeCredit());
        merchantRateNew.setMaxFee(merchantRateNewVo.getMaxFeeCredit());
        merchantRateNew.setMinFee(merchantRateNewVo.getMinFeeCredit());
        merchantRateNew.setBankCardType(bankCardType);
        return merchantRateNew;
    }

      
    /**     
    * @discription 转换MerchantRateProduct
    * @author ly     
    * @created 2016年11月30日 下午5:40:56     
    * @param merchantRateNewVo
    * @return     
    */
    private MerchantRateNew changeMerchantRateProduct(MerchantRateNewVo merchantRateNewVo) {
        MerchantRateNew merchantRateNew = new MerchantRateNew();
        //产品是否根据业务类型选择
        if(Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,Constants.SYS_COMMON,""))
                && (merchantRateNewVo.getBusinessType().equals(RateBusinessType.WALLET.getValue()) ||
                    merchantRateNewVo.getBusinessType().equals(RateBusinessType.TRUSTEE.getValue()))){
            //业务类型为钱包时，将产品改为外扣
            merchantRateNew.setChargeDeductType(ChargeDeductType.EXTERNAL_DEDUCT.getValue());
        }else{
            merchantRateNew.setChargeCollectionType(merchantRateNewVo.getChargeCollectionType());
            merchantRateNew.setChargeDeductType(merchantRateNewVo.getChargeDeductType());
            merchantRateNew.setChargeSource(merchantRateNewVo.getChargeSource());
        }
        //业务类型为电商托管时结算类型为T+X
        if(merchantRateNewVo.getBusinessType().equals(RateBusinessType.TRUSTEE.getValue())){
            merchantRateNew.setSettleType("X");
        }else{
            merchantRateNew.setSettleType(merchantRateNewVo.getSettleType());
        }
        merchantRateNew.setMerchantId(merchantRateNewVo.getMerchantId());
        merchantRateNew.setProductCode(merchantRateNewVo.getProductCode());
        merchantRateNew.setProductName(merchantRateNewVo.getProductName());
        merchantRateNew.setBusinessType(merchantRateNewVo.getBusinessType());
        merchantRateNew.setMerchantCompanyName(merchantRateNewVo.getMerchantCompanyName());
        merchantRateNew.setMerchantId(merchantRateNewVo.getMerchantId());
        merchantRateNew.setMerchantLoginName(merchantRateNewVo.getMerchantLoginName());
        merchantRateNew.setProductCode(merchantRateNewVo.getProductCode());
        merchantRateNew.setProductName(merchantRateNewVo.getProductName());
        merchantRateNew.setRuleBeginTime(merchantRateNewVo.getRuleBeginTime());
        merchantRateNew.setRuleEndTime(merchantRateNewVo.getRuleEndTime());
        merchantRateNew.setIsRefundable(merchantRateNewVo.getIsRefundable());
        merchantRateNew.setStatus(merchantRateNewVo.getStatus());
        merchantRateNew.setBackUrl(merchantRateNewVo.getBackUrl());
        merchantRateNew.setNotifyUrl(merchantRateNewVo.getNotifyUrl());
        merchantRateNew.setIpDomain(merchantRateNewVo.getIpDomain());
        merchantRateNew.setAutographType(merchantRateNewVo.getAutographType());
        merchantRateNew.setSettlementTo(merchantRateNewVo.getSettlementTo());
        merchantRateNew.setId(merchantRateNewVo.getId());
        return merchantRateNew;
    }
    

      
    /** 
    * @discription 默认费率保存 商户费率修改保存
    * @author ly       
    * @created 2016年12月2日 下午2:47:43      
    * @param merchantRateNew
    * @param flag     
    */  
        
    @Transactional(readOnly = false)
    public String saveRate(MerchantRateNew merchantRateNew, boolean flag) {
        MerchantRateBank merchantRateBank = new MerchantRateBank();
        merchantRateBank.setRateId(getRateId(merchantRateNew, flag));
        String businessType = merchantRateNew.getBusinessType();
        if(!Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,
                Constants.SYS_COMMON,""))){
            businessType = RateBusinessType.INTERNETPAY.getValue();
        }
        //判断成本
//        boolean isOverCost = isOverCost(merchantRateNew);
//        if(isOverCost){
//            return "产品费率小于通道费率，请重新配置";
//        }
        // 保存默认费率
        if (Constants.MERCHANT_DEFAULT_ID.equals(merchantRateNew.getMerchantId())) {
            // 默认费率更新merchant_product_rate表
            saveMerchantProductRateDefault(merchantRateNew, flag, merchantRateBank.getRateId());
            // 默认费率保存merchant_rate_bank表
            saveMerchantRateBank(merchantRateNew, flag, merchantRateBank);
            // 写默认费率缓存
            RedisUtil.getRedisUtil().saveDefaultFeeVORedis(merchantRateNew.getProductCode(),businessType);
        } else {//保存商户费率
            // 除(退款(不存),充值,提现,实名认证,平级分润)外其他产品没有bankId不存merchant_rate_bank
            Product product = productDao.selectByCode(merchantRateNew.getProductCode());
            switch (product.getTrxType()) {
            case "REFUND":
                // 退款 不存
                break;
            case "CHARGE":
            case "WZDRAW":
            case "RENAME":
            case "EPRISE"://企业认证
            case "AUTHEN"://鉴权认证
            case "SHARES":
            case "CACNCE":
            case "TRAFER"://内转
            case "WALLET"://钱包服务
            case "DPTWZD":
                // 充值,提现,实名认证,平级分润,通关申报不判断bankId 存
                // 构造merchantRateBank 信息 并保存
                saveMerchantRateBank(merchantRateNew, flag, merchantRateBank);
                break;
            default:
                //微信不区分bankId 其他判断bankId 存在存 不存在不存
                if (product.getName().indexOf(Constants.WECHAT) >= 0 || product.getName().indexOf(Constants.ALIPAY) >= 0 ||
                    product.getName().indexOf(Constants.UNOPAY) >= 0 || product.getName().indexOf(Constants.QUAGRP) >= 0 ||
                    StringUtils.isNotBlank(merchantRateNew.getBankNo())) {
                    // 构造merchantRateBank 信息 并保存
                    saveMerchantRateBank(merchantRateNew, flag, merchantRateBank);
                }
            }

            // 修改商家费率缓存
            RedisUtil.getRedisUtil().saveProductFeeVORedis(merchantRateNew.getMerchantId(),
                    merchantRateNew.getProductCode(), businessType);
        }
        merchantRateNew.setRateId(merchantRateBank.getRateId());
        return Boolean.TRUE.toString();
    }

      
    /**     
    * @discription 获取rate_id
    * @author ly     
    * @created 2016年11月29日 下午7:31:08     
    * @param merchantRateNew
    * @param flag
    * @return     
    */
    private String getRateId(MerchantRateNew merchantRateNew, boolean flag) {
        MerchantProductRate merchantProductRateExist = new MerchantProductRate();
        merchantProductRateExist.setMerchantId(merchantRateNew.getMerchantId());
        merchantProductRateExist.setProductCode(merchantRateNew.getProductCode());
        merchantProductRateExist.setBusinessType(merchantRateNew.getBusinessType());
        merchantProductRateExist = merchantProductRateCheckDao.exist(merchantProductRateExist);// 确定merchant_product_rate表里是否存在
        // new 一个MerchantRateBank对象
        // 获取rateId(商家签约产品merchant_product_rate ID)
        if (null != merchantProductRateExist) {
            return merchantProductRateExist.getId();
        } else {
            // 如果merchant_product_rate不存在要先存merchant_product_rate
            MerchantProductRate merchantProductRate = saveMerchantProductRate(merchantRateNew, flag);
            // 获取merchant_product_rate的id
            return merchantProductRate.getId();
        }
    }

    /**
     * @discription 保存merchant_product_rate表
     * @author ly
     * @created 2016年11月2日 下午7:00:48
     * @param merchantRateNew
     * @param flag
     * @return
     */
    private MerchantProductRate saveMerchantProductRate(MerchantRateNew merchantRateNew, boolean flag) {
        MerchantProductRate merchantProductRate = changeMerchantProductRate(merchantRateNew);
        toSaveMerchantProductRate(merchantProductRate, flag);
        return merchantProductRate;
    }

    /**
     * @discription 保存merchant_product_rate表(默认费率)
     * @author ly
     * @created 2016年11月2日 下午7:00:48
     * @param merchantRateNew
     * @param flag
     * @return
     */
    private void saveMerchantProductRateDefault(MerchantRateNew merchantRateNew, boolean flag, String id) {
        MerchantProductRate merchantProductRate = changeMerchantProductRate(merchantRateNew);
        if(null == merchantProductRate.getRuleBeginTime()){
            merchantProductRate.setRuleBeginTime(new Date());
        }
        if(null == merchantProductRate.getRuleEndTime()){
            merchantProductRate.setRuleEndTime(new Date(DateUtil.getNowTimeAfterYear(100)));
        }
        merchantProductRate.setId(id);
        toSaveMerchantProductRate(merchantProductRate, flag);
    }

    /**
     * @discription 转换MerchantProductRate
     * @author ly
     * @created 2016年11月4日 下午5:31:49
     * @param merchantRateNew
     * @return
     */
    private MerchantProductRate changeMerchantProductRate(MerchantRateNew merchantRateNew) {
        MerchantProductRate merchantProductRate = new MerchantProductRate();
        merchantProductRate.setBusinessType(merchantRateNew.getBusinessType());
        merchantProductRate.setChargeCollectionType(merchantRateNew.getChargeCollectionType());
        merchantProductRate.setChargeDeductType(merchantRateNew.getChargeDeductType());
        merchantProductRate.setChargeSource(merchantRateNew.getChargeSource());
        merchantProductRate.setMerchantCompanyName(merchantRateNew.getMerchantCompanyName());
        merchantProductRate.setMerchantId(merchantRateNew.getMerchantId());
        merchantProductRate.setMerchantLoginName(merchantRateNew.getMerchantLoginName());
        merchantProductRate.setProductCode(merchantRateNew.getProductCode());
        merchantProductRate.setProductName(merchantRateNew.getProductName());
        merchantProductRate.setRuleBeginTime(merchantRateNew.getRuleBeginTime());
        merchantProductRate.setRuleEndTime(merchantRateNew.getRuleEndTime());
        merchantProductRate.setIsRefundable(merchantRateNew.getIsRefundable());
        merchantProductRate.setStatus(merchantRateNew.getStatus());
        merchantProductRate.setBackUrl(merchantRateNew.getBackUrl());
        merchantProductRate.setNotifyUrl(merchantRateNew.getNotifyUrl());
        merchantProductRate.setIpDomain(merchantRateNew.getIpDomain());
        merchantProductRate.setAutographType(merchantRateNew.getAutographType());
        if(StringUtils.isNotBlank(merchantRateNew.getAutographType())){
            merchantProductRate.setAutographKey(RandomUtil.getKey(merchantRateNew.getMerchantId(),
                    merchantRateNew.getProductCode(),merchantRateNew.getAutographType()));
        }
        merchantProductRate.setSettlementTo(merchantRateNew.getSettlementTo());
        merchantProductRate.setSettleType(merchantRateNew.getSettleType());
        merchantProductRate.setRateAudit(merchantRateNew.getRateAudit());
        merchantProductRate.setContractType(ContractType.OFFLINE.getValue());
        return merchantProductRate;
    }

    /**
     * @discription 保存merchant_rate_bank表
     * @author ly
     * @created 2016年11月4日 下午5:43:09
     * @param merchantRateNew
     * @param flag
     * @param merchantRateBank
     */
    private void saveMerchantRateBank(MerchantRateNew merchantRateNew, boolean flag,
            MerchantRateBank merchantRateBank) {
        //将卡类型显示值转为枚举值
        changeEnumBnakCard(merchantRateNew);
        merchantRateBank.setId(merchantRateNew.getId());
        merchantRateBank.setBankName(merchantRateNew.getBankName());
        merchantRateBank.setBankNo(merchantRateNew.getBankNo());
        merchantRateBank.setChargeType(merchantRateNew.getChargeType());
        merchantRateBank.setMaxFee(merchantRateNew.getMaxFee());
        merchantRateBank.setMinFee(merchantRateNew.getMinFee());
        merchantRateBank.setBankCardType(merchantRateNew.getBankCardType());
        //集体费率
        merchantRateBank.setChargeFee(merchantRateNew.getChargeFee());
        merchantRateBank.setChargeFee2(merchantRateNew.getChargeFee2());
        merchantRateBank.setChargeFee3(merchantRateNew.getChargeFee3());
        merchantRateBank.setChargeRatio(merchantRateNew.getChargeRatio());
        merchantRateBank.setChargeRatio2(merchantRateNew.getChargeRatio2());
        merchantRateBank.setChargeRatio3(merchantRateNew.getChargeRatio3());
        merchantRateBank.setChargeMax(merchantRateNew.getChargeMax());
        merchantRateBank.setChargeMax2(merchantRateNew.getChargeMax2());
        merchantRateBank.setChargeMax3(merchantRateNew.getChargeMax3());
        merchantRateBank.setChargeMin(merchantRateNew.getChargeMin());
        merchantRateBank.setChargeMin2(merchantRateNew.getChargeMin2());
        merchantRateBank.setChargeMin3(merchantRateNew.getChargeMin3());
        toSaveMerchantRateBank(merchantRateBank, flag);
    }

    /**
     * 将卡类型显示值转为枚举值
     * @param merchantRateNew
     */
    private void changeEnumBnakCard(MerchantRateNew merchantRateNew) {
        if (StringUtils.isNotBlank(merchantRateNew.getBankCardType())) {
            if (RateBankcardType.DEBITCARD.getContent().equals(merchantRateNew.getBankCardType())) {
                merchantRateNew.setBankCardType(RateBankcardType.DEBITCARD.getValue());
            } else if (RateBankcardType.CREDITCARD.getContent().equals(merchantRateNew.getBankCardType())) {
                merchantRateNew.setBankCardType(RateBankcardType.CREDITCARD.getValue());
            }
        }
    }

    @Transactional(readOnly = false)
    public void status(MerchantRateNew merchantRateNew) {
        merchantRateNewDao.status(merchantRateNew);
    }

    /**
     * @discription 是否存在默认费率
     * @author ly
     * @created 2016年11月4日 下午5:43:57
     * @param merchantRateNew
     * @return
     */
    public MerchantRateNew existDefault(MerchantRateNew merchantRateNew) {
        return merchantRateNewDao.existDefault(merchantRateNew);
    }

    /**
     * @discription 是否存在费率包含本身
     * @author ly
     * @created 2016年11月4日 下午5:44:14
     * @param merchantRateNew
     * @return
     */
    public MerchantRateNew existNoId(MerchantRateNew merchantRateNew) {
        MerchantRateNew merchantRateNews = null;
            if(StringUtils.isNotBlank(merchantRateNew.getFlag())){//判断是否走默认的方法
                merchantRateNews = merchantRateNewDao.existNoIdForCheck(merchantRateNew);
            }else{
                 merchantRateNews = merchantRateNewDao.existNoId(merchantRateNew);
            }
        return merchantRateNews;
    }

    /**
     * @discription 获取默认费率Page
     * @author ly
     * @created 2016年11月4日 下午5:44:46
     * @param page
     * @param merchantRateNew
     * @return
     */
    @Override
    public Page<MerchantRateNew> findPageDefaul(Page<MerchantRateNew> page, MerchantRateNew merchantRateNew) {
        merchantRateNew.setPage(page);
        page.setList(merchantRateNewDao.findListDefault(merchantRateNew));
        return page;
    }

    /**
     * @discription 商户增加费率时获取默认费率
     * @author ly
     * @created 2016年10月17日 下午6:03:24
     * @param merchantRateNew
     * @return
     */
    @Override
    public MerchantRateDefault getDefault(MerchantRateNew merchantRateNew) {
        List<MerchantRateNew> list = merchantRateNewDao.getDefault(merchantRateNew);
        if (!list.isEmpty()) {
            MerchantRateDefault merchantRateDefault = new MerchantRateDefault();
            MerchantRateNew merchantRateReturn = list.get(0);
            merchantRateDefault.setBankCardType(merchantRateReturn.getBankCardType());
            merchantRateDefault.setChargeCollectionType(merchantRateReturn.getChargeCollectionType());
            merchantRateDefault.setChargeDeductType(merchantRateReturn.getChargeDeductType());
            merchantRateDefault.setChargeSource(merchantRateReturn.getChargeSource());
            merchantRateDefault.setIsRefundable(merchantRateReturn.getIsRefundable());
            merchantRateDefault.setChargeType(merchantRateReturn.getChargeType());
            if (null != merchantRateReturn.getChargeFee()) {
                merchantRateDefault.setChargeFee(AmountChangeUtil.change(merchantRateReturn.getChargeFee()));
            }
            if (null != merchantRateReturn.getChargeRatio()) {
                merchantRateDefault.setChargeRatio(AmountChangeUtil.change(merchantRateReturn.getChargeRatio()));
            }
            if (null != merchantRateReturn.getMaxFee()) {
                merchantRateDefault.setMaxFee(AmountChangeUtil.change(merchantRateReturn.getMaxFee()));
            }
            if (null != merchantRateReturn.getMinFee()) {
                merchantRateDefault.setMinFee(AmountChangeUtil.change(merchantRateReturn.getMinFee()));
            }
            return merchantRateDefault;
        }
        return null;
    }

    /**
     * @discription 获取商户详情页费率Page
     * @author ly
     * @created 2016年11月4日 下午5:45:10
     * @param page
     * @param merchantRate
     * @return
     */
    public Page<MerchantRateNew> findPageMerchant(Page<MerchantRateNew> page, MerchantRateNew merchantRate) {
        merchantRate.setPage(page);
        page.setList(dao.findMerchantProductAndRate(merchantRate.getMerchantId()));
        return page;
    }

    /**
     * @discription 获取费率详情页Page
     * @author ly
     * @created 2016年11月4日 下午5:51:12
     * @param page
     * @param merchantRate
     * @return
     */
    public Page<MerchantRateNew> findPage(Page<MerchantRateNew> page, MerchantRateNew merchantRate) {
        String searchBankNo = merchantRate.getBankNo();
        String searchBankCardType = merchantRate.getBankCardType();

        MerchantProductRate merchantProductRate = null;
        if(StringUtils.isNotBlank(merchantRate.getFlag())){//判断是否走默认的方法
            merchantProductRate = merchantProductRateCheckDao.get(merchantRate.getRateId());//查询 商家产品费率记录
        }else{
            merchantProductRate = merchantProductRateDao.get(merchantRate.getRateId());//查询 商家产品费率
        }
        merchantRate.setProductCode(merchantProductRate.getProductCode());
        merchantRate.setMerchantId(merchantProductRate.getMerchantId());
        merchantRate.setProductName(merchantProductRate.getProductName());
        merchantRate.setBusinessType(merchantProductRate.getBusinessType());
        //获取商户配置的费率信息
        List<MerchantRateNew> merchantRateNews = null;
        if(StringUtils.isNotBlank(merchantRate.getFlag())){//判断是否走默认的方法
            merchantRateNews = merchantRateNewDao.checkFindList(merchantRate);//查询 商户费率详情记录表
        }else{
            merchantRateNews = super.findList(merchantRate);//查询 原商户费率详情表
        }
        List<MerchantRateNew> list = Lists.newArrayList();
        Product product = productDao.selectByCode(merchantRate.getProductCode());
        switch (product.getTrxType()) {
        case "REFUND":
            // 退款 没有
            break;
        case "CHARGE":
        case "WZDRAW":
        case "RENAME":
        case "EPRISE"://企业认证
        case "AUTHEN"://鉴权认证
        case "SHARES":
        case "CACNCE":
        case "TRAFER"://内转
        case "WALLET"://钱包服务
        case "DPTWZD":
            // 充值,提现,实名认证,平级分润,通关申报,存管提现没有银行
            if (null == merchantRateNews || merchantRateNews.isEmpty()) {
                MerchantRateNew merchantRateNew = changeBean(merchantRate);
                list.add(merchantRateNew);
            } else {
                list = merchantRateNews;
            }
            break;
        case "BATPAY":
        case "BATCOL":
        case "DPTPAY":
            // 转账代收 存管支付 区分银行 没有卡类型
            list = getChangeList(merchantRate, "");
            break;
        default:
            //B2B网银支付 快捷支付2 区分银行 没有卡类型
            if(product.getName().indexOf(Constants.B2B) >= 0 || product.getName().indexOf(Constants.QUICKPAY2) >= 0){
                list = getChangeList(merchantRate, "");
                break;
            }else if(product.getName().indexOf(Constants.WECHAT) >= 0 || product.getName().indexOf(Constants.ALIPAY) >= 0 ||
                    product.getName().indexOf(Constants.UNOPAY) >= 0 || product.getName().indexOf(Constants.QUAGRP) >= 0){
                //微信支付宝银联没有银行
                if (null == merchantRateNews || merchantRateNews.isEmpty()) {
                    MerchantRateNew merchantRateNew = changeBean(merchantRate);
                    list.add(merchantRateNew);
                } else {
                    list = merchantRateNews;
                }
            }
            else{//B2C 快捷支付
                //获取产品配置的银行卡类型
                List<MerchantRateBank> merchantRateBanks = null;
                if(StringUtils.isNotBlank(merchantRate.getFlag())){//判断是否走默认的方法
                    merchantRateBanks = merchantRateBankCheckDao.getBankCardType(merchantProductRate.getId());
                }else{
                    merchantRateBanks = merchantRateBankDao.getBankCardType(merchantProductRate.getId());//查询 商户费率详情表
                }

                if(null != merchantRateBanks && !merchantRateBanks.isEmpty()){
                    for(MerchantRateBank merchantRateBank : merchantRateBanks){
                        list.addAll(getChangeList(merchantRate, merchantRateBank.getBankCardType()));
                    }
                }
                break;
            }
            
        }
        list = searchList(list,searchBankNo,searchBankCardType);
        //拼接page
        page.setCount(list.size());
        if (page.getPageNo() * page.getPageSize() > list.size()) {
            page.setList(list.subList((page.getPageNo() - 1) * page.getPageSize(), list.size()));
        } else {
            page.setList(
                    list.subList((page.getPageNo() - 1) * page.getPageSize(), page.getPageNo() * page.getPageSize()));
        }
        return page;
    }

    /**
     * @discription 查询 List(通过转换得到)
     * @author ly
     * @created 2016年10月17日 下午9:08:52
     */
    private List<MerchantRateNew> searchList(List<MerchantRateNew> list, String searchBankNo, String searchBankCardType) {
        List<MerchantRateNew> searchList = Lists.newArrayList();
        if(StringUtils.isBlank(searchBankNo) && StringUtils.isBlank(searchBankCardType)){
            return list;
        }
        for(MerchantRateNew merchantRateNew : list){
            boolean flagBankNo = StringUtils.isBlank(searchBankNo) || searchBankNo.equals(merchantRateNew.getBankNo());
            boolean flagBankCardType = StringUtils.isBlank(searchBankCardType) || searchBankCardType.equals(merchantRateNew.getBankCardType());
            if(flagBankNo && flagBankCardType){
                searchList.add(merchantRateNew);
            }
        }
        return searchList;
    }




    /**
     * @discription 获取费率 List(通过转换得到)
     * @author ly
     * @created 2016年10月17日 下午9:08:52
     * @param merchantRate
     *            费率信息(获取merchantId,productCode,productName)
     * @param bankCardType
     *            卡类型
     */
    private List<MerchantRateNew> getChangeList(MerchantRateNew merchantRate, String bankCardType) {
        List<ProductDetail> productDetails;
        if(StringUtils.isNotBlank(bankCardType)){
            ProductDetail productDetailFind = new ProductDetail();
            productDetailFind.setProductCode(merchantRate.getProductCode());
            productDetailFind.setCardTypeCode(bankCardType);
            //获取通道支持银行列表(包含银行卡类型)
            productDetails = productDetailDao.getProductDetailBank(productDetailFind);
        }else{
            productDetails = productDetailDao.getProductDetailBankNo(merchantRate.getProductCode());
        }
        Product product = productDao.selectByCode(merchantRate.getProductCode());
        boolean isProductDetails = RedisUtil.getRedisUtil().isJudgeChannel(productDetails, product);
        List<MerchantRateNew> list = Lists.newArrayList();
        //判断是否有支持的银行
        if(isProductDetails){
            for (ProductDetail productDetail : productDetails) {
                merchantRate.setBankNo(productDetail.getBankNo());
                //判断bankCardType是否存在 
                if(StringUtils.isNotBlank(bankCardType)){
                    //存在用通道配置的卡类型和银行
                    merchantRate.setBankCardType(productDetail.getCardTypeCode());
                }
                //判断费率是否存在
                MerchantRateNew merchantRateNew = existNoId(merchantRate);
                if (null == merchantRateNew) {
                    //不存在 获取商户配置的默认费率
                    merchantRateNew = changeBean(merchantRate);
                    merchantRateNew.setBankNo(productDetail.getBankNo());
                    merchantRateNew.setBankCardType(bankCardType);
                    merchantRateNew.setBankName(productDetail.getBankName());
                }
                list.add(merchantRateNew);
            }
        }
        return list;
    }

    /**
     * @discription 获取merchantRateNew Bean
     * @author ly
     * @created 2016年10月17日 下午9:46:22
     * @param  //merchantId,productCode,productName,bankCardType费率信息(获取merchantId,productCode,productName,bankCardType)
     * @param  //merchantId,productCode,productName,bankCardType 费率信息(获取merchantId,productCode,productName,bankCardType)
     * @return
     */
    private MerchantRateNew changeBean(MerchantRateNew merchantRate) {

        String merchantId = merchantRate.getMerchantId();
        String productCode = merchantRate.getProductCode();
        String productName = merchantRate.getProductName();
        String bankCardType = merchantRate.getBankCardType();
        String businessType = merchantRate.getBusinessType();
        // 获取默认费率
        MerchantRateNew merchantRateDefaultFind = new MerchantRateNew();
        merchantRateDefaultFind.setProductCode(productCode);
        merchantRateDefaultFind.setBankCardType(bankCardType);
        merchantRateDefaultFind.setBusinessType(businessType);
        MerchantRateNew merchantRateNew = new MerchantRateNew();
        Product product = productDao.selectByCode(productCode);
        switch(product.getTrxType()){
        case "CHARGE":
        case "WZDRAW":
        case "RENAME":
        case "EPRISE"://企业认证
        case "AUTHEN"://鉴权认证
        case "SHARES":
        case "CACNCE":
        case "TRAFER"://内转
        case "WALLET"://钱包服务
        case "DPTWZD":
            // 充值,提现,实名认证,平级分润,通关申报,存管提现没有银行 获取产品默认费率
            merchantRateDefaultFind.setMerchantId(Constants.MERCHANT_DEFAULT_ID);
            MerchantRateDefault merchantRateDefault = getDefault(merchantRateDefaultFind);
            if (null == merchantRateDefault) {
                merchantRateDefault = new MerchantRateDefault();
            }
            // 构造mercchantRateNew
            merchantRateNew.setMerchantId(merchantId);
            merchantRateNew.setProductCode(productCode);
            merchantRateNew.setProductName(productName);
            merchantRateNew.setChargeType(merchantRateDefault.getChargeType());
            merchantRateNew.setChargeFee(merchantRateDefault.getChargeFee());
            merchantRateNew.setChargeRatio(merchantRateDefault.getChargeRatio());
            merchantRateNew.setMaxFee(merchantRateDefault.getMaxFee());
            merchantRateNew.setMinFee(merchantRateDefault.getMinFee());
            break;
        default:
            // 代收转账支付 区分银行  根据默认银行获取商家配置产品所有银行费率
            merchantRateDefaultFind.setMerchantId(merchantId);
            merchantRateDefaultFind.setBankNo(Constants.MERCHANT_RATE_ALL_BANK);
            MerchantRateNew merchantRateNewFind = null;
            //判断是否走默认的方法
            if(StringUtils.isNotBlank(merchantRate.getFlag())){
                merchantRateDefaultFind.setFlag("Y");
            }
            merchantRateNewFind = existNoId(merchantRateDefaultFind);
            merchantRateNew.setMerchantId(merchantId);
            merchantRateNew.setProductCode(productCode);
            merchantRateNew.setProductName(productName);
            if(null != merchantRateNewFind){
                merchantRateNew.setChargeType(merchantRateNewFind.getChargeType());
                merchantRateNew.setMaxFee(merchantRateNewFind.getMaxFee());
                merchantRateNew.setMinFee(merchantRateNewFind.getMinFee());
                merchantRateNew.setChargeFee(merchantRateNewFind.getChargeFee());
                merchantRateNew.setChargeRatio(merchantRateNewFind.getChargeRatio());
                merchantRateNew.setChargeFee2(merchantRateNewFind.getChargeFee2());
                merchantRateNew.setChargeRatio2(merchantRateNewFind.getChargeRatio2());
                merchantRateNew.setChargeFee3(merchantRateNewFind.getChargeFee3());
                merchantRateNew.setChargeRatio3(merchantRateNewFind.getChargeRatio3());
                merchantRateNew.setChargeMax(merchantRateNewFind.getChargeMax());
                merchantRateNew.setChargeMin(merchantRateNewFind.getChargeMin());
                merchantRateNew.setChargeMax2(merchantRateNewFind.getChargeMax2());
                merchantRateNew.setChargeMin2(merchantRateNewFind.getChargeMin2());
                merchantRateNew.setChargeMax3(merchantRateNewFind.getChargeMax3());
                merchantRateNew.setChargeMin3(merchantRateNewFind.getChargeMin3());
                merchantRateNew.setUpdateBy(merchantRateNewFind.getUpdateBy());
            }
            break;
        }
        return merchantRateNew;
    }

      
    /** 
    * @discription 修改
    * @author ly       
    * @created 2016年11月28日 下午3:18:15      
    * @param merchantRateNew
    * @return     
    */  
    @Override
    public MerchantRateNew edit(MerchantRateNew merchantRateNew) {
        if (StringUtils.isNotBlank(merchantRateNew.getId())) {
            return merchantRateNewDao.get(merchantRateNew.getId());
        }
        //将卡类型显示值转为枚举值
        changeEnumBnakCard(merchantRateNew);
        // 获取MerchantProductRate数据
        MerchantProductRate merchantProductRateFind = new MerchantProductRate();
        merchantProductRateFind.setMerchantId(merchantRateNew.getMerchantId());
        merchantProductRateFind.setProductCode(merchantRateNew.getProductCode());
        MerchantProductRate merchantProductRate = merchantProductRateDao.exist(merchantProductRateFind);
        // 从MerchantProductRate中获取productCode,MerchantId,ProductName
        merchantRateNew.setBankNo(merchantRateNew.getBankNo());
        merchantRateNew.setBankCardType(merchantRateNew.getBankCardType());
        merchantRateNew.setProductCode(merchantProductRate.getProductCode());
        merchantRateNew.setMerchantId(merchantProductRate.getMerchantId());
        merchantRateNew.setProductName(merchantProductRate.getProductName());
        // 查询merchantRateNew是否存在
        // 存在返回merchantRateReturn
        MerchantRateNew merchantRateReturn = existNoId(merchantRateNew);
        if (null == merchantRateReturn) {
            // 不存在 通过merchantRateNew构造merchantRateReturn
            merchantRateReturn = changeBean(merchantRateNew);
            merchantRateReturn.setBankCardType(merchantRateNew.getBankCardType());
            merchantRateReturn.setBankNo(merchantRateNew.getBankNo());
            merchantRateReturn.setBankName(merchantRateNew.getBankName());
            merchantRateReturn.setMerchantCompanyName(merchantProductRate.getMerchantCompanyName());
            merchantRateReturn.setBusinessType(merchantProductRate.getBusinessType());
        }
        return merchantRateReturn;
    }

      
    /** 
    * @discription 判断是否存在不判断银行
    * @author ly       
    * @created 2016年12月9日 上午11:48:48      
    * @param merchantRateNewVo
    * @return     
    */  
        
    @Override
    public List<MerchantRateNew> existNoBank(MerchantRateNewVo merchantRateNewVo) {
        List<MerchantRateNew> list = Lists.newArrayList();
        List<String> bankCardTypes = merchantRateNewVo.getBankCardType();
        if(null != bankCardTypes && !bankCardTypes.isEmpty()){
            for(String bankCardType : bankCardTypes){
                MerchantRateNew merchantRateNew = new MerchantRateNew();
                merchantRateNew.setId(merchantRateNewVo.getId());
                merchantRateNew.setBankCardType(bankCardType);
                merchantRateNew.setMerchantId(merchantRateNewVo.getMerchantId());
                merchantRateNew.setProductCode(merchantRateNewVo.getProductCode());
                merchantRateNew.setBusinessType(merchantRateNewVo.getBusinessType());
                merchantRateNew = merchantRateNewDao.existNoBankCheck(merchantRateNew);
                if(null != merchantRateNew){
                    list.add(merchantRateNew);
                }
            }
        }else{
            MerchantRateNew merchantRateNew = new MerchantRateNew();
            merchantRateNew.setId(merchantRateNewVo.getId());
            merchantRateNew.setMerchantId(merchantRateNewVo.getMerchantId());
            merchantRateNew.setProductCode(merchantRateNewVo.getProductCode());
            merchantRateNew.setBusinessType(merchantRateNewVo.getBusinessType());
            merchantRateNew = merchantRateNewDao.existNoBank(merchantRateNew);
            if(null != merchantRateNew){
                list.add(merchantRateNew);
            }
        }
        return list;
    }
    
      
    /**     
    * @discription 保存MerhchantProductRate方法
    * @author ly     
    * @created 2016年12月21日 下午4:12:55     
    * @param entity
    * @param flag     
    */
    public void toSaveMerchantProductRate(MerchantProductRate entity,boolean flag) {
        if (entity.getIsNewRecord()){
            entity.setUserOrDate();
            if(StringUtils.isBlank(entity.getId())){
                entity.setId(null);
            }
            merchantProductRateCheckDao.insert(entity);
        }else{
            entity.preUpdate();
            merchantProductRateCheckDao.update(entity);
        }
        String businessType = entity.getBusinessType();
        if(!Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,
                Constants.SYS_COMMON,""))){
            businessType = RateBusinessType.INTERNETPAY.getValue();
        }
        //保存缓存
        if(!entity.getMerchantId().equals(Constants.MERCHANT_DEFAULT_ID)){
            RedisUtil.getRedisUtil().saveMerchantProductRedis(entity.getMerchantId(),
                    entity.getProductCode(), true, false, businessType);
        }
    }
    
      
    /**     
    * @discription 保存MerhcantRateBank方法
    * @author ly     
    * @created 2016年12月21日 下午4:13:22     
    * @param entity
    * @param flag     
    */
    public void toSaveMerchantRateBank(MerchantRateBank entity,boolean flag) {
        MerchantProductRate merchantProductRate = merchantProductRateCheckDao.get(entity.getRateId());
        //修改审核状态为待审核
        merchantProductRateCheckDao.uodateRateAudit(merchantProductRate.getId(),"S");
        MerchantRateBank entityFind = merchantRateBankCheckDao.getRateBank(entity);
        if (null == entityFind){
            entity.setUserOrDate();
            if(StringUtils.isBlank(entity.getId())){
                entity.setId(null);
            }
            merchantRateBankCheckDao.insert(entity);
            toSaveMerchantRateLog(merchantProductRate,entity,OperationType.SAVE.getValue());
        }else{
            entity.setId(entityFind.getId());
            //获取旧数据
            MerchantRateBank originalObject = merchantRateBankCheckDao.get(entity.getId());
            List<String> emails = notificationEmailDao.queryList();
            String toEmail = listToString(emails, ',');
            String content = "用户(id:name)"+ UserUtils.getUser().getId() + ":" + UserUtils.getUser().getName()+"在时间："
                    + com.heepay.common.util.DateUtil.getTodayYYYYMMDD_HHMMSS() + "修改了商户ID为："+ merchantProductRate.getMerchantId() +
                    "，产品《" + merchantProductRate.getProductName() +"》的费率；" +
                    table(originalObject,entity).replace("null","");
            boolean isSuccess = SendMailUtil.getInstance().sendMail(toEmail,environment + "费率修改通知",content,null);
            logger.info("费率修改发送邮件通知结果{}。发送内容为{}",isSuccess,content);
            entity.preUpdate();
            merchantRateBankCheckDao.update(entity);
            toSaveMerchantRateLog(merchantProductRate,entity,OperationType.UPDATE.getValue());
        }
    }

    /**
     * 获取展示修改信息的table
     * @param originalObject
     * @param entity
     * @return
     */
    private String table(MerchantRateBank originalObject,MerchantRateBank entity) {
        // 获取银行卡类型，如果有就转换中文，没有就显示""
        String bankCardType = "";
        if(StringUtils.isNotBlank(originalObject.getBankCardType())){
            bankCardType = RateBankcardType.getLabel(originalObject.getBankCardType());
        }
        // 修改前后都是    按笔数
        if(CostType.COUNT.getValue().equals(originalObject.getChargeType()) &&
                CostType.COUNT.getValue().equals(entity.getChargeType())){
            return "</br>计费类型：按笔数 ---> 按笔数</br>" + "银行卡类型：" + bankCardType + "</br>银行名称：" + originalObject.getBankName()+"</br>" +
                        "<table border=\"1\" cellspacing=\"0\">\n" +
                        "  <tr>\n" +
                        "    <th></th>\n" +
                        "    <th>修改前</th>\n" +
                        "    <th>修改后</th>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>手续费费用</td>\n" +
                        "    <td>区间" + originalObject.getChargeMin() + "-" + originalObject.getChargeMax() + "值" + originalObject.getChargeFee() + "</td>\n" +
                        "    <td>区间" + entity.getChargeMin() + "-" + entity.getChargeMax() + "值" + entity.getChargeFee() + "</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>手续费费用2</td>\n" +
                        "    <td>区间" + originalObject.getChargeMin2() + "-" + originalObject.getChargeMax2() + "值" + originalObject.getChargeFee2() + "</td>\n" +
                        "    <td>区间" + entity.getChargeMin2() + "-" + entity.getChargeMax2() + "值" + entity.getChargeFee2() + "</td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td>手续费费用3</td>\n" +
                        "    <td>区间" + originalObject.getChargeMin3() + "-" + originalObject.getChargeMax3() + "值" + originalObject.getChargeFee3() + "</td>\n" +
                        "    <td>区间" + entity.getChargeMin3() + "-" + entity.getChargeMax3() + "值" + entity.getChargeFee3() + "</td>\n" +
                        "  </tr>\n" +
                        "</table>";
            // 修改前后都是    按比例
        }else if(CostType.RATE.getValue().equals(originalObject.getChargeType()) &&
                CostType.RATE.getValue().equals(entity.getChargeType())){
            return  "</br>计费类型：按比例 ---> 按比例</br>" + "银行卡类型：" + bankCardType + "</br>银行名称：" + originalObject.getBankName()+"</br>" +
                    "<table border=\"1\" cellspacing=\"0\">\n" +
                    "  <tr>\n" +
                    "    <th></th>\n" +
                    "    <th>修改前</th>\n" +
                    "    <th>修改后</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin() + "-" + originalObject.getChargeMax() + "值" + originalObject.getChargeRatio() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin() + "-" + entity.getChargeMax() + "值" + entity.getChargeRatio() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用2</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin2() + "-" + originalObject.getChargeMax2() + "值" + originalObject.getChargeRatio2() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin2() + "-" + entity.getChargeMax2() + "值" + entity.getChargeRatio2() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用3</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin3() + "-" + originalObject.getChargeMax3() + "值" + originalObject.getChargeRatio3() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin3() + "-" + entity.getChargeMax3() + "值" + entity.getChargeRatio3() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>最小值</td>\n" +
                    "    <td>" + originalObject.getMinFee() + "</td>\n" +
                    "    <td>" + entity.getMinFee() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>最大值</td>\n" +
                    "    <td>" + originalObject.getMaxFee() + "</td>\n" +
                    "    <td>" + entity.getMaxFee() + "</td>\n" +
                    "  </tr>\n" +
                    "</table>";
            // 改变计费类型     按比例  --->   按笔数
        }else if(!originalObject.getChargeType().equals(entity.getChargeType()) && CostType.RATE.getValue().equals(originalObject.getChargeType())){
            return "</br>计费类型：" + CostType.labelOf(originalObject.getChargeType()) +
                    " ---> <font color=\"#FF0000\">" + CostType.labelOf(entity.getChargeType()) +
                    "</font></br>" + "银行卡类型：" + bankCardType + "</br>银行名称：" + originalObject.getBankName()+"</br>" +
                    "<table border=\"1\" cellspacing=\"0\">\n" +
                    "  <tr>\n" +
                    "    <th></th>\n" +
                    "    <th>修改前</th>\n" +
                    "    <th>修改后</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin() + "-" + originalObject.getChargeMax() + "值" + originalObject.getChargeRatio() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin() + "-" + entity.getChargeMax() + "值" + entity.getChargeFee() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用2</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin2() + "-" + originalObject.getChargeMax2() + "值" + originalObject.getChargeRatio2() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin2() + "-" + entity.getChargeMax2() + "值" + entity.getChargeFee2() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用3</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin3() + "-" + originalObject.getChargeMax3() + "值" + originalObject.getChargeRatio3() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin3() + "-" + entity.getChargeMax3() + "值" + entity.getChargeFee3() + "</td>\n" +
                    "  </tr>\n" +
                    "</table>";
            // 改变计费类型     按笔数  --->   按比例
        }else if(!originalObject.getChargeType().equals(entity.getChargeType()) && CostType.COUNT.getValue().equals(originalObject.getChargeType())){
            return "</br>计费类型：" + CostType.labelOf(originalObject.getChargeType()) +
                    " ---> <font color=\"#FF0000\">" + CostType.labelOf(entity.getChargeType()) +
                    "</font></br>" + "银行卡类型：" + bankCardType + "</br>银行名称：" + originalObject.getBankName()+"</br>" +
                    "<table border=\"1\" cellspacing=\"0\">\n" +
                    "  <tr>\n" +
                    "    <th></th>\n" +
                    "    <th>修改前</th>\n" +
                    "    <th>修改后</th>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin() + "-" + originalObject.getChargeMax() + "值" + originalObject.getChargeFee() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin() + "-" + entity.getChargeMax() + "值" + entity.getChargeRatio() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用2</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin2() + "-" + originalObject.getChargeMax2() + "值" + originalObject.getChargeFee2() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin2() + "-" + entity.getChargeMax2() + "值" + entity.getChargeRatio2() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>手续费费用3</td>\n" +
                    "    <td>区间" + originalObject.getChargeMin3() + "-" + originalObject.getChargeMax3() + "值" + originalObject.getChargeFee3() + "</td>\n" +
                    "    <td>区间" + entity.getChargeMin3() + "-" + entity.getChargeMax3() + "值" + entity.getChargeRatio3() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>最小值</td>\n" +
                    "    <td>" + originalObject.getMinFee() + "</td>\n" +
                    "    <td>" + entity.getMinFee() + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>最大值</td>\n" +
                    "    <td>" + originalObject.getMaxFee() + "</td>\n" +
                    "    <td>" + entity.getMaxFee() + "</td>\n" +
                    "  </tr>\n" +
                    "</table>";
        }
        return null;
    }

    /**
     * 把list变成用 ,链接的字符串
     * @param list          list
     * @param separator     分隔符
     * @return
     */
    private String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        // 没配置发给ngp@9186.com
        if(list.isEmpty()){
            return "ngp@9186.com";
        }else{
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
    }
      
    /**     
    * @discription 保存MerchantRateLog方法
    * @author ly     
    * @created 2016年12月23日 下午5:06:23     
    * @param merchantProductRate
    * @param merchantRateBank
    * @param operationType     
    */
    private void toSaveMerchantRateLog(MerchantProductRate merchantProductRate, MerchantRateBank merchantRateBank,
            String operationType) {
        MerchantRateLog merchantRateLog = toChangeMerchantRateLog(merchantProductRate,merchantRateBank,operationType);
        merchantRateLog.setId(null);
        merchantRateLogDao.insert(merchantRateLog);
    }



      
    /**     
    * @discription 转换MerchantRateLog
    * @author ly     
    * @created 2016年12月23日 下午5:06:42     
    * @param merchantProductRate
    * @param merchantRateBank
    * @param operationType
    * @return     
    */
    private MerchantRateLog toChangeMerchantRateLog(MerchantProductRate merchantProductRate,
            MerchantRateBank merchantRateBank, String operationType) {
        MerchantRateLog merchantRateLog = new MerchantRateLog();
        merchantRateLog.setAutographKey(merchantProductRate.getAutographKey());
        merchantRateLog.setAutographType(merchantProductRate.getAutographType());
        merchantRateLog.setBackUrl(merchantProductRate.getBackUrl());
        merchantRateLog.setBankCardType(merchantRateBank.getBankCardType());
        merchantRateLog.setBankName(merchantRateBank.getBankName());
        merchantRateLog.setBankNo(merchantRateBank.getBankNo());
        merchantRateLog.setBankRateId(merchantRateBank.getId());//merchant_bank_rate表Id
        merchantRateLog.setBusinessType(merchantProductRate.getBusinessType());
        merchantRateLog.setChargeCollectionType(merchantProductRate.getChargeCollectionType());
        merchantRateLog.setChargeDeductType(merchantProductRate.getChargeDeductType());
        merchantRateLog.setChargeFee(merchantRateBank.getChargeFee());
        merchantRateLog.setChargeRatio(merchantRateBank.getChargeRatio());
        merchantRateLog.setChargeSource(merchantProductRate.getChargeSource());
        merchantRateLog.setChargeType(merchantRateBank.getChargeType());
        merchantRateLog.setIpDomain(merchantProductRate.getIpDomain());
        merchantRateLog.setIsRefundable(merchantProductRate.getIsRefundable());
        merchantRateLog.setMaxFee(merchantRateBank.getMaxFee());
        merchantRateLog.setMerchantId(merchantProductRate.getMerchantId());
        merchantRateLog.setMinFee(merchantRateBank.getMinFee());
        merchantRateLog.setNotifyUrl(merchantProductRate.getNotifyUrl());
        merchantRateLog.setProductCode(merchantProductRate.getProductCode());
        merchantRateLog.setProductName(merchantProductRate.getProductName());
        merchantRateLog.setRateId(merchantProductRate.getId());//merchant_product_rate表Id
        merchantRateLog.setRuleBeginTime(merchantProductRate.getRuleBeginTime());
        merchantRateLog.setRuleEndTime(merchantProductRate.getRuleEndTime());
        merchantRateLog.setSettlementTo(merchantProductRate.getSettlementTo());
        merchantRateLog.setSettleType(merchantProductRate.getSettleType());
        merchantRateLog.setStatus(merchantProductRate.getStatus());
        merchantRateLog.setOperationType(operationType);
        merchantRateLog.setUserOrDate();
        return merchantRateLog;
    }

    /**
     * @discription 判断是否大于通道成本
     * @author ly
     * @created 2017年2月14日15:12:35
     * @param merchantRateNew
     * @return
     */
    public Map<String,String> isOverCost(MerchantRateNew merchantRateNew) {
        Product product = productDao.selectByCode(merchantRateNew.getProductCode());
        Map<String,String> map = new HashMap<>();
        boolean flag = false;
        BigDecimal decimal = new BigDecimal("0");
         // 实名认证,企业认证,鉴权认证
        if("RENAME".equals(product.getTrxType()) || "EPRISE".equals(product.getTrxType())
                || "AUTHEN".equals(product.getTrxType())){
            if(CostType.COUNT.getValue().equals(merchantRateNew.getChargeType())){
                BigDecimal cost = certifyChannelDao.getMaxCost(merchantRateNew.getProductCode());
                flag = merchantRateNew.getChargeFee().compareTo(cost) == -1;
                decimal = cost;
            }
        }else{//其他产品
            if(StringUtils.isNotBlank(merchantRateNew.getChargeType())){
                String bankCardType = merchantRateNew.getBankCardType();
                if(StringUtils.isBlank(bankCardType)){
                    bankCardType = BankcardType.SAVINGCARD.getValue();
                }
                BigDecimal cost = productDetailDao.getMaxCost(merchantRateNew.getProductCode(),
                        merchantRateNew.getChargeType(),bankCardType);
                if(null != cost){
                    if(CostType.COUNT.getValue().equals(merchantRateNew.getChargeType())){
                        flag = merchantRateNew.getChargeFee().compareTo(cost) == -1;
                        decimal = cost;
                    }else{
                        flag = merchantRateNew.getChargeRatio().multiply(new BigDecimal("10")).compareTo(cost) == -1;
                        decimal = cost;
                    }
                }
            }
        }
        map.put("flag",String.valueOf(flag));
        map.put("cost",decimal.toString());
        return map;
    }
}