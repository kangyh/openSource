/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *
 * 描    述：账户转账Entity
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
public class MerchantTransfer extends DataEntity<MerchantTransfer> {
	
	private static final long serialVersionUID = 1L;
	private String transferId;		// 账户转账流水号
	private String withdrawFlag;		// 是否更新可提现
	private String transferAmount;		// 转账金额
	private String status;		// 状态(待审核:AUDING,已撤销:REVOKE,审核通过:ADOPT)
	private String creator;		// 创建人
	private String auditor;		// 审核人
	private String filePath;		// 附件地址-有可能是多个文件
	private String transferReason;		// 调账原因
	private String auditRemark;		// 审核备注
	private Date createTime;		// 创建时间
	private Date auditTime;		// 审核时间
	private String dMerchantId;		// d商户编码
	private String dAccountId;		// d账户ID
	private String dAccountName;		// d商户名称
	private String cMerchantId;		// d商户编码
	private String cAccountId;		// d账户ID
	private String cAccountName;		// d商户名称
	private String opResource;			//来源
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间


	public MerchantTransfer() {
		super();
	}

	public MerchantTransfer(String id){
		super(id);
	}

	@Length(min=1, max=26, message="账户转账流水号长度必须介于 1 和 26 之间")
	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	
	@Length(min=0, max=1, message="是否更新可提现长度必须介于 0 和 1 之间")
	public String getWithdrawFlag() {
		return withdrawFlag;
	}

	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}
	
	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	@Length(min=1, max=6, message="状态(待审核:AUDING,已撤销:REVOKE,审核通过:ADOPT)长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="创建人长度必须介于 0 和 50 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Length(min=0, max=50, message="审核人长度必须介于 0 和 50 之间")
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@Length(min=0, max=1000, message="附件地址-有可能是多个文件长度必须介于 0 和 1000 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=255, message="调账原因长度必须介于 0 和 255 之间")
	public String getTransferReason() {
		return transferReason;
	}

	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	@Length(min=0, max=255, message="审核备注长度必须介于 0 和 255 之间")
	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
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

	public String getdMerchantId() {
		return dMerchantId;
	}

	public void setdMerchantId(String dMerchantId) {
		this.dMerchantId = dMerchantId;
	}

	public String getdAccountId() {
		return dAccountId;
	}

	public void setdAccountId(String dAccountId) {
		this.dAccountId = dAccountId;
	}

	public String getdAccountName() {
		return dAccountName;
	}

	public void setdAccountName(String dAccountName) {
		this.dAccountName = dAccountName;
	}

	public String getcMerchantId() {
		return cMerchantId;
	}

	public void setcMerchantId(String cMerchantId) {
		this.cMerchantId = cMerchantId;
	}

	public String getcAccountId() {
		return cAccountId;
	}

	public void setcAccountId(String cAccountId) {
		this.cAccountId = cAccountId;
	}

	public String getcAccountName() {
		return cAccountName;
	}

	public void setcAccountName(String cAccountName) {
		this.cAccountName = cAccountName;
	}

	public String getOpResource() {
		return opResource;
	}

	public void setOpResource(String opResource) {
		this.opResource = opResource;
	}
}