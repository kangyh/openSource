package com.heepay.manage.modules.riskManage.util;

import java.math.BigDecimal;
import java.util.List;

public class MerchantRiskVo {
	
	private String merchantId;
	private String accountType;
	private String bankCardType;
	private String productCode; //产品类型
	private String merchantCompany;
	private String bankCardNo;
	private String bankCardOwnerMobile;
	private String bankCardOwnerIdCard;
	private BigDecimal batPayAmount; //批量转账总金额
	private BigDecimal perItemAmount;  //单笔金额
	private BigDecimal perDayAmount; //单日金额
	private BigDecimal perMonthAmount; //单月金额
	private BigDecimal perDayIncomeAmount;//单日入金金额
	private String trans_type; //为批量转账添加此字段
	private String tradeIp;
	private String ruleId;
	private List ruleIdList;
	
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getMerchantCompany() {
		return merchantCompany;
	}
	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardOwnerMobile() {
		return bankCardOwnerMobile;
	}
	public void setBankCardOwnerMobile(String bankCardOwnerMobile) {
		this.bankCardOwnerMobile = bankCardOwnerMobile;
	}
	public String getBankCardOwnerIdCard() {
		return bankCardOwnerIdCard;
	}
	public void setBankCardOwnerIdCard(String bankCardOwnerIdCard) {
		this.bankCardOwnerIdCard = bankCardOwnerIdCard;
	}
	public BigDecimal getBatPayAmount() {
		return batPayAmount;
	}
	public void setBatPayAmount(BigDecimal batPayAmount) {
		this.batPayAmount = batPayAmount;
	}
	public BigDecimal getPerItemAmount() {
		return perItemAmount;
	}
	public void setPerItemAmount(BigDecimal perItemAmount) {
		this.perItemAmount = perItemAmount;
	}
	public BigDecimal getPerDayAmount() {
		return perDayAmount;
	}
	public void setPerDayAmount(BigDecimal perDayAmount) {
		this.perDayAmount = perDayAmount;
	}
	public BigDecimal getPerMonthAmount() {
		return perMonthAmount;
	}
	public void setPerMonthAmount(BigDecimal perMonthAmount) {
		this.perMonthAmount = perMonthAmount;
	}
	public BigDecimal getPerDayIncomeAmount() {
		return perDayIncomeAmount;
	}
	public void setPerDayIncomeAmount(BigDecimal perDayIncomeAmount) {
		this.perDayIncomeAmount = perDayIncomeAmount;
	}
	
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public String getTradeIp() {
		return tradeIp;
	}
	public void setTradeIp(String tradeIp) {
		this.tradeIp = tradeIp;
	}

	public List getRuleIdList() {
		return ruleIdList;
	}

	public void setRuleIdList(List ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
}
