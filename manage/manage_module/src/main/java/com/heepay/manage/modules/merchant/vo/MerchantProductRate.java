/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描 述：商户产品费率Entity
 *
 * 创 建 者： @author ly 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class MerchantProductRate extends DataEntity<MerchantProductRate> {

    private static final long serialVersionUID = 1L;
    private String merchantId; // 商户ID
    private String businessType; // 业务类型
    private String productCode; // 产品代码
    private String productName; // 产品名称
    private Date ruleBeginTime; // 规则开始时间
    private Date ruleEndTime; // 规则结束时间
    private String chargeSource; // 手续费来源
    private String chargeDeductType; // 手续费扣除方式
    private String chargeCollectionType; // 手续费收取方式
    private String isRefundable; // 退款时是否退还手续费
    private String status; // 状态
    private String merchantCompanyName; // 商户公司名称
    private String merchantLoginName; // 商户账号

    // 技术签约
    private String notifyUrl; // 异步通知地址
    private String backUrl; // 同步返回地址
    private String ipDomain;//ip/域名
    private String autographType; // 签名方式
    private String autographKey; // 签名key

    // 结算周期
    private String settlementTo; // 结算至
    private String settleType; // 结算类型

    private String rateAudit;// 费率审核(Y:审核通过 N:审核失败: S:未审核)
    private String rateAuditsValue;// 保持查询条件

    private String contractType; // 签约方式(枚举类ContractType)

    public String getRateAudit() {
        return rateAudit;
    }

    public void setRateAudit(String rateAudit) {
        this.rateAudit = rateAudit;
    }

    public MerchantProductRate() {
        super();
    }

    public MerchantProductRate(String id) {
        super(id);
    }

    public String getRateAuditsValue() {
        return rateAuditsValue;
    }

    public void setRateAuditsValue(String rateAuditsValue) {
        this.rateAuditsValue = rateAuditsValue;
    }

    @Length(min = 0, max = 10, message = "商户ID长度必须介于 0 和 10 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 6, message = "业务类型长度必须介于 0 和 6 之间")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Length(min = 1, max = 6, message = "产品代码长度必须介于 1 和 6 之间")
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

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Length(min = 0, max = 6, message = "结算至长度必须介于 0 和 6 之间")
    public String getSettlementTo() {
        return settlementTo;
    }

    public void setSettlementTo(String settlementTo) {
        this.settlementTo = settlementTo;
    }

    @Length(min = 0, max = 1, message = "结算类型长度必须介于 0 和 1 之间")
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
}