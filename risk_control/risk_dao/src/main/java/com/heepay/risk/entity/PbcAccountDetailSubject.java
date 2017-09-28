package com.heepay.risk.entity;

import java.util.Date;


public class PbcAccountDetailSubject {
    private static final long serialVersionUID = 1L;

	private Long accountDetailId;

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

    private String bankNumber;

    private String cardType;

    private String cardValidationStatus;

    private String cardExpiryDate;

    private String cardInfo;

    private Long feedBackId;

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

	public Long getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(Long accountDetailId) {
        this.accountDetailId = accountDetailId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName == null ? null : accountOwnerName.trim();
    }

    public String getAccountOwnerIdType() {
        return accountOwnerIdType;
    }

    public void setAccountOwnerIdType(String accountOwnerIdType) {
        this.accountOwnerIdType = accountOwnerIdType == null ? null : accountOwnerIdType.trim();
    }

    public String getAccountOwnerId() {
        return accountOwnerId;
    }

    public void setAccountOwnerId(String accountOwnerId) {
        this.accountOwnerId = accountOwnerId == null ? null : accountOwnerId.trim();
    }

    public String getCredentialValidity() {
        return credentialValidity;
    }

    public void setCredentialValidity(String credentialValidity) {
        this.credentialValidity = credentialValidity == null ? null : credentialValidity.trim();
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber == null ? null : telNumber.trim();
    }

    public String getBindingDataType() {
        return bindingDataType;
    }

    public void setBindingDataType(String bindingDataType) {
        this.bindingDataType = bindingDataType == null ? null : bindingDataType.trim();
    }

    public String getBindingData() {
        return bindingData;
    }

    public void setBindingData(String bindingData) {
        this.bindingData = bindingData == null ? null : bindingData.trim();
    }

    public String getOnlineShopName() {
        return onlineShopName;
    }

    public void setOnlineShopName(String onlineShopName) {
        this.onlineShopName = onlineShopName == null ? null : onlineShopName.trim();
    }

    public String getMerchantNumber() {
        return merchantNumber;
    }

    public void setMerchantNumber(String merchantNumber) {
        this.merchantNumber = merchantNumber == null ? null : merchantNumber.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber == null ? null : bankNumber.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardValidationStatus() {
        return cardValidationStatus;
    }

    public void setCardValidationStatus(String cardValidationStatus) {
        this.cardValidationStatus = cardValidationStatus == null ? null : cardValidationStatus.trim();
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate == null ? null : cardExpiryDate.trim();
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo == null ? null : cardInfo.trim();
    }

    public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }
}