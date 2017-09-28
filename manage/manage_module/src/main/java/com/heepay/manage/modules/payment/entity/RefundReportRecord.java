/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.math.BigDecimal;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：支付宝扫码支付Entity
 *
 * 创 建 者： @author tyq
 * 创建时间：
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class RefundReportRecord extends DataEntity<RefundReportRecord> {

	private Long merchantId;
	private String merchantLoginName;
	private String merchantCompany;
    private String merchantSource;
	private String payType;
	private String channelPartner;
    private String bankId;
	private String channelCode;

    private String refundId;
    private String originPaymentId;
    private BigDecimal payAmount;
    private BigDecimal refundAmount;
    private BigDecimal feeAmount;
    private String isFeeBack;


    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    public String getMerchantCompany() {
        return merchantCompany;
    }

    public void setMerchantCompany(String merchantCompany) {
        this.merchantCompany = merchantCompany;
    }

    public String getMerchantSource() {
        return merchantSource;
    }

    public void setMerchantSource(String merchantSource) {
        this.merchantSource = merchantSource;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getChannelPartner() {
        return channelPartner;
    }

    public void setChannelPartner(String channelPartner) {
        this.channelPartner = channelPartner;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getOriginPaymentId() {
        return originPaymentId;
    }

    public void setOriginPaymentId(String originPaymentId) {
        this.originPaymentId = originPaymentId;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getIsFeeBack() {
        return isFeeBack;
    }

    public void setIsFeeBack(String isFeeBack) {
        this.isFeeBack = isFeeBack;
    }

}