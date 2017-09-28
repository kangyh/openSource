package com.heepay.manage.modules.pbc.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcQueryInfo extends DataEntity<PbcQueryInfo>{
    private static final long serialVersionUID = 1L;

    private long queryInfoId;

    private String txCode;

    private String messageFrom;

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

	public long getQueryInfoId() {
		return queryInfoId;
	}

	public void setQueryInfoId(long queryInfoId) {
		this.queryInfoId = queryInfoId;
	}

	public String getTxCode() {
		return txCode;
	}

	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getTransSerialNumber() {
		return transSerialNumber;
	}

	public void setTransSerialNumber(String transSerialNumber) {
		this.transSerialNumber = transSerialNumber;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getOnlinePayCompanyId() {
		return onlinePayCompanyId;
	}

	public void setOnlinePayCompanyId(String onlinePayCompanyId) {
		this.onlinePayCompanyId = onlinePayCompanyId;
	}

	public String getOnlinePayCompanyName() {
		return onlinePayCompanyName;
	}

	public void setOnlinePayCompanyName(String onlinePayCompanyName) {
		this.onlinePayCompanyName = onlinePayCompanyName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplicationOrgId() {
		return applicationOrgId;
	}

	public void setApplicationOrgId(String applicationOrgId) {
		this.applicationOrgId = applicationOrgId;
	}

	public String getApplicationOrgName() {
		return applicationOrgName;
	}

	public void setApplicationOrgName(String applicationOrgName) {
		this.applicationOrgName = applicationOrgName;
	}

	public String getOperatorIdType() {
		return operatorIdType;
	}

	public void setOperatorIdType(String operatorIdType) {
		this.operatorIdType = operatorIdType;
	}

	public String getOperatorIdNumber() {
		return operatorIdNumber;
	}

	public void setOperatorIdNumber(String operatorIdNumber) {
		this.operatorIdNumber = operatorIdNumber;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorPhoneNumber() {
		return operatorPhoneNumber;
	}

	public void setOperatorPhoneNumber(String operatorPhoneNumber) {
		this.operatorPhoneNumber = operatorPhoneNumber;
	}

	public String getInvestigatorIdType() {
		return investigatorIdType;
	}

	public void setInvestigatorIdType(String investigatorIdType) {
		this.investigatorIdType = investigatorIdType;
	}

	public String getInvestigatorIdNumber() {
		return investigatorIdNumber;
	}

	public void setInvestigatorIdNumber(String investigatorIdNumber) {
		this.investigatorIdNumber = investigatorIdNumber;
	}

	public String getInvestigatorName() {
		return investigatorName;
	}

	public void setInvestigatorName(String investigatorName) {
		this.investigatorName = investigatorName;
	}

	public String getOriginalApplicationId() {
		return originalApplicationId;
	}

	public void setOriginalApplicationId(String originalApplicationId) {
		this.originalApplicationId = originalApplicationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
}