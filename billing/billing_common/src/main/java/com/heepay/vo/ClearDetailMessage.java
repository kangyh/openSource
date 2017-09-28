package com.heepay.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * 描    述：清算明细类，包括：支付单号，金额
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月20日下午6:09:48 
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
public class ClearDetailMessage {
	private String paymentId;
	private BigDecimal successAmount;
	private String transType;
	private BigDecimal costAmount; // 手续费
	private String transNo; // 交易单号
	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public BigDecimal getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public BigDecimal getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}
	
	
}
