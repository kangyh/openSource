/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描 述：试算平衡-账户日汇总Entity
 *
 * 创 建 者： @author 杨春龙 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class MerchantAccountDaily extends DataEntity<MerchantAccountDaily> {

	private static final long serialVersionUID = 1L;
	private Long accountId; // 商户账号ID
	private Long merchantId; // 商户ID
	private String merchantCompany;//商户名称
	private String debitBeginningBalances; // 期初余额(借方)
	private String debitEndingBalances; // 期末余额(借方)
	private String debitCurrentAmount; // 发生额(借方)
	private String creditBeginningBalances; // 期初余额(贷方)
	private String creditEndingBalances; // 期末余额(贷方)
	private String creditCurrentAmount; // 发生额(贷方)
	private String accountTitle; // 账户科目
	private String accountDate; // 会计日期
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间
	private Boolean isErrorData; // 是否为错误数据
	private String subjectType;// 账户类型
	private String beginAccountDate; // 会计日期
	private String endAccountDate; // 会计日期
	
	
	private String daijiesuanAccountId;//待结算账户
	private String daijiesuanBeginningBalances;//待结算期初
	private String daijiesuanEndingBalances;//待结算期末
	private String daijiesuanDebitCurrentAmount;//待结算 贷方发生额
	private String daijiesuanCreditCurrentAmount;//待结算 借方发生额

	private String hmDaijiesuanAccountId;//手动待结算账户
	private String hmDaijiesuanBeginningBalances;//手动待结算期初
	private String hmDaijiesuanEndingBalances;//手动待结算期末
	private String hmDaijiesuanDebitCurrentAmount;//手动待结算 贷方发生额
	private String hmDaijiesuanCreditCurrentAmount;//手动待结算 借方发生额
	
	
	public MerchantAccountDaily() {
		super();
	}

	public MerchantAccountDaily(String id) {
		super(id);
	}

	@NotNull(message = "商户账号ID不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@NotNull(message = "商户ID不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getDebitBeginningBalances() {
		return debitBeginningBalances;
	}

	public void setDebitBeginningBalances(String debitBeginningBalances) {
		this.debitBeginningBalances = debitBeginningBalances;
	}

	public String getDebitEndingBalances() {
		return debitEndingBalances;
	}

	public void setDebitEndingBalances(String debitEndingBalances) {
		this.debitEndingBalances = debitEndingBalances;
	}

	public String getDebitCurrentAmount() {
		return debitCurrentAmount;
	}

	public void setDebitCurrentAmount(String debitCurrentAmount) {
		this.debitCurrentAmount = debitCurrentAmount;
	}

	public String getCreditBeginningBalances() {
		return creditBeginningBalances;
	}

	public void setCreditBeginningBalances(String creditBeginningBalances) {
		this.creditBeginningBalances = creditBeginningBalances;
	}

	public String getCreditEndingBalances() {
		return creditEndingBalances;
	}

	public void setCreditEndingBalances(String creditEndingBalances) {
		this.creditEndingBalances = creditEndingBalances;
	}

	public String getCreditCurrentAmount() {
		return creditCurrentAmount;
	}

	public void setCreditCurrentAmount(String creditCurrentAmount) {
		this.creditCurrentAmount = creditCurrentAmount;
	}

	@Length(min = 0, max = 32, message = "账户科目长度必须介于 0 和 32 之间")
	public String getAccountTitle() {
		return accountTitle;
	}

	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}

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
	@NotNull(message = "修改时间不能为空")
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

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
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

	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}

	public String getDaijiesuanAccountId() {
		return daijiesuanAccountId;
	}

	public void setDaijiesuanAccountId(String daijiesuanAccountId) {
		this.daijiesuanAccountId = daijiesuanAccountId;
	}

	public String getDaijiesuanBeginningBalances() {
		return daijiesuanBeginningBalances;
	}

	public void setDaijiesuanBeginningBalances(String daijiesuanBeginningBalances) {
		this.daijiesuanBeginningBalances = daijiesuanBeginningBalances;
	}

	public String getDaijiesuanEndingBalances() {
		return daijiesuanEndingBalances;
	}

	public void setDaijiesuanEndingBalances(String daijiesuanEndingBalances) {
		this.daijiesuanEndingBalances = daijiesuanEndingBalances;
	}

	public String getDaijiesuanDebitCurrentAmount() {
		return daijiesuanDebitCurrentAmount;
	}

	public void setDaijiesuanDebitCurrentAmount(String daijiesuanDebitCurrentAmount) {
		this.daijiesuanDebitCurrentAmount = daijiesuanDebitCurrentAmount;
	}

	public String getDaijiesuanCreditCurrentAmount() {
		return daijiesuanCreditCurrentAmount;
	}

	public void setDaijiesuanCreditCurrentAmount(String daijiesuanCreditCurrentAmount) {
		this.daijiesuanCreditCurrentAmount = daijiesuanCreditCurrentAmount;
	}

	public String getHmDaijiesuanAccountId() {
		return hmDaijiesuanAccountId;
	}

	public void setHmDaijiesuanAccountId(String hmDaijiesuanAccountId) {
		this.hmDaijiesuanAccountId = hmDaijiesuanAccountId;
	}

	public String getHmDaijiesuanBeginningBalances() {
		return hmDaijiesuanBeginningBalances;
	}

	public void setHmDaijiesuanBeginningBalances(String hmDaijiesuanBeginningBalances) {
		this.hmDaijiesuanBeginningBalances = hmDaijiesuanBeginningBalances;
	}

	public String getHmDaijiesuanEndingBalances() {
		return hmDaijiesuanEndingBalances;
	}

	public void setHmDaijiesuanEndingBalances(String hmDaijiesuanEndingBalances) {
		this.hmDaijiesuanEndingBalances = hmDaijiesuanEndingBalances;
	}

	public String getHmDaijiesuanDebitCurrentAmount() {
		return hmDaijiesuanDebitCurrentAmount;
	}

	public void setHmDaijiesuanDebitCurrentAmount(String hmDaijiesuanDebitCurrentAmount) {
		this.hmDaijiesuanDebitCurrentAmount = hmDaijiesuanDebitCurrentAmount;
	}

	public String getHmDaijiesuanCreditCurrentAmount() {
		return hmDaijiesuanCreditCurrentAmount;
	}

	public void setHmDaijiesuanCreditCurrentAmount(String hmDaijiesuanCreditCurrentAmount) {
		this.hmDaijiesuanCreditCurrentAmount = hmDaijiesuanCreditCurrentAmount;
	}


}