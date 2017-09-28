/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：转账理由Entity
 *
 * 创 建 者： @author zjx
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
public class TransferAccountReason extends DataEntity<TransferAccountReason> {

	private static final long serialVersionUID = 1L;
	private Long transferReasonId;		// transfer_reason_id
	private Long accountId;	//账户编码
	private String creator;		// creator
	private String accountName;	//账户名称
	private Long merchantId;		// merchant_id
	private String merchantName;	//商户名称
	private String merchantCompany;	//商户公司名称
	private String transferReason;		// 转账理由
	private String auditNeed; //是否需要审核
	private String status;	//审核状态
	private Date createTime;		// create_time
	private Date updateTime;		// 审核时间
	private String autiter; //审批人
	private String remark;		// 备注

	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	private String sortOrder; //升降排序

	public TransferAccountReason() {
		super();
	}

	public TransferAccountReason(String id){
		super(id);
	}

	@NotNull(message="transfer_reason_id不能为空")
	public Long getTransferReasonId() {
		return transferReasonId;
	}

	public void setTransferReasonId(Long transferReasonId) {
		this.transferReasonId = transferReasonId;
	}

	@Length(min=0, max=256, message="转账理由长度必须介于 0 和 256 之间")
	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	@Length(min=0, max=256, message="creator长度必须介于 0 和 256 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuditNeed() {
		return auditNeed;
	}

	public void setAuditNeed(String auditNeed) {
		this.auditNeed = auditNeed;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAutiter() {
		return autiter;
	}

	public void setAutiter(String autiter) {
		this.autiter = autiter;
	}

	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}