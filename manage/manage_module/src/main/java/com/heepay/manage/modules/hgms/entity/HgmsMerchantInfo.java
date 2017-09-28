/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *
 * 描    述：资金归集商户Entity
 *
 * 创 建 者： @author 郭正新
 * 创建时间： 2017-03-20 11:32:56
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
public class HgmsMerchantInfo extends DataEntity<HgmsMerchantInfo> {

    private static final long serialVersionUID = 1L;
    private String loginName;                            //登录名
    private String companyId;        // 公司的ID系统自动生成
    private String merchantId;        // 商户编号  与user表id字段为一个值\r\n
    private String companyName;        // 公司名称
    private String superiorId;        // 上级商户ID 集团总部的ID
    private String superiorName;        // 上级商户公司名称 集团总部的公司名称
    private String email;        // 商户邮箱
    private String companyType;        // 账户类型    HQ（HEADQUAR）集团总部  DIVISION 分公司
    private String employeeAccount;        // 员工人数
    private String bankNo;        // 银行卡代码
    private String bankName;        // 银行名称
    private String openBankCode;        // 开户银行代码
    private String openBankName;        // 开户银行支行名称
    private String bankInfo;        // 银行信息
    private String bankcardNo;        // 银行卡号
    private String bankcardType;        // 银行卡类型 UNKNOW":"未知"  "SAVING":"储蓄卡"  "CREDIT":"信用卡
    private String bankcardExpiredDate;        // 银行卡有效期  yyyyMM
    private String bankcardOwnerMobile;        // 银行预留手机号
    private String bankcardOwnerName;        // 银行卡持卡人名称
    private String bankcardOwnerIdcard;        // 持卡人身份证号
    private String bankcardOwnerType;        // 银行卡持卡人类型  0=个人1=企业-1=未知
    private String associateLineNumber;        // 银行联行号就是一个地区银行的唯一识别标志，由12位组成：3位银行代码+4位城市代码+4位银行编号+1位校验位。
    private String authenticationStatus;        // 银行卡认证状态  (枚举类AuthenticationStatus((
    private String busipermit;        // 经营许可证
    private String busiaddr;        // 营业地址
    private String legalPersionAddress;        // 法人联系地址
    private String legalPersionEmail;        // 法人邮箱
    private String contactorPersionAddress;        // 联系人联系地址
    private String contactorPersionEmail;        // 联系人邮箱
    private String merchantIp;        // 商户ip
    private Date contractTime;        // 商户签约时间
    private String inputuserId;        // 录入人
    private Date createdTime;        // 录入时间
    private String updateUserId;        // 修改人
    private Date updateTime;        // 修改时间
    private String industryTypes;        // 事业类型
    private String riskLevel;        // 风险类型   1、低风险---LOW2、一般风险---GENERAL3、中等风险--MEDIUM4、高等风险--HIGH
    private String creditLevel;        // 信用评级      0、未评级1、一级2、二级3、三级
    private String merchantPosCode;        // 中国银联商户号
    private String legalRepresentative;        // 法人代表
    private String companyPhone;        // 公司联系电话
    private String legalMobile;        // 法人手机号码
    private String website;        // 公司网址
    private String businessAddress;        // 公司注册地址
    private String contryCode;        // 国家代码
    private String contryName;        // 国家名称
    private String provinceCode;        // 省、自治区、州代码
    private String provinceName;        // 省、自治区、州名称
    private String cityCode;        // 城市代码
    private String cityName;        // 城市名称
    private String countyCode;        // 县代码
    private String countyName;        // 县名称
    private String contactAddress;        // 联系地址
    private String contactor;        // 联系人
    private String type;        // 商户类型(枚举类MerchantType ((
    private String level;        // 商户等级
    private String chargeType;        // 手续费收取类型
    private String requestWays;        // 商户的请求方式
    private String requestVersion;        // 商户请求的版本号
    private String allowedIps;        // 允许商户请求的IP地址列表通过|分隔
    private String signedType;        // 签约类型
    private String status;        // 商户状态(最终通过审核 枚举类AuthenticationStatus((
    private Date opAuditTime;        // 运营修改时间
    private String opAuditor;        // 运营修改人
    private String rcAuditStatus;        // 风控审核状态(枚举类RouteStatus ((
    private Date rcAuditTime;        // 风控审核时间
    private String rcAuditor;        // 风控审核人
    private String legalAuditStatus;        // 法务审核状态(枚举类RouteStatus ((
    private Date legalAuditTime;        // 法务审核时间
    private String legalAuditor;        // 法务审核人
    private String salesId;        // 销售人员
    private String inchargerId;        // 当前负责人
    private String remark;        // 备注
    private Date createTime;        // 创建时间
    private Date beginCreateTime;   // 开始 创建时间
    private Date endCreateTime;   // 结束 创建时间
    private String welcomeMessage;        // 商户欢迎信息
    private String certificateType;        // 证件类型(枚举类CertificateType ((
    private Date businessLicenseEndTime;        // 营业执照结束时间
    private String organizationCode;        // 组织机构代码
    private String taxRegistrationCertificateNo;        // 税务登记证号码
    private String businessScope;        // 经营范围
    private String legalIdcard;        // 法人代表身份证号
    private Date legalCertificateEndTime;        // 法人代表证件有效期结束
    private String contactorIdcardNo;        // 联系人身份证号
    private Date contactorCertificateEndTime;        // 联系人证件有效期结束
    private String contactorPhone;        // 联系人手机号
    private String ipcNo;        // IPC备案号
    private String permitsAccounts;        // 开户许可证
    private String legalCertificateFront;        // 法人代表证件照(正)
    private String legalCertificateBack;        // 法人代表证件照(反)
    private String taxRegistrationCertificate;        // 税务登记证
    private String organizationCodeCertificate;        // 组织机构代码证
    private String businessLicenseFile;        // 营业执照文件本地存储路径及文件名
    private String businessLicenseNo;        // 营业执照号码
    private String objection;        // 拒绝理由
    private String retainedAmount;        // 留存金额
    private String unionPayProvinceCode;        // 银联区域编码(省代码merchant_unionpay_area_base表province_id字段)
    private String unionPayProvinceName;        // 银联区域编码(省名称merchant_unionpay_area_base表province_name字段)
    private String unionPayCityCode;        // 银联区域编码(市代码merchant_unionpay_area_base表city_id字段)
    private String unionPayCityName;        // 银联区域编码(市名称merchant_unionpay_area_base表city_name字段)
    private String mccType;        // 商户行业基本类型(merchant_industry_bass表industry_id字段)
    private String mccServer;        // 商户行业基本类型(merchant_industry_bass表industry_child_id字段)
    private String mccDetail;        // 商户行业基本类型(merchant_industry_bass表mcc字段)
    private String industryCategory;        // 商户MCC码(merchant_industry_bass表mcc字段)
    private String protocolNumber;        // 协议号
    private String remark1;        // 首次登录状态 0是首次登录 1非首次登录
    private String remark2;        // 登录账号状态(枚举类MerchantStatus  ("NORMAL","正常")("FREZED","冻结")("CLOSED","关闭")("UNKNOW","未知"))
    private String remark3;        // remark3
    private String remark4;        // remark4

