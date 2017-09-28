package com.heepay.risk.entity;

import java.util.Date;

public class RiskMerchantOrderConversionRatio {
    private Integer id;

    private String merchantId;

    private Integer merchantSucessOrder;

    private Integer merchantTotalOrder;

    private Date beginTime;

    private Date endTime;

    private Date createTime;

    private Integer sucessRatio;

    private String productCode;

    private String productName;

    private String host;
    private String transType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public Integer getMerchantSucessOrder() {
        return merchantSucessOrder;
    }

    public void setMerchantSucessOrder(Integer merchantSucessOrder) {
        this.merchantSucessOrder = merchantSucessOrder;
    }

    public Integer getMerchantTotalOrder() {
        return merchantTotalOrder;
    }

    public void setMerchantTotalOrder(Integer merchantTotalOrder) {
        this.merchantTotalOrder = merchantTotalOrder;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSucessRatio() {
        return sucessRatio;
    }

    public void setSucessRatio(Integer sucessRatio) {
        this.sucessRatio = sucessRatio;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}