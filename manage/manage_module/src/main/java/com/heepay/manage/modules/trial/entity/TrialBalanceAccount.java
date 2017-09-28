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
 * 描    述：试算平衡-账户维度Entity
 *
 * 创 建 者： @author 杨春龙
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
public class TrialBalanceAccount extends DataEntity<TrialBalanceAccount> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 账户id
	private String accountName;		// 账户名称
	private Long logId;		// 流水Id(当前)
	private String balanceAmount;		// 余额(当前)
	private Long nextLogId;		// 流水Id(下一条)
	private String nextAroseAmount;		// 发生额 (下一条)
	private String nextBalanceAmount;		// 余额(下一条)
	private String nextBalanceDirection;		// 资金方向(下一条)
	private Date nextAroseTime;		// 交易发生时间(下一条)
	private String mustBalanceAmount;		// 应记录余额
	private String accountTitle;		// 账户科目名称
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String status;          //处理状态  待处理: PREHAN 已处理 FINISH
	
	public TrialBalanceAccount() {
		super();
	}

	public TrialBalanceAccount(String id){
		super(id);
	}

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
	
	@NotNull(message="流水Id(当前)不能为空")
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}
	
	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	
	@NotNull(message="流水Id(下一条)不能为空")
	public Long getNextLogId() {
		return nextLogId;
	}

	public void setNextLogId(Long nextLogId) {
		this.nextLogId = nextLogId;
	}
	
	public String getNextAroseAmount() {
		return nextAroseAmount;
	}

	public void setNextAroseAmount(String nextAroseAmount) {
		this.nextAroseAmount = nextAroseAmount;
	}
	
	public String getNextBalanceAmount() {
		return nextBalanceAmount;
	}

	public void setNextBalanceAmount(String nextBalanceAmount) {
		this.nextBalanceAmount = nextBalanceAmount;
	}
	
	@Length(min=0, max=1, message="资金方向(下一条)长度必须介于 0 和 1 之间")
	public String getNextBalanceDirection() {
		return nextBalanceDirection;
	}

	public void setNextBalanceDirection(String nextBalanceDirection) {
		this.nextBalanceDirection = nextBalanceDirection;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNextAroseTime() {
		return nextAroseTime;
	}

	public void setNextAroseTime(Date nextAroseTime) {
		this.nextAroseTime = nextAroseTime;
	}
	
	public String getMustBalanceAmount() {
		return mustBalanceAmount;
	}

	public void setMustBalanceAmount(String mustBalanceAmount) {
		this.mustBalanceAmount = mustBalanceAmount;
	}
	
	@Length(min=0, max=32, message="账户科目名称长度必须介于 0 和 32 之间")
	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}