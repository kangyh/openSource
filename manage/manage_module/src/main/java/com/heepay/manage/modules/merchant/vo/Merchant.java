/**
 *
 */
package com.heepay.manage.modules.merchant.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * merchantEntity
 *
 * @author ly
 * @version V1.0
 */
public class Merchant extends DataEntity<Merchant> {

    private static final long serialVersionUID = 1L;
    private Integer userId;   // 商户ID
    private String email;   // 商户邮箱
    private String companyName;   // 公司名称
    private String merchantPosCode; //中国银联商户号
    private String legalRepresentative;   // 法人代表
    private String companyPhone;    // 公司联系电话
    private String legalMobile;   // 法人手机号码
    private String website;   // 公司网址
    private String businessAddress;   // 公司注册地址
    private String contryCode;    // 国家代码
    private String contryName;    // 国家名称
    private String provinceCode;    // 省、自治区、州代码
    private String provinceName;    // 省、自治区、州名称
    private String cityCode;    // 城市代码
    private String cityName;    // 城市名称
    private String countyCode;    // 县代码
    private String countyName;    // 县名称
    private String contactAddress;    // 联系地址
    private String contactor;   // 联系人
    private String type;    // 商户类型
    private String level;   // 商户等级
    private String chargeType;    // 手续费收取类型
    private String requestWays;   // 商户的请求方式
    private String requestVersion;    // 商户请求的版本号
    private String allowedIps;    // 允许商户请求的IP地址列表通过|分隔
    private String signedType;    // 签约类型
    private String status;    // 商户状态
    private Date opAuditTime;   // 运营修改时间
    private String opAuditor;   // 运营修改人
    private String rcAuditStatus;   // 风控审核状态
    private Date rcAuditTime;   // 风控审核时间
    private String rcAuditor;   // 风控审核人
    private String legalAuditStatus;    // 法务审核状态
    private Date legalAuditTime;    // 法务审核时间
    private String legalAuditor;    // 风控审核人
    private String salesId;   // 销售人员
    private String inchargerId;   // 当前负责人
    private String remark;    // 备注
    private Date createTime;    // 创建时间
    private String welcomeMessage;    // 商户欢迎信息
    private String certificateType;   // 证件类型
    private Date businessLicenseEndTime;    // 营业执照结束时间
    private String organizationCode;    // 组织机构代码
    private String taxRegistrationCertificateNo;    // 税务登记证号码
    private String businessScope;   // 经营范围
    private String legalIdcard;   // 法人代表身份证号
    private Date legalCertificateEndTime;   // 法人代表证件有效期结束
    private String contactorIdcardNo;   // 联系人身份证号
    private Date contactorCertificateEndTime;   // 联系人证件有效期结束
    private String contactorPhone;    // 联系人手机号
    private String ipcNo;   // IPC备案号
    private String permitsAccounts;   // 开户许可证
    private String legalCertificateFront;   // 法人代表证件照(正)
    private String contactorCertificateFront;    // 法人代表证件照(反)
    private String contactorCertificateBack;    // 联系人证件照(正)
    private String legalCertificateBack;    // 联系人证件照(反)
    private String taxRegistrationCertificate;    // 税务登记证
    private String organizationCodeCertificate;   // 组织机构代码证
    private String businessLicenseFile;   // 营业执照文件本地存储路径及文件名
    private String businessLicenseNo;   // 营业执照号码
    private String objection;//拒绝理由
    private BigDecimal retainedAmount;//留存金额
    private String unionPayProvinceCode;//银联区域编码(省代码)
    private String unionPayProvinceName;//银联区域编码(省名称)
    private String unionPayCityCode;//银联区域编码(市代码)
    private String unionPayCityName;//银联区域编码(市名称)
    private String mccType;//商户MCC码类别(1)
    private String mccServer;//商户MCC码服务(2)
    private String mccDetail;//商户MCC码详情(3)
    private String industryCategory;//平台会员行业分类
    private Date beginCreateTime;   // 开始 创建时间
    private Date endCreateTime;   // 结束 创建时间
    private String loginName; // 账号(登录名)
    private String userStatus; //账号状态(启用，禁用)
    private String source; // 来源
    private String allowSystem; // 允许登陆的系统
    private String protocolNumber; // 协议号
    private String superiorId; //上级商户ID
    private String shortName; //公司简称
    private String merchantFlag; //商户标识
    private String authorizationLetter; //委托授权书FastDFS中地址
    private String authenticationStatus;//认证状态
    private String authenticationReason;//认证失败原因
    private String phoneAuthStatus;//手机认证状态
    private String phoneAuthReason;//手机认证失败原因

