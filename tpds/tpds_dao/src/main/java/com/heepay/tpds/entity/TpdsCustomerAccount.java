package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsCustomerAccount {
    private Integer id;

    /**
     * 商户编号MERCHANT_NO
     */
    private String merchantNo;

    /**
     * 会员编号
     */
    private String customerNo;

    /**
     * 台账账户（资金存管系统生成）
     */
    private String accNo;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 监管银行台账账户
     */
    private String bankAccNo;

    /**
     * 监管银行核心二类户
     */
    private String secBankAccNo;

    /**
     * 状态
            ENABLE(启用) 
            DISABL(禁用)
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
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

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo == null ? null : bankAccNo.trim();
    }

    public String getSecBankAccNo() {
        return secBankAccNo;
    }

    public void setSecBankAccNo(String secBankAccNo) {
        this.secBankAccNo = secBankAccNo == null ? null : secBankAccNo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}