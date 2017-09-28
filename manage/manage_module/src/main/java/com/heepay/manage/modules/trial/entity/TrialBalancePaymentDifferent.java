/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：交易和账务数据日终校验Entity
 *
 * 创 建 者： @author yangcl
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
public class TrialBalancePaymentDifferent extends DataEntity<TrialBalancePaymentDifferent> {
	
	private static final long serialVersionUID = 1L;
	private String transType;		// 交易类型
	private String paymentAmount;		// 交易总金额
	private String creditBalanceAmount;		// 贷方金额
	private String debitBalanceAmount;		// 借方金额
	private String creditBalanceFeeAmount;		// 贷方手续费
	private String debitBalanceFeeAmount;		// 借方手续费
	private String accountDate;		// 会计日期
	private String status;		// 处理状态
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	private String beginAccountDate;
	private String endAccountDate;
	
	public TrialBalancePaymentDifferent() {
		super();
	}

	public TrialBalancePaymentDifferent(String id){
		super(id);
	}

	@Length(min=1, max=6, message="交易类型长度必须介于 1 和 6 之间")
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public String getCreditBalanceAmount() {
		return creditBalanceAmount;
	}

	public void setCreditBalanceAmount(String creditBalanceAmount) {
		this.creditBalanceAmount = creditBalanceAmount;
	}
	
	public String getDebitBalanceAmount() {
		return debitBalanceAmount;
	}

	public void setDebitBalanceAmount(String debitBalanceAmount) {
		this.debitBalanceAmount = debitBalanceAmount;
	}
	
	public String getCreditBalanceFeeAmount() {
		return creditBalanceFeeAmount;
	}

	public void setCreditBalanceFeeAmount(String creditBalanceFeeAmount) {
		this.creditBalanceFeeAmount = creditBalanceFeeAmount;
	}
	
	public String getDebitBalanceFeeAmount() {
		return debitBalanceFeeAmount;
	}

	public void setDebitBalanceFeeAmount(String debitBalanceFeeAmount) {
		this.debitBalanceFeeAmount = debitBalanceFeeAmount;
	}
	
	@Length(min=0, max=10, message="会计日期长度必须介于 0 和 10 之间")
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=6, message="处理状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBeginAccountDate() {
		return beginAccountDate;
	}

	public void setBeginAccountDate(String beginAccountDate) {
		this.beginAccountDate = beginAccountDate;
	}

	public String getEndAccountDate() {
		return endAccountDate;
	}

	public void setEndAccountDate(String endAccountDate) {
		this.endAccountDate = endAccountDate;
	}
	
}