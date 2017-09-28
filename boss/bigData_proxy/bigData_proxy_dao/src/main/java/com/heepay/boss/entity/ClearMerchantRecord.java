package com.heepay.boss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ClearMerchantRecord {
    /**
     * 清算ID
     */
    private Long clearingId;

    /**
     * 用户编码
     */
    private Integer merchantId;

    /**
     * 用户类型
     */
    private String merchantType;

    /**
     * 业务类型（交易类型）(product表code字段)
     */
    private String productCode;

    /**
     * 币种(156:人民币)
     */
    private String currency;

    /**
     * 交易订单号（支付单号）
     */
    private String transNo;

    /**
     * 原交易订单号（支付单号）
     */
    private String transNoOld;

    /**
     * 交易金额
     */
    private BigDecimal requestAmount;

    /**
     * 成功支付时间(yyyy-MM-dd hh:mi:ss)
     */
    private Date successTime;

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
     * 订单应结算金额
     */
    private BigDecimal settleAmountPlan;

    /**
     * 订单结算周期
     */
    private String settleCyc;

    /**
     * 结算批次号
     */
    private String settleBath;

    /**
     * 手续费结算日期
     */
    private Date feeTime;

    /**
     * 手续费
     */
    private BigDecimal fee;

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
     * 对账标记(QSTS：平账  
 QFTS：清结算系统失败，上游通道成功
 QSTF：清结算系统成功，上游通道失败
 QMTM：清结算系统与上游金额不一致
 QMQM：清结算系统内部金额不一致   QSQT:其他)
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
     * 是否分润（Y:是 N:否）
     */
    private String isProfit;

    /**
     * 用户订单号
     */
    private String merchantOrderNo;

    /**
     * 交易发起时间
     */
    private Date busiTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否归档，unzip(默认)
     */
    private String isZip;

    /**
     * 代理名称
     */
    private String agentName;

    /**
     * 代理商编码
     */
    private Long agentCode;

    /**
     * '银行卡类型"UNKNOW":"未知"  "SAVING":"储蓄卡"  "CREDIT":"信用卡" ',
     */
    private String bankcardType;

    /**
     * 产品编码名称
     */
    private String productName;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 账户编码
     */
    private String accountNo;

    /**
     * 支付发起时间
     */
    private Date payTime;

    public Long getClearingId() {
        return clearingId;
    }

    public void setClearingId(Long clearingId) {
        this.clearingId = clearingId;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
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

    public Date getSettleTimePlan() {
        return settleTimePlan;
    }

    public void setSettleTimePlan(Date settleTimePlan) {
        this.settleTimePlan = settleTimePlan;
    }

    public BigDecimal getSettleAmountPlan() {
        return settleAmountPlan;
    }

    public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
        this.settleAmountPlan = settleAmountPlan;
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

    public Date getFeeTime() {
        return feeTime;
    }

    public void setFeeTime(Date feeTime) {
        this.feeTime = feeTime;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
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

    public String getIsProfit() {
        return isProfit;
    }

    public void setIsProfit(String isProfit) {
        this.isProfit = isProfit == null ? null : isProfit.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public Date getBusiTime() {
        return busiTime;
    }

    public void setBusiTime(Date busiTime) {
        this.busiTime = busiTime;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    public Long getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(Long agentCode) {
        this.agentCode = agentCode;
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType == null ? null : bankcardType.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}