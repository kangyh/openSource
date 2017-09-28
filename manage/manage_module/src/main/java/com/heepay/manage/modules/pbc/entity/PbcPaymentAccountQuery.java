package com.heepay.manage.modules.pbc.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcPaymentAccountQuery extends DataEntity<PbcPaymentAccountQuery>{
    private static final long serialVersionUID = 1L;

    private long paymentAccountId;

    private String dataType;

    private String data;

    private String subjectType;

    private String accountOwnerName;

    private String accountOwnerIdType;

    private String applicationCode;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

	public long getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccountId(long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
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

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getAccountOwnerName() {
		return accountOwnerName;
	}

	public void setAccountOwnerName(String accountOwnerName) {
		this.accountOwnerName = accountOwnerName;
	}

	public String getAccountOwnerIdType() {
		return accountOwnerIdType;
	}

	public void setAccountOwnerIdType(String accountOwnerIdType) {
		this.accountOwnerIdType = accountOwnerIdType;
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