    public HgmsMerchantInfo() {
        super();
    }

    public HgmsMerchantInfo(String id) {
        super(id);
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Length(min = 1, max = 10, message = "公司的ID系统自动生成长度必须介于 1 和 10 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Length(min = 0, max = 64, message = "商户编号  与user表id字段为一个值\r\n长度必须介于 0 和 64 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Length(min = 0, max = 100, message = "公司名称长度必须介于 0 和 100 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Length(min = 0, max = 64, message = "上级商户ID 集团总部的ID长度必须介于 0 和 64 之间")
    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    @Length(min = 0, max = 256, message = "上级商户公司名称 集团总部的公司名称长度必须介于 0 和 256 之间")
    public String getSuperiorName() {
        return superiorName;
    }

    public void setSuperiorName(String superiorName) {
        this.superiorName = superiorName;
    }

    @Length(min = 0, max = 100, message = "商户邮箱长度必须介于 0 和 100 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 8, message = "账户类型    HQ（HEADQUAR）集团总部  DIVISION 分公司长度必须介于 0 和 8 之间")
    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    @Length(min = 0, max = 8, message = "员工人数长度必须介于 0 和 8 之间")
    public String getEmployeeAccount() {
        return employeeAccount;
    }

    public void setEmployeeAccount(String employeeAccount) {
        this.employeeAccount = employeeAccount;
    }

    @Length(min = 0, max = 3, message = "银行卡代码长度必须介于 0 和 3 之间")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Length(min = 0, max = 256, message = "银行名称长度必须介于 0 和 256 之间")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Length(min = 0, max = 5, message = "开户银行代码长度必须介于 0 和 5 之间")
    public String getOpenBankCode() {
        return openBankCode;
    }

    public void setOpenBankCode(String openBankCode) {
        this.openBankCode = openBankCode;
    }

    @Length(min = 0, max = 256, message = "开户银行支行名称长度必须介于 0 和 256 之间")
    public String getOpenBankName() {
        return openBankName;
    }

    public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName;
    }

    @Length(min = 0, max = 256, message = "银行信息长度必须介于 0 和 256 之间")
    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Length(min = 0, max = 256, message = "银行卡号长度必须介于 0 和 256 之间")
    public String getBankcardNo() {
        return bankcardNo;
    }

    public void setBankcardNo(String bankcardNo) {
        this.bankcardNo = bankcardNo;
    }

    @Length(min = 0, max = 6, message = "银行卡类型 UNKNOW长度必须介于 0 和 6 之间")
    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType;
    }

    @Length(min = 0, max = 16, message = "银行卡有效期  yyyyMM长度必须介于 0 和 16 之间")
    public String getBankcardExpiredDate() {
        return bankcardExpiredDate;
    }

    public void setBankcardExpiredDate(String bankcardExpiredDate) {
        this.bankcardExpiredDate = bankcardExpiredDate;
    }

    @Length(min = 0, max = 256, message = "银行预留手机号长度必须介于 0 和 256 之间")
    public String getBankcardOwnerMobile() {
        return bankcardOwnerMobile;
    }

    public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
        this.bankcardOwnerMobile = bankcardOwnerMobile;
    }

    @Length(min = 0, max = 64, message = "银行卡持卡人名称长度必须介于 0 和 64 之间")
    public String getBankcardOwnerName() {
        return bankcardOwnerName;
    }

    public void setBankcardOwnerName(String bankcardOwnerName) {
        this.bankcardOwnerName = bankcardOwnerName;
    }

    @Length(min = 0, max = 256, message = "持卡人身份证号长度必须介于 0 和 256 之间")
    public String getBankcardOwnerIdcard() {
        return bankcardOwnerIdcard;
    }

    public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
        this.bankcardOwnerIdcard = bankcardOwnerIdcard;
    }

