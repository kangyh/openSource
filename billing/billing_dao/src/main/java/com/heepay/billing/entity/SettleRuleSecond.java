package com.heepay.billing.entity;

import java.util.Date;

public class SettleRuleSecond {
    private Long ruleId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 账单类型
     */
    private String billType;

    /**
     * 分隔符
     */
    private String splitFlg;

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 上游流水
     */
    private String bankSeq;

    /**
     * 结算金额
     */
    private String successAmount;

    /**
     * 优惠金额
     */
    private String feeFree;

    /**
     * 服务费
     */
    private String feeService;

    /**
     * 发卡行手续费
     */
    private String feeBank;

    /**
     * 交易状态
     */
    private String transStatus;

    /**
     * 摘要
     */
    private String remark;

    private String field1;

    private String field2;

    private String field3;

    /**
     * 开始行
     */
    private Byte startRow;

    /**
     * 结束标识
     */
    private String endFlg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createAuthor;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateAuthor;

    /**
     * 对账方式：handle（手工对账）auto（自动对账）
     */
    private String settleWay;

    private String status;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getSplitFlg() {
        return splitFlg;
    }

    public void setSplitFlg(String splitFlg) {
        this.splitFlg = splitFlg == null ? null : splitFlg.trim();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq == null ? null : bankSeq.trim();
    }

    public String getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(String successAmount) {
        this.successAmount = successAmount == null ? null : successAmount.trim();
    }

    public String getFeeFree() {
        return feeFree;
    }

    public void setFeeFree(String feeFree) {
        this.feeFree = feeFree == null ? null : feeFree.trim();
    }

    public String getFeeService() {
        return feeService;
    }

    public void setFeeService(String feeService) {
        this.feeService = feeService == null ? null : feeService.trim();
    }

    public String getFeeBank() {
        return feeBank;
    }

    public void setFeeBank(String feeBank) {
        this.feeBank = feeBank == null ? null : feeBank.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public Byte getStartRow() {
        return startRow;
    }

    public void setStartRow(Byte startRow) {
        this.startRow = startRow;
    }

    public String getEndFlg() {
        return endFlg;
    }

    public void setEndFlg(String endFlg) {
        this.endFlg = endFlg == null ? null : endFlg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public String getSettleWay() {
        return settleWay;
    }

    public void setSettleWay(String settleWay) {
        this.settleWay = settleWay == null ? null : settleWay.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}