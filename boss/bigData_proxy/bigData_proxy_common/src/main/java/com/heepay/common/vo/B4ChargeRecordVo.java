package com.heepay.common.vo;

import java.math.BigDecimal;
import java.util.Date;

public class B4ChargeRecordVo {
	
	 private Long b4Id;

	    private String merchantOrderNo;   // 商户订单号

	    private String transNo;  //交易订单号

	    private String merchantId;  //商户号

	    private BigDecimal requestAmount; // 订单金额

	    private BigDecimal payAmount;//  支付金额

	    private String bankcardType;// 银行卡类型

	    private String cardholderGender;//  持卡人类别
	 
	    private String channelCode;//  通道编码

	    private String channelTransType;// 渠道交易类型

	    private String bankCode;// 银行编码

	    private String requestStatus;// 订单状态

	    private String requestIp;// 订单Ip

	    private String provinceId;// IP来源省Id

	    private Date requestTime;// 订单创建时间

	    private Date  payFinishTime;// 支付完成时间

	    private String settlePart;// 结算主题

	    private String bankId;// 银行Id

	    private String bankShortName;// 银行简称

	    private String productCode;// 产品编码

	    private String productNumber;//  产品数量

	    private String productDescription;//产品描述

	    private BigDecimal settleAmount;// 结算金额

	    private String settleStatus;// 结算状态

	    private String createPerson;// 创建人

	    private Date createTime;//创建时间

	    private String updatePerson;//更新人

	    private Date updateTime;// 更新时间

	    private String channleNo;// 上游流水号

	    private BigDecimal feeAmount;//手续费
	    
	    private String merchantName; //商户名称
	    
	    private String transType;  //交易类型
	    
	    private String year;
	    private String month;
	    private String day;

		public Long getB4Id() {
			return b4Id;
		}

		public void setB4Id(Long b4Id) {
			this.b4Id = b4Id;
		}


		public String getTransNo() {
			return transNo;
		}

		public void setTransNo(String transNo) {
			this.transNo = transNo;
		}

		public String getMerchantId() {
			return merchantId;
		}

		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}

		public BigDecimal getRequestAmount() {
			return requestAmount;
		}

		public void setRequestAmount(BigDecimal requestAmount) {
			this.requestAmount = requestAmount;
		}

		public BigDecimal getPayAmount() {
			return payAmount;
		}

		public void setPayAmount(BigDecimal payAmount) {
			this.payAmount = payAmount;
		}

		public String getBankcardType() {
			return bankcardType;
		}

		public void setBankcardType(String bankcardType) {
			this.bankcardType = bankcardType;
		}

		public String getCardholderGender() {
			return cardholderGender;
		}

		public void setCardholderGender(String cardholderGender) {
			this.cardholderGender = cardholderGender;
		}

		public String getChannelCode() {
			return channelCode;
		}

		public void setChannelCode(String channelCode) {
			this.channelCode = channelCode;
		}

		public String getChannelTransType() {
			return channelTransType;
		}

		public void setChannelTransType(String channelTransType) {
			this.channelTransType = channelTransType;
		}

		public String getBankCode() {
			return bankCode;
		}

		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}

		public String getRequestStatus() {
			return requestStatus;
		}

		public void setRequestStatus(String requestStatus) {
			this.requestStatus = requestStatus;
		}

		public String getRequestIp() {
			return requestIp;
		}

		public void setRequestIp(String requestIp) {
			this.requestIp = requestIp;
		}

		public String getProvinceId() {
			return provinceId;
		}

		public void setProvinceId(String provinceId) {
			this.provinceId = provinceId;
		}


		public String getSettlePart() {
			return settlePart;
		}

		public void setSettlePart(String settlePart) {
			this.settlePart = settlePart;
		}

		public String getBankId() {
			return bankId;
		}

		public void setBankId(String bankId) {
			this.bankId = bankId;
		}

		public String getBankShortName() {
			return bankShortName;
		}

		public void setBankShortName(String bankShortName) {
			this.bankShortName = bankShortName;
		}

		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}

		public String getProductNumber() {
			return productNumber;
		}

		public void setProductNumber(String productNumber) {
			this.productNumber = productNumber;
		}

		public String getProductDescription() {
			return productDescription;
		}

		public void setProductDescription(String productDescription) {
			this.productDescription = productDescription;
		}

		public BigDecimal getSettleAmount() {
			return settleAmount;
		}

		public void setSettleAmount(BigDecimal settleAmount) {
			this.settleAmount = settleAmount;
		}

		public String getSettleStatus() {
			return settleStatus;
		}

		public void setSettleStatus(String settleStatus) {
			this.settleStatus = settleStatus;
		}

		public String getCreatePerson() {
			return createPerson;
		}

		public void setCreatePerson(String createPerson) {
			this.createPerson = createPerson;
		}

		

		public String getUpdatePerson() {
			return updatePerson;
		}

		public void setUpdatePerson(String updatePerson) {
			this.updatePerson = updatePerson;
		}

		
		public String getChannleNo() {
			return channleNo;
		}

		public void setChannleNo(String channleNo) {
			this.channleNo = channleNo;
		}

		public BigDecimal getFeeAmount() {
			return feeAmount;
		}

		public void setFeeAmount(BigDecimal feeAmount) {
			this.feeAmount = feeAmount;
		}

		public String getMerchantName() {
			return merchantName;
		}

		public void setMerchantName(String merchantName) {
			this.merchantName = merchantName;
		}

		public String getMerchantOrderNo() {
			return merchantOrderNo;
		}

		public void setMerchantOrderNo(String merchantOrderNo) {
			this.merchantOrderNo = merchantOrderNo;
		}

		public String getYear() {
			return year;
		}

		public void setYear(String year) {
			this.year = year;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public Date getRequestTime() {
			return requestTime;
		}

		public void setRequestTime(Date requestTime) {
			this.requestTime = requestTime;
		}

		public Date getPayFinishTime() {
			return payFinishTime;
		}

		public void setPayFinishTime(Date payFinishTime) {
			this.payFinishTime = payFinishTime;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
		}

		
}
