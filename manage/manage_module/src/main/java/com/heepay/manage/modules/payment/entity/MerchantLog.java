/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：账户明细查询Entity
 *
 * 创 建 者： @author yq
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
public class MerchantLog extends DataEntity<MerchantLog> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账号
	private Long logId;		// 账务流水号
	private String accountName;		// 账户名称
	private Long merchantId;		// 商户id
	private String merchantName;		// 账户名称
	private String merchantType;		// 账户类型
	private String transNo;		// 汇付宝订单号
	private String paymentId;		// 银行流水号
	private String payNum;		// 交易总笔数
	private String balanceAmountChanges;		// 资金变动
	private String balanceAmount;		// 账户余额
	private String balanceFreezedAmount;		// 冻结总金额
	private String balanceAvailableAmount;		// 可用总金额
	private String balanceAvailableWithdrawAmount;		// 可提现总金额,小于等于可用总金额
	private String totalInAmount;		// 收入金额
	private String totalOutAmount;		// 支出金额
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String description;		// 流水描述
	private String remark;		// 流水备注
	private String accountDate;		// 会计日期，格式： 2016-09-05
	private String balanceDirection;		// 资金方向:C入 D出
	private String type;		// 交易类型
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String sortOrder; //排序方式

	private String accountMark;
	private String settleId;	//结算单号

	private int sourceSearch;
	private int accountExplain;



	//非表中字段
  private String accountCodes;//科目号-下拉树
  private String accountCodesHidden;//保存科目代码
  private List<Long> merchantIds;
  private List<Long> logIds;
  private String merchantSonType;//商户类型(内部,外部)


	public MerchantLog() {
		super();
	}

	public MerchantLog(String id){
		super(id);
	}

	
	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@NotNull(message="账务流水号不能为空")
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}
	
	@Length(min=0, max=256, message="账户名称长度必须介于 0 和 256 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=256, message="账户名称长度必须介于 0 和 256 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	@Length(min=0, max=6, message="账户类型长度必须介于 0 和 6 之间")
	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	
	@Length(min=1, max=26, message="汇付宝订单号长度必须介于 1 和 26 之间")
	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	
	@Length(min=0, max=26, message="银行流水号长度必须介于 0 和 26 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@Length(min=0, max=10, message="交易总笔数长度必须介于 0 和 10 之间")
	public String getPayNum() {
		return payNum;
	}

	public void setPayNum(String payNum) {
		this.payNum = payNum;
	}
	
	public String getBalanceAmountChanges() {
		return balanceAmountChanges;
	}

	public void setBalanceAmountChanges(String balanceAmountChanges) {
		this.balanceAmountChanges = balanceAmountChanges;
	}
	
	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	public String getBalanceFreezedAmount() {
		return balanceFreezedAmount;
	}

	public void setBalanceFreezedAmount(String balanceFreezedAmount) {
		this.balanceFreezedAmount = balanceFreezedAmount;
	}
	
	public String getBalanceAvailableAmount() {
		return balanceAvailableAmount;
	}

	public void setBalanceAvailableAmount(String balanceAvailableAmount) {
		this.balanceAvailableAmount = balanceAvailableAmount;
	}
	
	public String getBalanceAvailableWithdrawAmount() {
		return balanceAvailableWithdrawAmount;
	}

	public void setBalanceAvailableWithdrawAmount(String balanceAvailableWithdrawAmount) {
		this.balanceAvailableWithdrawAmount = balanceAvailableWithdrawAmount;
	}
	
	public String getTotalInAmount() {
		return totalInAmount;
	}

	public void setTotalInAmount(String totalInAmount) {
		this.totalInAmount = totalInAmount;
	}
	
	public String getTotalOutAmount() {
		return totalOutAmount;
	}

	public void setTotalOutAmount(String totalOutAmount) {
		this.totalOutAmount = totalOutAmount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=256, message="流水描述长度必须介于 0 和 256 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=256, message="流水备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=10, message="会计日期，格式： 2016-09-05长度必须介于 0 和 10 之间")
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}
	
	@Length(min=0, max=1, message="资金方向:C入 D出长度必须介于 0 和 1 之间")
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getAccountMark() {
		return accountMark;
	}

	public String getSettleId() {
		return settleId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}

	public void setAccountMark(String accountMark) {
		this.accountMark = accountMark;
	}

	public int getSourceSearch() {
		return sourceSearch;
	}

	public void setSourceSearch(int sourceSearch) {
		this.sourceSearch = sourceSearch;
	}

	public int getAccountExplain() {
		return accountExplain;
	}

	public void setAccountExplain(int accountExplain) {
		this.accountExplain = accountExplain;
	}

  public String getAccountCodes() {
    return accountCodes;
  }

  public void setAccountCodes(String accountCodes) {
    this.accountCodes = accountCodes;
  }

  public String getAccountCodesHidden() {
    return accountCodesHidden;
  }

  public void setAccountCodesHidden(String accountCodesHidden) {
    this.accountCodesHidden = accountCodesHidden;
  }

  public List<Long> getMerchantIds() {
    return merchantIds;
  }

  public void setMerchantIds(List<Long> merchantIds) {
    this.merchantIds = merchantIds;
  }

  public List<Long> getLogIds() {
    return logIds;
  }

  public void setLogIds(List<Long> logIds) {
    this.logIds = logIds;
  }

	public String getMerchantSonType() {
		return merchantSonType;
	}

	public void setMerchantSonType(String merchantSonType) {
		this.merchantSonType = merchantSonType;
	}
}