    private List<Long> merchantIds;

    public Merchant() {
        super();
    }

    public Merchant(String id) {
        super(id);
    }

    @NotNull(message = "商户ID不能为空")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Length(min = 0, max = 100, message = "商户邮箱长度必须介于 0 和 100 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Length(min = 0, max = 100, message = "公司名称长度必须介于 0 和 100 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Length(min = 0, max = 15, message = "中国银联商户号长度必须介于 0 和 15 之间")
    public String getMerchantPosCode() {
        return merchantPosCode;
    }

    public void setMerchantPosCode(String merchantPosCode) {
        this.merchantPosCode = merchantPosCode;
    }

    @Length(min = 0, max = 255, message = "法人代表长度必须介于 0 和 255 之间")
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

    @Length(min = 0, max = 255, message = "公司网址长度必须介于 0 和 255 之间")
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Length(min = 0, max = 255, message = "公司注册地址长度必须介于 0 和 255 之间")
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

    @Length(min = 0, max = 255, message = "联系地址长度必须介于 0 和 255 之间")
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    @Length(min = 0, max = 255, message = "联系人长度必须介于 0 和 255 之间")
    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
    }

    @Length(min = 0, max = 4, message = "商户类型长度必须介于 0 和 4 之间")
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

    @Length(min = 0, max = 255, message = "允许商户请求的IP地址列表通过|分隔长度必须介于 0 和 255 之间")
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

    @Length(min = 0, max = 6, message = "商户状态长度必须介于 0 和 6 之间")
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

    @Length(min = 0, max = 6, message = "风控审核状态长度必须介于 0 和 6 之间")
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

    @Length(min = 0, max = 6, message = "法务审核状态长度必须介于 0 和 6 之间")
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

    @Length(min = 0, max = 6, message = "风控审核人长度必须介于 0 和 6 之间")
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

    @Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
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

    @Length(min = 0, max = 255, message = "商户欢迎信息长度必须介于 0 和 255 之间")
    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    @Length(min = 0, max = 6, message = "证件类型长度必须介于 0 和 6 之间")
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

    @Length(min = 0, max = 255, message = "经营范围长度必须介于 0 和 255 之间")
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

    @Length(min = 0, max = 255, message = "开户许可证长度必须介于 0 和 255 之间")
    public String getPermitsAccounts() {
        return permitsAccounts;
    }

    public void setPermitsAccounts(String permitsAccounts) {
        this.permitsAccounts = permitsAccounts;
    }

    @Length(min = 0, max = 255, message = "法人代表证件照(正)长度必须介于 0 和 255 之间")
    public String getLegalCertificateFront() {
        return legalCertificateFront;
    }

    public void setLegalCertificateFront(String legalCertificateFront) {
        this.legalCertificateFront = legalCertificateFront;
    }

    @Length(min = 0, max = 255, message = "法人代表证件照(反)长度必须介于 0 和 255 之间")
    public String getLegalCertificateBack() {
        return legalCertificateBack;
    }

    public void setLegalCertificateBack(String legalCertificateBack) {
        this.legalCertificateBack = legalCertificateBack;
    }

    @Length(min = 0, max = 255, message = "税务登记证长度必须介于 0 和 255 之间")
    public String getTaxRegistrationCertificate() {
        return taxRegistrationCertificate;
    }

