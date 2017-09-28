/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：账户管理Entity
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
public class MerchantAccount extends DataEntity<MerchantAccount> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账户id
	private String accountName;		// 账户名称
	private Long merchantId;		// 商户id
	private String merchantLoginName;		// 商户名称
	private String type;		// 0：保证金账户1：人民币余额账户2：手续费账户
	private String balanceAmount;		// 账户总金额
	private String balanceFreezedAmount;		// 冻结总金额
	private String balanceAvailableAmount;		// 可用总金额
	private String balanceAvailableWithdrawAmount;		// 可提现总金额,小于等于可用总金额
	private String totalInAmount;		// 总收入
	private String totalOutAmount;		// 总支出
	private String retainedAmount;  //留存金额
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String status;		// 状态 0=正常，1=冻结，2=停用，3=删除
	private String description;		// 描述
	private String remark;		// 备注
	private String accountTitle;		// 账户科目名称
	private String balanceDirection;		// 余额方向  C：借 D：贷
	private String accountCode;	//账户code
	private String currency;		// 币种
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String isHot;
	private String sortOrder;
	private String version;
	//非表中字段
	private String accountCodes;//科目号-下拉树
	private String accountCodesHidden;//保存科目代码
	private List<String> accCodes;
	private String internalAccountTypeId;  //内部账户类型
	private String internalAccountDetailsTypeId; //银行代码
	private String accountSeno;  //账户序列号
	private List<Long> merchantIds;

	
	
	public MerchantAccount() {
		super();
	}

	public MerchantAccount(String id){
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
	
	@Length(min=1, max=256, message="账户名称长度必须介于 1 和 256 之间")
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
	
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=1, max=6, message="0：保证金账户1：人民币余额账户2：手续费账户长度必须介于 1 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	@Length(min=1, max=6, message="状态 0=正常，1=冻结，2=停用，3=删除长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=256, message="备注长度必须介于 0 和 256 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=32, message="账户科目名称长度必须介于 0 和 32 之间")
	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	
	@Length(min=0, max=1, message="余额方向  C：借 D：贷长度必须介于 0 和 1 之间")
	@NotNull(message="借贷方向不能为空")
	public String getBalanceDirection() {
		return balanceDirection;
	}

	public void setBalanceDirection(String balanceDirection) {
		this.balanceDirection = balanceDirection;
	}
	
	@Length(min=0, max=3, message="币种长度必须介于 0 和 3 之间")
	@NotNull(message="币种不能为空")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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

  public List<String> getAccCodes() {
    return accCodes;
  }

  public void setAccCodes(List<String> accCodes) {
    this.accCodes = accCodes;
  }

  public String getIsHot() {
    return isHot;
  }

  public void setIsHot(String isHot) {
    this.isHot = isHot;
  }

  public String getRetainedAmount() {
    return retainedAmount;
  }

  public void setRetainedAmount(String retainedAmount) {
    this.retainedAmount = retainedAmount;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getInternalAccountTypeId() {
    return internalAccountTypeId;
  }

  public void setInternalAccountTypeId(String internalAccountTypeId) {
    this.internalAccountTypeId = internalAccountTypeId;
  }

  public String getInternalAccountDetailsTypeId() {
    return internalAccountDetailsTypeId;
  }

  public void setInternalAccountDetailsTypeId(String internalAccountDetailsTypeId) {
    this.internalAccountDetailsTypeId = internalAccountDetailsTypeId;
  }

  public String getAccountSeno() {
    return accountSeno;
  }

  public void setAccountSeno(String accountSeno) {
    this.accountSeno = accountSeno;
  }

	public List<Long> getMerchantIds() {
		return merchantIds;
	}

	public void setMerchantIds(List<Long> merchantIds) {
		this.merchantIds = merchantIds;
	}
}