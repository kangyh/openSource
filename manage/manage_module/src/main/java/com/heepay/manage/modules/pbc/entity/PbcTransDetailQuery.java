package com.heepay.manage.modules.pbc.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcTransDetailQuery extends DataEntity<PbcTransDetailQuery>{
    private static final long serialVersionUID = 1L;

    private long paymentAccountTransDetailId;

    private String dataType;

    private String data;

    private String inquiryMode;

    private String subjectType;

    private Date startTime;

    private Date expireTime;

    private String applicationCode;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

	public long getPaymentAccountTransDetailId() {
		return paymentAccountTransDetailId;
	}

	public void setPaymentAccountTransDetailId(long paymentAccountTransDetailId) {
		this.paymentAccountTransDetailId = paymentAccountTransDetailId;
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

	public String getInquiryMode() {
		return inquiryMode;
	}

	public void setInquiryMode(String inquiryMode) {
		this.inquiryMode = inquiryMode;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
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

   
}