package com.heepay.manage.modules.tpds.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

/***
 * 
* 
* 描    述：商户信息表
*
* 创 建 者： wangl
* 创建时间：  9 Feb 201710:16:45
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
public class TpdsMerchant extends DataEntity<TpdsMerchant> {
    
	private static final long serialVersionUID = 1L;

	private Integer merchantId;

	private String loginName;
	
    private String merchantNo;

    private String email;

    private String contryName;

    private String type;

    private String certificateType;

    private String companyName;

    private String provinceCode;

    private String provinceName;

    private String cityCode;

    private String cityName;

    private String countyCode;

    private String countyName;

    private String businessAddress;

    private String website;

    private String businessLicenseNo;

    private Date businessLicenseEndTime;

    private String organizationCode;

    private String taxRegistrationCertificateNo;

    private String businessScope;

    private String llegalperson;

    private String legalIdcard;

    private Date legalCertificateEndTime;

    private String contactor;

    private String contactorIdcardNo;

    private Date contactorCertificateEndTime;

    private String contactorPhone;

    private String supplierName;

    private String enterpriseName;

    private String electricityUrl;

    private String individualUser;

    private String contryCode;

    private String merchantPosCode;

    private String companyPhone;

    private String legalMobile;

    private Byte chargeType;

    private String ipcNo;

    private String permitsAccounts;

    private String legalCertificateFront;

    private String legalCertificateBack;

    private String taxRegistrationCertificate;

    private String organizationCodeCertificate;

    private String businessLicenseFile;

    private String trademarkRegisPhotoAddress;

    private Date createTime;
    
    private Date updateTime;
    
    private String createAuthor;
    
    private String updateAuthor;
    
    //非映射字段
    private Date beginOperEndTime;
    
    private Date endOperEndTime;
    
    
    
    
    public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
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

	public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName == null ? null : contryName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType == null ? null : certificateType.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode == null ? null : countyCode.trim();
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName == null ? null : countyName.trim();
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress == null ? null : businessAddress.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
    }

    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo == null ? null : businessLicenseNo.trim();
    }

    public Date getBusinessLicenseEndTime() {
        return businessLicenseEndTime;
    }

    public void setBusinessLicenseEndTime(Date businessLicenseEndTime) {
        this.businessLicenseEndTime = businessLicenseEndTime;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
    }

    public String getTaxRegistrationCertificateNo() {
        return taxRegistrationCertificateNo;
    }

    public void setTaxRegistrationCertificateNo(String taxRegistrationCertificateNo) {
        this.taxRegistrationCertificateNo = taxRegistrationCertificateNo == null ? null : taxRegistrationCertificateNo.trim();
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getLlegalperson() {
        return llegalperson;
    }

    public void setLlegalperson(String llegalperson) {
        this.llegalperson = llegalperson == null ? null : llegalperson.trim();
    }

    public String getLegalIdcard() {
        return legalIdcard;
    }

    public void setLegalIdcard(String legalIdcard) {
        this.legalIdcard = legalIdcard == null ? null : legalIdcard.trim();
    }

    public Date getLegalCertificateEndTime() {
        return legalCertificateEndTime;
    }

    public void setLegalCertificateEndTime(Date legalCertificateEndTime) {
        this.legalCertificateEndTime = legalCertificateEndTime;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor == null ? null : contactor.trim();
    }

    public String getContactorIdcardNo() {
        return contactorIdcardNo;
    }

    public void setContactorIdcardNo(String contactorIdcardNo) {
        this.contactorIdcardNo = contactorIdcardNo == null ? null : contactorIdcardNo.trim();
    }

    public Date getContactorCertificateEndTime() {
        return contactorCertificateEndTime;
    }

    public void setContactorCertificateEndTime(Date contactorCertificateEndTime) {
        this.contactorCertificateEndTime = contactorCertificateEndTime;
    }

    public String getContactorPhone() {
        return contactorPhone;
    }

    public void setContactorPhone(String contactorPhone) {
        this.contactorPhone = contactorPhone == null ? null : contactorPhone.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getElectricityUrl() {
        return electricityUrl;
    }

    public void setElectricityUrl(String electricityUrl) {
        this.electricityUrl = electricityUrl == null ? null : electricityUrl.trim();
    }

    public String getIndividualUser() {
        return individualUser;
    }

    public void setIndividualUser(String individualUser) {
        this.individualUser = individualUser == null ? null : individualUser.trim();
    }

    public String getContryCode() {
        return contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode == null ? null : contryCode.trim();
    }

    public String getMerchantPosCode() {
        return merchantPosCode;
    }

    public void setMerchantPosCode(String merchantPosCode) {
        this.merchantPosCode = merchantPosCode == null ? null : merchantPosCode.trim();
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone == null ? null : companyPhone.trim();
    }

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile == null ? null : legalMobile.trim();
    }

    public Byte getChargeType() {
        return chargeType;
    }

    public void setChargeType(Byte chargeType) {
        this.chargeType = chargeType;
    }

    public String getIpcNo() {
        return ipcNo;
    }

    public void setIpcNo(String ipcNo) {
        this.ipcNo = ipcNo == null ? null : ipcNo.trim();
    }

    public String getPermitsAccounts() {
        return permitsAccounts;
    }

    public void setPermitsAccounts(String permitsAccounts) {
        this.permitsAccounts = permitsAccounts == null ? null : permitsAccounts.trim();
    }

    public String getLegalCertificateFront() {
        return legalCertificateFront;
    }

    public void setLegalCertificateFront(String legalCertificateFront) {
        this.legalCertificateFront = legalCertificateFront == null ? null : legalCertificateFront.trim();
    }

    public String getLegalCertificateBack() {
        return legalCertificateBack;
    }

    public void setLegalCertificateBack(String legalCertificateBack) {
        this.legalCertificateBack = legalCertificateBack == null ? null : legalCertificateBack.trim();
    }

    public String getTaxRegistrationCertificate() {
        return taxRegistrationCertificate;
    }

    public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
        this.taxRegistrationCertificate = taxRegistrationCertificate == null ? null : taxRegistrationCertificate.trim();
    }

    public String getOrganizationCodeCertificate() {
        return organizationCodeCertificate;
    }

    public void setOrganizationCodeCertificate(String organizationCodeCertificate) {
        this.organizationCodeCertificate = organizationCodeCertificate == null ? null : organizationCodeCertificate.trim();
    }

    public String getBusinessLicenseFile() {
        return businessLicenseFile;
    }

    public void setBusinessLicenseFile(String businessLicenseFile) {
        this.businessLicenseFile = businessLicenseFile == null ? null : businessLicenseFile.trim();
    }

    public String getTrademarkRegisPhotoAddress() {
        return trademarkRegisPhotoAddress;
    }

    public void setTrademarkRegisPhotoAddress(String trademarkRegisPhotoAddress) {
        this.trademarkRegisPhotoAddress = trademarkRegisPhotoAddress == null ? null : trademarkRegisPhotoAddress.trim();
    }
}