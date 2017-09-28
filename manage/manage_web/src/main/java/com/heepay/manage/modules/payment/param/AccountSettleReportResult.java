package com.heepay.manage.modules.payment.param;

import java.math.BigDecimal;

/**
 * 描    述：
 *
 * 创 建 者： 张俊新
 * 创建时间： 2016年8月19日 下午3:30:28
 * 创建描述： 2017/6/27 20:06
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class AccountSettleReportResult {

    private String merchantId;
    private String merchantName;
    private String accountId;
    private String accountName;
    private String merchantType;
    private String transType;
    private String createDate;
    private String accountExplain;
    private BigDecimal crebitAmountSum = BigDecimal.ZERO;
    private BigDecimal deebitAmountSum =BigDecimal.ZERO;

    private BigDecimal allAccountCrebitAmountSum =BigDecimal.ZERO;
    private BigDecimal allAccountDeebitAmountSum =BigDecimal.ZERO;


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public BigDecimal getCrebitAmountSum() {
        return crebitAmountSum;
    }

    public void setCrebitAmountSum(BigDecimal crebitAmountSum) {
        this.crebitAmountSum = crebitAmountSum;
    }

    public BigDecimal getDeebitAmountSum() {
        return deebitAmountSum;
    }

    public void setDeebitAmountSum(BigDecimal deebitAmountSum) {
        this.deebitAmountSum = deebitAmountSum;
    }

    public BigDecimal getAllAccountCrebitAmountSum() {
        return allAccountCrebitAmountSum;
    }

    public void setAllAccountCrebitAmountSum(BigDecimal allAccountCrebitAmountSum) {
        this.allAccountCrebitAmountSum = allAccountCrebitAmountSum;
    }

    public BigDecimal getAllAccountDeebitAmountSum() {
        return allAccountDeebitAmountSum;
    }

    public void setAllAccountDeebitAmountSum(BigDecimal allAccountDeebitAmountSum) {
        this.allAccountDeebitAmountSum = allAccountDeebitAmountSum;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getAccountExplain() {
        return accountExplain;
    }

    public void setAccountExplain(String accountExplain) {
        this.accountExplain = accountExplain;
    }
}
