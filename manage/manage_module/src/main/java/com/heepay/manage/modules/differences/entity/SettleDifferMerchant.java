package com.heepay.manage.modules.differences.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class SettleDifferMerchant extends DataEntity<SettleDifferMerchant> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long differMerbillId;

    private Integer merchantId;

    private String transType;

    private String currency;

    private String errorBath;

    private Date errorDate;

    private Date dealTime;

    private String transNo;

    private BigDecimal requestAmount;

    private BigDecimal settleAmountPlan;

    private BigDecimal fee;

    private String billType;

    private String errorStatus;
    
    private String checkStatus;
    
    private String checkMessage;

    private String updateAuthor;
    
    //自定义属性
    private Date beginOperEndTime;
    private Date endOperEndTime;
    private String status;//用于页面操作判断
    private String flag;
    
    
    
    
    public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckMessage() {
		return checkMessage;
	}

	public void setCheckMessage(String checkMessage) {
		this.checkMessage = checkMessage;
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

	public Long getDifferMerbillId() {
        return differMerbillId;
    }

    public void setDifferMerbillId(Long differMerbillId) {
        this.differMerbillId = differMerbillId;
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

    public String getErrorBath() {
        return errorBath;
    }

    public void setErrorBath(String errorBath) {
        this.errorBath = errorBath == null ? null : errorBath.trim();
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
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

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public BigDecimal getSettleAmountPlan() {
        return settleAmountPlan;
    }

    public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
        this.settleAmountPlan = settleAmountPlan;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus == null ? null : errorStatus.trim();
    }
}