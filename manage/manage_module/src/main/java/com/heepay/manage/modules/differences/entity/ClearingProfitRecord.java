package com.heepay.manage.modules.differences.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class ClearingProfitRecord extends DataEntity<ClearingProfitRecord> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long profitId;
	

    private Integer merchantId;

    private String transNo;

    private BigDecimal profitAmount;

    private BigDecimal profitAmountPlan;

    private BigDecimal profitFee;

    private String feeWay;

    private String profitBath;

    private Date profitTime;

    private String profitStatus;
    
    private Long shareDetailId;
    private String transType;
    
    //自定义属性
    private Date beginOperEndTime;
    private Date endOperEndTime;
    
    
    
    public Long getShareDetailId() {
		return shareDetailId;
	}

	public void setShareDetailId(Long shareDetailId) {
		this.shareDetailId = shareDetailId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

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

	public Long getProfitId() {
        return profitId;
    }

    public void setProfitId(Long profitId) {
        this.profitId = profitId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    public BigDecimal getProfitAmountPlan() {
        return profitAmountPlan;
    }

    public void setProfitAmountPlan(BigDecimal profitAmountPlan) {
        this.profitAmountPlan = profitAmountPlan;
    }

    public BigDecimal getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(BigDecimal profitFee) {
        this.profitFee = profitFee;
    }

    public String getFeeWay() {
        return feeWay;
    }

    public void setFeeWay(String feeWay) {
        this.feeWay = feeWay == null ? null : feeWay.trim();
    }

    public String getProfitBath() {
        return profitBath;
    }

    public void setProfitBath(String profitBath) {
        this.profitBath = profitBath == null ? null : profitBath.trim();
    }

    public Date getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(Date profitTime) {
        this.profitTime = profitTime;
    }

    public String getProfitStatus() {
        return profitStatus;
    }

    public void setProfitStatus(String profitStatus) {
        this.profitStatus = profitStatus == null ? null : profitStatus.trim();
    }
}