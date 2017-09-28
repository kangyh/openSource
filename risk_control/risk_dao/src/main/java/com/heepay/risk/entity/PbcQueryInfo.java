package com.heepay.risk.entity;

import java.util.Date;


public class PbcQueryInfo {
    private static final long serialVersionUID = 1L;

	private Long queryInfoId;

    private String transTypeCode;

    private String messageFromCode;

    private String transSerialNumber;

    private String applicationId;

    private String caseNumber;

    private String caseType;

    private String onlinePayCompanyId;

    private String onlinePayCompanyName;

    private String reason;

    private String remark;

    private Date applicationTime;

    private String applicationOrgId;

    private String applicationOrgName;

    private String operatorIdType;

    private String operatorIdNumber;

    private String operatorName;

    private String operatorPhoneNumber;

    private String investigatorIdType;

    private String investigatorIdNumber;

    private String investigatorName;

    private String originalApplicationId;

    private String status;

    private Date dealTime;
    
    private String dataType;
    
    private String data;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    
    public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}
	
    public Long getQueryInfoId() {
        return queryInfoId;
    }

    public void setQueryInfoId(Long queryInfoId) {
        this.queryInfoId = queryInfoId;
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode == null ? null : transTypeCode.trim();
    }

    public String getMessageFromCode() {
        return messageFromCode;
    }

    public void setMessageFromCode(String messageFromCode) {
        this.messageFromCode = messageFromCode == null ? null : messageFromCode.trim();
    }

    public String getTransSerialNumber() {
        return transSerialNumber;
    }

    public void setTransSerialNumber(String transSerialNumber) {
        this.transSerialNumber = transSerialNumber == null ? null : transSerialNumber.trim();
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber == null ? null : caseNumber.trim();
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType == null ? null : caseType.trim();
    }

    public String getOnlinePayCompanyId() {
        return onlinePayCompanyId;
    }

    public void setOnlinePayCompanyId(String onlinePayCompanyId) {
        this.onlinePayCompanyId = onlinePayCompanyId == null ? null : onlinePayCompanyId.trim();
    }

    public String getOnlinePayCompanyName() {
        return onlinePayCompanyName;
    }

    public void setOnlinePayCompanyName(String onlinePayCompanyName) {
        this.onlinePayCompanyName = onlinePayCompanyName == null ? null : onlinePayCompanyName.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApplicationOrgId() {
        return applicationOrgId;
    }

    public void setApplicationOrgId(String applicationOrgId) {
        this.applicationOrgId = applicationOrgId == null ? null : applicationOrgId.trim();
    }

    public String getApplicationOrgName() {
        return applicationOrgName;
    }

    public void setApplicationOrgName(String applicationOrgName) {
        this.applicationOrgName = applicationOrgName == null ? null : applicationOrgName.trim();
    }

    public String getOperatorIdType() {
        return operatorIdType;
    }

    public void setOperatorIdType(String operatorIdType) {
        this.operatorIdType = operatorIdType == null ? null : operatorIdType.trim();
    }

    public String getOperatorIdNumber() {
        return operatorIdNumber;
    }

    public void setOperatorIdNumber(String operatorIdNumber) {
        this.operatorIdNumber = operatorIdNumber == null ? null : operatorIdNumber.trim();
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getOperatorPhoneNumber() {
        return operatorPhoneNumber;
    }

    public void setOperatorPhoneNumber(String operatorPhoneNumber) {
        this.operatorPhoneNumber = operatorPhoneNumber == null ? null : operatorPhoneNumber.trim();
    }

    public String getInvestigatorIdType() {
        return investigatorIdType;
    }

    public void setInvestigatorIdType(String investigatorIdType) {
        this.investigatorIdType = investigatorIdType == null ? null : investigatorIdType.trim();
    }

    public String getInvestigatorIdNumber() {
        return investigatorIdNumber;
    }

    public void setInvestigatorIdNumber(String investigatorIdNumber) {
        this.investigatorIdNumber = investigatorIdNumber == null ? null : investigatorIdNumber.trim();
    }

    public String getInvestigatorName() {
        return investigatorName;
    }

    public void setInvestigatorName(String investigatorName) {
        this.investigatorName = investigatorName == null ? null : investigatorName.trim();
    }

    public String getOriginalApplicationId() {
        return originalApplicationId;
    }

    public void setOriginalApplicationId(String originalApplicationId) {
        this.originalApplicationId = originalApplicationId == null ? null : originalApplicationId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}