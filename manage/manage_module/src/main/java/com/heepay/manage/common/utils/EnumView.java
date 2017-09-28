package com.heepay.manage.common.utils;

import java.util.List;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountStatus;
import com.heepay.enums.AccountType;
import com.heepay.enums.AuthenticationStatus;
import com.heepay.enums.BankcardType;
import com.heepay.enums.BusinessType;
import com.heepay.enums.CertificateType;
import com.heepay.enums.ChargeCollectionType;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.ChargeSource;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.CostType;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.MerchantType;
import com.heepay.enums.RateBankcardType;
import com.heepay.enums.RateBusinessType;
import com.heepay.enums.RemitStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.enums.SettlePeriod;
import com.heepay.enums.SettleType;
import com.heepay.enums.SettlementTo;
import com.heepay.manage.common.enums.*;
import com.heepay.manage.modules.merchant.utils.ProductTrxTypeUtils;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateLog;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.merchant.vo.ProductNetGroup;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;
import com.heepay.manage.modules.notification.entity.NotificationEmail;
import com.heepay.manage.modules.route.entity.BankCardBin;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.CertifyChannel;
import com.heepay.manage.modules.route.entity.ChannelBankid;
import com.heepay.manage.modules.route.entity.IntegrationChannel;
import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.merchant.vo.*;
import com.heepay.manage.modules.route.entity.*;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**          
* 
* 描    述：view显示枚举类的值和内容转换
*
* 创 建 者： ly
* 创建时间： 2016年12月24日 下午4:47:54 
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
*/      
    
public class EnumView {

      
    /**     
    * @discription 产品list方法显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:47:59     
    * @param products
    * @return     
    */
    public static List<Product> productList(List<Product> products) {
        List<Product> list = Lists.newArrayList();
        for (Product product : products) {
            if (StringUtils.isNotBlank(product.getAuditStatus())) {
                product.setAuditStatus(RouteStatus.labelOf(product.getAuditStatus()));
            }
            if (StringUtils.isNotBlank(product.getStatus())) {
                product.setStatus(CommonStatus.labelOf(product.getStatus()));
            }
            if (StringUtils.isNotBlank(product.getBusinessType())) {
                product.setBusinessType(RateBusinessType.labelOf(product.getBusinessType()));
            }
            if (StringUtils.isNotBlank(product.getTrxType())) {
                product.setTrxType(ProductTrxTypeUtils.getProductTrxTypeName(product.getTrxType()));
            }
            list.add(product);
        }
        return list;
    }

      
    /**     
    * @discription 结算周期List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:48:19     
    * @param settleCycleManages
    * @return     
    */
    public static List<SettleCycleManage> settleCycleManageList(List<SettleCycleManage> settleCycleManages) {
        List<SettleCycleManage> list = Lists.newArrayList();
        for (SettleCycleManage settleCycleManage : settleCycleManages) {
            if (StringUtils.isNotBlank(settleCycleManage.getStatus())) {
                settleCycleManage.setStatus(CommonStatus.labelOf(settleCycleManage.getStatus()));
            }
            list.add(settleCycleManage);
        }
        return list;
    }

      
    /**     
    * @discription 技术签约List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:48:44     
    * @param merchantAutographInfos
    * @return     
    */
    public static List<MerchantAutographInfo> merchantAutographInfoList(
            List<MerchantAutographInfo> merchantAutographInfos) {
        List<MerchantAutographInfo> list = Lists.newArrayList();
        for (MerchantAutographInfo merchantAutographInfo : merchantAutographInfos) {
            if (StringUtils.isNotBlank(merchantAutographInfo.getStatus())) {
                merchantAutographInfo.setStatus(CommonStatus.labelOf(merchantAutographInfo.getStatus()));
            }
            if (StringUtils.isNotBlank(merchantAutographInfo.getAutographKey())) {
                Integer length = merchantAutographInfo.getAutographKey().length();
                merchantAutographInfo.setAutographKey(merchantAutographInfo.getAutographKey().substring(0, 6) + "****"
                        + merchantAutographInfo.getAutographKey().substring(length - 4, length));
            }
            list.add(merchantAutographInfo);
        }
        return list;
    }

      
    /**     
    * @discription 商户List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:49:32     
    * @param merchants
    * @return     
    */
    public static List<Merchant> merchant(List<Merchant> merchants) {
        List<Merchant> list = Lists.newArrayList();
        for (Merchant merchant : merchants) {
            if (StringUtils.isNotBlank(merchant.getLegalAuditStatus())) {
                merchant.setLegalAuditStatus(RouteStatus.labelOf(merchant.getLegalAuditStatus()));
            }
            if (StringUtils.isNotBlank(merchant.getRcAuditStatus())) {
                merchant.setRcAuditStatus(RouteStatus.labelOf(merchant.getRcAuditStatus()));
            }
            if (StringUtils.isNotBlank(merchant.getType())) {
                merchant.setType(MerchantType.labelOf(merchant.getType()));
            }
            if (StringUtils.isNotBlank(merchant.getCertificateType())) {
                merchant.setCertificateType(CertificateType.labelOf(merchant.getCertificateType()));
            }
            list.add(merchant);
        }
        return list;
    }

      
    /**     
    * @discription 打款认证List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:49:47     
    * @param merchantBankCardAuthentications
    * @return     
    */
    public static List<MerchantBankCardAuthentication> merchantBankCardAuthenticationController(
            List<MerchantBankCardAuthentication> merchantBankCardAuthentications) {
        List<MerchantBankCardAuthentication> list = Lists.newArrayList();
        for (MerchantBankCardAuthentication merchantBankCardAuthentication : merchantBankCardAuthentications) {
            if (StringUtils.isNotBlank(merchantBankCardAuthentication.getPayStatus())) {
                merchantBankCardAuthentication
                        .setPayStatus(RemitStatus.labelOf(merchantBankCardAuthentication.getPayStatus()));
            }
            if (StringUtils.isNotBlank(merchantBankCardAuthentication.getAuthenticationStatus())) {
                merchantBankCardAuthentication.setAuthenticationStatus(
                        AuthenticationStatus.labelOf(merchantBankCardAuthentication.getAuthenticationStatus()));
            }
            if (StringUtils.isNotBlank(merchantBankCardAuthentication.getBankNo())) {
                merchantBankCardAuthentication
                        .setBankNo(RandomUtil.getBankNo(merchantBankCardAuthentication.getBankNo()));
            }
            list.add(merchantBankCardAuthentication);
        }
        return list;
    }

      
    /**     
    * @discription 银行卡List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:50:01     
    * @param bankInfos
    * @return     
    */
    public static List<BankInfo> BankInfo(List<BankInfo> bankInfos) {
        List<BankInfo> list = Lists.newArrayList();
        for (BankInfo bankInfo : bankInfos) {
            if (StringUtils.isNotBlank(bankInfo.getStatus())) {
                bankInfo.setStatus(CommonStatus.labelOf(bankInfo.getStatus()));
            }
            if (null != bankInfo.getUpdateBy()){
                if(StringUtils.isNotBlank(bankInfo.getUpdateBy().getId())) {
                    bankInfo.setUpdateName(UserUtils.get(bankInfo.getUpdateBy().getId()).getName());
                }
            }
            list.add(bankInfo);
        }
        return list;
    }

