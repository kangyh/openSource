/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：跨境商户信息显示Entity
 *
 * 创 建 者： @author guozx
 * 创建时间：	 2017年1月9日 09:51:25
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
public class CbmsSuppliersetting extends DataEntity<CbmsSuppliersetting> {
	
	private static final long serialVersionUID = 1L;
	private String merchantId;		// merchant_id
	private String merchantNo;		// 商户编号MERCHANT_NO
	private String supplierName;		// 商户名称
	private String orgCustomsCode;		// 电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。（跟汇元合作的电商平台）
	private String orgCustomsName;		// 平台商户名称（海关报备）
	private String centralOfficeNumber;		// 电商平台企业的代码            电商平台编号（总局备案号）[国检]            （跟汇元合作的电商平台）
	private String enterpriseName;		// 电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）
	private String busipermit;		// 经验许可证
	private String llegalperson;		// 法人名称
	private String provinceNo;		// 省编码
	private String cityCode;		// 城市编码
	private String busiaddr;		// 营业地址
	private String llegalPersonFrontPhotoAddress;		// 法人身份证正面照片地址
	private String llegalPersonBankPhotoAddress;		// 法人身份证反面照片地址
	private String busLicensePhotoAddress;		// 商户营业执照照片地址
	private String orgCodePhotoAddress;		// 组织机构代码证照片地址
	private String taxRegisCertPhotoAddress;		// 税务登记证(国税、地税)照片地址
	private String trademarkRegisPhotoAddress;		// 商标注册证照片地址
	private String commodityQualityPhotoAddress;		// 商品质检报告照片地址
	private String contactName;		// 联系人姓名
	private String phone;		// 联系电话
	private String contactFixPhone;		// 联系固定电话
	private String email;		// 联系邮箱
	private String countryId;		// 商户国家ID
	private String merchantKey;		// 商户密钥
	private String merchantIp;		// merchant_ip
	private Date contractTime;		// 商户签约时间
	private Long inputuserId;		// 录入人
	private Date createdTime;		// 录入时间
	private Long updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private String status;		// 状态（2-锁定1-有效0-无效）
	private String electricityUrl;		// 电商/店铺网址
	private String cbmsEnterpriseTypeName;		// 商户企业类别名称
	private String cbmsSupplierCategoryName;		// 商户企业类型名称
	private String cbmsTradeTypeName;		// 商户贸易类型名称
	private String businessScope;		// 经营范围
	private String electricityName;		//电商/店铺名称
	private String inchargerId;		//当前负责人id
	private String companyName;		//商户公司名称
	private String automaticAudit;		//自动审核按钮
	private String timeInterval;		//api接口查询区间

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getAutomaticAudit() {
		return automaticAudit;
	}

	public void setAutomaticAudit(String automaticAudit) {
		this.automaticAudit = automaticAudit;
	}

	public String getInchargerId() {
		return inchargerId;
	}

