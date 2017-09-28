package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleProfitBath {
    /**
     * 分润汇总ID
     */
    private Long settleProfitId;

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
     * 分润批次号
     */
    private String profitBath;

    /**
     * 分润日期
     */
    private Date profitDate;

    /**
     * 处理时间
     */
    private Date dealTime;

    /**
     * 交易单号
     */
    private String transNo;

    /**
     * 订单金额
     */
    private BigDecimal profitAmount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 分润状态（N：未处理 D：处理中 Y：已处理）
     */
    private String profitStatus;

    /**
     * 结算批次
     */
    private String settleBath;

    /**
     * 商户交易订单号
     */
    private String merchantOrderNo;

    public Long getSettleProfitId() {
        return settleProfitId;
    }

    public void setSettleProfitId(Long settleProfitId) {
        this.settleProfitId = settleProfitId;
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

    public String getProfitBath() {
        return profitBath;
    }

    public void setProfitBath(String profitBath) {
        this.profitBath = profitBath == null ? null : profitBath.trim();
    }

    public Date getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(Date profitDate) {
        this.profitDate = profitDate;
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

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(String profitStatus) {
        this.profitStatus = profitStatus == null ? null : profitStatus.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }
}