    @Length(min = 0, max = 4, message = "银行卡持卡人类型  0=个人1=企业-1=未知长度必须介于 0 和 4 之间")
    public String getBankcardOwnerType() {
        return bankcardOwnerType;
    }

    public void setBankcardOwnerType(String bankcardOwnerType) {
        this.bankcardOwnerType = bankcardOwnerType;
    }

    @Length(min = 0, max = 12, message = "银行联行号就是一个地区银行的唯一识别标志，由12位组成：3位银行代码+4位城市代码+4位银行编号+1位校验位。长度必须介于 0 和 12 之间")
    public String getAssociateLineNumber() {
        return associateLineNumber;
    }

    public void setAssociateLineNumber(String associateLineNumber) {
        this.associateLineNumber = associateLineNumber;
    }

    @Length(min = 0, max = 6, message = "银行卡认证状态  (枚举类AuthenticationStatus((长度必须介于 0 和 6 之间")
    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    @Length(min = 0, max = 20, message = "经营许可证长度必须介于 0 和 20 之间")
    public String getBusipermit() {
        return busipermit;
    }

    public void setBusipermit(String busipermit) {
        this.busipermit = busipermit;
    }

    @Length(min = 0, max = 256, message = "营业地址长度必须介于 0 和 256 之间")
    public String getBusiaddr() {
        return busiaddr;
    }

    public void setBusiaddr(String busiaddr) {
        this.busiaddr = busiaddr;
    }

    @Length(min = 0, max = 256, message = "法人联系地址长度必须介于 0 和 256 之间")
    public String getLegalPersionAddress() {
        return legalPersionAddress;
    }

    public void setLegalPersionAddress(String legalPersionAddress) {
        this.legalPersionAddress = legalPersionAddress;
    }

    @Length(min = 0, max = 100, message = "法人邮箱长度必须介于 0 和 100 之间")
    public String getLegalPersionEmail() {
        return legalPersionEmail;
    }

