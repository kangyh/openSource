package com.heepay.manage.modules.pbc.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcTransSerialCardPaymentAccountQuery extends DataEntity<PbcTransSerialCardPaymentAccountQuery>{
    private static final long serialVersionUID = 1L;

    private long transSerialCardPaymentAccounId;

    private String dataType;

    private String data;

    private String bankId;

    private Date transactionDate;

    private String applicationCode;

    /*
     * 非映射字段
     */
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

	public long getTransSerialCardPaymentAccounId() {
		return transSerialCardPaymentAccounId;
	}

	public void setTransSerialCardPaymentAccounId(long transSerialCardPaymentAccounId) {
		this.transSerialCardPaymentAccounId = transSerialCardPaymentAccounId;
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

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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