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
 * 描 述：试算平衡-汇总Entity
 *
 * 创 建 者： @author 杨春龙 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class TrialBalanceDay extends DataEntity<TrialBalanceDay> {

	private static final long serialVersionUID = 1L;
	private String debitCurrentAmount; // 发生额(借方)
	private String creditCurrentAmount; // 发生额(贷方)
	private String debitBeginingBalances; // 期初余额(借方)
	private String creditBeginingBalances; // 期初余额(贷方)
	private String debitEndingBalances; // 期末余额(借方)
	private String creditEndingBalances; // 期末余额(贷方)
	private String accountDate; // 会计日期
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private boolean isErrorData; // 是否为错误数据
	// 查询条件
	private String beginAccountDate;
	private String endAccountDate;

	public TrialBalanceDay() {
		super();
	}

	public TrialBalanceDay(String id) {
		super(id);
	}

	public String getDebitCurrentAmount() {
		return debitCurrentAmount;
	}

	public void setDebitCurrentAmount(String debitCurrentAmount) {
		this.debitCurrentAmount = debitCurrentAmount;
	}

	public String getCreditCurrentAmount() {
		return creditCurrentAmount;
	}

	public void setCreditCurrentAmount(String creditCurrentAmount) {
		this.creditCurrentAmount = creditCurrentAmount;
	}

	public String getDebitBeginingBalances() {
		return debitBeginingBalances;
	}

	public void setDebitBeginingBalances(String debitBeginingBalances) {
		this.debitBeginingBalances = debitBeginingBalances;
	}

	public String getCreditBeginingBalances() {
		return creditBeginingBalances;
	}

	public void setCreditBeginingBalances(String creditBeginingBalances) {
		this.creditBeginingBalances = creditBeginingBalances;
	}

	public String getDebitEndingBalances() {
		return debitEndingBalances;
	}

	public void setDebitEndingBalances(String debitEndingBalances) {
		this.debitEndingBalances = debitEndingBalances;
	}

	public String getCreditEndingBalances() {
		return creditEndingBalances;
	}

	public void setCreditEndingBalances(String creditEndingBalances) {
		this.creditEndingBalances = creditEndingBalances;
	}

	@Length(min = 0, max = 10, message = "会计日期长度必须介于 0 和 10 之间")
	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsErrorData() {
		return isErrorData;
	}

	public void setIsErrorData(Boolean isErrorData) {
		this.isErrorData = isErrorData;
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