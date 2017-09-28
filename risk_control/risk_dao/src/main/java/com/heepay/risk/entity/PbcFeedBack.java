package com.heepay.risk.entity;

import java.util.Date;


public class PbcFeedBack {
    private static final long serialVersionUID = 1L;

	private Long feedBackId;

    private String mode;

    private String toId;

    private String transTypeCode;

    private String transSerialNumber;

    private String applicationId;

    private String result;

    private String operatorName;

    private String operatorPhoneNumber;

    private String feedBackOrgName;

    private String feedBackRemark;

    private String remark;

    private String requestEventType;

    private String applicationOrgCode;

    private Date applicationTime;

    private String applicationOrgName;

    private String emergencyLevel;

    private String feedbackLimit;

    private String status;

    private String returnCode;

    private String returnResults;

    private Date riskTime;

    private String riskOperator;

    private String riskRemark;

    private Integer num;

    private String riskStatus;
    
    private String failRemark;
    
    private String paramType;
    
    private String params;
    
    private Date reportTime;
    
    private Date dealTime;
    
    private String featureCodes;
    
    private String abnormalDescribe;
    
    private String reportCodes;
    private String feedType;
    
    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    private String yes;
    
    private String changeType;
    
    
    
    
    public String getFeatureCodes() {
		return featureCodes;
	}

	public void setFeatureCodes(String featureCodes) {
		this.featureCodes = featureCodes;
	}

	public String getAbnormalDescribe() {
		return abnormalDescribe;
	}

	public void setAbnormalDescribe(String abnormalDescribe) {
		this.abnormalDescribe = abnormalDescribe;
	}

	public String getReportCodes() {
		return reportCodes;
	}

	public void setReportCodes(String reportCodes) {
		this.reportCodes = reportCodes;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getYes() {
		return yes;
	}

	public void setYes(String yes) {
		this.yes = yes;
	}

	public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId == null ? null : toId.trim();
    }

    public String getTransTypeCode() {
        return transTypeCode;
    }

    public void setTransTypeCode(String transTypeCode) {
        this.transTypeCode = transTypeCode == null ? null : transTypeCode.trim();
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
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

    public String getFeedBackOrgName() {
        return feedBackOrgName;
    }

    public void setFeedBackOrgName(String feedBackOrgName) {
        this.feedBackOrgName = feedBackOrgName == null ? null : feedBackOrgName.trim();
    }

    public String getFeedBackRemark() {
        return feedBackRemark;
    }

    public void setFeedBackRemark(String feedBackRemark) {
        this.feedBackRemark = feedBackRemark == null ? null : feedBackRemark.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRequestEventType() {
        return requestEventType;
    }

    public void setRequestEventType(String requestEventType) {
        this.requestEventType = requestEventType == null ? null : requestEventType.trim();
    }

    public String getApplicationOrgCode() {
        return applicationOrgCode;
    }

    public void setApplicationOrgCode(String applicationOrgCode) {
        this.applicationOrgCode = applicationOrgCode == null ? null : applicationOrgCode.trim();
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getApplicationOrgName() {
        return applicationOrgName;
    }

    public void setApplicationOrgName(String applicationOrgName) {
        this.applicationOrgName = applicationOrgName == null ? null : applicationOrgName.trim();
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel == null ? null : emergencyLevel.trim();
    }

    public String getFeedbackLimit() {
        return feedbackLimit;
    }

    public void setFeedbackLimit(String feedbackLimit) {
        this.feedbackLimit = feedbackLimit == null ? null : feedbackLimit.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode == null ? null : returnCode.trim();
    }

    public String getReturnResults() {
        return returnResults;
    }

    public void setReturnResults(String returnResults) {
        this.returnResults = returnResults == null ? null : returnResults.trim();
    }

    public Date getRiskTime() {
        return riskTime;
    }

    public void setRiskTime(Date riskTime) {
        this.riskTime = riskTime;
    }

    public String getRiskOperator() {
        return riskOperator;
    }

    public void setRiskOperator(String riskOperator) {
        this.riskOperator = riskOperator == null ? null : riskOperator.trim();
    }

    public String getRiskRemark() {
        return riskRemark;
    }

    public void setRiskRemark(String riskRemark) {
        this.riskRemark = riskRemark == null ? null : riskRemark.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus == null ? null : riskStatus.trim();
    }
    
    public String getFailRemark() {
        return failRemark;
    }

    public void setFailRemark(String failRemark) {
        this.failRemark = failRemark == null ? null : failRemark.trim();
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

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getFeedType() {
		return feedType;
	}

	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
}