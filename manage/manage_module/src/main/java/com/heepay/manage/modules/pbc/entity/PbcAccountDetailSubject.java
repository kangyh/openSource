package com.heepay.manage.modules.pbc.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcAccountDetailSubject extends DataEntity<PbcAccountDetailSubject>{
    private static final long serialVersionUID = 1L;

    private long accountDetailId;

    private String dataType;

    private String data;

    private String subjectType;

    private String accountOwnerName;

    private String accountOwnerIdType;

    private String accountOwnerId;

    private String credentialValidity;

    private String telNumber;

    private String bindingDataType;

    private String bindingData;

    private String onlineShopName;

    private String merchantNumber;

    private String bankId;

    private String bankName;

    private String bankAccount;

    private String cardType;

    private String cardValidation;

    private String cardExpiryDate;

    private String cardInfo;

    private String applicationCode;
    
    //private String applicationID;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;
    
    private String local;//用于页面跳转的地址保存
    
    private long feedBackId;//用于页面跳转的地址保存

	public long getAccountDetailId() {
		return accountDetailId;
	}

	public void setAccountDetailId(long accountDetailId) {
		this.accountDetailId = accountDetailId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getAccountOwnerName() {
		return accountOwnerName;
	}

	public void setAccountOwnerName(String accountOwnerName) {
		this.accountOwnerName = accountOwnerName;
	}

	public String getAccountOwnerIdType() {
		return accountOwnerIdType;
	}

	public void setAccountOwnerIdType(String accountOwnerIdType) {
		this.accountOwnerIdType = accountOwnerIdType;
	}

	public String getAccountOwnerId() {
		return accountOwnerId;
	}

	public void setAccountOwnerId(String accountOwnerId) {
		this.accountOwnerId = accountOwnerId;
	}

	public String getCredentialValidity() {
		return credentialValidity;
	}

	public void setCredentialValidity(String credentialValidity) {
		this.credentialValidity = credentialValidity;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getBindingDataType() {
		return bindingDataType;
	}

	public void setBindingDataType(String bindingDataType) {
		this.bindingDataType = bindingDataType;
	}

	public String getBindingData() {
		return bindingData;
	}

	public void setBindingData(String bindingData) {
		this.bindingData = bindingData;
	}

	public String getOnlineShopName() {
		return onlineShopName;
	}

	public void setOnlineShopName(String onlineShopName) {
		this.onlineShopName = onlineShopName;
	}

	public String getMerchantNumber() {
		return merchantNumber;
	}

	public void setMerchantNumber(String merchantNumber) {
		this.merchantNumber = merchantNumber;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardValidation() {
		return cardValidation;
	}

	public void setCardValidation(String cardValidation) {
		this.cardValidation = cardValidation;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
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

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public long getFeedBackId() {
		return feedBackId;
	}

	public void setFeedBackId(long feedBackId) {
		this.feedBackId = feedBackId;
	}
    
    
}