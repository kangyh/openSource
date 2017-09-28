package com.heepay.manage.modules.differences.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleProfitBath extends DataEntity<SettleProfitBath> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 133332235432423L;

	private Long settleProfitId;

    private Integer merchantId;

    private String transType;

    private String currency;

    private String profitBath;

    private Date profitDate;

    private Date dealTime;

    private String transNo;

    private BigDecimal profitAmount;

    private BigDecimal fee;

    private String profitStatus;

    private String settleBath;

    
    //自定义属性
    private Date beginOperEndTime;
    private Date endOperEndTime;
    
    
    
    public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Long getSettleProfitId() {
        return settleProfitId;
    }

    public void setSettleProfitId(Long settleProfitId) {
        this.settleProfitId = settleProfitId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getProfitBath() {
        return profitBath;
    }

    public void setProfitBath(String profitBath) {
        this.profitBath = profitBath == null ? null : profitBath.trim();
    }

    public Date getProfitDate() {
        return profitDate;
    }

    public void setProfitDate(Date profitDate) {
        this.profitDate = profitDate;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(String profitStatus) {
        this.profitStatus = profitStatus == null ? null : profitStatus.trim();
    }

    public String getSettleBath() {
        return settleBath;
    }

    public void setSettleBath(String settleBath) {
        this.settleBath = settleBath == null ? null : settleBath.trim();
    }
}