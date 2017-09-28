package com.heepay.billing.entity;

public class ClearingException {
    /**
     * 主键
     */
    private Long clearId;

    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 支付单号
     */
    private String paymentId;

    /**
     * 原支付单号
     */
    private String paymentIdOld;

    /**
     * 订单金额
     */
    private String payAmount;

    /**
     * 订单结算周期
     */
    private String settleCyc;

    /**
     * 手续费扣除方式
     */
    private String costWay;

    /**
     * 交易类型
     */
    private String transType;

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
     * 商户编码
     */
    private String merchantId;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 用户类型
     */
    private String merchantType;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 币种(156:人民币)
     */
    private String currency;

    /**
     * 交易订单号
     */
    private String transNo;

    private String paymentid;

    /**
     * 用户订单号
     */
    private String merchantOrderNo;

    /**
     * 原交易订单号
     */
    private String transNoOld;

    /**
     * 交易金额
     */
    private String requestAmount;

    /**
     * 手续费
     */
    private String fee;

    /**
     * 支付发起时间
     */
    private String payTime;

    /**
     * 成功支付时间(yyyy-MM-dd hh:mi:ss)
     */
    private String successTime;

    /**
     * 手续费扣除方式
     */
    private String feeWay;

    /**
     * 订单创建时间
     */
    private String createTime;

    /**
     * '银行卡类型"UNKNOW":"未知"  "SAVING":"储蓄卡"  "CREDIT":"信用卡" ',
     */
    private String bankcardType;

    /**
     * 支付类型
     */
    private String payType;

    /**
     * 01:存在空值 02:商户侧处理失败 03:通道侧处理失败
     */
    private String status;

    private String field1;

    private String field2;

    public Long getClearId() {
        return clearId;
    }

    public void setClearId(Long clearId) {
        this.clearId = clearId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
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

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount == null ? null : payAmount.trim();
    }

    public String getSettleCyc() {
        return settleCyc;
    }

    public void setSettleCyc(String settleCyc) {
        this.settleCyc = settleCyc == null ? null : settleCyc.trim();
    }

    public String getCostWay() {
        return costWay;
    }

    public void setCostWay(String costWay) {
        this.costWay = costWay == null ? null : costWay.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
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

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid == null ? null : paymentid.trim();
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
    }

    public String getTransNoOld() {
        return transNoOld;
    }

    public void setTransNoOld(String transNoOld) {
        this.transNoOld = transNoOld == null ? null : transNoOld.trim();
    }

    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount == null ? null : requestAmount.trim();
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee == null ? null : fee.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime == null ? null : successTime.trim();
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType == null ? null : bankcardType.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
}