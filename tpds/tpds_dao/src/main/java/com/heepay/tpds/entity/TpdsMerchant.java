package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsMerchant {
    private Integer merchantId;

    /**
     * 商户编号MERCHANT_NO
     */
    private String merchantNo;

    /**
     * 商户名称
     */
    private String loginName;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 国家名称
     */
    private String contryName;

    /**
     * 商户类型(枚举类MerchantType (("ENTERP", "企业"),("PUBINS", "事业单位"),("PRIENT", "民办非企业单位"),("INDBUS", "个体工商户"),("SOCGRO", "社会团体"),"PPSORG", "党政及国家机关")))
     */
    private String type;

    /**
     * 证件类型(枚举类CertificateType (("ORDINA","普通证件"),("MULTIP","多证合一"),("INDIVI","个体商户")))
     */
    private String certificateType;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 省、自治区、州代码
     */
    private String provinceCode;

    /**
     * 省、自治区、州名称
     */
    private String provinceName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 县代码
     */
    private String countyCode;

    /**
     * 县名称
     */
    private String countyName;

    /**
     * 公司注册地址 - 经营地址
     */
    private String businessAddress;

    /**
     * 公司网址
     */
    private String website;

    /**
     * 营业执照号码
     */
    private String businessLicenseNo;

    /**
     * 营业执照结束时间
     */
    private Date businessLicenseEndTime;

    /**
     * 组织机构代码
     */
    private String organizationCode;

    /**
     * 税务登记证号码
     */
    private String taxRegistrationCertificateNo;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 法人名称
     */
    private String llegalperson;

    /**
     * 法人代表身份证号
     */
    private String legalIdcard;

    /**
     * 法人代表证件有效期结束
     */
    private Date legalCertificateEndTime;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系人身份证号
     */
    private String contactorIdcardNo;

    /**
     * 联系人证件有效期结束
     */
    private Date contactorCertificateEndTime;

    /**
     * 联系人手机号
     */
    private String contactorPhone;

    /**
     * 商户名称
     */
    private String supplierName;

    /**
     * 电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）
     */
    private String enterpriseName;

    /**
     * 电商/店铺网址
     */
    private String electricityUrl;

    /**
     * 个人用户
     */
    private String individualUser;

    /**
     * 国家代码
     */
    private String contryCode;

    /**
     * 中国银联商户号
     */
    private String merchantPosCode;

    /**
     * 公司联系电话
     */
    private String companyPhone;

    /**
     * 法人手机号码
     */
    private String legalMobile;

    /**
     * 手续费收取类型
     */
    private Byte chargeType;

    /**
     * IPC备案号
     */
    private String ipcNo;

    /**
     * 开户许可证
     */
    private String permitsAccounts;

    /**
     * 法人代表证件照(正)
     */
    private String legalCertificateFront;

    /**
     * 法人代表证件照(反)
     */
    private String legalCertificateBack;

    /**
     * 税务登记证
     */
    private String taxRegistrationCertificate;

    /**
     * 组织机构代码证
     */
    private String organizationCodeCertificate;

    /**
     * 营业执照文件本地存储路径及文件名
     */
    private String businessLicenseFile;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createAuthor;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 商标注册证照片地址
     */
    private String trademarkRegisPhotoAddress;

    /**
     * 更新者
     */
    private byte[] updateAuthor;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTrademarkRegisPhotoAddress() {
        return trademarkRegisPhotoAddress;
    }

    public void setTrademarkRegisPhotoAddress(String trademarkRegisPhotoAddress) {
        this.trademarkRegisPhotoAddress = trademarkRegisPhotoAddress == null ? null : trademarkRegisPhotoAddress.trim();
    }

    public byte[] getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(byte[] updateAuthor) {
        this.updateAuthor = updateAuthor;
    }
}