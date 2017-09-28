/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 *
 * 描    述：支付机构设置Entity
 *
 * 创 建 者： @author 牛俊鹏
 * 创建时间：
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
public class CbmsPaycompany extends DataEntity<CbmsPaycompany> {
	
	private static final long serialVersionUID = 1L;
	private String companyId;		// ID
	private String companyNo;		// 申报企业的单一窗口注册编号
	private String companyName;		// 支付企业的单一窗口注册名称
	private String companyCode;		// 支付企业的海关注册登记编号
	private String companyCustomsName;		// 支付企业名称
	private String organizationCode;		// 组织机构代码
	private String inputuserName;		// 填报人
	private String phone;		// 联系方式
	private String inputuserId;		// 录入人
	private Date createdTime;		// 录入时间
	private String updateUserId;		// 修改人
	private Date updateTime;		// 修改时间
	private String creditCode;		// 统一社会信用代码
	private String legalPerson;		// 法人名称
	private String legalCardId;		// 法人身份证
	private String inspectionNumber;		// 国检备案编号
	private String officeAddress;		// 办公地址
	
	public CbmsPaycompany() {
		super();
	}

	public CbmsPaycompany(String id){
		super(id);
	}

	@Length(min=1, max=11, message="ID长度必须介于 1 和 11 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=64, message="申报企业的单一窗口注册编号长度必须介于 0 和 64 之间")
	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}
	
	@Length(min=0, max=500, message="支付企业的单一窗口注册名称长度必须介于 0 和 500 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=64, message="支付企业的海关注册登记编号长度必须介于 0 和 64 之间")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@Length(min=0, max=500, message="支付企业名称长度必须介于 0 和 500 之间")

	public String getCompanyCustomsName() {
		return companyCustomsName;
	}

	public void setCompanyCustomsName(String companyCustomsName) {
		this.companyCustomsName = companyCustomsName;
	}
	
	@Length(min=0, max=64, message="组织机构代码长度必须介于 0 和 64 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=500, message="填报人长度必须介于 0 和 500 之间")
	public String getInputuserName() {
		return inputuserName;
	}

	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
	}
	
	@Length(min=0, max=40, message="联系方式长度必须介于 0 和 40 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
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
	
	@Length(min=0, max=64, message="统一社会信用代码长度必须介于 0 和 64 之间")
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	
	@Length(min=0, max=64, message="法人名称长度必须介于 0 和 64 之间")
	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	
	@Length(min=0, max=64, message="法人身份证长度必须介于 0 和 64 之间")
	public String getLegalCardId() {
		return legalCardId;
	}

	public void setLegalCardId(String legalCardId) {
		this.legalCardId = legalCardId;
	}
	
	@Length(min=0, max=64, message="国检备案编号长度必须介于 0 和 64 之间")
	public String getInspectionNumber() {
		return inspectionNumber;
	}

	public void setInspectionNumber(String inspectionNumber) {
		this.inspectionNumber = inspectionNumber;
	}
	
	@Length(min=0, max=200, message="办公地址长度必须介于 0 和 200 之间")
	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	@Override
	public String toString() {
		return "CbmsPaycompany{" +
				"companyId='" + companyId + '\'' +
				"| companyNo='" + companyNo + '\'' +
				"| companyName='" + companyName + '\'' +
				"| companyCode='" + companyCode + '\'' +
				"| companyCustomsName='" + companyCustomsName + '\'' +
				"| organizationCode='" + organizationCode + '\'' +
				"| inputuserName='" + inputuserName + '\'' +
				"| phone='" + phone + '\'' +
				"| inputuserId='" + inputuserId + '\'' +
				"| createdTime=" + createdTime +
				"| updateUserId='" + updateUserId + '\'' +
				"| updateTime=" + updateTime +
				"| creditCode='" + creditCode + '\'' +
				"| legalPerson='" + legalPerson + '\'' +
				"| legalCardId='" + legalCardId + '\'' +
				"| inspectionNumber='" + inspectionNumber + '\'' +
				"| officeAddress='" + officeAddress + '\'' +
				'}';
	}
}