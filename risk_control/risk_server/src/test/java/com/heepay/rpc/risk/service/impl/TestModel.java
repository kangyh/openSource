/**
 * 
 */
package com.heepay.rpc.risk.service.impl;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
public class TestModel {
	private String orderId;//主键
	private String merchantOrderNo;//商户订单号
	private String transNo;//交易单号
	private String productType;//产品类型
	private String productName;//产品名称
	private String merchantId;//商户编号
	private String merchantCompany;//商户公司名称
	private String transType;//交易类型
	private BigDecimal transAmount;//交易金额
	private String bankCardType;//银行卡类型
	private String bankCardNo;//银行卡号
	private String bankCardOwnerName;//银行卡所有人姓名
	private String bankCardOwnerIdCard;//银行卡所有人身份证
	private String bankCardOwnerMobile;//银行卡所有者手机号
	private String channelCode;//通道编码
	private String channelName;//通道名称
	private String channelTransType;//通道交易类型
	private String feeType;//手续费类型
	private String bankCardOwnerType;//银行卡所有者类型
	private Long createTime;//创建时间
	private BigDecimal transferBatchAmount;//
	private BigDecimal feeAmount;//手续费
	private String reqIp;
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
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
	public String getBankCardOwnerType() {
		return bankCardOwnerType;
	}
	public void setBankCardOwnerType(String bankCardOwnerType) {
		this.bankCardOwnerType = bankCardOwnerType;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public BigDecimal getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
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

}
