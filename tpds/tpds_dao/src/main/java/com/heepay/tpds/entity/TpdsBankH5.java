package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsBankH5 {
    private Integer id;

    /**
     * 银行编码
     */
    private String bankNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 会员编号
     */
    private String customerNo;

    /**
     * 接入系统编码
     */
    private String systemNo;

    /**
     * 回调地址
     */
    private String backUrl;

    /**
     * 操作类型：
            enterpwd：客户设置交易密码
            changepwd：客户修改交易密码
            forgetpwd：客户重置交易密码
            checkpwd：客户校验交易密码
            checkcode：客户校验验证码
            cardbind：客户绑定银行卡
            changecard：客户修改绑卡信息
     */
    private String opType;

    /**
     * 业务流水号
     */
    private String businessSeqNo;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 结果码
            1：成功
            2：失败
            3：处理中
     */
    private String flag;

    /**
     * 请求内容
     */
    private String reqField;

    /**
     * 应答内容
     */
    private String resField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo == null ? null : bankNo.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo == null ? null : systemNo.trim();
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl == null ? null : backUrl.trim();
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType == null ? null : opType.trim();
    }

    public String getBusinessSeqNo() {
        return businessSeqNo;
    }

    public void setBusinessSeqNo(String businessSeqNo) {
        this.businessSeqNo = businessSeqNo == null ? null : businessSeqNo.trim();
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getReqField() {
        return reqField;
    }

    public void setReqField(String reqField) {
        this.reqField = reqField == null ? null : reqField.trim();
    }

    public String getResField() {
        return resField;
    }

    public void setResField(String resField) {
        this.resField = resField == null ? null : resField.trim();
    }
}