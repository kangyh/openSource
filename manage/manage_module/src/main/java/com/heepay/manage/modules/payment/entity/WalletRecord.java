/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述： 钱包Entity
 *
 * 创 建 者： liudh
 * 创建时间：	 2017/7/6 14:00
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
public class WalletRecord extends DataEntity<WalletRecord> {
	
	private static final long serialVersionUID = 1L;
	private String walletId;		// 支付ID
	private String transNo;		// 汇付宝交易号记录各种业务类型的交易订单号
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String productCode;		// 产品代码(product表code字段)
	private String transType;		// 交易类型
	private String status;		// 支付状态
	private String description;		// 支付说明
	private Date updateTime;		// 更新时间
	private String payAmount;		// 支付金额
	private String successAmount;		// 成功金额
	private Date successTime;		// 成功时间
	private String balanceDirection;		// 资金方向:C入 D出
	private String merchantOrderNo;		// 商户订单号
	private String currency;		// 币种
	private String feeType;		// 交易手续费扣除方式
	private String feeAmount;		// 手续费
	private String operator;		// 操作人
	private String settleCyc;		// 结算方式  0,1,x
	private Date createTime;		// 创建时间
	private Long accountId;		// 账户id
	private String accountName;		// 账户名称
	private Long walletMerchantId;		// 钱包商户id
	private String merchantFeeAmount;		// 商户手续费
	
	public WalletRecord() {
		super();
	}

	public WalletRecord(String id){
		super(id);
	}

	@Length(min=1, max=26, message="支付ID长度必须介于 1 和 26 之间")
	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	
	@Length(min=0, max=26, message="汇付宝交易号记录各种业务类型的交易订单号长度必须介于 0 和 26 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户登录账号长度必须介于 0 和 64 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=0, max=64, message="商户公司长度必须介于 0 和 64 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=0, max=6, message="产品代码(product表code字段)长度必须介于 0 和 6 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=1, max=6, message="交易类型长度必须介于 1 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Length(min=0, max=6, message="支付状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1024, message="支付说明长度必须介于 0 和 1024 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Length(min=1, max=1, message="资金方向:C入 D出长度必须介于 1 和 1 之间")
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=8, message="币种长度必须介于 0 和 8 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Length(min=0, max=6, message="交易手续费扣除方式长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="操作人长度必须介于 0 和 6 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=1, message="结算方式  0,1,x长度必须介于 0 和 1 之间")
	public String getSettleCyc() {
		return settleCyc;
	}

	public void setSettleCyc(String settleCyc) {
		this.settleCyc = settleCyc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@NotNull(message="账户id不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=256, message="账户名称长度必须介于 0 和 256 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public Long getWalletMerchantId() {
		return walletMerchantId;
	}

	public void setWalletMerchantId(Long walletMerchantId) {
		this.walletMerchantId = walletMerchantId;
	}
	
	public String getMerchantFeeAmount() {
		return merchantFeeAmount;
	}

	public void setMerchantFeeAmount(String merchantFeeAmount) {
		this.merchantFeeAmount = merchantFeeAmount;
	}
	
}