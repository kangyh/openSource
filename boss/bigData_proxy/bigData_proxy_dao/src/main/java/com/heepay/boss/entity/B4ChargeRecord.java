package com.heepay.boss.entity;

import java.math.BigDecimal;
import java.util.Date;

public class B4ChargeRecord {
    private Long b4Id;

    private String merchantNo;   // 商户订单号

    private String transNo;  //交易订单号

    private String merchantId;  //商户号

    private BigDecimal requestAmount; // 订单金额

    private BigDecimal payAmount;//  支付金额

    private String bankcardType;// 银行卡类型

    private String cardholderGender;//  持卡人类别
 
    private String channelCode;//  通道编码

    private String channelTransType;// 渠道交易类型

    private String bankCode;// 银行编码

    private String requestStatus;// 订单状态

    private String requestIp;// 订单Ip

    private String provinceId;// IP来源省Id

    private Date requestTime;// 订单创建时间

    private Date payFinishTime;// 支付完成时间

    private String settlePart;// 结算主题

    private String bankId;// 银行Id

    private String bankShortName;// 银行简称

    private String productCode;// 产品编码

    private String productNumber;//  产品数量

    private String productDescription;//产品描述

    private BigDecimal settleAmount;// 结算金额

    private String settleStatus;// 结算状态

    private String createPerson;// 创建人

    private Date createTime;//创建时间

    private String updatePerson;//更新人

    private Date updateTime;// 更新时间

    private String channleNo;// 上游流水号

    private BigDecimal feeAmount;//手续费

    public Long getB4Id() {
        return b4Id;
    }

    public void setB4Id(Long b4Id) {
        this.b4Id = b4Id;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType == null ? null : bankcardType.trim();
    }

    public String getCardholderGender() {
        return cardholderGender;
    }

    public void setCardholderGender(String cardholderGender) {
        this.cardholderGender = cardholderGender == null ? null : cardholderGender.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelTransType() {
        return channelTransType;
    }

    public void setChannelTransType(String channelTransType) {
        this.channelTransType = channelTransType == null ? null : channelTransType.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus == null ? null : requestStatus.trim();
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp == null ? null : requestIp.trim();
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getPayFinishTime() {
        return payFinishTime;
    }

    public void setPayFinishTime(Date payFinishTime) {
        this.payFinishTime = payFinishTime;
    }

    public String getSettlePart() {
        return settlePart;
    }

    public void setSettlePart(String settlePart) {
        this.settlePart = settlePart == null ? null : settlePart.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName == null ? null : bankShortName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber == null ? null : productNumber.trim();
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription == null ? null : productDescription.trim();
    }

    public BigDecimal getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(BigDecimal settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson == null ? null : updatePerson.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getChannleNo() {
        return channleNo;
    }

    public void setChannleNo(String channleNo) {
        this.channleNo = channleNo == null ? null : channleNo.trim();
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }
}