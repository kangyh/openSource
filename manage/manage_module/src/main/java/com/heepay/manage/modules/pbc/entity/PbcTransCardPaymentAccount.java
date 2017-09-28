package com.heepay.manage.modules.pbc.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcTransCardPaymentAccount extends DataEntity<PbcTransCardPaymentAccount>{
    private static final long serialVersionUID = 1L;

    private long transCardPaymentId;

    private String onlinePayCompanyType;

    private String transactionType;

    private String paymentType;

    private String currency;

    private BigDecimal transferAmount;

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
    
    private BigDecimal transactionLongitude;

    private BigDecimal transactionLatitude;

    private String applicationCode;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

	public long getTransCardPaymentId() {
		return transCardPaymentId;
	}

	public void setTransCardPaymentId(long transCardPaymentId) {
		this.transCardPaymentId = transCardPaymentId;
	}

	public String getOnlinePayCompanyType() {
		return onlinePayCompanyType;
	}

	public void setOnlinePayCompanyType(String onlinePayCompanyType) {
		this.onlinePayCompanyType = onlinePayCompanyType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getTransInBankId() {
		return transInBankId;
	}

	public void setTransInBankId(String transInBankId) {
		this.transInBankId = transInBankId;
	}

	public String getTransInBankName() {
		return transInBankName;
	}

	public void setTransInBankName(String transInBankName) {
		this.transInBankName = transInBankName;
	}

	public String getTransInCardNumber() {
		return transInCardNumber;
	}

	public void setTransInCardNumber(String transInCardNumber) {
		this.transInCardNumber = transInCardNumber;
	}

	public String getTransInAccountNumber() {
		return transInAccountNumber;
	}

	public void setTransInAccountNumber(String transInAccountNumber) {
		this.transInAccountNumber = transInAccountNumber;
	}

	public String getPosIdentity() {
		return posIdentity;
	}

	public void setPosIdentity(String posIdentity) {
		this.posIdentity = posIdentity;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getTransOutBankId() {
		return transOutBankId;
	}

	public void setTransOutBankId(String transOutBankId) {
		this.transOutBankId = transOutBankId;
	}

	public String getTransOutBankName() {
		return transOutBankName;
	}

	public void setTransOutBankName(String transOutBankName) {
		this.transOutBankName = transOutBankName;
	}

	public String getTransOutCardNumber() {
		return transOutCardNumber;
	}

	public void setTransOutCardNumber(String transOutCardNumber) {
		this.transOutCardNumber = transOutCardNumber;
	}

	public String getTransOutAccountNumber() {
		return transOutAccountNumber;
	}

	public void setTransOutAccountNumber(String transOutAccountNumber) {
		this.transOutAccountNumber = transOutAccountNumber;
	}

	public String getTransactionDeviceType() {
		return transactionDeviceType;
	}

	public void setTransactionDeviceType(String transactionDeviceType) {
		this.transactionDeviceType = transactionDeviceType;
	}

	public String getTransactionIp() {
		return transactionIp;
	}

	public void setTransactionIp(String transactionIp) {
		this.transactionIp = transactionIp;
	}

	public String getTransactionMac() {
		return transactionMac;
	}

	public void setTransactionMac(String transactionMac) {
		this.transactionMac = transactionMac;
	}

	public String getTransactionDeviceCode() {
		return transactionDeviceCode;
	}

	public void setTransactionDeviceCode(String transactionDeviceCode) {
		this.transactionDeviceCode = transactionDeviceCode;
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

	public String getBankTransactionSerial() {
		return bankTransactionSerial;
	}

	public void setBankTransactionSerial(String bankTransactionSerial) {
		this.bankTransactionSerial = bankTransactionSerial;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

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

	
}