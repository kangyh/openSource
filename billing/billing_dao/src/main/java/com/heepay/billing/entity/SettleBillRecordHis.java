package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleBillRecordHis {
    /**
     * 历史表主键
     */
    private Long hisId;

    /**
     * 主键
     */
    private Integer billDetailId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 入库时间
     */
    private Date saveTime;

    /**
     * 支付订单号
     */
    private String paymentId;

    /**
     * 上游流水号
     */
    private String channleNo;

    /**
     * 支付资金
     */
    private Long successAmount;

    /**
     * 手续费
     */
    private Long fee;

    /**
     * 账单状态
     */
    private String billStatus;

    /**
     * 对账批次
     */
    private String checkBathNo;

    /**
     * 归档日期
     */
    private Date dateZip;

    /**
     * 服务费（银联）
     */
    private BigDecimal feeService;

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

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public Integer getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Integer billDetailId) {
        this.billDetailId = billDetailId;
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

    public String getChannleNo() {
        return channleNo;
    }

    public void setChannleNo(String channleNo) {
        this.channleNo = channleNo == null ? null : channleNo.trim();
    }

    public Long getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(Long successAmount) {
        this.successAmount = successAmount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public String getCheckBathNo() {
        return checkBathNo;
    }

    public void setCheckBathNo(String checkBathNo) {
        this.checkBathNo = checkBathNo == null ? null : checkBathNo.trim();
    }

    public Date getDateZip() {
        return dateZip;
    }

    public void setDateZip(Date dateZip) {
        this.dateZip = dateZip;
    }

    public BigDecimal getFeeService() {
        return feeService;
    }

    public void setFeeService(BigDecimal feeService) {
        this.feeService = feeService;
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