package com.heepay.manage.modules.reconciliation.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleRuleSecond extends DataEntity<SettleRuleSecond> {

	private static final long serialVersionUID = 1L;

	private Long ruleId;

	private String channelCode;

	private String channelType;

	private String channelName;

	private String billType;

	private String splitFlg;

	private String paymentId;

	private String bankSeq;

	private String bankSeq2;

	private String successAmount;//结算金额位置

	private String successAmountAdd;

	private String feeFree;//优惠金额位置

	private String feeFreeAdd;

	private String feeService;//服务费位置

	private String feeServiceAdd;

	private String feeBank;//发卡行手续费位置

	private String feeBankAdd;

	private String transStatus;//交易状态位置

	private String transStatusAdd;

	private String remark;

	private String field1;

	private String field2;

	private String field3;

	private Byte startRow;

	private String endFlg;

	private Date createTime;

	private String createAuthor;

	private Date updateTime;

	private String updateAuthor;

	private String settleWay;

	private String status;

	//添加规则非映射字段
	//支付单号规则名称
	private String paymentIdName;
	//支付单号规则
	private String paymentIdRule;


	//银行流水号规则名称
	private String bankSeqName;
	//银行流水号规则
	private String bankSeqRule;

	public String getTransStatusAdd() {
		return transStatusAdd;
	}

	public void setTransStatusAdd(String transStatusAdd) {
		this.transStatusAdd = transStatusAdd;
	}

	public String getSuccessAmountAdd() {
		return successAmountAdd;
	}

	public void setSuccessAmountAdd(String successAmountAdd) {
		this.successAmountAdd = successAmountAdd;
	}

	public String getFeeFreeAdd() {
		return feeFreeAdd;
	}

	public void setFeeFreeAdd(String feeFreeAdd) {
		this.feeFreeAdd = feeFreeAdd;
	}

	public String getFeeServiceAdd() {
		return feeServiceAdd;
	}

	public void setFeeServiceAdd(String feeServiceAdd) {
		this.feeServiceAdd = feeServiceAdd;
	}

	public String getFeeBankAdd() {
		return feeBankAdd;
	}

	public void setFeeBankAdd(String feeBankAdd) {
		this.feeBankAdd = feeBankAdd;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getSplitFlg() {
		return splitFlg;
	}

	public void setSplitFlg(String splitFlg) {
		this.splitFlg = splitFlg;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getBankSeq() {
		return bankSeq;
	}

	public void setBankSeq(String bankSeq) {
		this.bankSeq = bankSeq;
	}

	public String getBankSeq2() {
		return bankSeq2;
	}

	public void setBankSeq2(String bankSeq2) {
		this.bankSeq2 = bankSeq2;
	}

	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}

	public String getFeeFree() {
		return feeFree;
	}

	public void setFeeFree(String feeFree) {
		this.feeFree = feeFree;
	}

	public String getFeeService() {
		return feeService;
	}

	public void setFeeService(String feeService) {
		this.feeService = feeService;
	}

	public String getFeeBank() {
		return feeBank;
	}

	public void setFeeBank(String feeBank) {
		this.feeBank = feeBank;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public Byte getStartRow() {
		return startRow;
	}

	public void setStartRow(Byte startRow) {
		this.startRow = startRow;
	}

	public String getEndFlg() {
		return endFlg;
	}

	public void setEndFlg(String endFlg) {
		this.endFlg = endFlg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	public String getSettleWay() {
		return settleWay;
	}

	public void setSettleWay(String settleWay) {
		this.settleWay = settleWay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentIdName() {
		return paymentIdName;
	}

	public void setPaymentIdName(String paymentIdName) {
		this.paymentIdName = paymentIdName;
	}

	public String getPaymentIdRule() {
		return paymentIdRule;
	}

	public void setPaymentIdRule(String paymentIdRule) {
		this.paymentIdRule = paymentIdRule;
	}

	public String getBankSeqName() {
		return bankSeqName;
	}

	public void setBankSeqName(String bankSeqName) {
		this.bankSeqName = bankSeqName;
	}

	public String getBankSeqRule() {
		return bankSeqRule;
	}

	public void setBankSeqRule(String bankSeqRule) {
		this.bankSeqRule = bankSeqRule;
	}
}