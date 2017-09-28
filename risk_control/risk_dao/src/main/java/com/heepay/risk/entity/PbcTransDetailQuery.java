package com.heepay.risk.entity;

import java.util.Date;


public class PbcTransDetailQuery {
    private static final long serialVersionUID = 1L;

	private Long paymentAccountTransDetailId;

    private String dataType;

    private String data;

    private String inquiryMode;

    private String paymentAccountDetailType;

    private Date transStartTime;

    private Date transEndTime;

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
	
    public Long getPaymentAccountTransDetailId() {
        return paymentAccountTransDetailId;
    }

    public void setPaymentAccountTransDetailId(Long paymentAccountTransDetailId) {
        this.paymentAccountTransDetailId = paymentAccountTransDetailId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public String getInquiryMode() {
        return inquiryMode;
    }

    public void setInquiryMode(String inquiryMode) {
        this.inquiryMode = inquiryMode == null ? null : inquiryMode.trim();
    }

    public String getPaymentAccountDetailType() {
        return paymentAccountDetailType;
    }

    public void setPaymentAccountDetailType(String paymentAccountDetailType) {
        this.paymentAccountDetailType = paymentAccountDetailType == null ? null : paymentAccountDetailType.trim();
    }

    public Date getTransStartTime() {
        return transStartTime;
    }

    public void setTransStartTime(Date transStartTime) {
        this.transStartTime = transStartTime;
    }

    public Date getTransEndTime() {
        return transEndTime;
    }

    public void setTransEndTime(Date transEndTime) {
        this.transEndTime = transEndTime;
    }


	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
}