    /**
     * @discription 银行卡List显示枚举类的值和内容转换
     * @author ly
     * @created 2016年12月24日 下午4:50:01
     * @param productDetails
     * @return
     */
    public static List<ProductDetail> ProductDetail(List<ProductDetail> productDetails) {
        List<ProductDetail> list = Lists.newArrayList();
        for (ProductDetail productDetail : productDetails) {
            if (null != productDetail.getCreateBy()){
                if(StringUtils.isNotBlank(productDetail.getCreateBy().getId())) {
                    productDetail.setCreateName(UserUtils.get(productDetail.getUpdateBy().getId()).getName());
                }
            }
            list.add(productDetail);
        }
        return list;
    }

    /**     
    * @discription 联行号显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:50:14     
    * @param lineBankNumbers
    * @return     
    */
    public static List<LineBankNumber> LineBankNumber(List<LineBankNumber> lineBankNumbers) {
        List<LineBankNumber> list = Lists.newArrayList();
        for (LineBankNumber lineBankNumber : lineBankNumbers) {
            if (StringUtils.isNotBlank(lineBankNumber.getStatus())) {
                lineBankNumber.setStatus(CommonStatus.labelOf(lineBankNumber.getStatus()));
            }
            if (null != lineBankNumber.getUpdateBy()){
                if(StringUtils.isNotBlank(lineBankNumber.getUpdateBy().getId())) {
                    lineBankNumber.setUpdateName(UserUtils.get(lineBankNumber.getUpdateBy().getId()).getName());
                }
            }
            list.add(lineBankNumber);
        }
        return list;
    }

      
    /**     
    * @discription 支付通道List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:50:25     
    * @param payChannels
    * @return     
    */
    public static List<PayChannel> PayChannel(List<PayChannel> payChannels) {
        List<PayChannel> list = Lists.newArrayList();
        for (PayChannel payChannel : payChannels) {
            if (StringUtils.isNotBlank(payChannel.getCostType())) {
                payChannel.setCostType(CostType.labelOf(payChannel.getCostType()));
            }
            if (StringUtils.isNotBlank(payChannel.getStatus())) {
                payChannel.setStatus(CommonStatus.labelOf(payChannel.getStatus()));
            }
            if (null != payChannel.getUpdateBy()){
                if(StringUtils.isNotBlank(payChannel.getUpdateBy().getId())) {
                    payChannel.setUpdateName(UserUtils.get(payChannel.getUpdateBy().getId()).getName());
                }
            }
            if ((com.heepay.manage.common.utils.Constants.DEFAULT_SORT).equals(payChannel.getSort())) {
                payChannel.setSort("默认");
            } else {
                payChannel.setSort("无");
            }
            //新增批量修改通道合作方枚举类的值和内容转换
            if (StringUtils.isNotBlank(payChannel.getSettleType())) {
                payChannel.setSettleType(SettleType.labelOf(payChannel.getSettleType()));
            }
            if (StringUtils.isNotBlank(payChannel.getSettlePeriod())) {
                payChannel.setSettlePeriod(SettlePeriod.labelOf(payChannel.getSettlePeriod()));
            }
            if (StringUtils.isNotBlank(payChannel.getBusinessType())) {
                payChannel.setBusinessType(BusinessType.labelOf(payChannel.getBusinessType()));
            }
            if (StringUtils.isNotBlank(payChannel.getAccountType())) {
                payChannel.setAccountType(AccountType.labelOf(payChannel.getAccountType()));
            }
            if (StringUtils.isNotBlank(payChannel.getChargeDeductType())) {
                payChannel.setChargeDeductType(ChargeDeductType.labelOf(payChannel.getChargeDeductType()));
            }
            //手续费退还方式、是否退还手续费、通道结算周期
            if (StringUtils.isNotBlank(payChannel.getChargeReturnTag())) {
                if((com.heepay.manage.common.utils.Constants.DEFAULT_RETURN_TAG).equals(payChannel.getChargeReturnTag())){
                    payChannel.setChargeReturnTag("是");
                }else {
                    payChannel.setChargeReturnTag("否");
                }
            }
            if (StringUtils.isNotBlank(payChannel.getOrderSettlePeriod())) {
                if((com.heepay.manage.common.utils.Constants.DEFAULT_RETURN_TAG).equals(payChannel.getOrderSettlePeriod())){
                    payChannel.setOrderSettlePeriod("T+1");
                }else {
                    payChannel.setOrderSettlePeriod("T+0");
                }
            }
            //通道合作方、支付类型、银行卡类型转换
            if(StringUtils.isNotBlank(payChannel.getChannelPartnerCode())){
                payChannel.setChannelPartnerName(DictUtils.getDictLabel(payChannel.getChannelPartnerCode(),
                        com.heepay.manage.common.utils.Constants.CHANNEL_PARTNER, ""));
            }
            if(StringUtils.isNotBlank(payChannel.getChannelTypeCode())){
                payChannel.setChannelTypeName(DictUtils.getDictLabel(payChannel.getChannelTypeCode(),
                        com.heepay.manage.common.utils.Constants.CHANNEL_TYPE, ""));
            }
            if(StringUtils.isNotBlank(payChannel.getCardTypeCode())) {
                payChannel.setCardTypeName(BankcardType.labelOf(payChannel.getCardTypeCode()));
            }
            list.add(payChannel);
        }
        return list;
    }

      
    /**     
    * @discription 实名认证通道显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:50:47     
    * @param certifyChannels
    * @return     
    */
    public static List<CertifyChannel> certifyChannelList(List<CertifyChannel> certifyChannels) {
        List<CertifyChannel> list = Lists.newArrayList();
        for (CertifyChannel certifyChannel : certifyChannels) {
            if (StringUtils.isNotBlank(certifyChannel.getSettleType())) {
                certifyChannel.setSettleType(SettleType.labelOf(certifyChannel.getSettleType()));
            }
            if (StringUtils.isNotBlank(certifyChannel.getSettlePeriod())) {
                certifyChannel.setSettlePeriod(SettlePeriod.labelOf(certifyChannel.getSettlePeriod()));
            }
            if (null != certifyChannel.getUpdateBy()){
                if(StringUtils.isNotBlank(certifyChannel.getUpdateBy().getId())) {
                    certifyChannel.setUpdateName(UserUtils.get(certifyChannel.getUpdateBy().getId()).getName());
                }
            }
            list.add(certifyChannel);
        }
        return list;
    }

      
    /**     
    * @discription 显示商户List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:50:59     
    * @param merchantUsers
    * @return     
    */
    public static List<MerchantUser> merchantUserShow(List<MerchantUser> merchantUsers) {
        List<MerchantUser> list = Lists.newArrayList();
        for (MerchantUser merchantUser : merchantUsers) {
            if (StringUtils.isNotBlank(merchantUser.getStatus())) {
                merchantUser.setStatus(AccountStatus.labelOf(merchantUser.getStatus()));
            }
            if (StringUtil.isBlank(merchantUser.getMobile())) {
                merchantUser.setMobile("未绑定");
            }
            list.add(merchantUser);
        }
        return list;
    }

      
    /**     
    * @discription 显示商户对象显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:51:43     
    * @param merchantUser
    * @return     
    */
    public static MerchantUser merchantUserShow(MerchantUser merchantUser) {
        if (null != merchantUser) {
            if (StringUtil.notBlank(merchantUser.getStatus())) {
                merchantUser.setStatus(AccountStatus.labelOf(merchantUser.getStatus()));
            }
            if (StringUtil.isBlank(merchantUser.getMobile())) {
                merchantUser.setMobile("未绑定");
            }
        }
        return merchantUser;
    }

      
    /**     
    * @discription 卡binList显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:52:19     
    * @param bankCardBins
    * @return     
    */
    public static List<BankCardBin> bankCardBin(List<BankCardBin> bankCardBins) {
        List<BankCardBin> list = Lists.newArrayList();
        for (BankCardBin bankCardBin : bankCardBins) {
            if (StringUtils.isNotBlank(bankCardBin.getCardType())) {
                bankCardBin.setCardType(BankcardType.labelOf(bankCardBin.getCardType()));
                bankCardBin.setStatus(CommonStatus.labelOf(bankCardBin.getStatus()));
            }
            if (null != bankCardBin.getUpdateBy()){
                if(StringUtils.isNotBlank(bankCardBin.getUpdateBy().getId())) {
                    bankCardBin.setUpdateName(UserUtils.get(bankCardBin.getUpdateBy().getId()).getName());
                }
            }
            list.add(bankCardBin);
        }
        return list;
    }

      
    /**     
    * @discription 转换Merchant显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:52:32     
    * @param merchant
    * @return     
    */
    public static Merchant changeMerchant(Merchant merchant) {
        if (StringUtils.isNotBlank(merchant.getType())) {
            merchant.setType(MerchantType.labelOf(merchant.getType()));
        }
        if (StringUtils.isNotBlank(merchant.getCertificateType())) {
            merchant.setCertificateType(CertificateType.labelOf(merchant.getCertificateType()));
        }
        if (StringUtils.isNotBlank(merchant.getPermitsAccounts())) {
            merchant.setPermitsAccounts(RandomUtil.getFastDfs(merchant.getPermitsAccounts()));
        }
        if (StringUtils.isNotBlank(merchant.getLegalCertificateFront())) {
            merchant.setLegalCertificateFront(RandomUtil.getFastDfs(merchant.getLegalCertificateFront()));
        }
        if (StringUtils.isNotBlank(merchant.getLegalCertificateBack())) {
            merchant.setLegalCertificateBack(RandomUtil.getFastDfs(merchant.getLegalCertificateBack()));
        }
        if (StringUtils.isNotBlank(merchant.getTaxRegistrationCertificate())) {
            merchant.setTaxRegistrationCertificate(RandomUtil.getFastDfs(merchant.getTaxRegistrationCertificate()));
        }
        if (StringUtils.isNotBlank(merchant.getOrganizationCodeCertificate())) {
            merchant.setOrganizationCodeCertificate(RandomUtil.getFastDfs(merchant.getOrganizationCodeCertificate()));
        }
        if (StringUtils.isNotBlank(merchant.getBusinessLicenseFile())) {
            merchant.setBusinessLicenseFile(RandomUtil.getFastDfs(merchant.getBusinessLicenseFile()));
        }
        if (StringUtils.isNotBlank(merchant.getContactorCertificateBack())) {
            merchant.setContactorCertificateBack(RandomUtil.getFastDfs(merchant.getContactorCertificateBack()));
        }
        if (StringUtils.isNotBlank(merchant.getContactorCertificateFront())) {
            merchant.setContactorCertificateFront(RandomUtil.getFastDfs(merchant.getContactorCertificateFront()));
        }
        if(StringUtils.isNotBlank(merchant.getAuthenticationStatus())){
            merchant.setAuthenticationStatus(DictUtils.getDictLabel(merchant.getAuthenticationStatus(),
                    "AuthenticationStatus",""));
        }
        if(StringUtils.isNotBlank(merchant.getPhoneAuthStatus())){
            merchant.setPhoneAuthStatus(PhoneAuthStatus.labelOf(merchant.getPhoneAuthStatus()));
        }
        return merchant;
    }

      
    /**     
    * @discription 转换卡bin显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:52:47     
    * @param bankCardBin
    * @return     
    */
    public static BankCardBin bankCardBinShow(BankCardBin bankCardBin) {
        if (StringUtils.isNotBlank(bankCardBin.getCardType())) {
            bankCardBin.setCardType(BankcardType.labelOf(bankCardBin.getCardType()));
        }
        if (StringUtils.isNotBlank(bankCardBin.getStatus())) {
            bankCardBin.setStatus(CommonStatus.labelOf(bankCardBin.getStatus()));
        }
        return bankCardBin;
    }

      
    /**     
    * @discription 转换卡信息显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:53:04     
    * @param bankInfo
    * @return     
    */
    public static BankInfo bankInfoShow(BankInfo bankInfo) {
        if (StringUtils.isNotBlank(bankInfo.getStatus())) {
            bankInfo.setStatus(CommonStatus.labelOf(bankInfo.getStatus()));
        }
        return bankInfo;
    }

      
    /**     
    * @discription 转换显示产品显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:53:21     
    * @param product
    * @return     
    */
    public static Product productShow(Product product) {
        if (StringUtils.isNotBlank(product.getStatus())) {
            product.setStatus(CommonStatus.labelOf(product.getStatus()));
        }
        return product;
    }

