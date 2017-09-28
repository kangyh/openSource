package com.heepay.manage.modules.merchant.vo;

import java.util.Date;

/**
 * 
 * 描 述：商户对接的产品
 *
 * 创 建 者： ly 创建时间： 2016年11月2日 下午7:19:52 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

public class MerchantProductRedis {
    // 商户id
    private String merchantId;
    // 产品名称
    private String productName;
    // 对接产品的秘钥
    private String productKey;
    // 对接产品的backurl
    private String backUrl;
    // 对接产品的notifyurl
    private String notifyUrl;
    // 对接产品的ip域名
    private String ipDomain;
    // 对接产品的有效期截止日 YYYY/MM/DD HH:MM:SS
    private Date validityDateEnd;
    // 对接产品的有效期开始日 YYYY/MM/DD HH:MM:SS
    private Date validityDateBegin;
    // 防钓鱼referer
    public String referer;
    // 代扣产品专用配置（到账户余额还是到银行卡）
    private String toBalanceOrBankcard;
    // 产品编码
    private String productCode;
    // 手续费扣除方式
    private String feeWay;
    // 手续费结算周期
    private String feeSettleCyc;
    // 订单结算周期
    private String settleCyc;
    // 是否退还手续费
    private String isRefundable;
    // 状态
    private String status;
    // 审核状态
    private String rateAudit;
    // 业务类型
    private String businessType;


    public String getToBalanceOrBankcard() {
        return toBalanceOrBankcard;
    }

    public void setToBalanceOrBankcard(String toBalanceOrBankcard) {
        this.toBalanceOrBankcard = toBalanceOrBankcard;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay;
    }

    public String getSettleCyc() {
        return settleCyc;
    }

    public void setSettleCyc(String settleCyc) {
        this.settleCyc = settleCyc;
    }

    public String getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(String isRefundable) {
        this.isRefundable = isRefundable;
    }

    public String getFeeSettleCyc() {
        return feeSettleCyc;
    }

    public void setFeeSettleCyc(String feeSettleCyc) {
        this.feeSettleCyc = feeSettleCyc;
    }

    public Date getValidityDateEnd() {
        return validityDateEnd;
    }

    public void setValidityDateEnd(Date validityDateEnd) {
        this.validityDateEnd = validityDateEnd;
    }

    public Date getValidityDateBegin() {
        return validityDateBegin;
    }

    public void setValidityDateBegin(Date validityDateBegin) {
        this.validityDateBegin = validityDateBegin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpDomain() {
        return ipDomain;
    }

    public void setIpDomain(String ipDomain) {
        this.ipDomain = ipDomain;
    }

    public String getRateAudit() {
        return rateAudit;
    }

    public void setRateAudit(String rateAudit) {
        this.rateAudit = rateAudit;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
