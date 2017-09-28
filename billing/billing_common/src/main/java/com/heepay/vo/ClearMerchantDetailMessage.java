package com.heepay.vo;

import java.math.BigDecimal;
import java.util.Date;

public class ClearMerchantDetailMessage {
	
	private int         merchantId;
	private String 		transNo;
	private BigDecimal  successAmount;
	private String 		transType;
	private BigDecimal feeAmount;
	private BigDecimal requestAmount;
	private Date successTime;
	
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public BigDecimal getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	public BigDecimal getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(BigDecimal requestAmount) {
		this.requestAmount = requestAmount;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
}
