package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleMerchantRecord {
    /**
     * 清算ID
     */
    private Long settleId;

    /**
     * 用户编码
     */
    private Integer merchantId;

    /**
     * 用户类型
     */
    private String merchantType;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 币种(156:人民币)
     */
    private String currency;

    /**
     * 交易总笔数
     */
    private Integer payNum;

    /**
     * 交易总金额
     */
    private BigDecimal totalAmount;

    /**
     * 会计时间
     */
    private Date checkTime;

    /**
     * 清算日期
     */
    private Date settleTime;

    /**
     * 订单结算周期
     */
    private String settleCyc;

    /**
     * 结算批次号
     */
    private String settleBath;

    /**
     * 应结算总金额
     */
    private BigDecimal settleAmount;

    /**
     * 手续费结算日期
     */
    private Date feeTime;

    /**
     * 总手续费
     */
    private BigDecimal totalFee;

    /**
     * 手续费扣除方式
     */
    private String feeWay;

    /**
     * 手续费结算周期
     */
    private String feeSettleCyc;

    /**
     * 对账状态（N：未对账 Y：已对账）
     */
    private String checkStatus;

    /**
     * 结算状态（N：未结算，D：结算中 Y：已结算）
     */
    private String settleStatus;

    /**
     * 是否归是否归档unzip(默认)
     */
    private String isZip;

    /**
     * 商户名称
     */
    private String merchantName;

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType == null ? null : merchantType.trim();
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

    public Integer getPayNum() {
        return payNum;
    }

    public void setPayNum(Integer payNum) {
        this.payNum = payNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettleCyc() {
        return settleCyc;
    }

    public void setSettleCyc(String settleCyc) {
        this.settleCyc = settleCyc == null ? null : settleCyc.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public Date getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(Date feeTime) {
        this.feeTime = feeTime;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getFeeSettleCyc() {
        return feeSettleCyc;
    }

    public void setFeeSettleCyc(String feeSettleCyc) {
        this.feeSettleCyc = feeSettleCyc == null ? null : feeSettleCyc.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
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