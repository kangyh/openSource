package com.heepay.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RiskIncomeQuota {
    private Long quotaId;

    private Integer merchantId;

    private BigDecimal dayIncomeQuotaAmount;

    private String incomeDirection;

    private String createAuthor;

    private Date createTime;

    private String updateAuthor;

    private Date updateTime;

    private String status;

    public Long getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(Long quotaId) {
        this.quotaId = quotaId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getDayIncomeQuotaAmount() {
        return dayIncomeQuotaAmount;
    }

    public void setDayIncomeQuotaAmount(BigDecimal dayIncomeQuotaAmount) {
        this.dayIncomeQuotaAmount = dayIncomeQuotaAmount;
    }

    public String getIncomeDirection() {
        return incomeDirection;
    }

    public void setIncomeDirection(String incomeDirection) {
        this.incomeDirection = incomeDirection == null ? null : incomeDirection.trim();
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}