package com.heepay.tpds.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TpdsSettleItem {
    /**
     * 清算ID
     */
    private Long clearingId;

    /**
     * 商户编码
     */
    private String merchantNo;

    /**
     * 商户类型
     */
    private String merchantType;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 币种(156:人民币)
     */
    private String currency;

    /**
     * 用户订单号
     */
    private String merchantOrderNo;

    /**
     * 交易订单号（支付单号）
     */
    private String transNo;

    /**
     * 交易金额
     */
    private BigDecimal requestAmount;

    /**
     * 成功支付时间(yyyy-MM-dd hh:mi:ss)
     */
    private Date successTime;

    /**
     * 结算日期
     */
    private Date settleTime;

    /**
     * 清算流水号
     */
    private String settleNo;

    /**
     * 结算批次号
     */
    private String settleBath;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 结算状态（N：未结算，D：结算中 Y：已结算）
     */
    private String settleStatus;

    /**
     * 处理结束时间
     */
    private Date finishTime;

    public Long getClearingId() {
        return clearingId;
    }

    public void setClearingId(Long clearingId) {
        this.clearingId = clearingId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType == null ? null : merchantType.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
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

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
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

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo == null ? null : settleNo.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}