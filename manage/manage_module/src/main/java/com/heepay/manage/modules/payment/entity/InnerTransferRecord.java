/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：会聚财转账查询Entity
 *
 * 创 建 者： @author tyq
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
public class InnerTransferRecord extends DataEntity<InnerTransferRecord> {
	
	private static final long serialVersionUID = 1L;
	private String innerTransferId;		// 转账IDa
	private String merchantOrderNo;		// 商户订单号
	private Long fromAccountId;		// 转出账户
	private String fromAccountName;		// 转出账户名称
	private Long fromMerchantId;		// 转出商户号
	private String fromMerchantLoginName;		// 转出商户名
	private String fromMerchantCompany;		// 转出商户公司
	private String transferAmount;		// 转账金额
	private String feeAmount;		// 手续费金额
	private String feeType;		// 手续费类型
	private Long toAccountId;		// 转入账户
	private String toAccountName;		// 转入账户名称
	private Long toMerchantId;		// 转入商户号
	private String toMerchantLoginName;		// 转入商户
	private String toMerchantCompany;		// 入账商户公司
	private Date updateTime;		// 修改时间
	private Date createTime;		// 转账时间
	private String productCode;		// 产品编码
	private Long merchantId;		// 商户id
	private String status;		// 转账状态
	
	public InnerTransferRecord() {
		super();
	}

	public InnerTransferRecord(String id){
		super(id);
	}

	@Length(min=1, max=26, message="转账IDa长度必须介于 1 和 26 之间")
	public String getInnerTransferId() {
		return innerTransferId;
	}

	public void setInnerTransferId(String innerTransferId) {
		this.innerTransferId = innerTransferId;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@NotNull(message="转出账户不能为空")
	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}
	
	@Length(min=0, max=256, message="转出账户名称长度必须介于 0 和 256 之间")
	public String getFromAccountName() {
		return fromAccountName;
	}

	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}
	
	public Long getFromMerchantId() {
		return fromMerchantId;
	}

	public void setFromMerchantId(Long fromMerchantId) {
		this.fromMerchantId = fromMerchantId;
	}
	
	@Length(min=0, max=64, message="转出商户名长度必须介于 0 和 64 之间")
	public String getFromMerchantLoginName() {
		return fromMerchantLoginName;
	}

	public void setFromMerchantLoginName(String fromMerchantLoginName) {
		this.fromMerchantLoginName = fromMerchantLoginName;
	}
	
	@Length(min=0, max=200, message="转出商户公司长度必须介于 0 和 200 之间")
	public String getFromMerchantCompany() {
		return fromMerchantCompany;
	}

	public void setFromMerchantCompany(String fromMerchantCompany) {
		this.fromMerchantCompany = fromMerchantCompany;
	}
	
	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="手续费类型长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	@NotNull(message="转入账户不能为空")
	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}
	
	@Length(min=0, max=256, message="转入账户名称长度必须介于 0 和 256 之间")
	public String getToAccountName() {
		return toAccountName;
	}

	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}
	
	public Long getToMerchantId() {
		return toMerchantId;
	}

	public void setToMerchantId(Long toMerchantId) {
		this.toMerchantId = toMerchantId;
	}
	
	@Length(min=0, max=64, message="转入商户长度必须介于 0 和 64 之间")
	public String getToMerchantLoginName() {
		return toMerchantLoginName;
	}

	public void setToMerchantLoginName(String toMerchantLoginName) {
		this.toMerchantLoginName = toMerchantLoginName;
	}
	
	@Length(min=0, max=200, message="入账商户公司长度必须介于 0 和 200 之间")
	public String getToMerchantCompany() {
		return toMerchantCompany;
	}

	public void setToMerchantCompany(String toMerchantCompany) {
		this.toMerchantCompany = toMerchantCompany;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="修改时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="转账时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=1, max=6, message="产品编码长度必须介于 1 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=6, message="转账状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}