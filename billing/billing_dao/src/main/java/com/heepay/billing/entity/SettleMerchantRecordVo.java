package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * 描 述：自定义用户侧接收查询数据对象
 *
 * 创 建 者：chenyanming 
 * 创建时间： 2016年9月10日下午3:48:06 
 * 创建描述：
 * 
 * 修 改 者： 
 * 修改时间： 
 * 修改描述：
 * 
 * 审 核 者：
 *  
 * 审核时间： 
 * 审核描述：
 *
 */
public class SettleMerchantRecordVo {

	private String merchantId;   //用户编码
	//private String productCode;  //业务类型（产品编码）
	private String transType;
	private String merchantType; //用户类型
	private int payNum;          //交易总笔数
	private BigDecimal requestAmount;
	private String settleBath;
	private Date finishTime;
	private String settleStatus;
	
	private Date settleTime;
	private Date feeTime;
	private String feeWay;
	
	
	
	private String settleCyc;
	private String transNo;
	private String checkStatus;
	private BigDecimal fee;
	private Date checkTime;
	private BigDecimal totalAmount;
	private BigDecimal totalFee;
	private BigDecimal settleAmount;
	private BigDecimal settleAmountPlan;
	
	private String merchantName;
	
	private Long clearingId;
	
	
	
	
	/*
	 * private Date checkTime; private Date settleTime; private String transNo;
	 * // 交易订单号 private String settleCyc; // 交易结算周期 private Date costTime; //
	 * 成本结算日期 private String costSettleCyc; // 成本结算周期
	 */

	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public Date getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}

	public Date getFeeTime() {
		return feeTime;
	}

	public void setFeeTime(Date feeTime) {
		this.feeTime = feeTime;
	}

	public String getFeeWay() {
		return feeWay;
	}

	public void setFeeWay(String feeWay) {
		this.feeWay = feeWay;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getSettleCyc() {
		return settleCyc;
	}

	public void setSettleCyc(String settleCyc) {
		this.settleCyc = settleCyc;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
	}

	public BigDecimal getSettleAmountPlan() {
		return settleAmountPlan;
	}

	public void setSettleAmountPlan(BigDecimal settleAmountPlan) {
		this.settleAmountPlan = settleAmountPlan;
	}

	public String getSettleStatus() {
		return settleStatus;
	}

	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

//	public String getProductCode() {
//		return productCode;
//	}
//
//	public void setProductCode(String productCode) {
//		this.productCode = productCode;
//	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public int getPayNum() {
		return payNum;
	}

	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}

	

	public BigDecimal getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(BigDecimal requestAmount) {
		this.requestAmount = requestAmount;
	}

	public String getSettleBath() {
		return settleBath;
	}

	public void setSettleBath(String settleBath) {
		this.settleBath = settleBath;
	}

	public Long getClearingId() {
		return clearingId;
	}

	public void setClearingId(Long clearingId) {
		this.clearingId = clearingId;
	}

}
