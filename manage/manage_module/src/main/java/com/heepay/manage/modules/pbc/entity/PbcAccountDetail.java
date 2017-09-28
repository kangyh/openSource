package com.heepay.manage.modules.pbc.entity;

import java.util.List;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcAccountDetail extends DataEntity<PbcAccountDetail>{
    /**
	 * 2016年12月24日
	 */
	private static final long serialVersionUID = 4830733261864805429L;

	private Long pbcId;

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
	
    private String dataType;

    private String data;

    private String applicationCode;
    
    private List<PbcBankInfo> backCadr;

    public Long getPbcId() {
        return pbcId;
    }

    public void setPbcId(Long pbcId) {
        this.pbcId = pbcId;
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

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }

	public List<PbcBankInfo> getBackCadr() {
		return backCadr;
	}

	public void setBackCadr(List<PbcBankInfo> backCadr) {
		this.backCadr = backCadr;
	}
}