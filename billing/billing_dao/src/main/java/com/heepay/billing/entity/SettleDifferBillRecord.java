package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleDifferBillRecord {
    /**
     * 主键
     */
    private Long billDifferId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 通道业务类型
     */
    private String channelType;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 成功支付时间
     */
    private Date successTime;

    /**
     * 手续费扣除方式
     */
    private String costWay;

    /**
     * 银行流水
     */
    private String bankSeq;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 通道提供者
     */
    private String channelProvider;

    /**
     * 入库时间
     */
    private Date saveTime;

    /**
     * 支付订单号
     */
    private String paymentId;

    /**
     * 交易单号
     */
    private String transNo;

    /**
     * 上游流水号
     */
    private String channleNo;

    /**
     * 成功金额
     */
    private BigDecimal successAmount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 对账批次
     */
    private String checkBathNo;

    /**
     * 优惠金额
     */
    private BigDecimal promotionAmount;

    /**
     * 摘要(SHA256)
     */
    private String remark;

    /**
     * 服务费（银联）
     */
    private BigDecimal feeService;

    /**
     * 差异类型 CF：重复入库，CZ：冲正单，HD：差异表已存在
     */
    private String differType;

    /**
     * 预留1
     */
    private String field1;

    /**
     * 预留2
     */
    private String field2;

    /**
     * 预留3
     */
    private String field3;

    public Long getBillDifferId() {
        return billDifferId;
    }

    public void setBillDifferId(Long billDifferId) {
        this.billDifferId = billDifferId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public String getCostWay() {
        return costWay;
    }

    public void setCostWay(String costWay) {
        this.costWay = costWay == null ? null : costWay.trim();
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq == null ? null : bankSeq.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getChannelProvider() {
        return channelProvider;
    }

    public void setChannelProvider(String channelProvider) {
        this.channelProvider = channelProvider == null ? null : channelProvider.trim();
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
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

    public String getChannleNo() {
        return channleNo;
    }

    public void setChannleNo(String channleNo) {
        this.channleNo = channleNo == null ? null : channleNo.trim();
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getCheckBathNo() {
        return checkBathNo;
    }

    public void setCheckBathNo(String checkBathNo) {
        this.checkBathNo = checkBathNo == null ? null : checkBathNo.trim();
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getFeeService() {
        return feeService;
    }

    public void setFeeService(BigDecimal feeService) {
        this.feeService = feeService;
    }

    public String getDifferType() {
        return differType;
    }

    public void setDifferType(String differType) {
        this.differType = differType == null ? null : differType.trim();
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
}