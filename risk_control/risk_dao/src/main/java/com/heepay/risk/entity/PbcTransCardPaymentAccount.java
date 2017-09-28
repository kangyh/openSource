package com.heepay.risk.entity;

import java.math.BigDecimal;
import java.util.Date;


public class PbcTransCardPaymentAccount {
    private static final long serialVersionUID = 1L;

	private Long transCardPaymentId;

    private String onlinePayCompanyType;

    private String transType;

    private String paymentType;

    private String currency;

    private BigDecimal transFerAmount;

    private String transBankId;

    private String transBankName;

    private String transCardNumber;

    private String transAccountNumber;

    private String posId;

    private String merchantNumber;

    private String transOutBankId;

    private String transOutBankName;

    private String transOutCardNumber;

    private String transOutAccountNumber;

    private String transDeviceType;

    private String transIp;

    private String transMac;

    private String transDeviceCode;

    private BigDecimal transLongitude;

    private BigDecimal transLatitude;

    private String bankTransSerial;

    private Long feedBackId;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    
    public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}
	
    public Long getTransCardPaymentId() {
        return transCardPaymentId;
    }

    public void setTransCardPaymentId(Long transCardPaymentId) {
        this.transCardPaymentId = transCardPaymentId;
    }

    public String getOnlinePayCompanyType() {
        return onlinePayCompanyType;
    }

    public void setOnlinePayCompanyType(String onlinePayCompanyType) {
        this.onlinePayCompanyType = onlinePayCompanyType == null ? null : onlinePayCompanyType.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getTransFerAmount() {
        return transFerAmount;
    }

    public void setTransFerAmount(BigDecimal transFerAmount) {
        this.transFerAmount = transFerAmount;
    }

    public String getTransBankId() {
        return transBankId;
    }

    public void setTransBankId(String transBankId) {
        this.transBankId = transBankId == null ? null : transBankId.trim();
    }

    public String getTransBankName() {
        return transBankName;
    }

    public void setTransBankName(String transBankName) {
        this.transBankName = transBankName == null ? null : transBankName.trim();
    }

    public String getTransCardNumber() {
        return transCardNumber;
    }

    public void setTransCardNumber(String transCardNumber) {
        this.transCardNumber = transCardNumber == null ? null : transCardNumber.trim();
    }

    public String getTransAccountNumber() {
        return transAccountNumber;
    }

    public void setTransAccountNumber(String transAccountNumber) {
        this.transAccountNumber = transAccountNumber == null ? null : transAccountNumber.trim();
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId == null ? null : posId.trim();
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber == null ? null : merchantNumber.trim();
    }

    public String getTransOutBankId() {
        return transOutBankId;
    }

    public void setTransOutBankId(String transOutBankId) {
        this.transOutBankId = transOutBankId == null ? null : transOutBankId.trim();
    }

    public String getTransOutBankName() {
        return transOutBankName;
    }

    public void setTransOutBankName(String transOutBankName) {
        this.transOutBankName = transOutBankName == null ? null : transOutBankName.trim();
    }

    public String getTransOutCardNumber() {
        return transOutCardNumber;
    }

    public void setTransOutCardNumber(String transOutCardNumber) {
        this.transOutCardNumber = transOutCardNumber == null ? null : transOutCardNumber.trim();
    }

    public String getTransOutAccountNumber() {
        return transOutAccountNumber;
    }

    public void setTransOutAccountNumber(String transOutAccountNumber) {
        this.transOutAccountNumber = transOutAccountNumber == null ? null : transOutAccountNumber.trim();
    }

    public String getTransDeviceType() {
        return transDeviceType;
    }

    public void setTransDeviceType(String transDeviceType) {
        this.transDeviceType = transDeviceType == null ? null : transDeviceType.trim();
    }

    public String getTransIp() {
        return transIp;
    }

    public void setTransIp(String transIp) {
        this.transIp = transIp == null ? null : transIp.trim();
    }

    public String getTransMac() {
        return transMac;
    }

    public void setTransMac(String transMac) {
        this.transMac = transMac == null ? null : transMac.trim();
    }

    public String getTransDeviceCode() {
        return transDeviceCode;
    }

    public void setTransDeviceCode(String transDeviceCode) {
        this.transDeviceCode = transDeviceCode == null ? null : transDeviceCode.trim();
    }

    public BigDecimal getTransLongitude() {
        return transLongitude;
    }

    public void setTransLongitude(BigDecimal transLongitude) {
        this.transLongitude = transLongitude;
    }

    public BigDecimal getTransLatitude() {
        return transLatitude;
    }

    public void setTransLatitude(BigDecimal transLatitude) {
        this.transLatitude = transLatitude;
    }

    public String getBankTransSerial() {
        return bankTransSerial;
    }

    public void setBankTransSerial(String bankTransSerial) {
        this.bankTransSerial = bankTransSerial == null ? null : bankTransSerial.trim();
    }

    public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }
}