    /**
     * 转换商户显示枚举类的值和内容转换
     * @author N.W
     * @param merchant
     * @return
     */
    public static Merchant merchantShow(Merchant merchant) {
        if (StringUtils.isNotBlank(merchant.getUserStatus())) {
            merchant.setUserStatus(MerchantStatus.labelOf(merchant.getUserStatus()));
        }
        return merchant;
    }


    /**     
    * @discription 转换显示支付通道显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:53:36     
    * @param payChannel
    * @return     
    */
    public static PayChannel payChannelShow(PayChannel payChannel) {
        if (StringUtils.isNotBlank(payChannel.getCostType())) {
            payChannel.setCostType(CostType.labelOf(payChannel.getCostType()));
        }
        if (StringUtils.isNotBlank(payChannel.getSettleType())) {
            payChannel.setSettleType(SettleType.labelOf(payChannel.getSettleType()));
        }
        if (StringUtils.isNotBlank(payChannel.getSettlePeriod())) {
            payChannel.setSettlePeriod(SettlePeriod.labelOf(payChannel.getSettlePeriod()));
        }
        if (StringUtils.isNotBlank(payChannel.getBusinessType())) {
            payChannel.setBusinessType(BusinessType.labelOf(payChannel.getBusinessType()));
        }
        if (StringUtils.isNotBlank(payChannel.getAccountType())) {
            payChannel.setAccountType(AccountType.labelOf(payChannel.getAccountType()));
        }
        if (StringUtils.isNotBlank(payChannel.getStatus())) {
            payChannel.setStatus(CommonStatus.labelOf(payChannel.getStatus()));
        }
        if (StringUtils.isNotBlank(payChannel.getChargeDeductType())) {
            payChannel.setChargeDeductType(ChargeDeductType.labelOf(payChannel.getChargeDeductType()));
        }
        return payChannel;
    }

      
    /**     
    * @discription 技术签约显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:54:02     
    * @param merchantAutographInfo
    * @return     
    */
    public static MerchantAutographInfo merchantAutographInfoShow(MerchantAutographInfo merchantAutographInfo) {
        if (StringUtils.isNotBlank(merchantAutographInfo.getStatus())) {
            merchantAutographInfo.setStatus(CommonStatus.labelOf(merchantAutographInfo.getStatus()));
        }
        if (StringUtils.isNotBlank(merchantAutographInfo.getAutographKey())) {
            Integer length = merchantAutographInfo.getAutographKey().length();
            merchantAutographInfo.setAutographKey(merchantAutographInfo.getAutographKey().substring(0, 6) + "****"
                    + merchantAutographInfo.getAutographKey().substring(length - 4, length));
        }
        return merchantAutographInfo;
    }

      
    /**     
    * @discription 签约产品显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:54:29     
    * @param merchantProductRates
    * @return     
    */
    public static List<MerchantProductRate> merchantProductRate(List<MerchantProductRate> merchantProductRates) {
        List<MerchantProductRate> list = Lists.newArrayList();
        for (MerchantProductRate merchantProductRate : merchantProductRates) {
            if (StringUtils.isNotBlank(merchantProductRate.getStatus())) {
                merchantProductRate.setStatus(CommonStatus.labelOf(merchantProductRate.getStatus()));
            }
            if (StringUtils.isNotBlank(merchantProductRate.getContractType())) {
                merchantProductRate.setContractType(ContractType.labelOf(merchantProductRate.getContractType()));
            }
            if (StringUtils.isNotBlank(merchantProductRate.getBusinessType())) {
                merchantProductRate.setBusinessType(RateBusinessType.labelOf(merchantProductRate.getBusinessType()));
            }
            if(StringUtils.isNoneBlank(merchantProductRate.getRateAudit())){
                merchantProductRate.setRateAudit(RateAudit.labelOf(merchantProductRate.getRateAudit()));
            }
            list.add(merchantProductRate);
        }
        return list;
    }

      
    /**     
    * @discription 费率显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:54:44     
    * @param merchantRateNews
    * @return     
    */
    public static List<MerchantRateNew> merchantRateNew(List<MerchantRateNew> merchantRateNews) {
        List<MerchantRateNew> list = Lists.newArrayList();
        for (MerchantRateNew merchantRateNew : merchantRateNews) {
            if (null != merchantRateNew.getChargeFee()) {
                merchantRateNew.setChargeFee(AmountChangeUtil.change(merchantRateNew.getChargeFee()));
            }
            if (null != merchantRateNew.getChargeRatio()) {
                merchantRateNew.setChargeRatio(AmountChangeUtil.change(merchantRateNew.getChargeRatio()));
            }
            if (null != merchantRateNew.getMaxFee()) {
                merchantRateNew.setMaxFee(AmountChangeUtil.change(merchantRateNew.getMaxFee()));
            }
            if (null != merchantRateNew.getMinFee()) {
                merchantRateNew.setMinFee(AmountChangeUtil.change(merchantRateNew.getMinFee()));
            }
            if (StringUtils.isNotBlank(merchantRateNew.getStatus())) {
                merchantRateNew.setStatus(CommonStatus.labelOf(merchantRateNew.getStatus()));
            }
            if (StringUtils.isNotBlank(merchantRateNew.getChargeType())) {
                merchantRateNew.setChargeType(CostType.labelOf(merchantRateNew.getChargeType()));
            }
            if (StringUtils.isNotBlank(merchantRateNew.getBankCardType())) {
                merchantRateNew.setBankCardType(RateBankcardType.labelOf(merchantRateNew.getBankCardType()));
            }
            if (StringUtils.isNotBlank(merchantRateNew.getContractType())) {
                merchantRateNew.setContractType(ContractType.labelOf(merchantRateNew.getContractType()));
            }
            if (StringUtils.isNotBlank(merchantRateNew.getBusinessType())) {
                merchantRateNew.setBusinessType(RateBusinessType.labelOf(merchantRateNew.getBusinessType()));
            }
            list.add(merchantRateNew);
        }
        return list;
    }

      
    /**     
    * @discription 转换merchantRateNew 显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:54:52     
    * @param merchantRateNew
    * @return     
    */
    public static MerchantRateNew changeMerchantRateNew(MerchantRateNew merchantRateNew) {
        if (StringUtils.isNotBlank(merchantRateNew.getBusinessType())) {
            if (StringUtils.isNotBlank(RateBusinessType.labelOf(merchantRateNew.getBusinessType()))) {
                merchantRateNew.setBusinessType(RateBusinessType.labelOf(merchantRateNew.getBusinessType()));
            }
        }
        if (null != merchantRateNew.getChargeFee()) {
            merchantRateNew.setChargeFee(AmountChangeUtil.change(merchantRateNew.getChargeFee()));
        }
        if (null != merchantRateNew.getChargeRatio()) {
            merchantRateNew.setChargeRatio(AmountChangeUtil.change(merchantRateNew.getChargeRatio()));
        }
        if (null != merchantRateNew.getMaxFee()) {
            merchantRateNew.setMaxFee(AmountChangeUtil.change(merchantRateNew.getMaxFee()));
        }
        if (null != merchantRateNew.getMinFee()) {
            merchantRateNew.setMinFee(AmountChangeUtil.change(merchantRateNew.getMinFee()));
        }
        if (StringUtils.isNotBlank(merchantRateNew.getStatus())) {
            merchantRateNew.setStatus(CommonStatus.labelOf(merchantRateNew.getStatus()));
        }
        if (StringUtils.isNotBlank(merchantRateNew.getChargeCollectionType())) {
            merchantRateNew
                    .setChargeCollectionType(ChargeCollectionType.labelOf(merchantRateNew.getChargeCollectionType()));
        }
        if (StringUtils.isNotBlank(merchantRateNew.getChargeDeductType())) {
            merchantRateNew.setChargeDeductType(ChargeDeductType.labelOf(merchantRateNew.getChargeDeductType()));
        }
        if (StringUtils.isNotBlank(merchantRateNew.getChargeSource())) {
            merchantRateNew.setChargeSource(ChargeSource.labelOf(merchantRateNew.getChargeSource()));
        }
        return merchantRateNew;
    }

      
    /**     
    * @discription 转换MerchantProductRate显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:55:12     
    * @param merchantProductRate
    * @return     
    */
    public static MerchantProductRate changeMerchantProductRate(MerchantProductRate merchantProductRate) {
        if (StringUtils.isNotBlank(merchantProductRate.getBusinessType())) {
            if (StringUtils.isNotBlank(RateBusinessType.labelOf(merchantProductRate.getBusinessType()))) {
                merchantProductRate.setBusinessType(RateBusinessType.labelOf(merchantProductRate.getBusinessType()));
            }
        }
        return merchantProductRate;
    }

