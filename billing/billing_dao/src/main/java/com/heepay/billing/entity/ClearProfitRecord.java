package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ClearProfitRecord {
	 /**
     * 主键ID
     */
    private Long profitId;

    /**
     * 分润明细ID（交易传过来的ID）
     */
    private String shareDetailId;

    /**
     * 商户号
     */
    private Integer merchantId;

    /**
     * 交易单号
     */
    private String transNo;

    /**
     * 分润金额
     */
    private BigDecimal profitAmount;

    /**
     * 应结算金额
     */
    private BigDecimal profitAmountPlan;

    /**
     * 分润手续费
     */
    private BigDecimal profitFee;

    /**
     * 手续费扣除方式
     */
    private String feeWay;

    /**
     * 分润批次号
     */
    private String profitBath;

    /**
     * 分润时间
     */
    private Date profitTime;

    /**
     * 分润状态(N：未分润 Y：已分润)
     */
    private String profitStatus;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 分润号（交易系统用，清结算只负责记录）
     */
    private String shareId;

    /**
     * 商户交易订单号
     */
    private String merchantOrderNo;

    public Long getProfitId() {
        return profitId;
    }

    public void setProfitId(Long profitId) {
        this.profitId = profitId;
    }

    public String getShareDetailId() {
        return shareDetailId;
    }

    public void setShareDetailId(String shareDetailId) {
        this.shareDetailId = shareDetailId == null ? null : shareDetailId.trim();
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    public BigDecimal getProfitAmountPlan() {
        return profitAmountPlan;
    }

    public void setProfitAmountPlan(BigDecimal profitAmountPlan) {
        this.profitAmountPlan = profitAmountPlan;
    }

    public BigDecimal getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(BigDecimal profitFee) {
        this.profitFee = profitFee;
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getProfitBath() {
        return profitBath;
    }

    public void setProfitBath(String profitBath) {
        this.profitBath = profitBath == null ? null : profitBath.trim();
    }

    public Date getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(Date profitTime) {
        this.profitTime = profitTime;
    }

    public String getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(String profitStatus) {
        this.profitStatus = profitStatus == null ? null : profitStatus.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }
}