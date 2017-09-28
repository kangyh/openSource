package com.heepay.manage.modules.pbc.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcAccountInfo extends DataEntity<PbcAccountInfo>{
    /**
	 * 2016年12月24日
	 */
	private static final long serialVersionUID = 2272426661443534901L;

	private Long pbcId;

    private String subjectType;

    private String accountNumber;

    private String accountType;

    private String accountStatus;

    private String currency;

    private BigDecimal accountBalance;

    private String accountInfo;

    private String accountOpenTime;

    private String accountOpenIpAddress;

    private String accountOpenMachineNumber;

    private Date lastTransactionTime;

    private String applicationCode;
    
    private List<PbcTransInfo> transList;

    public Long getPbcId() {
        return pbcId;
    }

    public void setPbcId(Long pbcId) {
        this.pbcId = pbcId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus == null ? null : accountStatus.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo == null ? null : accountInfo.trim();
    }

    public String getAccountOpenIpAddress() {
        return accountOpenIpAddress;
    }

    public void setAccountOpenIpAddress(String accountOpenIpAddress) {
        this.accountOpenIpAddress = accountOpenIpAddress == null ? null : accountOpenIpAddress.trim();
    }

    public String getAccountOpenMachineNumber() {
        return accountOpenMachineNumber;
    }

    public void setAccountOpenMachineNumber(String accountOpenMachineNumber) {
        this.accountOpenMachineNumber = accountOpenMachineNumber == null ? null : accountOpenMachineNumber.trim();
    }

    public Date getLastTransactionTime() {
        return lastTransactionTime;
    }

    public void setLastTransactionTime(Date lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }

	public List<PbcTransInfo> getTransList() {
		return transList;
	}

	public void setTransList(List<PbcTransInfo> transList) {
		this.transList = transList;
	}

	public String getAccountOpenTime() {
		return accountOpenTime;
	}

	public void setAccountOpenTime(String accountOpenTime) {
		this.accountOpenTime = accountOpenTime;
	}
}