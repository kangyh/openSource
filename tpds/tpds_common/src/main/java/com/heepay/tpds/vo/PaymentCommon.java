package com.heepay.tpds.vo;

import org.springframework.stereotype.Component;

/**
 * 描 述：调用交易系统的公共参数
 * <p>
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月13日 上午10:30:58
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Component
public class PaymentCommon {
    private String paymentDepositUrl;
    private String paymentDepositQueryUrl;
    private String paymentB2CChargeUrl;
    private String paymentB2CPayUrl;
    private String paymentBankListUrl;
    private String paymentQuickpayUrl;
    private String paymentQuickpayConfirmUrl;
    private String paymentSignqueryUrl;
    private String paymentTransqueryUrl;

    public String getPaymentB2CChargeUrl() {
        return paymentB2CChargeUrl;
    }

    public void setPaymentB2CChargeUrl(String paymentB2CChargeUrl) {
        this.paymentB2CChargeUrl = paymentB2CChargeUrl;
    }

    public String getPaymentB2CPayUrl() {
        return paymentB2CPayUrl;
    }

    public void setPaymentB2CPayUrl(String paymentB2CPayUrl) {
        this.paymentB2CPayUrl = paymentB2CPayUrl;
    }

    public String getPaymentBankListUrl() {
        return paymentBankListUrl;
    }

    public void setPaymentBankListUrl(String paymentBankListUrl) {
        this.paymentBankListUrl = paymentBankListUrl;
    }

    public String getPaymentQuickpayUrl() {
        return paymentQuickpayUrl;
    }

    public void setPaymentQuickpayUrl(String paymentQuickpayUrl) {
        this.paymentQuickpayUrl = paymentQuickpayUrl;
    }

    public String getPaymentQuickpayConfirmUrl() {
        return paymentQuickpayConfirmUrl;
    }

    public void setPaymentQuickpayConfirmUrl(String paymentQuickpayConfirmUrl) {
        this.paymentQuickpayConfirmUrl = paymentQuickpayConfirmUrl;
    }

    public String getPaymentSignqueryUrl() {
        return paymentSignqueryUrl;
    }

    public void setPaymentSignqueryUrl(String paymentSignqueryUrl) {
        this.paymentSignqueryUrl = paymentSignqueryUrl;
    }

    public String getPaymentTransqueryUrl() {
        return paymentTransqueryUrl;
    }

    public void setPaymentTransqueryUrl(String paymentTransqueryUrl) {
        this.paymentTransqueryUrl = paymentTransqueryUrl;
    }

    public String getPaymentDepositUrl() {
        return paymentDepositUrl;
    }

    public void setPaymentDepositUrl(String paymentDepositUrl) {
        this.paymentDepositUrl = paymentDepositUrl;
    }

    public String getPaymentDepositQueryUrl() {
        return paymentDepositQueryUrl;
    }

    public void setPaymentDepositQueryUrl(String paymentDepositQueryUrl) {
        this.paymentDepositQueryUrl = paymentDepositQueryUrl;
    }
}

 