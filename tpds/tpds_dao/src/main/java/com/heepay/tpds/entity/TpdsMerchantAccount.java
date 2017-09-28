package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsMerchantAccount {
    private Integer tpdsMerchantId;

    /**
     * 商户编号MERCHANT_NO
     */
    private String merchantNo;

    /**
     * 商户名称
     */
    private String loginName;

    /**
     * 接入系统编码
     */
    private String systemNo;

    /**
     * 台账账户（资金存管系统生成）
            银行账户在本系统不开台账账户
     */
    private String accNo;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 银行卡号（现金提现）
     */
    private String bankCard;

    /**
     * 银行卡所在银行联行号
     */
    private String bankCardBranch;

    /**
     * 存管户（监管银行提现）
     */
    private String bankAccount;

    /**
     * 存管户所在银行联行号
     */
    private String bankAccountBranch;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 状态
            ENABLE(启用) 
            DISABL(禁用)
     */
    private String status;


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

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
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
}