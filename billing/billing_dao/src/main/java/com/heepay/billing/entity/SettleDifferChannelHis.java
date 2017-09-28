package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleDifferChannelHis {
    /**
     * 主键
     */
    private Long hisId;

    /**
     * 通道差错账汇总ID
     */
    private Long differChanbillId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 币种
     */
    private String currency;

    /**
     * 差错批次号
     */
    private String errorBath;

    /**
     * 差错日期
     */
    private Date errorDate;

    /**
     * 处理时间
     */
    private Date dealTime;

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 交易订单号
     */
    private String transNo;

    /**
     * 支付金额
     */
    private BigDecimal successAmount;

    /**
     * 成本
     */
    private BigDecimal cost;

    /**
     * 账单类型（补账  撤账）
     */
    private String billType;

    /**
     * 差错状态（N：未处理 D：处理中 Y：已处理）
     */
    private String errorStatus;

    /**
     * 审核状态（N：未审核 F：审核失败 S：审核成功
     */
    private String checkStatus;

    /**
     * 审核备注
     */
    private String checkMessage;

    /**
     * 归档日期
     */
    private Date dateZip;

    /**
     * 处理人
     */
    private String updateAuthor;

    /**
     * 上游流水
     */
    private String channelSeq;

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public Long getDifferChanbillId() {
        return differChanbillId;
    }

    public void setDifferChanbillId(Long differChanbillId) {
        this.differChanbillId = differChanbillId;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getErrorBath() {
        return errorBath;
    }

    public void setErrorBath(String errorBath) {
        this.errorBath = errorBath == null ? null : errorBath.trim();
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus == null ? null : errorStatus.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getCheckMessage() {
        return checkMessage;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage = checkMessage == null ? null : checkMessage.trim();
    }

    public Date getDateZip() {
        return dateZip;
    }

    public void setDateZip(Date dateZip) {
        this.dateZip = dateZip;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public String getChannelSeq() {
        return channelSeq;
    }

    public void setChannelSeq(String channelSeq) {
        this.channelSeq = channelSeq == null ? null : channelSeq.trim();
    }
}