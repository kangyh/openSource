package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ClearingChannelRecordHis {
    /**
     * 主键
     */
    private Long hisId;

    /**
     * 清算ID
     */
    private Long clearingId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 交易日期
     */
    private Date payTime;

    /**
     * 币种(156:人民币)
     */
    private String currency;

    /**
     * 支付订单号
     */
    private String paymentId;

    /**
     * 支付订单号
     */
    private String paymentIdOld;

    /**
     * 交易订单号（订单号）
     */
    private String transNo;

    /**
     * 原交易订单号（订单号）
     */
    private String transNoOld;

    /**
     * 交易金额
     */
    private BigDecimal successAmount;

    /**
     * 通道对账日期
     */
    private Date channelTime;

    /**
     * 会计时间
     */
    private Date checkTime;

    /**
     * 清算日期
     */
    private Date settleTime;

    /**
     * 清算流水号
     */
    private String settleNo;

    /**
     * 订单应结算日期
     */
    private Date settleTimePlan;

    /**
     * 订单结算周期
     */
    private String settleCyc;

    /**
     * 结算批次号
     */
    private String settleBath;

    /**
     * 成本结算日期
     */
    private Date costTime;

    /**
     * 成本
     */
    private BigDecimal costAmount;

    /**
     * 手续费扣除方式
     */
    private String costWay;

    /**
     * 成本结算周期
     */
    private String costSettleCyc;

    /**
     * 对账状态（N：未对账 Y：已对账）
     */
    private String checkStatus;

    /**
     * 对账标记(QSTS：平账  
 QFTS：清结算系统失败，上游通道成功
 QSTF：清结算系统成功，上游通道失败
 QMTM：清结算系统与上游金额不一致
 QMQM：清结算系统内部金额不一致  QSQT:其他)
     */
    private String checkFlg;

    /**
     * 结算状态（N：未结算，D：结算中 Y：已结算）
     */
    private String settleStatus;

    /**
     * 处理结束时间
     */
    private Date finishTime;

    /**
     * 对账批次号，生成规则类似结算批次号：以DZ开头（替换19）
     */
    private String checkBath;

    /**
     * 已对账次数
     */
    private Integer checkNum;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否归档unzip(默认)
     */
    private String isZip;

    /**
     * 通道提供者
     */
    private String channelProvider;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行流水号
     */
    private String bankSeq;

    /**
     * 归档日期
     */
    private Date dateZip;

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    public Long getClearingId() {
        return clearingId;
    }

    public void setClearingId(Long clearingId) {
        this.clearingId = clearingId;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getPaymentIdOld() {
        return paymentIdOld;
    }

    public void setPaymentIdOld(String paymentIdOld) {
        this.paymentIdOld = paymentIdOld == null ? null : paymentIdOld.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getTransNoOld() {
        return transNoOld;
    }

    public void setTransNoOld(String transNoOld) {
        this.transNoOld = transNoOld == null ? null : transNoOld.trim();
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public Date getChannelTime() {
        return channelTime;
    }

    public void setChannelTime(Date channelTime) {
        this.channelTime = channelTime;
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

    public String getSettleNo() {
        return settleNo;
    }

    public void setSettleNo(String settleNo) {
        this.settleNo = settleNo == null ? null : settleNo.trim();
    }

    public Date getSettleTimePlan() {
        return settleTimePlan;
    }

    public void setSettleTimePlan(Date settleTimePlan) {
        this.settleTimePlan = settleTimePlan;
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

    public Date getCostTime() {
        return costTime;
    }

    public void setCostTime(Date costTime) {
        this.costTime = costTime;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public String getCostWay() {
        return costWay;
    }

    public void setCostWay(String costWay) {
        this.costWay = costWay == null ? null : costWay.trim();
    }

    public String getCostSettleCyc() {
        return costSettleCyc;
    }

    public void setCostSettleCyc(String costSettleCyc) {
        this.costSettleCyc = costSettleCyc == null ? null : costSettleCyc.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getCheckFlg() {
        return checkFlg;
    }

    public void setCheckFlg(String checkFlg) {
        this.checkFlg = checkFlg == null ? null : checkFlg.trim();
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

    public String getCheckBath() {
        return checkBath;
    }

    public void setCheckBath(String checkBath) {
        this.checkBath = checkBath == null ? null : checkBath.trim();
    }

    public Integer getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(Integer checkNum) {
        this.checkNum = checkNum;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsZip() {
        return isZip;
    }

    public void setIsZip(String isZip) {
        this.isZip = isZip == null ? null : isZip.trim();
    }

    public String getChannelProvider() {
        return channelProvider;
    }

    public void setChannelProvider(String channelProvider) {
        this.channelProvider = channelProvider == null ? null : channelProvider.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankSeq() {
        return bankSeq;
    }

    public void setBankSeq(String bankSeq) {
        this.bankSeq = bankSeq == null ? null : bankSeq.trim();
    }

    public Date getDateZip() {
        return dateZip;
    }

    public void setDateZip(Date dateZip) {
        this.dateZip = dateZip;
    }
}