    public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
        this.taxRegistrationCertificate = taxRegistrationCertificate;
    }

    @Length(min = 0, max = 255, message = "组织机构代码证长度必须介于 0 和 255 之间")
    public String getOrganizationCodeCertificate() {
        return organizationCodeCertificate;
    }

    public void setOrganizationCodeCertificate(String organizationCodeCertificate) {
        this.organizationCodeCertificate = organizationCodeCertificate;
    }

    @Length(min = 0, max = 255, message = "营业执照文件本地存储路径及文件名长度必须介于 0 和 255 之间")
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

    @Length(min = 0, max = 100, message = "拒绝理由长度必须介于 0 和 255 之间")
    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    public BigDecimal getRetainedAmount() {
        return retainedAmount;
    }

    public void setRetainedAmount(BigDecimal retainedAmount) {
        this.retainedAmount = retainedAmount;
    }

    @Length(min = 0, max = 6, message = "银联区域编码(省代码)长度必须介于 0 和 6 之间")
    public String getUnionPayProvinceCode() {
        return unionPayProvinceCode;
    }

    public void setUnionPayProvinceCode(String unionPayProvinceCode) {
        this.unionPayProvinceCode = unionPayProvinceCode;
    }

    @Length(min = 0, max = 100, message = "银联区域编码(省名称)长度必须介于 0 和 100 之间")
    public String getUnionPayProvinceName() {
        return unionPayProvinceName;
    }

    public void setUnionPayProvinceName(String unionPayProvinceName) {
        this.unionPayProvinceName = unionPayProvinceName;
    }

    @Length(min = 0, max = 6, message = "银联区域编码(市代码)长度必须介于 0 和 6 之间")
    public String getUnionPayCityCode() {
        return unionPayCityCode;
    }

    public void setUnionPayCityCode(String unionPayCityCode) {
        this.unionPayCityCode = unionPayCityCode;
    }

    @Length(min = 0, max = 100, message = "银联区域编码(市名称)长度必须介于 0 和 100 之间")
    public String getUnionPayCityName() {
        return unionPayCityName;
    }

    public void setUnionPayCityName(String unionPayCityName) {
        this.unionPayCityName = unionPayCityName;
    }

    @Length(min = 0, max = 6, message = "商户MCC码类别(1)长度必须介于 0 和 6 之间")
    public String getMccType() {
        return mccType;
    }

    public void setMccType(String mccType) {
        this.mccType = mccType;
    }

    @Length(min = 0, max = 6, message = "商户MCC码服务(2)长度必须介于 0 和 6 之间")
    public String getMccServer() {
        return mccServer;
    }

    public void setMccServer(String mccServer) {
        this.mccServer = mccServer;
    }

    @Length(min = 0, max = 6, message = "商户MCC码详情(3)长度必须介于 0 和 6 之间")
    public String getMccDetail() {
        return mccDetail;
    }

    public void setMccDetail(String mccDetail) {
        this.mccDetail = mccDetail;
    }

    @Length(min = 0, max = 6, message = "平台会员行业分类长度必须介于 0 和 6 之间")
    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAllowSystem() {
        return allowSystem;
    }

    public void setAllowSystem(String allowSystem) {
        this.allowSystem = allowSystem;
    }

    @Length(min = 0, max = 40, message = "协议号长度必须介于 0 和 40 之间")
    public String getProtocolNumber() {
        return protocolNumber;
    }

    public void setProtocolNumber(String protocolNumber) {
        this.protocolNumber = protocolNumber;
    }

    @Length(min=0, max=100, message="上级商户ID长度必须介于 0 和 100 之间")
    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    public String getContactorCertificateFront() {
        return contactorCertificateFront;
    }

    public void setContactorCertificateFront(String contactorCertificateFront) {
        this.contactorCertificateFront = contactorCertificateFront;
    }

    public String getContactorCertificateBack() {
        return contactorCertificateBack;
    }

    public void setContactorCertificateBack(String contactorCertificateBack) {
        this.contactorCertificateBack = contactorCertificateBack;
    }


    @Length(max=16, message="商户简称长度必须介于 0 和 16 之间")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMerchantFlag() {
        return merchantFlag;
    }

    public void setMerchantFlag(String merchantFlag) {
        this.merchantFlag = merchantFlag;
    }

    public String getAuthorizationLetter() {
        return authorizationLetter;
    }

    public void setAuthorizationLetter(String authorizationLetter) {
        this.authorizationLetter = authorizationLetter;
    }

    public String getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(String authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getAuthenticationReason() {
        return authenticationReason;
    }

    public void setAuthenticationReason(String authenticationReason) {
        this.authenticationReason = authenticationReason;
    }

    public String getPhoneAuthStatus() {
        return phoneAuthStatus;
    }

    public void setPhoneAuthStatus(String phoneAuthStatus) {
        this.phoneAuthStatus = phoneAuthStatus;
    }

    public String getPhoneAuthReason() {
        return phoneAuthReason;
    }

    public void setPhoneAuthReason(String phoneAuthReason) {
        this.phoneAuthReason = phoneAuthReason;
    }

    public List<Long> getMerchantIds() {
        return merchantIds;
    }

    public void setMerchantIds(List<Long> merchantIds) {
        this.merchantIds = merchantIds;
    }
}