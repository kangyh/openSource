package com.heepay.risk.entity;

import java.util.Date;


public class PbcReleaseFeedback {
    private static final long serialVersionUID = 1L;

	private Long releaseFeedbackId;

    private String result;

    private String caseNumber;

    private String applicationTime;

    private String accountNumber;

    private String feedbackRemark;

    private Long feedBackId;
    private String applicationCode;

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
	
    public Long getReleaseFeedbackId() {
        return releaseFeedbackId;
    }

    public void setReleaseFeedbackId(Long releaseFeedbackId) {
        this.releaseFeedbackId = releaseFeedbackId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber == null ? null : caseNumber.trim();
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime == null ? null : applicationTime.trim();
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getFeedbackRemark() {
        return feedbackRemark;
    }

    public void setFeedbackRemark(String feedbackRemark) {
        this.feedbackRemark = feedbackRemark == null ? null : feedbackRemark.trim();
    }

    public Long getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(Long feedBackId) {
        this.feedBackId = feedBackId;
    }

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
}