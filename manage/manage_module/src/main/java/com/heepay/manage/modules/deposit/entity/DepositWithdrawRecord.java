/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.deposit.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：查询Entity
 *
 * 创 建 者： @author 王亚洪
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
public class DepositWithdrawRecord extends DataEntity<DepositWithdrawRecord> {
	
	private static final long serialVersionUID = 1L;
	private String depositWithdrawId;		// 主键id
	private String businessSeqNo;		// 银行业务流水号
	private String paymentId;		// 支付id
	private Long merchantId;		// 商户id
	private String merchantLoginName;		// 商户名称
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private String amount;		// 提现金额
	private String recAccNo;		// 银行卡号
	private String recAccName;		// 卡持有人
	private String recOpenAccDept;		// 开户行支行名称
	private String recBankNo;		// 银联行号
	private String bankId;		// 银行id
	private String accountType;		// 对公/对私(PUBLIC-对公  PRIVAT-对私  COMMON-通用)
	private String cardTypeCode;		// 银行卡类型(储蓄卡-SAVING  信用卡-CREDIT)
	private String status;		// 状态(待处理：PREDRA  成功：SUCCES  失败：FAILED)
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	
	private Date beginCreateTime;   // 开始 创建时间
  private Date endCreateTime;   // 结束 创建时间
	
  private String statisticsDate;
  
	private String sortOrder;
	
	public DepositWithdrawRecord() {
		super();
	}

	public DepositWithdrawRecord(String id){
		super(id);
	}

	@Length(min=1, max=20, message="主键id长度必须介于 1 和 20 之间")
	public String getDepositWithdrawId() {
		return depositWithdrawId;
	}

	public void setDepositWithdrawId(String depositWithdrawId) {
		this.depositWithdrawId = depositWithdrawId;
	}
	
	@Length(min=1, max=64, message="银行业务流水号长度必须介于 1 和 64 之间")
	public String getBusinessSeqNo() {
		return businessSeqNo;
	}

	public void setBusinessSeqNo(String businessSeqNo) {
		this.businessSeqNo = businessSeqNo;
	}
	
	@Length(min=1, max=26, message="支付id长度必须介于 1 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@NotNull(message="商户id不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=128, message="商户名称长度必须介于 1 和 128 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@NotNull(message="账户ID不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=1, max=64, message="账户名称长度必须介于 1 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@Length(min=1, max=64, message="银行卡号长度必须介于 1 和 64 之间")
	public String getRecAccNo() {
		return recAccNo;
	}

	public void setRecAccNo(String recAccNo) {
		this.recAccNo = recAccNo;
	}
	
	@Length(min=1, max=64, message="卡持有人长度必须介于 1 和 64 之间")
	public String getRecAccName() {
		return recAccName;
	}

	public void setRecAccName(String recAccName) {
		this.recAccName = recAccName;
	}
	
	@Length(min=1, max=128, message="开户行支行名称长度必须介于 1 和 128 之间")
	public String getRecOpenAccDept() {
		return recOpenAccDept;
	}

	public void setRecOpenAccDept(String recOpenAccDept) {
		this.recOpenAccDept = recOpenAccDept;
	}
	
	@Length(min=1, max=20, message="银联行号长度必须介于 1 和 20 之间")
	public String getRecBankNo() {
		return recBankNo;
	}

	public void setRecBankNo(String recBankNo) {
		this.recBankNo = recBankNo;
	}
	
	@Length(min=1, max=20, message="银行id长度必须介于 1 和 20 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=1, max=6, message="对公/对私(PUBLIC-对公  PRIVAT-对私  COMMON-通用)长度必须介于 1 和 6 之间")
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Length(min=1, max=6, message="银行卡类型(储蓄卡-SAVING  信用卡-CREDIT)长度必须介于 1 和 6 之间")
	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	
	@Length(min=1, max=6, message="状态(待处理：PREDRA  成功：SUCCES  失败：FAILED)长度必须介于 1 和 6 之间")
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

  public String getStatisticsDate() {
    return statisticsDate;
  }

  public void setStatisticsDate(String statisticsDate) {
    this.statisticsDate = statisticsDate;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }
	
}