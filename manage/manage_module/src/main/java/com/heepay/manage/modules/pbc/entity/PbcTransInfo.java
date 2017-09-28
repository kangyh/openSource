package com.heepay.manage.modules.pbc.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcTransInfo extends DataEntity<PbcTransInfo>{
    /**
	 * 2016年12月24日
	 */
	private static final long serialVersionUID = 106740763320905628L;

	private Long pbcId;

    private String orderId;

    private String transactionType;

    private String paymentType;

    private String borrowingSigns;

    private Date transactionTime;

    private BigDecimal transactionAmount;

    private String transactionSerial;

    private BigDecimal accountBalance;

    private String currency;

    private String transInBankId;

    private String transInBankName;

    private String transInCardNumber;

    private String transInAccountNumber;

    private String posIdentity;

    private String merchantNumber;

    private String transOutBankId;

    private String transOutBankName;

    private String transOutCardNumber;

    private String transOutAccountNumber;

    private String transactionDeviceType;

    private String transactionIp;

    private String transactionMac;

    private String transactionDeviceCode;

    private String bankTransactionSerial;

    private String remark;

    private String applicationCode;

    private BigDecimal transactionLongitude;

    private BigDecimal transactionLatitude;

    public Long getPbcId() {
        return pbcId;
    }

    public void setPbcId(Long pbcId) {
        this.pbcId = pbcId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType == null ? null : transactionType.trim();
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

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionSerial() {
        return transactionSerial;
    }

    public void setTransactionSerial(String transactionSerial) {
        this.transactionSerial = transactionSerial == null ? null : transactionSerial.trim();
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getTransInBankId() {
        return transInBankId;
    }

    public void setTransInBankId(String transInBankId) {
        this.transInBankId = transInBankId == null ? null : transInBankId.trim();
    }

    public String getTransInBankName() {
        return transInBankName;
    }

    public void setTransInBankName(String transInBankName) {
        this.transInBankName = transInBankName == null ? null : transInBankName.trim();
    }

    public String getTransInCardNumber() {
        return transInCardNumber;
    }

    public void setTransInCardNumber(String transInCardNumber) {
        this.transInCardNumber = transInCardNumber == null ? null : transInCardNumber.trim();
    }

    public String getTransInAccountNumber() {
        return transInAccountNumber;
    }

    public void setTransInAccountNumber(String transInAccountNumber) {
        this.transInAccountNumber = transInAccountNumber == null ? null : transInAccountNumber.trim();
    }

    public String getPosIdentity() {
        return posIdentity;
    }

    public void setPosIdentity(String posIdentity) {
        this.posIdentity = posIdentity == null ? null : posIdentity.trim();
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

    public String getTransactionDeviceType() {
        return transactionDeviceType;
    }

    public void setTransactionDeviceType(String transactionDeviceType) {
        this.transactionDeviceType = transactionDeviceType == null ? null : transactionDeviceType.trim();
    }

    public String getTransactionIp() {
        return transactionIp;
    }

    public void setTransactionIp(String transactionIp) {
        this.transactionIp = transactionIp == null ? null : transactionIp.trim();
    }

    public String getTransactionMac() {
        return transactionMac;
    }

    public void setTransactionMac(String transactionMac) {
        this.transactionMac = transactionMac == null ? null : transactionMac.trim();
    }

    public String getTransactionDeviceCode() {
        return transactionDeviceCode;
    }

    public void setTransactionDeviceCode(String transactionDeviceCode) {
        this.transactionDeviceCode = transactionDeviceCode == null ? null : transactionDeviceCode.trim();
    }

    public String getBankTransactionSerial() {
        return bankTransactionSerial;
    }

    public void setBankTransactionSerial(String bankTransactionSerial) {
        this.bankTransactionSerial = bankTransactionSerial == null ? null : bankTransactionSerial.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }

    public BigDecimal getTransactionLongitude() {
        return transactionLongitude;
    }

    public void setTransactionLongitude(BigDecimal transactionLongitude) {
        this.transactionLongitude = transactionLongitude;
    }

    public BigDecimal getTransactionLatitude() {
        return transactionLatitude;
    }

    public void setTransactionLatitude(BigDecimal transactionLatitude) {
        this.transactionLatitude = transactionLatitude;
    }
}