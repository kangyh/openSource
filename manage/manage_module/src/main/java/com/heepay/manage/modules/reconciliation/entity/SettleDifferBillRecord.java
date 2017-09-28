package com.heepay.manage.modules.reconciliation.entity;

import com.heepay.manage.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @方法说明：异常明细表
 * @时间： 2017-07-28 14:05
 * @创建人：wangl
 */
public class SettleDifferBillRecord extends DataEntity<SettleDifferBillRecord> {

    private static final long serialVersionUID = 1L;

    private Long billDifferId;

    private String channelCode;

    private String channelName;

    private String channelType;

    private String transType;

    private Date successTime;

    private String costWay;

    private String bankSeq;

    private String bankName;

    private String bankCode;

    private String channelProvider;

    private Date saveTime;

    private String paymentId;

    private String transNo;

    private String channleNo;

    private BigDecimal successAmount;

    private BigDecimal fee;

    private String checkBathNo;

    private BigDecimal promotionAmount;

    private String remark;

    private BigDecimal feeService;

    private String differType;

    private String field1;

    private String field2;

    private String field3;

    //非映射字段
    private Date beginOperEndTime;

    private Date endOperEndTime;

    public Date getBeginOperEndTime() {
        return beginOperEndTime;
    }

    public void setBeginOperEndTime(Date beginOperEndTime) {
        this.beginOperEndTime = beginOperEndTime;
    }

    public Date getEndOperEndTime() {
        return endOperEndTime;
    }

    public void setEndOperEndTime(Date endOperEndTime) {
        this.endOperEndTime = endOperEndTime;
    }

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