    /**
     * @discription 转换MerchantProductRate显示枚举类的值和内容转换(详情页面)
     * @author ly
     * @created 2017年1月13日18:28:54
     * @param merchantProductRate
     * @return
     */
    public static MerchantProductRate changeMerchantProductRateDetail(MerchantProductRate merchantProductRate) {
        if (StringUtils.isNotBlank(merchantProductRate.getBusinessType())) {
            if (StringUtils.isNotBlank(RateBusinessType.labelOf(merchantProductRate.getBusinessType()))) {
                merchantProductRate.setBusinessType(RateBusinessType.labelOf(merchantProductRate.getBusinessType()));
            }
        }
        if(StringUtils.isNotBlank(merchantProductRate.getSettlementTo())){
            merchantProductRate.setSettlementTo(SettlementTo.labelOf(merchantProductRate.getSettlementTo()));
        }
        if(StringUtils.isNotBlank(merchantProductRate.getStatus())){
            merchantProductRate.setStatus(CommonStatus.labelOf(merchantProductRate.getStatus()));
        }

        if(StringUtils.isNotBlank(merchantProductRate.getIsRefundable())){
            merchantProductRate.setIsRefundable(CommonStatus.labelOf(merchantProductRate.getIsRefundable()));
        }
        //手续费来源
        if(StringUtils.isNotBlank(merchantProductRate.getChargeSource())){
            merchantProductRate.setChargeSource(ChargeSource.labelOf(merchantProductRate.getChargeSource()));
        }
        //手续费扣除方式
        if(StringUtils.isNotBlank(merchantProductRate.getChargeDeductType())){
            merchantProductRate.setChargeDeductType(ChargeDeductType.labelOf(merchantProductRate.getChargeDeductType()));
        }
        //手续费收取方式
        if(StringUtils.isNotBlank(merchantProductRate.getChargeCollectionType())){
            merchantProductRate.setChargeCollectionType(ChargeCollectionType.labelOf(merchantProductRate.getChargeCollectionType()));
        }

        return merchantProductRate;
    }

      
    /**     
    * @discription 转换MerchantProductRate默认显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:55:37     
    * @param merchantRateNew
    * @return     
    */
    public static MerchantRateNew changeMerchantRateNewDefault(MerchantRateNew merchantRateNew) {
        if (StringUtils.isNotBlank(merchantRateNew.getBusinessType())) {
            if (StringUtils.isNotBlank(RateBusinessType.labelOf(merchantRateNew.getBusinessType()))) {
                merchantRateNew.setBusinessType(RateBusinessType.labelOf(merchantRateNew.getBusinessType()));
            }
        }
        if (null != merchantRateNew.getChargeFee()) {
            merchantRateNew.setChargeFee(AmountChangeUtil.change(merchantRateNew.getChargeFee()));
        }
        if (null != merchantRateNew.getChargeRatio()) {
            merchantRateNew.setChargeRatio(AmountChangeUtil.change(merchantRateNew.getChargeRatio()));
        }
        if (null != merchantRateNew.getMaxFee()) {
            merchantRateNew.setMaxFee(AmountChangeUtil.change(merchantRateNew.getMaxFee()));
        }
        if (null != merchantRateNew.getMinFee()) {
            merchantRateNew.setMinFee(AmountChangeUtil.change(merchantRateNew.getMinFee()));
        }
        return merchantRateNew;
    }

      
    /**     
    * @discription 转换商户银行卡显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:56:06     
    * @param merchantBankCards
    * @return     
    */
    public static List<MerchantBankCard> changeMerchantBankCard(List<MerchantBankCard> merchantBankCards) {
        List<MerchantBankCard> list = Lists.newArrayList();
        for (MerchantBankCard merchantBankCard : merchantBankCards) {
            if (StringUtils.isNotBlank(merchantBankCard.getBankNo())) {
                String bankNo = Aes.decryptStr(merchantBankCard.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
                merchantBankCard.setBankNo(RandomUtil.getBankNo(bankNo));
            }
            list.add(merchantBankCard);
        }
        return list;
    }

      
    /**     
    * @discription 转换费率操作日志显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午4:56:23     
    * @param merchantRateLog
    * @return     
    */
    public static MerchantRateLog changeMerchantRateLog(MerchantRateLog merchantRateLog) {
        if(StringUtils.isNotBlank(merchantRateLog.getBusinessType())){
            merchantRateLog.setBusinessType(RateBusinessType.labelOf(merchantRateLog.getBusinessType()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getStatus())) {
            merchantRateLog.setStatus(CommonStatus.labelOf(merchantRateLog.getStatus()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getChargeType())) {
            merchantRateLog.setChargeType(CostType.labelOf(merchantRateLog.getChargeType()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getBankCardType())) {
            merchantRateLog.setBankCardType(RateBankcardType.labelOf(merchantRateLog.getBankCardType()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getSettlementTo())) {
            merchantRateLog.setSettlementTo(SettlementTo.labelOf(merchantRateLog.getSettlementTo()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getChargeCollectionType())) {
            merchantRateLog.setChargeCollectionType(ChargeCollectionType.labelOf(merchantRateLog.getChargeCollectionType()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getChargeDeductType())) {
            merchantRateLog.setChargeDeductType(ChargeDeductType.labelOf(merchantRateLog.getChargeDeductType()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getChargeSource())) {
            merchantRateLog.setChargeSource(ChargeSource.labelOf(merchantRateLog.getChargeSource()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getIsRefundable())) {
            merchantRateLog.setIsRefundable(CommonStatus.labelOf(merchantRateLog.getIsRefundable()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getOperationType())) {
            merchantRateLog.setOperationType(OperationType.labelOf(merchantRateLog.getOperationType()));
        }
        if (StringUtils.isNotBlank(merchantRateLog.getOperationUser())) {
            merchantRateLog.setOperationUser(UserUtils.get(merchantRateLog.getOperationUser()).getName());
        }
        return merchantRateLog;
    }


      
    /**     
    * @discription 费率操作日志List显示枚举类的值和内容转换
    * @author ly     
    * @created 2016年12月24日 下午5:22:21     
    * @param merchantRateLogs
    * @return     
    */
    public static List<MerchantRateLog> merchantRateLogList(List<MerchantRateLog> merchantRateLogs) {
        List<MerchantRateLog> list = Lists.newArrayList();
        for(MerchantRateLog merchantRateLog : merchantRateLogs){
            if(StringUtils.isNotBlank(merchantRateLog.getOperationType())){
                merchantRateLog.setOperationType(OperationType.labelOf(merchantRateLog.getOperationType()));
            }
            if (StringUtils.isNotBlank(merchantRateLog.getOperationUser())) {
                merchantRateLog.setOperationUser(UserUtils.get(merchantRateLog.getOperationUser()).getName());
            }
            list.add(merchantRateLog);
        }
        return list;
    }

    /**
     * @discription 产品枚举类的值和内容转换
     * @author ly
     * @created 2016年12月24日 下午5:22:21
     * @param product
     * @return
     */
    public static Product changeProduct(Product product) {
        if(StringUtils.isNotBlank(product.getCreateBy().getId())){
            product.setCreateName(UserUtils.get(product.getCreateBy().getId()).getName());
        }
        if (StringUtils.isNotBlank(product.getBusinessType())) {
            product.setBusinessType(RateBusinessType.labelOf(product.getBusinessType()));
        }
        if (StringUtils.isNotBlank(product.getTrxType())) {
            product.setTrxType(ProductTrxTypeUtils.getProductTrxTypeName(product.getTrxType()));
        }
        return product;
    }

    /**
     * 转换通道excel
     * ly 2017年4月13日14:09:23
     */
    public static List<PayChannel> PayChannelExcel(List<PayChannel> payChannels) {
        List<PayChannel> list = Lists.newArrayList();
        for (PayChannel payChannel : payChannels) {
            if (StringUtils.isNotBlank(payChannel.getCostType())) {
                payChannel.setCostType(CostType.labelOf(payChannel.getCostType()));
            }
            if(StringUtils.isNotBlank(payChannel.getChargeDeductType())){
                payChannel.setChargeDeductType(ChargeDeductType.labelOf(payChannel.getChargeDeductType()));
            }
            if(StringUtils.isNotBlank(payChannel.getAccountType())){
                payChannel.setAccountType(AccountType.labelOf(payChannel.getAccountType()));
            }
            if(StringUtils.isNotBlank(payChannel.getBusinessType())){
                payChannel.setBusinessType(BusinessType.labelOf(payChannel.getBusinessType()));
            }
            if(StringUtils.isNotBlank(payChannel.getSettleType())){
                payChannel.setSettleType(SettleType.labelOf(payChannel.getSettleType()));
            }
            if(StringUtils.isNotBlank(payChannel.getSettlePeriod())){
                payChannel.setSettlePeriod(SettlePeriod.labelOf(payChannel.getSettlePeriod()));
            }
            if (StringUtils.isNotBlank(payChannel.getSort())) {
                payChannel.setSort("默认");
            } else {
                payChannel.setSort("无");
            }
            list.add(payChannel);
        }
        return list;
    }

    /**
     * 转换产品关联组
     */
    public static List<ProductNetGroup> productNetGroupList(List<ProductNetGroup> productNegGroups) {
        List<ProductNetGroup> list = Lists.newArrayList();
        for(ProductNetGroup productNetGroup : productNegGroups){
            if(StringUtils.isNotBlank(productNetGroup.getCreateBy().getId())){
                productNetGroup.setCreateName(UserUtils.get(productNetGroup.getCreateBy().getId()).getName());
            }
            if(StringUtils.isNotBlank(productNetGroup.getUpdateBy().getId())){
                productNetGroup.setUpdateName(UserUtils.get(productNetGroup.getUpdateBy().getId()).getName());
            }
            list.add(productNetGroup);
        }
        return list;
    }

    /**
     * @discription .NET聚合通道字段转换
     * @return
     */
    public static List<IntegrationChannel> integraChannel(List<IntegrationChannel> integrationChannels) {
        List<IntegrationChannel> list = Lists.newArrayList();
        for (IntegrationChannel integrationChannel : integrationChannels) {
            //通道状态
            if (StringUtils.isNotBlank(integrationChannel.getStatus())) {
                integrationChannel.setStatus(IntegraChannelStatus.labelOf(integrationChannel.getStatus()));
            }
            //支付类型
            if (StringUtils.isNotBlank(integrationChannel.getPayType())) {
                integrationChannel.setPayType(IntegraChannelPayType.labelOf(integrationChannel.getPayType()));
            }
            //银行提供者
            if (StringUtils.isNotBlank(integrationChannel.getBankProvider())) {
                integrationChannel.setBankProvider(DirectBankProvider.labelOf(integrationChannel.getBankProvider()));
            }
            //卡类型
            if (StringUtils.isNotBlank(integrationChannel.getBankCardType())) {
                integrationChannel.setBankCardType(IntegraChannelCardType.labelOf(integrationChannel.getBankCardType()));
            }
            list.add(integrationChannel);
        }
        return list;
    }

    /**
     * 字段转换
     * @param channelBankids
     */
    public static void channelBankId(List<ChannelBankid> channelBankids){
        for (ChannelBankid channelBankid : channelBankids){
            //支付类型
            if (StringUtils.isNotBlank(channelBankid.getPayType())) {
                channelBankid.setPayType(IntegraChannelPayType.labelOf(channelBankid.getPayType()));
            }
            //银行提供者
            if (StringUtils.isNotBlank(channelBankid.getBankProvider())) {
                channelBankid.setBankProvider(DirectBankProvider.labelOf(channelBankid.getBankProvider()));
            }
        }
    }

    public static List<OnlineContractInfo> onlineContractInfo(List<OnlineContractInfo> onlineContractInfos) {
        List<OnlineContractInfo> list = Lists.newArrayList();
        for(OnlineContractInfo onlineContractInfo : onlineContractInfos){
            if(StringUtils.isNotBlank(onlineContractInfo.getLegalAuditStatus())){
                onlineContractInfo.setLegalAuditStatus(RouteStatus.labelOf(onlineContractInfo.getLegalAuditStatus()));
            }
            if(StringUtils.isNotBlank(onlineContractInfo.getRcAuditStatus())){
                onlineContractInfo.setRcAuditStatus(RouteStatus.labelOf(onlineContractInfo.getRcAuditStatus()));
            }
            list.add(onlineContractInfo);
        }
        return list;
    }

    public static List<NotificationEmail> notificationEmail(List<NotificationEmail> notificationEmails){
        List<NotificationEmail> list = Lists.newArrayList();
        for (NotificationEmail notificationEmail:notificationEmails) {
            if(StringUtils.isNotBlank(notificationEmail.getStatus())){
                notificationEmail.setStatus(CommonStatus.labelOf(notificationEmail.getStatus()));
            }
            if(StringUtils.isNotBlank(notificationEmail.getCreateUser())){
                notificationEmail.setCreateUser(UserUtils.get(notificationEmail.getCreateUser()).getName());
            }
            if(StringUtils.isNotBlank(notificationEmail.getUpdateUser())){
                notificationEmail.setUpdateUser(UserUtils.get(notificationEmail.getUpdateUser()).getName());
            }
            list.add(notificationEmail);
        }
        return list;
    }
}
