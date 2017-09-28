package com.heepay.manage.modules.riskManage.util;

import java.math.BigDecimal;
import java.util.Date;

public class RiskTransOrderVo {
	
	 private String orderId;
	private String merchantOrderNo;
	private String transNo;
	private String productType;
	private String productName;
	private String merchantId;
	private String merchantCompany;
	private String transType;
	private BigDecimal transAmount;
	private String bankCardType;
	private String bankCardNo;
	private String bankCardOwnerName;
	private String bankCardOwnerIdCard;
	private String bankCardOwnerMobile;
	private String channelCode;
	private String channelName;
	private String channelTransType;
	private String feeType;
	private String bankCardOwnerType;
	private Long createTime;
	private BigDecimal transferBatchAmount;
	private BigDecimal feeAmount;
	private String reqIp;
	
	
	private Date beginOperEndTime;
	private Date endOperEndTime;
	private String pagefrom;
	private String pagesize;

	private Date createDate;
	private Date beginTime;
	private Date endTime;
	
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantCompany() {
		return merchantCompany;
	}
	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardOwnerName() {
		return bankCardOwnerName;
	}
	public void setBankCardOwnerName(String bankCardOwnerName) {
		this.bankCardOwnerName = bankCardOwnerName;
	}
	public String getBankCardOwnerIdCard() {
		return bankCardOwnerIdCard;
	}
	public void setBankCardOwnerIdCard(String bankCardOwnerIdCard) {
		this.bankCardOwnerIdCard = bankCardOwnerIdCard;
	}
	public String getBankCardOwnerMobile() {
		return bankCardOwnerMobile;
	}
	public void setBankCardOwnerMobile(String bankCardOwnerMobile) {
		this.bankCardOwnerMobile = bankCardOwnerMobile;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelTransType() {
		return channelTransType;
	}
	public void setChannelTransType(String channelTransType) {
		this.channelTransType = channelTransType;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getTransferBatchAmount() {
		return transferBatchAmount;
	}
	public void setTransferBatchAmount(BigDecimal transferBatchAmount) {
		this.transferBatchAmount = transferBatchAmount;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
	public String getPagefrom() {
		return pagefrom;
	}
	public void setPagefrom(String pagefrom) {
		this.pagefrom = pagefrom;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getBankCardOwnerType() {
		return bankCardOwnerType;
	}
	public void setBankCardOwnerType(String bankCardOwnerType) {
		this.bankCardOwnerType = bankCardOwnerType;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
