/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import com.heepay.common.util.StringUtil;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：总的商户增删改Entity（整合merchant类和CbmsSuppliersetting类）
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月6日 10:38:25
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
public class CbmsMerchantSuppliersetting extends DataEntity<CbmsMerchantSuppliersetting> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// merchant_id
	private String merchantNo;		// 商户编码
	private String email;		// 联系邮箱
	private String contryName;		// 国家名称
	private String type;		// 商户类型(枚举类MerchantType ((
	private String certificateType;		// 证件类型(枚举类CertificateType ((
	private String companyName;		// 公司名称
	private String provinceCode;		// 省、自治区、州代码
	private String provinceName;		// 省、自治区、州名称
	private String cityCode;		// 城市编码
	private String cityName;		// 城市名称
	private String countyCode;		// 县代码
	private String countyName;		// 县名称
	private String businessAddress;		// 公司注册地址
	private String website;		// 公司网址
	private String businessLicenseNo;		// 营业执照号码
	private Date businessLicenseEndTime;		// 营业执照结束时间
	private String organizationCode;		// 组织机构代码
	private String taxRegistrationCertificateNo;		// 税务登记证号码
	private String businessScope;		// 经营范围
	private String llegalperson;		// 法人名称
	private String legalIdcard;		// 法人代表身份证号
	private Date legalCertificateEndTime;		// 法人代表证件有效期结束
	private String contactor;		// 联系人
	private String contactorIdcardNo;		// 联系人身份证号
	private Date contactorCertificateEndTime;		// 联系人证件有效期结束
	private String contactorPhone;		// 联系人手机号
	private String orgCustomsCode;		// 电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。（跟汇元合作的电商平台）
	private String supplierName;		// 商户名称
	private String enterpriseName;		// 电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）
	private String electricityUrl;		// 电商/店铺网址
	private String orgCustomsName;		// 平台商户名称（海关报备）
	private String centralOfficeNumber;		// 电商平台企业的代码\r\n            电商平台编号（总局备案号）[国检]\r\n            （跟汇元合作的电商平台）
	private String cbmsEnterpriseTypeName;		// 商户企业类别名称
	private String cbmsSupplierCategoryName;		// 商户企业类型名称
	private String cbmsTradeTypeName;		// 商户贸易类型名称
	private String individualUser;		// 个人用户
	private String electricEnterprise;		// 电子商务企业
	private String electricBusinessEnterprise;		// 电子商务交易平台企业
	private String logisticsEnterprises;		// 物流企业
	private String electricBusinessPlatform;		// 电子商务通关服务平台
	private String generalTrade;		// 一般贸易区
	private String freetradeareaNoregulation;		// 自由贸易试验区（非特殊监管）
	private String freetradeareaRegulation;		// 自由贸易试验区（特殊监管）
	private String bondedArea;		// 保税区
	private String exportProcessingZone;		// 出口加工区
	private String boundedWarehouseA;		// 保税物流中心A型
	private String boundedWarehouseB;		// 保税物流中心B型
	private String bondedLogisticsPark;		// 保税物流园区
	private String diamondBourse;		// 钻石交易所
	private String bondedPortArea;		// 保税港区
	private String comprehensiveBondedZone;		// 综合保税区
	private String crossborderIndustrialPark;		// 跨境工业园区
	private String exportsupervisedWarehouse;		// 出口监管仓库
	private String importsupervisedWarehouse;		// 进口保税仓库
	private String merchandise;		// 货物
	private String studyAbroad;		// 留学
	private String hotel;		// 酒店
	private String ticket;		// 机票
	private String internationalExhibition;		// 国际展览
	private String communicationService;		// 通信服务
	private String contryCode;		// 国家代码
	private String merchantPosCode;		// 中国银联商户号
	private String companyPhone;		// 公司联系电话
	private String legalMobile;		// 法人手机号码
	private String chargeType;		// 手续费收取类型
	private String ipcNo;		// IPC备案号
	private String permitsAccounts;		// 开户许可证
	private String legalCertificateFront;		// 法人代表证件照(正)
	private String legalCertificateBack;		// 法人代表证件照(反)
	private String taxRegistrationCertificate;		// 税务登记证
	private String organizationCodeCertificate;		// 组织机构代码证
	private String businessLicenseFile;		// 营业执照文件本地存储路径及文件名
	private String trademarkRegisPhotoAddress;		// 商标注册证照片地址
	private String commodityQualityPhotoAddress;		// 商品质检报告照片地址
	private String loginName;							//登录名
	private List<String> cbmsTradeTypeNameList;		//商户贸易类型名称
    private String electricityName;		//电商/店铺名称
    private String timeInterval;		//时间区间(用于查询)

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getElectricityName() {
        return electricityName;
    }

    public void setElectricityName(String electricityName) {
        this.electricityName = electricityName;
    }

    public List<String> getCbmsTradeTypeNameList() {
		return cbmsTradeTypeNameList;
	}

	public void setCbmsTradeTypeNameList(List<String> cbmsTradeTypeNameList) {
		this.cbmsTradeTypeNameList = cbmsTradeTypeNameList;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public CbmsMerchantSuppliersetting() {
		super();
	}

	public CbmsMerchantSuppliersetting(String id){
		super(id);
	}

	@Length(min=1, max=11, message="merchant_id长度必须介于 1 和 11 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Length(min=1, max=11, message="merchant_no长度必须介于 1 和 11 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	@Length(min=0, max=50, message="联系邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=100, message="国家名称长度必须介于 0 和 100 之间")
	public String getContryName() {
		return contryName;
	}

	public void setContryName(String contryName) {
		this.contryName = contryName;
	}
	
	@Length(min=0, max=100, message="商户类型(枚举类MerchantType ((长度必须介于 0 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=6, message="证件类型(枚举类CertificateType ((长度必须介于 0 和 6 之间")
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	
	@Length(min=0, max=100, message="公司名称长度必须介于 0 和 100 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=6, message="省、自治区、州代码长度必须介于 0 和 6 之间")
	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	@Length(min=0, max=100, message="省、自治区、州名称长度必须介于 0 和 100 之间")
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	@Length(min=0, max=6, message="城市编码长度必须介于 0 和 6 之间")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Length(min=0, max=100, message="城市名称长度必须介于 0 和 100 之间")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Length(min=0, max=6, message="县代码长度必须介于 0 和 6 之间")
	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	@Length(min=0, max=101, message="县名称长度必须介于 0 和 101 之间")
	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	
	@Length(min=0, max=256, message="公司注册地址长度必须介于 0 和 256 之间")
	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	
	@Length(min=0, max=256, message="公司网址长度必须介于 0 和 256 之间")
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Length(min=0, max=100, message="营业执照号码长度必须介于 0 和 100 之间")
	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBusinessLicenseEndTime() {
		return businessLicenseEndTime;
	}

	public void setBusinessLicenseEndTime(Date businessLicenseEndTime) {
		this.businessLicenseEndTime = businessLicenseEndTime;
	}
	
	@Length(min=0, max=100, message="组织机构代码长度必须介于 0 和 100 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=100, message="税务登记证号码长度必须介于 0 和 100 之间")
	public String getTaxRegistrationCertificateNo() {
		return taxRegistrationCertificateNo;
	}

	public void setTaxRegistrationCertificateNo(String taxRegistrationCertificateNo) {
		this.taxRegistrationCertificateNo = taxRegistrationCertificateNo;
	}
	
	@Length(min=0, max=256, message="经营范围长度必须介于 0 和 256 之间")
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	
	@Length(min=0, max=200, message="法人名称长度必须介于 0 和 200 之间")
	public String getLlegalperson() {
		return llegalperson;
	}

	public void setLlegalperson(String llegalperson) {
		this.llegalperson = llegalperson;
	}
	
	@Length(min=0, max=50, message="法人代表身份证号长度必须介于 0 和 50 之间")
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
	
	@Length(min=0, max=256, message="联系人长度必须介于 0 和 256 之间")
	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}
	
	@Length(min=0, max=50, message="联系人身份证号长度必须介于 0 和 50 之间")
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
	
	@Length(min=0, max=100, message="联系人手机号长度必须介于 0 和 100 之间")
	public String getContactorPhone() {
		return contactorPhone;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}
	
	@Length(min=0, max=64, message="电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。（跟汇元合作的电商平台）长度必须介于 0 和 64 之间")
	public String getOrgCustomsCode() {
		return orgCustomsCode;
	}

	public void setOrgCustomsCode(String orgCustomsCode) {
		this.orgCustomsCode = orgCustomsCode;
	}
	
	@Length(min=0, max=200, message="商户名称长度必须介于 0 和 200 之间")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@Length(min=0, max=500, message="电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）长度必须介于 0 和 500 之间")
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	@Length(min=0, max=256, message="电商/店铺网址长度必须介于 0 和 256 之间")
	public String getElectricityUrl() {
		return electricityUrl;
	}

	public void setElectricityUrl(String electricityUrl) {
		this.electricityUrl = electricityUrl;
	}
	
	@Length(min=0, max=500, message="平台商户名称（海关报备）长度必须介于 0 和 500 之间")
	public String getOrgCustomsName() {
		return orgCustomsName;
	}

	public void setOrgCustomsName(String orgCustomsName) {
		this.orgCustomsName = orgCustomsName;
	}
	
	@Length(min=0, max=64, message="电商平台企业的代码\r\n            电商平台编号（总局备案号）[国检]\r\n            （跟汇元合作的电商平台）长度必须介于 0 和 64 之间")
	public String getCentralOfficeNumber() {
		return centralOfficeNumber;
	}

	public void setCentralOfficeNumber(String centralOfficeNumber) {
		this.centralOfficeNumber = centralOfficeNumber;
	}
	
	@Length(min=0, max=1024, message="商户企业类别名称长度必须介于 0 和 1024 之间")
	public String getCbmsEnterpriseTypeName() {
		return cbmsEnterpriseTypeName;
	}

	public void setCbmsEnterpriseTypeName(String cbmsEnterpriseTypeName) {
		this.cbmsEnterpriseTypeName = cbmsEnterpriseTypeName;
	}
	
	@Length(min=0, max=1024, message="商户企业类型名称长度必须介于 0 和 1024 之间")
	public String getCbmsSupplierCategoryName() {
		return cbmsSupplierCategoryName;
	}

	public void setCbmsSupplierCategoryName(String cbmsSupplierCategoryName) {
		this.cbmsSupplierCategoryName = cbmsSupplierCategoryName;
	}
	
	@Length(min=0, max=1024, message="商户贸易类型名称长度必须介于 0 和 1024 之间")
	public String getCbmsTradeTypeName() {
		return cbmsTradeTypeName;
	}

	public void setCbmsTradeTypeName(String cbmsTradeTypeName) {
		this.cbmsTradeTypeName = cbmsTradeTypeName;
	}
	
	@Length(min=0, max=64, message="个人用户长度必须介于 0 和 64 之间")
	public String getIndividualUser() {
		return individualUser;
	}

	public void setIndividualUser(String individualUser) {
		this.individualUser = individualUser;
	}
	
	@Length(min=0, max=64, message="电子商务企业长度必须介于 0 和 64 之间")
	public String getElectricEnterprise() {
		return electricEnterprise;
	}

	public void setElectricEnterprise(String electricEnterprise) {
		this.electricEnterprise = electricEnterprise;
	}
	
	@Length(min=0, max=64, message="电子商务交易平台企业长度必须介于 0 和 64 之间")
	public String getElectricBusinessEnterprise() {
		return electricBusinessEnterprise;
	}

	public void setElectricBusinessEnterprise(String electricBusinessEnterprise) {
		this.electricBusinessEnterprise = electricBusinessEnterprise;
	}
	
	@Length(min=0, max=64, message="物流企业长度必须介于 0 和 64 之间")
	public String getLogisticsEnterprises() {
		return logisticsEnterprises;
	}

	public void setLogisticsEnterprises(String logisticsEnterprises) {
		this.logisticsEnterprises = logisticsEnterprises;
	}
	
	@Length(min=0, max=64, message="电子商务通关服务平台长度必须介于 0 和 64 之间")
	public String getElectricBusinessPlatform() {
		return electricBusinessPlatform;
	}

	public void setElectricBusinessPlatform(String electricBusinessPlatform) {
		this.electricBusinessPlatform = electricBusinessPlatform;
	}
	
	@Length(min=0, max=64, message="一般贸易区长度必须介于 0 和 64 之间")
	public String getGeneralTrade() {
		return generalTrade;
	}

	public void setGeneralTrade(String generalTrade) {
		this.generalTrade = generalTrade;
	}
	
	@Length(min=0, max=64, message="自由贸易试验区（非特殊监管）长度必须介于 0 和 64 之间")
	public String getFreetradeareaNoregulation() {
		return freetradeareaNoregulation;
	}

	public void setFreetradeareaNoregulation(String freetradeareaNoregulation) {
		this.freetradeareaNoregulation = freetradeareaNoregulation;
	}
	
	@Length(min=0, max=64, message="自由贸易试验区（特殊监管）长度必须介于 0 和 64 之间")
	public String getFreetradeareaRegulation() {
		return freetradeareaRegulation;
	}

	public void setFreetradeareaRegulation(String freetradeareaRegulation) {
		this.freetradeareaRegulation = freetradeareaRegulation;
	}
	
	@Length(min=0, max=64, message="保税区长度必须介于 0 和 64 之间")
	public String getBondedArea() {
		return bondedArea;
	}

	public void setBondedArea(String bondedArea) {
		this.bondedArea = bondedArea;
	}
	
	@Length(min=0, max=64, message="出口加工区长度必须介于 0 和 64 之间")
	public String getExportProcessingZone() {
		return exportProcessingZone;
	}

	public void setExportProcessingZone(String exportProcessingZone) {
		this.exportProcessingZone = exportProcessingZone;
	}
	
	@Length(min=0, max=64, message="保税物流中心A型长度必须介于 0 和 64 之间")
	public String getBoundedWarehouseA() {
		return boundedWarehouseA;
	}

	public void setBoundedWarehouseA(String boundedWarehouseA) {
		this.boundedWarehouseA = boundedWarehouseA;
	}
	
	@Length(min=0, max=64, message="保税物流中心B型长度必须介于 0 和 64 之间")
	public String getBoundedWarehouseB() {
		return boundedWarehouseB;
	}

	public void setBoundedWarehouseB(String boundedWarehouseB) {
		this.boundedWarehouseB = boundedWarehouseB;
	}
	
	@Length(min=0, max=64, message="保税物流园区长度必须介于 0 和 64 之间")
	public String getBondedLogisticsPark() {
		return bondedLogisticsPark;
	}

	public void setBondedLogisticsPark(String bondedLogisticsPark) {
		this.bondedLogisticsPark = bondedLogisticsPark;
	}
	
	@Length(min=0, max=64, message="钻石交易所长度必须介于 0 和 64 之间")
	public String getDiamondBourse() {
		return diamondBourse;
	}

	public void setDiamondBourse(String diamondBourse) {
		this.diamondBourse = diamondBourse;
	}
	
	@Length(min=0, max=64, message="保税港区长度必须介于 0 和 64 之间")
	public String getBondedPortArea() {
		return bondedPortArea;
	}

	public void setBondedPortArea(String bondedPortArea) {
		this.bondedPortArea = bondedPortArea;
	}
	
	@Length(min=0, max=64, message="综合保税区长度必须介于 0 和 64 之间")
	public String getComprehensiveBondedZone() {
		return comprehensiveBondedZone;
	}

	public void setComprehensiveBondedZone(String comprehensiveBondedZone) {
		this.comprehensiveBondedZone = comprehensiveBondedZone;
	}
	
	@Length(min=0, max=64, message="跨境工业园区长度必须介于 0 和 64 之间")
	public String getCrossborderIndustrialPark() {
		return crossborderIndustrialPark;
	}

	public void setCrossborderIndustrialPark(String crossborderIndustrialPark) {
		this.crossborderIndustrialPark = crossborderIndustrialPark;
	}
	
	@Length(min=0, max=64, message="出口监管仓库长度必须介于 0 和 64 之间")
	public String getExportsupervisedWarehouse() {
		return exportsupervisedWarehouse;
	}

	public void setExportsupervisedWarehouse(String exportsupervisedWarehouse) {
		this.exportsupervisedWarehouse = exportsupervisedWarehouse;
	}
	
	@Length(min=0, max=64, message="进口保税仓库长度必须介于 0 和 64 之间")
	public String getImportsupervisedWarehouse() {
		return importsupervisedWarehouse;
	}

	public void setImportsupervisedWarehouse(String importsupervisedWarehouse) {
		this.importsupervisedWarehouse = importsupervisedWarehouse;
	}
	
	@Length(min=0, max=64, message="货物长度必须介于 0 和 64 之间")
	public String getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(String merchandise) {
		this.merchandise = merchandise;
	}
	
	@Length(min=0, max=64, message="留学长度必须介于 0 和 64 之间")
	public String getStudyAbroad() {
		return studyAbroad;
	}

	public void setStudyAbroad(String studyAbroad) {
		this.studyAbroad = studyAbroad;
	}
	
	@Length(min=0, max=64, message="酒店长度必须介于 0 和 64 之间")
	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	
	@Length(min=0, max=64, message="机票长度必须介于 0 和 64 之间")
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	@Length(min=0, max=64, message="国际展览长度必须介于 0 和 64 之间")
	public String getInternationalExhibition() {
		return internationalExhibition;
	}

	public void setInternationalExhibition(String internationalExhibition) {
		this.internationalExhibition = internationalExhibition;
	}
	
	@Length(min=0, max=64, message="通信服务长度必须介于 0 和 64 之间")
	public String getCommunicationService() {
		return communicationService;
	}

	public void setCommunicationService(String communicationService) {
		this.communicationService = communicationService;
	}
	
	@Length(min=0, max=8, message="国家代码长度必须介于 0 和 8 之间")
	public String getContryCode() {
		return contryCode;
	}

	public void setContryCode(String contryCode) {
		this.contryCode = contryCode;
	}
	
	@Length(min=0, max=15, message="中国银联商户号长度必须介于 0 和 15 之间")
	public String getMerchantPosCode() {
		return merchantPosCode;
	}

	public void setMerchantPosCode(String merchantPosCode) {
		this.merchantPosCode = merchantPosCode;
	}
	
	@Length(min=0, max=50, message="公司联系电话长度必须介于 0 和 50 之间")
	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	
	@Length(min=0, max=50, message="法人手机号码长度必须介于 0 和 50 之间")
	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}
	
	@Length(min=0, max=4, message="手续费收取类型长度必须介于 0 和 4 之间")
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	@Length(min=0, max=100, message="IPC备案号长度必须介于 0 和 100 之间")
	public String getIpcNo() {
		return ipcNo;
	}

	public void setIpcNo(String ipcNo) {
		this.ipcNo = ipcNo;
	}
	
	@Length(min=0, max=256, message="开户许可证长度必须介于 0 和 256 之间")
	public String getPermitsAccounts() {
		return permitsAccounts;
	}

	public void setPermitsAccounts(String permitsAccounts) {
		this.permitsAccounts = permitsAccounts;
	}
	
	@Length(min=0, max=256, message="法人代表证件照(正)长度必须介于 0 和 256 之间")
	public String getLegalCertificateFront() {
		return legalCertificateFront;
	}

	public void setLegalCertificateFront(String legalCertificateFront) {
		this.legalCertificateFront = legalCertificateFront;
	}
	
	@Length(min=0, max=256, message="法人代表证件照(反)长度必须介于 0 和 256 之间")
	public String getLegalCertificateBack() {
		return legalCertificateBack;
	}

	public void setLegalCertificateBack(String legalCertificateBack) {
		this.legalCertificateBack = legalCertificateBack;
	}
	
	@Length(min=0, max=256, message="税务登记证长度必须介于 0 和 256 之间")
	public String getTaxRegistrationCertificate() {
		return taxRegistrationCertificate;
	}

	public void setTaxRegistrationCertificate(String taxRegistrationCertificate) {
		this.taxRegistrationCertificate = taxRegistrationCertificate;
	}
	
	@Length(min=0, max=256, message="组织机构代码证长度必须介于 0 和 256 之间")
	public String getOrganizationCodeCertificate() {
		return organizationCodeCertificate;
	}

	public void setOrganizationCodeCertificate(String organizationCodeCertificate) {
		this.organizationCodeCertificate = organizationCodeCertificate;
	}
	
	@Length(min=0, max=256, message="营业执照文件本地存储路径及文件名长度必须介于 0 和 256 之间")
	public String getBusinessLicenseFile() {
		return businessLicenseFile;
	}

	public void setBusinessLicenseFile(String businessLicenseFile) {
		this.businessLicenseFile = businessLicenseFile;
	}
	
	@Length(min=0, max=300, message="商标注册证照片地址长度必须介于 0 和 300 之间")
	public String getTrademarkRegisPhotoAddress() {
		return trademarkRegisPhotoAddress;
	}

	public void setTrademarkRegisPhotoAddress(String trademarkRegisPhotoAddress) {
		this.trademarkRegisPhotoAddress = trademarkRegisPhotoAddress;
	}
	
	@Length(min=0, max=300, message="商品质检报告照片地址长度必须介于 0 和 300 之间")
	public String getCommodityQualityPhotoAddress() {
		return commodityQualityPhotoAddress;
	}

	public void setCommodityQualityPhotoAddress(String commodityQualityPhotoAddress) {
		this.commodityQualityPhotoAddress = commodityQualityPhotoAddress;
	}

	@Override
	public String toString() {
		return "CbmsMerchantSuppliersetting{" +
				"merchantId='" + merchantId + '\'' +
				"| merchantNo='" + merchantNo + '\'' +
				"| email='" + email + '\'' +
				"| contryName='" + contryName + '\'' +
				"| type='" + type + '\'' +
				"| certificateType='" + certificateType + '\'' +
				"| companyName='" + companyName + '\'' +
				"| provinceCode='" + provinceCode + '\'' +
				"| provinceName='" + provinceName + '\'' +
				"| cityCode='" + cityCode + '\'' +
				"| cityName='" + cityName + '\'' +
				"| countyCode='" + countyCode + '\'' +
				"| countyName='" + countyName + '\'' +
				"| businessAddress='" + businessAddress + '\'' +
				"| website='" + website + '\'' +
				"| businessLicenseNo='" + businessLicenseNo + '\'' +
				"| businessLicenseEndTime=" + businessLicenseEndTime +
				"| organizationCode='" + organizationCode + '\'' +
				"| taxRegistrationCertificateNo='" + taxRegistrationCertificateNo + '\'' +
				"| businessScope='" + businessScope + '\'' +
				"| llegalperson='" + llegalperson + '\'' +
				"| legalIdcard='" + StringUtil.getShortCardNo(legalIdcard) + '\'' +
				"| legalCertificateEndTime=" + legalCertificateEndTime +
				"| contactor='" + contactor + '\'' +
				"| contactorIdcardNo='" + StringUtil.getShortCardNo(contactorIdcardNo) + '\'' +
				"| contactorCertificateEndTime=" + contactorCertificateEndTime +
				"| contactorPhone='" + contactorPhone + '\'' +
				"| orgCustomsCode='" + orgCustomsCode + '\'' +
				"| supplierName='" + supplierName + '\'' +
				"| enterpriseName='" + enterpriseName + '\'' +
				"| electricityUrl='" + electricityUrl + '\'' +
				"| orgCustomsName='" + orgCustomsName + '\'' +
				"| centralOfficeNumber='" + centralOfficeNumber + '\'' +
				"| cbmsEnterpriseTypeName='" + cbmsEnterpriseTypeName + '\'' +
				"| cbmsSupplierCategoryName='" + cbmsSupplierCategoryName + '\'' +
				"| cbmsTradeTypeName='" + cbmsTradeTypeName + '\'' +
				"| individualUser='" + individualUser + '\'' +
				"| electricEnterprise='" + electricEnterprise + '\'' +
				"| electricBusinessEnterprise='" + electricBusinessEnterprise + '\'' +
				"| logisticsEnterprises='" + logisticsEnterprises + '\'' +
				"| electricBusinessPlatform='" + electricBusinessPlatform + '\'' +
				"| generalTrade='" + generalTrade + '\'' +
				"| freetradeareaNoregulation='" + freetradeareaNoregulation + '\'' +
				"| freetradeareaRegulation='" + freetradeareaRegulation + '\'' +
				"| bondedArea='" + bondedArea + '\'' +
				"| exportProcessingZone='" + exportProcessingZone + '\'' +
				"| boundedWarehouseA='" + boundedWarehouseA + '\'' +
				"| boundedWarehouseB='" + boundedWarehouseB + '\'' +
				"| bondedLogisticsPark='" + bondedLogisticsPark + '\'' +
				"| diamondBourse='" + diamondBourse + '\'' +
				"| bondedPortArea='" + bondedPortArea + '\'' +
				"| comprehensiveBondedZone='" + comprehensiveBondedZone + '\'' +
				"| crossborderIndustrialPark='" + crossborderIndustrialPark + '\'' +
				"| exportsupervisedWarehouse='" + exportsupervisedWarehouse + '\'' +
				"| importsupervisedWarehouse='" + importsupervisedWarehouse + '\'' +
				"| merchandise='" + merchandise + '\'' +
				"| studyAbroad='" + studyAbroad + '\'' +
				"| hotel='" + hotel + '\'' +
				"| ticket='" + ticket + '\'' +
				"| internationalExhibition='" + internationalExhibition + '\'' +
				"| communicationService='" + communicationService + '\'' +
				"| contryCode='" + contryCode + '\'' +
				"| merchantPosCode='" + merchantPosCode + '\'' +
				"| companyPhone='" + companyPhone + '\'' +
				"| legalMobile='" + legalMobile + '\'' +
				"| chargeType='" + chargeType + '\'' +
				"| ipcNo='" + ipcNo + '\'' +
				"| permitsAccounts='" + permitsAccounts + '\'' +
				"| legalCertificateFront='" + legalCertificateFront + '\'' +
				"| legalCertificateBack='" + legalCertificateBack + '\'' +
				"| taxRegistrationCertificate='" + taxRegistrationCertificate + '\'' +
				"| organizationCodeCertificate='" + organizationCodeCertificate + '\'' +
				"| businessLicenseFile='" + businessLicenseFile + '\'' +
				"| trademarkRegisPhotoAddress='" + trademarkRegisPhotoAddress + '\'' +
				"| commodityQualityPhotoAddress='" + commodityQualityPhotoAddress + '\'' +
				"| loginName='" + loginName + '\'' +
				"| cbmsTradeTypeNameList=" + cbmsTradeTypeNameList +
				"| electricityName='" + electricityName + '\'' +
				"| timeInterval='" + timeInterval + '\'' +
				'}';
	}
}