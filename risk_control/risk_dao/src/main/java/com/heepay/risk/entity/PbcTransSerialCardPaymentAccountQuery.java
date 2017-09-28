package com.heepay.risk.entity;

import java.util.Date;


public class PbcTransSerialCardPaymentAccountQuery {
    private static final long serialVersionUID = 1L;

	private Long transSerialCardPaymentAccounId;

    private String dataType;

    private String data;

    private String bankId;

    private Date transTime;

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
	
    public Long getTransSerialCardPaymentAccounId() {
        return transSerialCardPaymentAccounId;
    }

    public void setTransSerialCardPaymentAccounId(Long transSerialCardPaymentAccounId) {
        this.transSerialCardPaymentAccounId = transSerialCardPaymentAccounId;
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

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }


	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
}