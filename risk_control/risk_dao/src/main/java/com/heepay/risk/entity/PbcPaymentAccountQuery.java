package com.heepay.risk.entity;

import java.util.Date;


public class PbcPaymentAccountQuery {
    private static final long serialVersionUID = 1L;

	private Long paymentAccountId;

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

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public String getAccountOwnerName() {
        return accountOwnerName;
    }

    public void setAccountOwnerName(String accountOwnerName) {
        this.accountOwnerName = accountOwnerName == null ? null : accountOwnerName.trim();
    }

    public String getAccountOwnerIdType() {
        return accountOwnerIdType;
    }

    public void setAccountOwnerIdType(String accountOwnerIdType) {
        this.accountOwnerIdType = accountOwnerIdType == null ? null : accountOwnerIdType.trim();
    }

  

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public Long getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccountId(Long paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}
}