package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SettleDifferRecord {
    /**
     * 差异单ID
     */
    private Long differId;

    /**
     * 商户编码
     */
    private Integer merchantId;

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 交易单号
     */
    private String transNo;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 支付单金额（通道金额）
     */
    private BigDecimal successAmount;

    /**
     * 成功金额（订单金额）
     */
    private BigDecimal requestAmount;

    /**
     * 对账批次
     */
    private String checkBathNo;

    /**
     * 结算批次
     */
    private String settleBath;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 产品编码(product表code字段)
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 通道流水（上游流水）
     */
    private String channleNo;

    /**
     * 差异类型（CK：长款   DK：短款   JE：金额   QT：其他   WZ:未知）
     */
    private String differType;

    /**
     * 处理结果（N：未处理   Y：处理完 D:处理中）
     */
    private String handleResult;

    /**
     * 处理意见（BD：补单 CD：撤单   QX：取消差异 CL:未处理 WZ:未知）
     */
    private String handleMessage;

    /**
     * 差错日期
     */
    private Date errorDate;

    /**
     * 操作人
     */
    private String updateAuthor;

    /**
     * 操作日期
     */
    private Date operationDate;

    /**
     * 手续费
     */
    private BigDecimal feeAmount;

    /**
     * 成本
     */
    private BigDecimal costAmount;

    /**
     * 订单应结算金额（订单金额，交易金额银行返回的金额  - 手续费）
     */
    private BigDecimal settleAmountPlan;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * '支付状态"PREPAY 待支付"  "PAYING 处理中" "FAILED 支付失败" "TIMOUT 支付超时"  "SUCCES 支付成功'
     */
    private String transStatus;

    /**
     * 记账标记  "N  未记账" "Y 已记账"',
     */
    private String isBill;

    /**
     * 是否分润（Y:是 N:否）
     */
    private String isProfit;

    /**
     * 商户交易订单号
     */
    private String merchantOrderNo;

    /**
     * 成功支付时间
     */
    private Date successTime;

    /**
     * 支付发起时间
     */
    private Date payTime;

    /**
     * 交易发起时间
     */
    private Date busiTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 归是否归档unzip(默认)
     */
    private String isZip;

    /**
     * 代理名称
     */
    private String agentName;

    /**
     * 代理代码
     */
    private Long agentCode;

    /**
     * 手续费扣除方式
     */
    private String feeWay;

    /**
     * 银行卡类型"UNKNOW":"未知"  "SAVING":"储蓄卡"  "CREDIT":"信用卡"   '
     */
    private String bankcardType;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 银行流水
     */
    private String bankSeq;

    /**
     * 银行流水
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
     * 订单创建时间
     */
    private String payType;

    /**
     * 账户编码
     */
    private String accountNo;

    public Long getDifferId() {
        return differId;
    }

    public void setDifferId(Long differId) {
        this.differId = differId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getCheckBathNo() {
        return checkBathNo;
    }

    public void setCheckBathNo(String checkBathNo) {
        this.checkBathNo = checkBathNo == null ? null : checkBathNo.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getChannleNo() {
        return channleNo;
    }

    public void setChannleNo(String channleNo) {
        this.channleNo = channleNo == null ? null : channleNo.trim();
    }

    public String getDifferType() {
        return differType;
    }

    public void setDifferType(String differType) {
        this.differType = differType == null ? null : differType.trim();
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult == null ? null : handleResult.trim();
    }

    public String getHandleMessage() {
        return handleMessage;
    }

    public void setHandleMessage(String handleMessage) {
        this.handleMessage = handleMessage == null ? null : handleMessage.trim();
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public BigDecimal getSettleAmountPlan() {
        return settleAmountPlan;
    }

    public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
        this.settleAmountPlan = settleAmountPlan;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus == null ? null : transStatus.trim();
    }

    public String getIsBill() {
        return isBill;
    }

    public void setIsBill(String isBill) {
        this.isBill = isBill == null ? null : isBill.trim();
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

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType == null ? null : bankcardType.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }
}