    public void setLegalPersionEmail(String legalPersionEmail) {
        this.legalPersionEmail = legalPersionEmail;
    }

    @Length(min = 0, max = 256, message = "联系人联系地址长度必须介于 0 和 256 之间")
    public String getContactorPersionAddress() {
        return contactorPersionAddress;
    }

    public void setContactorPersionAddress(String contactorPersionAddress) {
        this.contactorPersionAddress = contactorPersionAddress;
    }

    @Length(min = 0, max = 100, message = "联系人邮箱长度必须介于 0 和 100 之间")
    public String getContactorPersionEmail() {
        return contactorPersionEmail;
    }

    public void setContactorPersionEmail(String contactorPersionEmail) {
        this.contactorPersionEmail = contactorPersionEmail;
    }

    @Length(min = 0, max = 64, message = "商户ip长度必须介于 0 和 64 之间")
    public String getMerchantIp() {
        return merchantIp;
    }

    public void setMerchantIp(String merchantIp) {
        this.merchantIp = merchantIp;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getContractTime() {
        return contractTime;
    }

    public void setContractTime(Date contractTime) {
        this.contractTime = contractTime;
    }

    @Length(min = 0, max = 6, message = "录入人长度必须介于 0 和 6 之间")
    public String getInputuserId() {
        return inputuserId;
    }

    public void setInputuserId(String inputuserId) {
        this.inputuserId = inputuserId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Length(min = 0, max = 6, message = "修改人长度必须介于 0 和 6 之间")
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 0, max = 30, message = "事业类型长度必须介于 0 和 30 之间")
    public String getIndustryTypes() {
        return industryTypes;
    }

    public void setIndustryTypes(String industryTypes) {
        this.industryTypes = industryTypes;
    }

    @Length(min = 0, max = 7, message = "风险类型   1、低风险---LOW2、一般风险---GENERAL3、中等风险--MEDIUM4、高等风险--HIGH长度必须介于 0 和 7 之间")
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Length(min = 0, max = 7, message = "信用评级      0、未评级1、一级2、二级3、三级长度必须介于 0 和 7 之间")
    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    @Length(min = 0, max = 15, message = "中国银联商户号长度必须介于 0 和 15 之间")
    public String getMerchantPosCode() {
        return merchantPosCode;
    }

    public void setMerchantPosCode(String merchantPosCode) {
        this.merchantPosCode = merchantPosCode;
    }

    @Length(min = 0, max = 256, message = "法人代表长度必须介于 0 和 256 之间")
    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    @Length(min = 0, max = 50, message = "公司联系电话长度必须介于 0 和 50 之间")
    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    @Length(min = 0, max = 50, message = "法人手机号码长度必须介于 0 和 50 之间")
    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile;
    }

    @Length(min = 0, max = 256, message = "公司网址长度必须介于 0 和 256 之间")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Length(min = 0, max = 256, message = "公司注册地址长度必须介于 0 和 256 之间")
    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    @Length(min = 0, max = 8, message = "国家代码长度必须介于 0 和 8 之间")
    public String getContryCode() {
        return contryCode;
    }

    public void setContryCode(String contryCode) {
        this.contryCode = contryCode;
    }

