/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.prom.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：商户管理Entity
 *
 * 创 建 者： @author wangdong
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
public class PromMerchantManage extends DataEntity<PromMerchantManage> {
	
	private static final long serialVersionUID = 1L;
	private Long merchantId;		// 商户编码
	private String merchantName;		// 商户名称
	private String merchantType;		// 商户性质
	private String merSource;		// 注册来源
	private String payType;		// 付款方式
	private String wechatNo;		// 微信号
	private String payAccountNo;		// 支付宝账号
	private String contact;		// 联系人
	private String telPhone;		// 联系电话
	private String email;		// 联系邮箱
	private String address;		// 地址
	private String organizationCode;		// 社会统一信用代码
	private String legal;		// 法定代表人
	private String legalIdcard;		// 法人身份证号
	private String businessLicenseNo;		// 营业执照
	private String referees;		// 推荐人
	private String level;		// 商户等级
	private String status;		// 状态
	private String operator;		// 操作人
	private Date createTime;		// 操作人
	private String updateAuthor;		// 修改人
	private Date updateTime;		// 修改时间

	public PromMerchantManage() {
		super();
	}

	public PromMerchantManage(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=20, message="商户名称长度必须介于 0 和 20 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=6, message="商户性质长度必须介于 0 和 6 之间")
	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	
	@Length(min=0, max=6, message="注册来源长度必须介于 0 和 6 之间")
	public String getMerSource() {
		return merSource;
	}

	public void setMerSource(String merSource) {
		this.merSource = merSource;
	}
	
	@Length(min=0, max=6, message="付款方式长度必须介于 0 和 6 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=50, message="微信号长度必须介于 0 和 50 之间")
	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}
	
	@Length(min=0, max=50, message="支付宝账号长度必须介于 0 和 50 之间")
	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}
	
	@Length(min=0, max=20, message="联系人长度必须介于 0 和 20 之间")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Length(min=0, max=20, message="联系电话长度必须介于 0 和 20 之间")
	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
	@Length(min=0, max=50, message="联系邮箱长度必须介于 0 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=200, message="地址长度必须介于 0 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="社会统一信用代码长度必须介于 0 和 100 之间")
	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
	
	@Length(min=0, max=20, message="法定代表人长度必须介于 0 和 20 之间")
	public String getLegal() {
		return legal;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}
	
	@Length(min=0, max=20, message="法人身份证号长度必须介于 0 和 20 之间")
	public String getLegalIdcard() {
		return legalIdcard;
	}

	public void setLegalIdcard(String legalIdcard) {
		this.legalIdcard = legalIdcard;
	}
	
	@Length(min=0, max=100, message="营业执照长度必须介于 0 和 100 之间")
	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}
	
	@Length(min=0, max=20, message="推荐人长度必须介于 0 和 20 之间")
	public String getReferees() {
		return referees;
	}

	public void setReferees(String referees) {
		this.referees = referees;
	}
	
	@Length(min=0, max=1, message="商户等级长度必须介于 0 和 1 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=20, message="操作人长度必须介于 0 和 20 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=20, message="修改人长度必须介于 0 和 20 之间")
	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}