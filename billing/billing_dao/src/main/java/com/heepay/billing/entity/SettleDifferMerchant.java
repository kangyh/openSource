package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleDifferMerchant {
    /**
     * 商户差错账汇总ID
     */
    private Long differMerbillId;

    /**
     * 商户号
     */
    private Integer merchantId;

    /**
     * 交易类型
     */
    private String transType;

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
     * 处理人
     */
    private String updateAuthor;

    /**
     * 处理时间
     */
    private Date dealTime;

    /**
     * 交易单号
     */
    private String transNo;

    /**
     * 成功金额（订单金额）
     */
    private BigDecimal requestAmount;

    /**
     * 应结算金额
     */
    private BigDecimal settleAmountPlan;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 账单类型（FILL：补账  REFUND：撤账）
     */
    private String billType;

    /**
     * 差错状态（N：未处理 D：处理中 Y：已处理）
     */
    private String errorStatus;

    /**
     * 审核状态（N：未审核 F：审核失败 S：审核成功）
     */
    private String checkStatus;

    /**
     * 审核备注
     */
    private String checkMessage;

    /**
     * 商户交易订单号
     */
    private String merchantOrderNo;

    /**
     * 是否归是否归档unzip(默认)
     */
    private String isZip;

    /**
     * 商户名称
     */
    private String merchantName;

    public Long getDifferMerbillId() {
        return differMerbillId;
    }

    public void setDifferMerbillId(Long differMerbillId) {
        this.differMerbillId = differMerbillId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public BigDecimal getSettleAmountPlan() {
        return settleAmountPlan;
    }

    public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
        this.settleAmountPlan = settleAmountPlan;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public String getIsZip() {
        return isZip;
    }

    public void setIsZip(String isZip) {
        this.isZip = isZip == null ? null : isZip.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }
}