package com.heepay.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RiskChannel {
    private Long riskChannelId;

    private String paymentId;

    private String channelCode;

    private String channelPartnerName;

    private String channelPartnerCode;

    private String bankCode;

    private String bankName;

    private String cardTypeCode;

    private String cardTypeName;

    private String channelTypeCode;

    private String channelTypeName;

    private String accountType;

    private String businessType;

    private BigDecimal perlimitAmount;

    private BigDecimal daylimitAmount;

    private BigDecimal monlimitAmount;

    private BigDecimal amount;

    private Date reqTime;

    private Date respTime;

    private String requestStatus;

    private String result;

    private Long ruleDetailId;

    private String quotaType;
    private String url;

    public Long getRiskChannelId() {
        return riskChannelId;
    }

    public void setRiskChannelId(Long riskChannelId) {
        this.riskChannelId = riskChannelId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelPartnerName() {
        return channelPartnerName;
    }

    public void setChannelPartnerName(String channelPartnerName) {
        this.channelPartnerName = channelPartnerName == null ? null : channelPartnerName.trim();
    }

    public String getChannelPartnerCode() {
        return channelPartnerCode;
    }

    public void setChannelPartnerCode(String channelPartnerCode) {
        this.channelPartnerCode = channelPartnerCode == null ? null : channelPartnerCode.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getCardTypeCode() {
        return cardTypeCode;
    }

    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode == null ? null : cardTypeCode.trim();
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName == null ? null : cardTypeName.trim();
    }

    public String getChannelTypeCode() {
        return channelTypeCode;
    }

    public void setChannelTypeCode(String channelTypeCode) {
        this.channelTypeCode = channelTypeCode == null ? null : channelTypeCode.trim();
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName == null ? null : channelTypeName.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public BigDecimal getPerlimitAmount() {
        return perlimitAmount;
    }

    public void setPerlimitAmount(BigDecimal perlimitAmount) {
        this.perlimitAmount = perlimitAmount;
    }

    public BigDecimal getDaylimitAmount() {
        return daylimitAmount;
    }

    public void setDaylimitAmount(BigDecimal daylimitAmount) {
        this.daylimitAmount = daylimitAmount;
    }

    public BigDecimal getMonlimitAmount() {
        return monlimitAmount;
    }

    public void setMonlimitAmount(BigDecimal monlimitAmount) {
        this.monlimitAmount = monlimitAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getRespTime() {
        return respTime;
    }

    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus == null ? null : requestStatus.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Long getRuleDetailId() {
        return ruleDetailId;
    }

    public void setRuleDetailId(Long ruleDetailId) {
        this.ruleDetailId = ruleDetailId;
    }

    public String getQuotaType() {
        return quotaType;
    }

    public void setQuotaType(String quotaType) {
        this.quotaType = quotaType == null ? null : quotaType.trim();
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}