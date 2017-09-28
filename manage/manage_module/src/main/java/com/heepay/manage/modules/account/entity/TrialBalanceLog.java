/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：试算平衡Entity
 *
 * 创 建 者： @author ycl
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
public class TrialBalanceLog extends DataEntity<TrialBalanceLog> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 所属账户id
	private Long merchantId;		// 所属商户id
	private Long merchantLogIdForm;		// 对比方：资金流水id
	private String balanceAmountChangesForm;		// 对比方：资金变动
	private String balanceAmountForm;		// 对比方：账户余额
	private String balanceDirectionForm;		// 对比方：资金方向:C入 D出
	private String transNoForm;		// 对比方：交易号|支付号|结算批次号
	private String paymentIdForm;		// 对比方：支付号
	private String typeForm;		// 对比方：交易类型
	private Long merchantLogIdTo;		// 被对比方：资金流水id
	private String balanceAmountChangesTo;		// 被对比方：资金变动
	private String balanceAmountTo;		// 被对比方：账户余额
	private String balanceDirectionTo;		// 被对比方：资金方向:C入 D出
	private String transNoTo;		// 被对比方：交易号|支付号|结算批次号
	private String paymentIdTo;		// 被对比方：支付号
	private String typeTo;		// 被对比方：交易类型
	private String category;		// 错误类别
	private String status;		// 状态
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	public TrialBalanceLog() {
		super();
	}

	public TrialBalanceLog(String id){
		super(id);
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public Long getMerchantLogIdForm() {
		return merchantLogIdForm;
	}

	public void setMerchantLogIdForm(Long merchantLogIdForm) {
		this.merchantLogIdForm = merchantLogIdForm;
	}
	
	public String getBalanceAmountChangesForm() {
		return balanceAmountChangesForm;
	}

	public void setBalanceAmountChangesForm(String balanceAmountChangesForm) {
		this.balanceAmountChangesForm = balanceAmountChangesForm;
	}
	
	public String getBalanceAmountForm() {
		return balanceAmountForm;
	}

	public void setBalanceAmountForm(String balanceAmountForm) {
		this.balanceAmountForm = balanceAmountForm;
	}
	
	@Length(min=0, max=1, message="对比方：资金方向:C入 D出长度必须介于 0 和 1 之间")
	public String getBalanceDirectionForm() {
		return balanceDirectionForm;
	}

	public void setBalanceDirectionForm(String balanceDirectionForm) {
		this.balanceDirectionForm = balanceDirectionForm;
	}
	
	@Length(min=1, max=26, message="对比方：交易号|支付号|结算批次号长度必须介于 1 和 26 之间")
	public String getTransNoForm() {
		return transNoForm;
	}

	public void setTransNoForm(String transNoForm) {
		this.transNoForm = transNoForm;
	}
	
	@Length(min=0, max=26, message="对比方：支付号长度必须介于 0 和 26 之间")
	public String getPaymentIdForm() {
		return paymentIdForm;
	}

	public void setPaymentIdForm(String paymentIdForm) {
		this.paymentIdForm = paymentIdForm;
	}
	
	@Length(min=0, max=6, message="对比方：交易类型长度必须介于 0 和 6 之间")
	public String getTypeForm() {
		return typeForm;
	}

	public void setTypeForm(String typeForm) {
		this.typeForm = typeForm;
	}
	
	public Long getMerchantLogIdTo() {
		return merchantLogIdTo;
	}

	public void setMerchantLogIdTo(Long merchantLogIdTo) {
		this.merchantLogIdTo = merchantLogIdTo;
	}
	
	public String getBalanceAmountChangesTo() {
		return balanceAmountChangesTo;
	}

	public void setBalanceAmountChangesTo(String balanceAmountChangesTo) {
		this.balanceAmountChangesTo = balanceAmountChangesTo;
	}
	
	public String getBalanceAmountTo() {
		return balanceAmountTo;
	}

	public void setBalanceAmountTo(String balanceAmountTo) {
		this.balanceAmountTo = balanceAmountTo;
	}
	
	@Length(min=0, max=1, message="被对比方：资金方向:C入 D出长度必须介于 0 和 1 之间")
	public String getBalanceDirectionTo() {
		return balanceDirectionTo;
	}

	public void setBalanceDirectionTo(String balanceDirectionTo) {
		this.balanceDirectionTo = balanceDirectionTo;
	}
	
	@Length(min=1, max=26, message="被对比方：交易号|支付号|结算批次号长度必须介于 1 和 26 之间")
	public String getTransNoTo() {
		return transNoTo;
	}

	public void setTransNoTo(String transNoTo) {
		this.transNoTo = transNoTo;
	}
	
	@Length(min=0, max=26, message="被对比方：支付号长度必须介于 0 和 26 之间")
	public String getPaymentIdTo() {
		return paymentIdTo;
	}

	public void setPaymentIdTo(String paymentIdTo) {
		this.paymentIdTo = paymentIdTo;
	}
	
	@Length(min=0, max=6, message="被对比方：交易类型长度必须介于 0 和 6 之间")
	public String getTypeTo() {
		return typeTo;
	}

	public void setTypeTo(String typeTo) {
		this.typeTo = typeTo;
	}
	
	@Length(min=0, max=12, message="错误类别长度必须介于 0 和 12 之间")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
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
	
}