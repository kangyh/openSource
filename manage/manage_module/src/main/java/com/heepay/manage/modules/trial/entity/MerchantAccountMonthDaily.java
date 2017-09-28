/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：商户出入金月汇总Entity
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
public class MerchantAccountMonthDaily extends DataEntity<MerchantAccountMonthDaily> {
	
	private static final long serialVersionUID = 1L;
	private Long accountId;		// 商户账号ID
	private Long merchantId;		// 商户ID
	private String outMoney;		// 出金
	private String inMoney;		// 入金
	private String beginningBalances;		// 期初余额
	private String endingBalances;		// 期末余额
	private String accountTitle;		// 账户科目名称：资产类：期末余额=期初余额+本期借方发生额-本期贷方发生额,负债类：期末余额=期初余额+本期贷方发生额-本期借方发生额，权益类：期末余额=期初余额+本期贷方发生额-本期借方发生额
	private String accountMonth;		// 会计日期(月)，格式： 2016-09
	private Date accountDate;		// 最后汇总的会计日期，格式： 2016-09-05
	private Date createTime;		// 创建时间
	private Date updateTime;		// 修改时间
	private String merchantCompany;		//商户名称
	
	public MerchantAccountMonthDaily() {
		super();
	}

	public MerchantAccountMonthDaily(String id){
		super(id);
	}

	@NotNull(message="商户账号ID不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@NotNull(message="商户ID不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(String outMoney) {
		this.outMoney = outMoney;
	}
	
	public String getInMoney() {
		return inMoney;
	}

	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}
	
	public String getBeginningBalances() {
		return beginningBalances;
	}

	public void setBeginningBalances(String beginningBalances) {
		this.beginningBalances = beginningBalances;
	}
	
	public String getEndingBalances() {
		return endingBalances;
	}

	public void setEndingBalances(String endingBalances) {
		this.endingBalances = endingBalances;
	}
	
	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	
	public String getAccountMonth() {
		return accountMonth;
	}

	public void setAccountMonth(String accountMonth) {
		this.accountMonth = accountMonth;
	}
	

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
}