package com.heepay.manage.modules.tpds.entity;

import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class TpdsMerchantAccount extends DataEntity<TpdsMerchantAccount>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer tpdsMerchantId;

    private String merchantNo;

    private String loginName;

    private String systemNo;

    private String accNo;

    private String bankCard;

    private String bankCardBranch;

    private String bankAccount;

    private String bankAccountBranch;

    private Date createTime;

    private Date updateTime;

    private String updateUser;
    
    private String certNo;

    private String status;

    //非映射字段
    private Date beginOperEndTime;
    
    private Date endOperEndTime;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo == null ? null : systemNo.trim();
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo == null ? null : accNo.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getBankCardBranch() {
        return bankCardBranch;
    }

    public void setBankCardBranch(String bankCardBranch) {
        this.bankCardBranch = bankCardBranch == null ? null : bankCardBranch.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBankAccountBranch() {
        return bankAccountBranch;
    }

    public void setBankAccountBranch(String bankAccountBranch) {
        this.bankAccountBranch = bankAccountBranch == null ? null : bankAccountBranch.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public Integer getTpdsMerchantId() {
		return tpdsMerchantId;
	}

	public void setTpdsMerchantId(Integer tpdsMerchantId) {
		this.tpdsMerchantId = tpdsMerchantId;
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

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
}