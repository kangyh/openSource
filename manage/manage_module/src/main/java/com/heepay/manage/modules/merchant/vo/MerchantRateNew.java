/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 * 
 * @author ly
 * @version V1.0
 */
public class MerchantRateNew extends DataEntity<MerchantRateNew> {

    private static final long serialVersionUID = 1L;
    private String rateId;// 费率产品id
    private String merchantId; // 商户ID
    private String merchantCompanyName; // 商户公司名称
    private String merchantLoginName; // 商户账号
    private String businessType; // 业务类型
    private String productCode; // 产品代码
    private String productName; // 产品名称
    private String bankCardType; // 银行卡类型
    private Date ruleBeginTime; // 规则开始时间
    private Date ruleEndTime; // 规则结束时间
    private String chargeType; // 计费类型
    private BigDecimal chargeRatio; // 手续费费用(%)
    private BigDecimal chargeFee; // 手续费费用(元)
    private String chargeSource; // 手续费来源
    private String chargeDeductType; // 手续费扣除方式
    private String chargeCollectionType; // 手续费收取方式
    private String isRefundable; // 退款时是否退还手续费
    private String status; // 规则状态
    private Date beginCreateTime; // 开始 create_time
    private Date endCreateTime; // 结束 create_time
    private BigDecimal maxFee; // 手续费最大值
    private BigDecimal minFee; // 手续费最小值
    private String bankNo; // 银行编码
    private String bankName; // 银行名称
    private String returnUrl;// 返回的地址(用于区分商户添加还是详情修改)
    private String rateAudit;// 费率审核(Y:审核通过 N:审核失败: S:未审核)

    // 技术签约
    private String notifyUrl; // 异步通知地址
    private String backUrl; // 同步返回地址
    private String ipDomain; // ip/域名
    private String autographType; // 签名方式
    private String autographKey; // 签名key

    //结算周期
    private String settlementTo; //结算至
    private String settleType;   // 结算类型

    //阶梯费率
    private BigDecimal chargeMax; //收费的上限额度，为空或0表示无上限(阶梯1)
    private BigDecimal chargeMin; //收费的下限额度，为空或0表示无下限(阶梯1)
    private BigDecimal chargeRatio2; // 手续费费用(%)比例(阶梯2)
    private BigDecimal chargeFee2; // 手续费费用(元)固定(阶梯2)
    private BigDecimal chargeMax2; //收费的上限额度，为空或0表示无上限(阶梯2)
    private BigDecimal chargeMin2; //收费的下限额度，为空或0表示无下限(阶梯2)
    private BigDecimal chargeRatio3; // 手续费费用(%)比例(阶梯3)
    private BigDecimal chargeFee3; // 手续费费用(元)固定(阶梯3)
    private BigDecimal chargeMax3; //收费的上限额度，为空或0表示无上限(阶梯3)
    private BigDecimal chargeMin3; //收费的下限额度，为空或0表示无下限(阶梯3)

    private String contractType; // 签约方式(枚举类ContractType)

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRateAudit() {
        return rateAudit;
    }

    public void setRateAudit(String rateAudit) {
        this.rateAudit = rateAudit;
    }

    public MerchantRateNew() {
        super();
    }

