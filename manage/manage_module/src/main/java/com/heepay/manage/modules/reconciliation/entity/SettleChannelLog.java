package com.heepay.manage.modules.reconciliation.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleChannelLog extends DataEntity<SettleChannelLog> {
    
	private static final long serialVersionUID = 1L;

	private long logId;

    private String channelCode;

    private String channelType;
    
    private String checkBathNo; 

    private Date operBeginTime;

    private Date operEndTime;

    private long inRecordNum;
    
    private BigDecimal inTotalAmount;
    
    private String checkStatus;
    
    private String checkFileName;
    
    private long outRecordNum;
    
    private BigDecimal outTotalAmount;
    
    private long recordNum;

    private BigDecimal totalAmount;

    private long errorRecordNum;//差错记录总数
    
    private BigDecimal errorTotalAmount;//差错记录总金额
    
    private String checkFileWhere;

    private String checkFileFrom;

	private String ruleType;
    /**
     * 非映射字段（数据库中没有对应的字段，用于查询）
     */
    private String channelTypeName;
    
    private String path;
    
    private Date beginOperEndTime;
    
    private Date endOperEndTime;
    
    private String channelName;
    
    private String bankNo;
    
    private String bankName;
    
    private String checkStatusTwo;

	public long getLogId() {
		return logId;
	}


	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public void setLogId(long logId) {
		this.logId = logId;
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

	public String getCheckBathNo() {
		return checkBathNo;
	}

	public void setCheckBathNo(String checkBathNo) {
		this.checkBathNo = checkBathNo;
	}

	public Date getOperBeginTime() {
		return operBeginTime;
	}

	public void setOperBeginTime(Date operBeginTime) {
		this.operBeginTime = operBeginTime;
	}

	public Date getOperEndTime() {
		return operEndTime;
	}

	public void setOperEndTime(Date operEndTime) {
		this.operEndTime = operEndTime;
	}

	public long getInRecordNum() {
		return inRecordNum;
	}

	public void setInRecordNum(long inRecordNum) {
		this.inRecordNum = inRecordNum;
	}

	public BigDecimal getInTotalAmount() {
		return inTotalAmount;
	}

	public void setInTotalAmount(BigDecimal inTotalAmount) {
		this.inTotalAmount = inTotalAmount;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckFileName() {
		return checkFileName;
	}

	public void setCheckFileName(String checkFileName) {
		this.checkFileName = checkFileName;
	}

	public long getOutRecordNum() {
		return outRecordNum;
	}

	public void setOutRecordNum(long outRecordNum) {
		this.outRecordNum = outRecordNum;
	}

	public BigDecimal getOutTotalAmount() {
		return outTotalAmount;
	}

	public void setOutTotalAmount(BigDecimal outTotalAmount) {
		this.outTotalAmount = outTotalAmount;
	}

	public long getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(long recordNum) {
		this.recordNum = recordNum;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public long getErrorRecordNum() {
		return errorRecordNum;
	}

	public void setErrorRecordNum(long errorRecordNum) {
		this.errorRecordNum = errorRecordNum;
	}

	public BigDecimal getErrorTotalAmount() {
		return errorTotalAmount;
	}

	public void setErrorTotalAmount(BigDecimal errorTotalAmount) {
		this.errorTotalAmount = errorTotalAmount;
	}

	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCheckStatusTwo() {
		return checkStatusTwo;
	}

	public void setCheckStatusTwo(String checkStatusTwo) {
		this.checkStatusTwo = checkStatusTwo;
	}

	public String getCheckFileWhere() {
		return checkFileWhere;
	}

	public void setCheckFileWhere(String checkFileWhere) {
		this.checkFileWhere = checkFileWhere;
	}

	public String getCheckFileFrom() {
		return checkFileFrom;
	}

	public void setCheckFileFrom(String checkFileFrom) {
		this.checkFileFrom = checkFileFrom;
	}
}