	public void setInchargerId(String inchargerId) {
		this.inchargerId = inchargerId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getElectricityName() {
		return electricityName;
	}

	public void setElectricityName(String electricityName) {
		this.electricityName = electricityName;
	}

	public CbmsSuppliersetting() {
		super();
	}

	public CbmsSuppliersetting(String id){
		super(id);
	}

	@Length(min=1, max=11, message="merchant_id长度必须介于 1 和 11 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户编号MERCHANT_NO长度必须介于 0 和 64 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=0, max=200, message="商户名称长度必须介于 0 和 200 之间")
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	@Length(min=0, max=64, message="电商平台的海关注册登记编号；电商平台未在海关注册登记，由电商企业发送订单的，以中国电子口岸发布的电商平台标识编号为准。（跟汇元合作的电商平台）长度必须介于 0 和 64 之间")
	public String getOrgCustomsCode() {
		return orgCustomsCode;
	}

	public void setOrgCustomsCode(String orgCustomsCode) {
		this.orgCustomsCode = orgCustomsCode;
	}
	
	@Length(min=0, max=500, message="平台商户名称（海关报备）长度必须介于 0 和 500 之间")
	public String getOrgCustomsName() {
		return orgCustomsName;
	}

	public void setOrgCustomsName(String orgCustomsName) {
		this.orgCustomsName = orgCustomsName;
	}
	
	@Length(min=0, max=64, message="电商平台企业的代码            电商平台编号（总局备案号）[国检]（跟汇元合作的电商平台）长度必须介于 0 和 64 之间")
	public String getCentralOfficeNumber() {
		return centralOfficeNumber;
	}

	public void setCentralOfficeNumber(String centralOfficeNumber) {
		this.centralOfficeNumber = centralOfficeNumber;
	}
	
	@Length(min=0, max=500, message="电商平台名称（总局备案名称）[国检]（跟汇元合作的电商平台）长度必须介于 0 和 500 之间")
	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	@Length(min=0, max=20, message="经验许可证长度必须介于 0 和 20 之间")
	public String getBusipermit() {
		return busipermit;
	}

	public void setBusipermit(String busipermit) {
		this.busipermit = busipermit;
	}
	
	@Length(min=0, max=200, message="法人名称长度必须介于 0 和 200 之间")
	public String getLlegalperson() {
		return llegalperson;
	}

	public void setLlegalperson(String llegalperson) {
		this.llegalperson = llegalperson;
	}
	
	@Length(min=0, max=5, message="省编码长度必须介于 0 和 5 之间")
	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	
	@Length(min=0, max=5, message="城市编码长度必须介于 0 和 5 之间")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	@Length(min=0, max=500, message="营业地址长度必须介于 0 和 500 之间")
	public String getBusiaddr() {
		return busiaddr;
	}

	public void setBusiaddr(String busiaddr) {
		this.busiaddr = busiaddr;
	}
	
	@Length(min=0, max=300, message="法人身份证正面照片地址长度必须介于 0 和 300 之间")
	public String getLlegalPersonFrontPhotoAddress() {
		return llegalPersonFrontPhotoAddress;
	}

	public void setLlegalPersonFrontPhotoAddress(String llegalPersonFrontPhotoAddress) {
		this.llegalPersonFrontPhotoAddress = llegalPersonFrontPhotoAddress;
	}
	
	@Length(min=0, max=300, message="法人身份证反面照片地址长度必须介于 0 和 300 之间")
	public String getLlegalPersonBankPhotoAddress() {
		return llegalPersonBankPhotoAddress;
	}

	public void setLlegalPersonBankPhotoAddress(String llegalPersonBankPhotoAddress) {
		this.llegalPersonBankPhotoAddress = llegalPersonBankPhotoAddress;
	}
	
	@Length(min=0, max=300, message="商户营业执照照片地址长度必须介于 0 和 300 之间")
	public String getBusLicensePhotoAddress() {
		return busLicensePhotoAddress;
	}

	public void setBusLicensePhotoAddress(String busLicensePhotoAddress) {
		this.busLicensePhotoAddress = busLicensePhotoAddress;
	}
	
	@Length(min=0, max=300, message="组织机构代码证照片地址长度必须介于 0 和 300 之间")
	public String getOrgCodePhotoAddress() {
		return orgCodePhotoAddress;
	}

	public void setOrgCodePhotoAddress(String orgCodePhotoAddress) {
		this.orgCodePhotoAddress = orgCodePhotoAddress;
	}
	
	@Length(min=0, max=300, message="税务登记证(国税、地税)照片地址长度必须介于 0 和 300 之间")
	public String getTaxRegisCertPhotoAddress() {
		return taxRegisCertPhotoAddress;
	}

	public void setTaxRegisCertPhotoAddress(String taxRegisCertPhotoAddress) {
		this.taxRegisCertPhotoAddress = taxRegisCertPhotoAddress;
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
	
	@Length(min=0, max=100, message="联系人姓名长度必须介于 0 和 100 之间")
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	@Length(min=0, max=20, message="联系电话长度必须介于 0 和 20 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=20, message="联系固定电话长度必须介于 0 和 20 之间")
	public String getContactFixPhone() {
		return contactFixPhone;
	}

	public void setContactFixPhone(String contactFixPhone) {
		this.contactFixPhone = contactFixPhone;
	}
	
	@Length(min=0, max=50, message="联系邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=11, message="商户国家ID长度必须介于 0 和 11 之间")
	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	@Length(min=0, max=64, message="商户密钥长度必须介于 0 和 64 之间")
	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}
	
	@Length(min=0, max=64, message="merchant_ip长度必须介于 0 和 64 之间")
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
	
	public Long getInputuserId() {
		return inputuserId;
	}

	public void setInputuserId(Long inputuserId) {
		this.inputuserId = inputuserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=4, message="状态（2-锁定1-有效0-无效）长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=500, message="电商/店铺网址长度必须介于 0 和 500 之间")
	public String getElectricityUrl() {
		return electricityUrl;
	}

	public void setElectricityUrl(String electricityUrl) {
		this.electricityUrl = electricityUrl;
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
	
	@Length(min=0, max=1024, message="经营范围长度必须介于 0 和 1024 之间")
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

    @Override
    public String toString() {
        return "CbmsSuppliersetting{" +
                "merchantId='" + merchantId + '\'' +
                "| merchantNo='" + merchantNo + '\'' +
                "| supplierName='" + supplierName + '\'' +
                "| orgCustomsCode='" + orgCustomsCode + '\'' +
                "| orgCustomsName='" + orgCustomsName + '\'' +
                "| centralOfficeNumber='" + centralOfficeNumber + '\'' +
                "| enterpriseName='" + enterpriseName + '\'' +
                "| busipermit='" + busipermit + '\'' +
                "| llegalperson='" + llegalperson + '\'' +
                "| provinceNo='" + provinceNo + '\'' +
                "| cityCode='" + cityCode + '\'' +
                "| busiaddr='" + busiaddr + '\'' +
                "| llegalPersonFrontPhotoAddress='" + llegalPersonFrontPhotoAddress + '\'' +
                "| llegalPersonBankPhotoAddress='" + llegalPersonBankPhotoAddress + '\'' +
                "| busLicensePhotoAddress='" + busLicensePhotoAddress + '\'' +
                "| orgCodePhotoAddress='" + orgCodePhotoAddress + '\'' +
                "| taxRegisCertPhotoAddress='" + taxRegisCertPhotoAddress + '\'' +
                "| trademarkRegisPhotoAddress='" + trademarkRegisPhotoAddress + '\'' +
                "| commodityQualityPhotoAddress='" + commodityQualityPhotoAddress + '\'' +
                "| contactName='" + contactName + '\'' +
                "| phone='" + phone + '\'' +
                "| contactFixPhone='" + contactFixPhone + '\'' +
                "| email='" + email + '\'' +
                "| countryId='" + countryId + '\'' +
                "| merchantIp='" + merchantIp + '\'' +
                "| contractTime=" + contractTime +
                "| inputuserId=" + inputuserId +
                "| createdTime=" + createdTime +
                "| updateUserId=" + updateUserId +
                "| updateTime=" + updateTime +
                "| status='" + status + '\'' +
                "| electricityUrl='" + electricityUrl + '\'' +
                "| cbmsEnterpriseTypeName='" + cbmsEnterpriseTypeName + '\'' +
                "| cbmsSupplierCategoryName='" + cbmsSupplierCategoryName + '\'' +
                "| cbmsTradeTypeName='" + cbmsTradeTypeName + '\'' +
                "| businessScope='" + businessScope + '\'' +
                "| electricityName='" + electricityName + '\'' +
                "| inchargerId='" + inchargerId + '\'' +
                "| companyName='" + companyName + '\'' +
                "| automaticAudit='" + automaticAudit + '\'' +
                "| timeInterval='" + timeInterval + '\'' +
                '}';
    }
}