    public MerchantRateNew(String id) {
        super(id);
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    @Length(min = 0, max = 10, message = "商户ID长度必须介于 0 和 10 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCompanyName() {
        return merchantCompanyName;
    }

    public void setMerchantCompanyName(String merchantCompanyName) {
        this.merchantCompanyName = merchantCompanyName;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    @Length(min = 0, max = 6, message = "业务类型长度必须介于 0 和 6 之间")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @NotNull(message = "产品不能为空")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Length(min = 0, max = 100, message = "产品名称长度必须介于 0 和 100 之间")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 0, max = 100, message = "银行卡类型长度必须介于 0 和 100 之间")
    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRuleBeginTime() {
        return ruleBeginTime;
    }

    public void setRuleBeginTime(Date ruleBeginTime) {
        this.ruleBeginTime = ruleBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRuleEndTime() {
        return ruleEndTime;
    }

    public void setRuleEndTime(Date ruleEndTime) {
        this.ruleEndTime = ruleEndTime;
    }

    @Length(min = 0, max = 6, message = "计费类型长度必须介于 0 和 6 之间")
    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getChargeRatio() {
        return chargeRatio;
    }

    public void setChargeRatio(BigDecimal chargeRatio) {
        this.chargeRatio = chargeRatio;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    @Length(min = 0, max = 6, message = "手续费来源长度必须介于 0 和 6 之间")
    public String getChargeSource() {
        return chargeSource;
    }

    public void setChargeSource(String chargeSource) {
        this.chargeSource = chargeSource;
    }

    @Length(min = 0, max = 6, message = "手续费扣除方式长度必须介于 0 和 6 之间")
    public String getChargeDeductType() {
        return chargeDeductType;
    }

    public void setChargeDeductType(String chargeDeductType) {
        this.chargeDeductType = chargeDeductType;
    }

    @Length(min = 0, max = 6, message = "手续费收取方式长度必须介于 0 和 6 之间")
    public String getChargeCollectionType() {
        return chargeCollectionType;
    }

    public void setChargeCollectionType(String chargeCollectionType) {
        this.chargeCollectionType = chargeCollectionType;
    }

    @Length(min = 0, max = 6, message = "退款时是否退还手续费长度必须介于 0 和 6 之间")
    public String getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(String isRefundable) {
        this.isRefundable = isRefundable;
    }

    @Length(min = 0, max = 6, message = "规则状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public BigDecimal getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(BigDecimal maxFee) {
        this.maxFee = maxFee;
    }

    public BigDecimal getMinFee() {
        return minFee;
    }

    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }

    @Length(min = 0, max = 3, message = "银行代码长度必须介于 0 和 6 之间")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Length(min = 0, max = 512, message = "异步通知地址长度必须介于 0 和 512 之间")
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Length(min = 0, max = 512, message = "同步返回地址长度必须介于 0 和 512 之间")
    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    @Length(min = 0, max = 3, message = "签名方式长度必须介于 0 和 3 之间")
    public String getAutographType() {
        return autographType;
    }

    public void setAutographType(String autographType) {
        this.autographType = autographType;
    }

    @Length(min = 0, max = 512, message = "签名key长度必须介于 0 和 512 之间")
    public String getAutographKey() {
        return autographKey;
    }

    public void setAutographKey(String autographKey) {
        this.autographKey = autographKey;
    }
    
    @Length(min=0, max=6, message="结算至长度必须介于 0 和 6 之间")
    public String getSettlementTo() {
      return settlementTo;
    }

    public void setSettlementTo(String settlementTo) {
      this.settlementTo = settlementTo;
    }
    
    @Length(min=0, max=1, message="结算类型长度必须介于 0 和 1 之间")
    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }
    
    @Length(min = 0, max = 512, message = "ip/域名长度必须介于 0 和 512 之间")
    public String getIpDomain() {
        return ipDomain;
    }

    public void setIpDomain(String ipDomain) {
        this.ipDomain = ipDomain;
    }

    public BigDecimal getChargeMax() {
        return chargeMax;
    }

    public void setChargeMax(BigDecimal chargeMax) {
        this.chargeMax = chargeMax;
    }

    public BigDecimal getChargeMin() {
        return chargeMin;
    }

    public void setChargeMin(BigDecimal chargeMin) {
        this.chargeMin = chargeMin;
    }

    public BigDecimal getChargeRatio2() {
        return chargeRatio2;
    }

    public void setChargeRatio2(BigDecimal chargeRatio2) {
        this.chargeRatio2 = chargeRatio2;
    }

    public BigDecimal getChargeFee2() {
        return chargeFee2;
    }

    public void setChargeFee2(BigDecimal chargeFee2) {
        this.chargeFee2 = chargeFee2;
    }

    public BigDecimal getChargeMax2() {
        return chargeMax2;
    }

    public void setChargeMax2(BigDecimal chargeMax2) {
        this.chargeMax2 = chargeMax2;
    }

    public BigDecimal getChargeMin2() {
        return chargeMin2;
    }

    public void setChargeMin2(BigDecimal chargeMin2) {
        this.chargeMin2 = chargeMin2;
    }

    public BigDecimal getChargeRatio3() {
        return chargeRatio3;
    }

    public void setChargeRatio3(BigDecimal chargeRatio3) {
        this.chargeRatio3 = chargeRatio3;
    }

    public BigDecimal getChargeFee3() {
        return chargeFee3;
    }

    public void setChargeFee3(BigDecimal chargeFee3) {
        this.chargeFee3 = chargeFee3;
    }

    public BigDecimal getChargeMax3() {
        return chargeMax3;
    }

    public void setChargeMax3(BigDecimal chargeMax3) {
        this.chargeMax3 = chargeMax3;
    }

    public BigDecimal getChargeMin3() {
        return chargeMin3;
    }

    public void setChargeMin3(BigDecimal chargeMin3) {
        this.chargeMin3 = chargeMin3;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}