    @Length(min = 0, max = 100, message = "国家名称长度必须介于 0 和 100 之间")
    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName;
    }

    @Length(min = 0, max = 6, message = "省、自治区、州代码长度必须介于 0 和 6 之间")
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Length(min = 0, max = 100, message = "省、自治区、州名称长度必须介于 0 和 100 之间")
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Length(min = 0, max = 6, message = "城市代码长度必须介于 0 和 6 之间")
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Length(min = 0, max = 100, message = "城市名称长度必须介于 0 和 100 之间")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Length(min = 0, max = 6, message = "县代码长度必须介于 0 和 6 之间")
    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    @Length(min = 0, max = 101, message = "县名称长度必须介于 0 和 101 之间")
    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Length(min = 0, max = 256, message = "联系地址长度必须介于 0 和 256 之间")
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    @Length(min = 0, max = 256, message = "联系人长度必须介于 0 和 256 之间")
    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    @Length(min = 0, max = 100, message = "商户类型(枚举类MerchantType ((长度必须介于 0 和 100 之间")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 0, max = 4, message = "商户等级长度必须介于 0 和 4 之间")
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Length(min = 0, max = 4, message = "手续费收取类型长度必须介于 0 和 4 之间")
    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    @Length(min = 0, max = 6, message = "商户的请求方式长度必须介于 0 和 6 之间")
    public String getRequestWays() {
        return requestWays;
    }

    public void setRequestWays(String requestWays) {
        this.requestWays = requestWays;
    }

    @Length(min = 0, max = 4, message = "商户请求的版本号长度必须介于 0 和 4 之间")
    public String getRequestVersion() {
        return requestVersion;
    }

    public void setRequestVersion(String requestVersion) {
        this.requestVersion = requestVersion;
    }

    @Length(min = 0, max = 256, message = "允许商户请求的IP地址列表通过|分隔长度必须介于 0 和 256 之间")
    public String getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(String allowedIps) {
        this.allowedIps = allowedIps;
    }

    @Length(min = 0, max = 6, message = "签约类型长度必须介于 0 和 6 之间")
    public String getSignedType() {
        return signedType;
    }

    public void setSignedType(String signedType) {
        this.signedType = signedType;
    }

    @Length(min = 0, max = 6, message = "商户状态(最终通过审核 枚举类AuthenticationStatus((长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getOpAuditTime() {
        return opAuditTime;
    }

    public void setOpAuditTime(Date opAuditTime) {
        this.opAuditTime = opAuditTime;
    }

    @Length(min = 0, max = 6, message = "运营修改人长度必须介于 0 和 6 之间")
    public String getOpAuditor() {
        return opAuditor;
    }

    public void setOpAuditor(String opAuditor) {
        this.opAuditor = opAuditor;
    }

    @Length(min = 0, max = 6, message = "风控审核状态(枚举类RouteStatus ((长度必须介于 0 和 6 之间")
    public String getRcAuditStatus() {
        return rcAuditStatus;
    }

    public void setRcAuditStatus(String rcAuditStatus) {
        this.rcAuditStatus = rcAuditStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRcAuditTime() {
        return rcAuditTime;
    }

    public void setRcAuditTime(Date rcAuditTime) {
        this.rcAuditTime = rcAuditTime;
    }

    @Length(min = 0, max = 6, message = "风控审核人长度必须介于 0 和 6 之间")
    public String getRcAuditor() {
        return rcAuditor;
    }

    public void setRcAuditor(String rcAuditor) {
        this.rcAuditor = rcAuditor;
    }

    @Length(min = 0, max = 6, message = "法务审核状态(枚举类RouteStatus ((长度必须介于 0 和 6 之间")
    public String getLegalAuditStatus() {
        return legalAuditStatus;
    }

    public void setLegalAuditStatus(String legalAuditStatus) {
        this.legalAuditStatus = legalAuditStatus;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLegalAuditTime() {
        return legalAuditTime;
    }

    public void setLegalAuditTime(Date legalAuditTime) {
        this.legalAuditTime = legalAuditTime;
    }

    @Length(min = 0, max = 6, message = "法务审核人长度必须介于 0 和 6 之间")
    public String getLegalAuditor() {
        return legalAuditor;
    }

    public void setLegalAuditor(String legalAuditor) {
        this.legalAuditor = legalAuditor;
    }

    @Length(min = 0, max = 6, message = "销售人员长度必须介于 0 和 6 之间")
    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    @Length(min = 0, max = 6, message = "当前负责人长度必须介于 0 和 6 之间")
    public String getInchargerId() {
        return inchargerId;
    }

    public void setInchargerId(String inchargerId) {
        this.inchargerId = inchargerId;
    }

    @Length(min = 0, max = 256, message = "备注长度必须介于 0 和 256 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Length(min = 0, max = 256, message = "商户欢迎信息长度必须介于 0 和 256 之间")
    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Length(min = 0, max = 6, message = "证件类型(枚举类CertificateType ((长度必须介于 0 和 6 之间")
    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBusinessLicenseEndTime() {
        return businessLicenseEndTime;
    }

    public void setBusinessLicenseEndTime(Date businessLicenseEndTime) {
        this.businessLicenseEndTime = businessLicenseEndTime;
    }

    @Length(min = 0, max = 100, message = "组织机构代码长度必须介于 0 和 100 之间")
    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    @Length(min = 0, max = 100, message = "税务登记证号码长度必须介于 0 和 100 之间")
    public String getTaxRegistrationCertificateNo() {
        return taxRegistrationCertificateNo;
    }

    public void setTaxRegistrationCertificateNo(String taxRegistrationCertificateNo) {
        this.taxRegistrationCertificateNo = taxRegistrationCertificateNo;
    }

    @Length(min = 0, max = 256, message = "经营范围长度必须介于 0 和 256 之间")
    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Length(min = 0, max = 50, message = "法人代表身份证号长度必须介于 0 和 50 之间")
    public String getLegalIdcard() {
        return legalIdcard;
    }

    public void setLegalIdcard(String legalIdcard) {
        this.legalIdcard = legalIdcard;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLegalCertificateEndTime() {
        return legalCertificateEndTime;
    }

    public void setLegalCertificateEndTime(Date legalCertificateEndTime) {
        this.legalCertificateEndTime = legalCertificateEndTime;
    }

    @Length(min = 0, max = 50, message = "联系人身份证号长度必须介于 0 和 50 之间")
    public String getContactorIdcardNo() {
        return contactorIdcardNo;
    }

    public void setContactorIdcardNo(String contactorIdcardNo) {
        this.contactorIdcardNo = contactorIdcardNo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getContactorCertificateEndTime() {
        return contactorCertificateEndTime;
    }

    public void setContactorCertificateEndTime(Date contactorCertificateEndTime) {
        this.contactorCertificateEndTime = contactorCertificateEndTime;
    }

    @Length(min = 0, max = 100, message = "联系人手机号长度必须介于 0 和 100 之间")
    public String getContactorPhone() {
        return contactorPhone;
    }

    public void setContactorPhone(String contactorPhone) {
        this.contactorPhone = contactorPhone;
    }

    @Length(min = 0, max = 100, message = "IPC备案号长度必须介于 0 和 100 之间")
    public String getIpcNo() {
        return ipcNo;
    }

    public void setIpcNo(String ipcNo) {
        this.ipcNo = ipcNo;
    }

    @Length(min = 0, max = 256, message = "开户许可证长度必须介于 0 和 256 之间")
    public String getPermitsAccounts() {
        return permitsAccounts;
    }

    public void setPermitsAccounts(String permitsAccounts) {
        this.permitsAccounts = permitsAccounts;
    }

    @Length(min = 0, max = 256, message = "法人代表证件照(正)长度必须介于 0 和 256 之间")
    public String getLegalCertificateFront() {
        return legalCertificateFront;
    }

    public void setLegalCertificateFront(String legalCertificateFront) {
        this.legalCertificateFront = legalCertificateFront;
    }

    @Length(min = 0, max = 256, message = "法人代表证件照(反)长度必须介于 0 和 256 之间")
    public String getLegalCertificateBack() {
        return legalCertificateBack;
    }

    public void setLegalCertificateBack(String legalCertificateBack) {
        this.legalCertificateBack = legalCertificateBack;
    }

    @Length(min = 0, max = 256, message = "税务登记证长度必须介于 0 和 256 之间")
    public String getTaxRegistrationCertificate() {
        return taxRegistrationCertificate;
    }

    public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
        this.taxRegistrationCertificate = taxRegistrationCertificate;
    }

    @Length(min = 0, max = 256, message = "组织机构代码证长度必须介于 0 和 256 之间")
    public String getOrganizationCodeCertificate() {
        return organizationCodeCertificate;
    }

    public void setOrganizationCodeCertificate(String organizationCodeCertificate) {
        this.organizationCodeCertificate = organizationCodeCertificate;
    }

    @Length(min = 0, max = 256, message = "营业执照文件本地存储路径及文件名长度必须介于 0 和 256 之间")
    public String getBusinessLicenseFile() {
        return businessLicenseFile;
    }

    public void setBusinessLicenseFile(String businessLicenseFile) {
        this.businessLicenseFile = businessLicenseFile;
    }

    @Length(min = 0, max = 100, message = "营业执照号码长度必须介于 0 和 100 之间")
    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    @Length(min = 0, max = 256, message = "拒绝理由长度必须介于 0 和 256 之间")
    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    public String getRetainedAmount() {
        return retainedAmount;
    }

    public void setRetainedAmount(String retainedAmount) {
        this.retainedAmount = retainedAmount;
    }

    @Length(min = 0, max = 6, message = "银联区域编码(省代码merchant_unionpay_area_base表province_id字段)长度必须介于 0 和 6 之间")
    public String getUnionPayProvinceCode() {
        return unionPayProvinceCode;
    }

    public void setUnionPayProvinceCode(String unionPayProvinceCode) {
        this.unionPayProvinceCode = unionPayProvinceCode;
    }

    @Length(min = 0, max = 100, message = "银联区域编码(省名称merchant_unionpay_area_base表province_name字段)长度必须介于 0 和 100 之间")
    public String getUnionPayProvinceName() {
        return unionPayProvinceName;
    }

    public void setUnionPayProvinceName(String unionPayProvinceName) {
        this.unionPayProvinceName = unionPayProvinceName;
    }

    @Length(min = 0, max = 6, message = "银联区域编码(市代码merchant_unionpay_area_base表city_id字段)长度必须介于 0 和 6 之间")
    public String getUnionPayCityCode() {
        return unionPayCityCode;
    }

    public void setUnionPayCityCode(String unionPayCityCode) {
        this.unionPayCityCode = unionPayCityCode;
    }

    @Length(min = 0, max = 100, message = "银联区域编码(市名称merchant_unionpay_area_base表city_name字段)长度必须介于 0 和 100 之间")
    public String getUnionPayCityName() {
        return unionPayCityName;
    }

    public void setUnionPayCityName(String unionPayCityName) {
        this.unionPayCityName = unionPayCityName;
    }

    @Length(min = 0, max = 6, message = "商户行业基本类型(merchant_industry_bass表industry_id字段)长度必须介于 0 和 6 之间")
    public String getMccType() {
        return mccType;
    }

    public void setMccType(String mccType) {
        this.mccType = mccType;
    }

    @Length(min = 0, max = 6, message = "商户行业基本类型(merchant_industry_bass表industry_child_id字段)长度必须介于 0 和 6 之间")
    public String getMccServer() {
        return mccServer;
    }

    public void setMccServer(String mccServer) {
        this.mccServer = mccServer;
    }

    @Length(min = 0, max = 6, message = "商户行业基本类型(merchant_industry_bass表mcc字段)长度必须介于 0 和 6 之间")
    public String getMccDetail() {
        return mccDetail;
    }

    public void setMccDetail(String mccDetail) {
        this.mccDetail = mccDetail;
    }

    @Length(min = 0, max = 4, message = "商户MCC码(merchant_industry_bass表mcc字段)长度必须介于 0 和 4 之间")
    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    @Length(min = 0, max = 40, message = "协议号长度必须介于 0 和 40 之间")
    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    @Length(min = 0, max = 256, message = "remark1长度必须介于 0 和 256 之间")
    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    @Length(min = 0, max = 256, message = "remark2长度必须介于 0 和 256 之间")
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    @Length(min = 0, max = 256, message = "remark3长度必须介于 0 和 256 之间")
    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    @Length(min = 0, max = 256, message = "remark4长度必须介于 0 和 256 之间")
    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    @Override
    public String toString() {
        return "HgmsMerchantInfo{" +
                "loginName='" + loginName + '\'' +
                ", companyId='" + companyId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", superiorId='" + superiorId + '\'' +
                ", superiorName='" + superiorName + '\'' +
                ", email='" + email + '\'' +
                ", companyType='" + companyType + '\'' +
                ", employeeAccount='" + employeeAccount + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", openBankCode='" + openBankCode + '\'' +
                ", openBankName='" + openBankName + '\'' +
                ", bankInfo='" + bankInfo + '\'' +
                ", bankcardNo='" + bankcardNo + '\'' +
                ", bankcardType='" + bankcardType + '\'' +
                ", bankcardExpiredDate='" + bankcardExpiredDate + '\'' +
                ", bankcardOwnerMobile='" + bankcardOwnerMobile + '\'' +
                ", bankcardOwnerName='" + bankcardOwnerName + '\'' +
                ", bankcardOwnerIdcard='" + bankcardOwnerIdcard + '\'' +
                ", bankcardOwnerType='" + bankcardOwnerType + '\'' +
                ", associateLineNumber='" + associateLineNumber + '\'' +
                ", authenticationStatus='" + authenticationStatus + '\'' +
                ", busipermit='" + busipermit + '\'' +
                ", busiaddr='" + busiaddr + '\'' +
                ", legalPersionAddress='" + legalPersionAddress + '\'' +
                ", legalPersionEmail='" + legalPersionEmail + '\'' +
                ", contactorPersionAddress='" + contactorPersionAddress + '\'' +
                ", contactorPersionEmail='" + contactorPersionEmail + '\'' +
                ", merchantIp='" + merchantIp + '\'' +
                ", contractTime=" + contractTime +
                ", inputuserId='" + inputuserId + '\'' +
                ", createdTime=" + createdTime +
                ", updateUserId='" + updateUserId + '\'' +
                ", updateTime=" + updateTime +
                ", industryTypes='" + industryTypes + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", creditLevel='" + creditLevel + '\'' +
                ", merchantPosCode='" + merchantPosCode + '\'' +
                ", legalRepresentative='" + legalRepresentative + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", legalMobile='" + legalMobile + '\'' +
                ", website='" + website + '\'' +
                ", businessAddress='" + businessAddress + '\'' +
                ", contryCode='" + contryCode + '\'' +
                ", contryName='" + contryName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyCode='" + countyCode + '\'' +
                ", countyName='" + countyName + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", contactor='" + contactor + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", chargeType='" + chargeType + '\'' +
                ", requestWays='" + requestWays + '\'' +
                ", requestVersion='" + requestVersion + '\'' +
                ", allowedIps='" + allowedIps + '\'' +
                ", signedType='" + signedType + '\'' +
                ", status='" + status + '\'' +
                ", opAuditTime=" + opAuditTime +
                ", opAuditor='" + opAuditor + '\'' +
                ", rcAuditStatus='" + rcAuditStatus + '\'' +
                ", rcAuditTime=" + rcAuditTime +
                ", rcAuditor='" + rcAuditor + '\'' +
                ", legalAuditStatus='" + legalAuditStatus + '\'' +
                ", legalAuditTime=" + legalAuditTime +
                ", legalAuditor='" + legalAuditor + '\'' +
                ", salesId='" + salesId + '\'' +
                ", inchargerId='" + inchargerId + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", beginCreateTime=" + beginCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", welcomeMessage='" + welcomeMessage + '\'' +
                ", certificateType='" + certificateType + '\'' +
                ", businessLicenseEndTime=" + businessLicenseEndTime +
                ", organizationCode='" + organizationCode + '\'' +
                ", taxRegistrationCertificateNo='" + taxRegistrationCertificateNo + '\'' +
                ", businessScope='" + businessScope + '\'' +
                ", legalIdcard='" + legalIdcard + '\'' +
                ", legalCertificateEndTime=" + legalCertificateEndTime +
                ", contactorIdcardNo='" + contactorIdcardNo + '\'' +
                ", contactorCertificateEndTime=" + contactorCertificateEndTime +
                ", contactorPhone='" + contactorPhone + '\'' +
                ", ipcNo='" + ipcNo + '\'' +
                ", permitsAccounts='" + permitsAccounts + '\'' +
                ", legalCertificateFront='" + legalCertificateFront + '\'' +
                ", legalCertificateBack='" + legalCertificateBack + '\'' +
                ", taxRegistrationCertificate='" + taxRegistrationCertificate + '\'' +
                ", organizationCodeCertificate='" + organizationCodeCertificate + '\'' +
                ", businessLicenseFile='" + businessLicenseFile + '\'' +
                ", businessLicenseNo='" + businessLicenseNo + '\'' +
                ", objection='" + objection + '\'' +
                ", retainedAmount='" + retainedAmount + '\'' +
                ", unionPayProvinceCode='" + unionPayProvinceCode + '\'' +
                ", unionPayProvinceName='" + unionPayProvinceName + '\'' +
                ", unionPayCityCode='" + unionPayCityCode + '\'' +
                ", unionPayCityName='" + unionPayCityName + '\'' +
                ", mccType='" + mccType + '\'' +
                ", mccServer='" + mccServer + '\'' +
                ", mccDetail='" + mccDetail + '\'' +
                ", industryCategory='" + industryCategory + '\'' +
                ", protocolNumber='" + protocolNumber + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", remark2='" + remark2 + '\'' +
                ", remark3='" + remark3 + '\'' +
                ", remark4='" + remark4 + '\'' +
                '}';
    }
}