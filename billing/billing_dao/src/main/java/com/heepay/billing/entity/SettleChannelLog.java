package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleChannelLog {
    /**
     * 主键ID
     */
    private Long logId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 对账批次号
     */
    private String checkBathNo;

    /**
     * 操作开始时间
     */
    private Date operBeginTime;

    /**
     * 操作结束时间
     */
    private Date operEndTime;

    /**
     * 入款总记录数
     */
    private Long inRecordNum;

    /**
     * 入款总金额
     */
    private BigDecimal inTotalAmount;

    /**
     * 对账状态(WS：开始  WS-D:开始中   CD：对账中  CD-D:对账中中  CE：结束）
     */
    private String checkStatus;

    /**
     * 对账文件名
     */
    private String checkFileName;

    /**
     * 出款总笔数
     */
    private Long outRecordNum;

    /**
     * 出款总金额
     */
    private BigDecimal outTotalAmount;

    /**
     * 总记录笔数
     */
    private Long recordNum;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 差错记录总数
     */
    private Long errorRecordNum;

    /**
     * 差错记录总金额
     */
    private BigDecimal errorTotalAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 文件存储位置
     */
    private String checkFileWhere;

    /**
     * 文件来源
     */
    private String checkFileFrom;
    
    /**
     * 对账规则
     */
    private String ruleType;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getCheckBathNo() {
        return checkBathNo;
    }

    public void setCheckBathNo(String checkBathNo) {
        this.checkBathNo = checkBathNo == null ? null : checkBathNo.trim();
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

    public Long getInRecordNum() {
        return inRecordNum;
    }

    public void setInRecordNum(Long inRecordNum) {
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
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getCheckFileName() {
        return checkFileName;
    }

    public void setCheckFileName(String checkFileName) {
        this.checkFileName = checkFileName == null ? null : checkFileName.trim();
    }

    public Long getOutRecordNum() {
        return outRecordNum;
    }

    public void setOutRecordNum(Long outRecordNum) {
        this.outRecordNum = outRecordNum;
    }

    public BigDecimal getOutTotalAmount() {
        return outTotalAmount;
    }

    public void setOutTotalAmount(BigDecimal outTotalAmount) {
        this.outTotalAmount = outTotalAmount;
    }

    public Long getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(Long recordNum) {
        this.recordNum = recordNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getErrorRecordNum() {
        return errorRecordNum;
    }

    public void setErrorRecordNum(Long errorRecordNum) {
        this.errorRecordNum = errorRecordNum;
    }

    public BigDecimal getErrorTotalAmount() {
        return errorTotalAmount;
    }

    public void setErrorTotalAmount(BigDecimal errorTotalAmount) {
        this.errorTotalAmount = errorTotalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCheckFileWhere() {
        return checkFileWhere;
    }

    public void setCheckFileWhere(String checkFileWhere) {
        this.checkFileWhere = checkFileWhere == null ? null : checkFileWhere.trim();
    }

    public String getCheckFileFrom() {
        return checkFileFrom;
    }

    public void setCheckFileFrom(String checkFileFrom) {
        this.checkFileFrom = checkFileFrom == null ? null : checkFileFrom.trim();
    }

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
    
    
}