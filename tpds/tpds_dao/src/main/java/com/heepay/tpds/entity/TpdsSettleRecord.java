package com.heepay.tpds.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TpdsSettleRecord {
    /**
     * 清算ID
     */
    private Long settleId;

    /**
     * 商户编码
     */
    private String merchantNo;

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
     * 结算日期
     */
    private Date settleTime;

    /**
     * 结算批次号
     */
    private String settleBath;

    /**
     * 总手续费
     */
    private BigDecimal totalFee;

    /**
     * 结算状态
            N：未结算
            D：结算中
            Y：已结算
     */
    private String settleStatus;

    public Long getSettleId() {
        return settleId;
    }

    public void setSettleId(Long settleId) {
        this.settleId = settleId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
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

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }
}