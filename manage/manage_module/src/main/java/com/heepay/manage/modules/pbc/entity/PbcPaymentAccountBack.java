package com.heepay.manage.modules.pbc.entity;

import java.util.List;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcPaymentAccountBack extends DataEntity<PbcPaymentAccountBack>{
    /**
	 * 2016年12月24日
	 */
	private static final long serialVersionUID = -4516776465019106350L;

	private Long paymentAccountId;

	private String accountNumber;

    private String subjectType;

    private String accountOwnerName;

    private String accountOwnerIdType;

    private String accountOwnerId;

    private String credentialValidity;

    private String telNumber;

    private String loginId;

    private String wechatId;

    private String qq;

    private String regularIpAddress;

    private String regularDeviceNumber;

    private String onlineShopName;

    private String merchantNumber;

    private String posIdentity;

    private String posAddress;

    private String posCommonlyUsedAddress;

    private String applicationCode;
    
    private String featureCode;
    
    private String accountName;
    /**
     * 
     */
    
    private List<PbcBankInfo> backCard;
    
    private List<PbcMeasureInfo> measures;
    
    

    public Long getPaymentAccountId() {
        return paymentAccountId;
    }

    public void setPaymentAccountId(Long paymentAccountId) {
        this.paymentAccountId = paymentAccountId;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode == null ? null : featureCode.trim();
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getRegularIpAddress() {
        return regularIpAddress;
    }

    public void setRegularIpAddress(String regularIpAddress) {
        this.regularIpAddress = regularIpAddress == null ? null : regularIpAddress.trim();
    }

    public String getRegularDeviceNumber() {
        return regularDeviceNumber;
    }

    public void setRegularDeviceNumber(String regularDeviceNumber) {
        this.regularDeviceNumber = regularDeviceNumber == null ? null : regularDeviceNumber.trim();
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

    public String getPosIdentity() {
        return posIdentity;
    }

    public void setPosIdentity(String posIdentity) {
        this.posIdentity = posIdentity == null ? null : posIdentity.trim();
    }

    public String getPosAddress() {
        return posAddress;
    }

    public void setPosAddress(String posAddress) {
        this.posAddress = posAddress == null ? null : posAddress.trim();
    }

    public String getPosCommonlyUsedAddress() {
        return posCommonlyUsedAddress;
    }

    public void setPosCommonlyUsedAddress(String posCommonlyUsedAddress) {
        this.posCommonlyUsedAddress = posCommonlyUsedAddress == null ? null : posCommonlyUsedAddress.trim();
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }

	public List<PbcBankInfo> getBackCard() {
		return backCard;
	}

	public void setBackCard(List<PbcBankInfo> backCard) {
		this.backCard = backCard;
	}

	public List<PbcMeasureInfo> getMeasures() {
		return measures;
	}

	public void setMeasures(List<PbcMeasureInfo> measures) {
		this.measures = measures;
	}

	
}