package com.heepay.risk.entity;

import java.math.BigDecimal;
import java.util.Date;


public class PbcAccountDynamic {
    private static final long serialVersionUID = 1L;

	private Long accountDynamicId;

    private String subjectType;

    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private String currency;

    private BigDecimal accountBalance;

    private String accountInfo;

    private Date accountOpenTime;

    private String accountOpenIp;

    private String accountOpenDeviceNumber;

    private Date lastTransTime;

    private String paymentId;

    private String transType;

    private String paymentType;

    private String borrowingSigns;

    private Date transTime;

    private String transCurrency;

    private BigDecimal transAmount;

    private String transSerial;

    private BigDecimal transBalance;

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

    private String bankTransSerial;

    private String remark;

    private Long feedBackId;
    private String applicationCode;
    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    
    public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Long getAccountDynamicId() {
        return accountDynamicId;
    }

    public void setAccountDynamicId(Long accountDynamicId) {
        this.accountDynamicId = accountDynamicId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus == null ? null : accountStatus.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo == null ? null : accountInfo.trim();
    }

    public Date getAccountOpenTime() {
        return accountOpenTime;
    }

    public void setAccountOpenTime(Date accountOpenTime) {
        this.accountOpenTime = accountOpenTime;
    }

    public String getAccountOpenIp() {
        return accountOpenIp;
    }

    public void setAccountOpenIp(String accountOpenIp) {
        this.accountOpenIp = accountOpenIp == null ? null : accountOpenIp.trim();
    }

    public String getAccountOpenDeviceNumber() {
        return accountOpenDeviceNumber;
    }

    public void setAccountOpenDeviceNumber(String accountOpenDeviceNumber) {
        this.accountOpenDeviceNumber = accountOpenDeviceNumber == null ? null : accountOpenDeviceNumber.trim();
    }

    public Date getLastTransTime() {
        return lastTransTime;
    }

    public void setLastTransTime(Date lastTransTime) {
        this.lastTransTime = lastTransTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
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

    public String getBorrowingSigns() {
        return borrowingSigns;
    }

    public void setBorrowingSigns(String borrowingSigns) {
        this.borrowingSigns = borrowingSigns == null ? null : borrowingSigns.trim();
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransCurrency() {
        return transCurrency;
    }

    public void setTransCurrency(String transCurrency) {
        this.transCurrency = transCurrency == null ? null : transCurrency.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransSerial() {
        return transSerial;
    }

    public void setTransSerial(String transSerial) {
        this.transSerial = transSerial == null ? null : transSerial.trim();
    }

    public BigDecimal getTransBalance() {
        return transBalance;
    }

    public void setTransBalance(BigDecimal transBalance) {
        this.transBalance = transBalance;
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

    public String getBankTransSerial() {
        return bankTransSerial;
    }

    public void setBankTransSerial(String bankTransSerial) {
        this.bankTransSerial = bankTransSerial == null ? null : bankTransSerial.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }

	

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
}