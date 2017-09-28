package com.heepay.risk.entity;

import java.math.BigDecimal;

/**
 * 
* 
* 描    述：
*
* 创 建 者：   王英雷
* 创建时间： 2016年11月22日 上午11:47:22 
* 创建描述：返回商户限额信息

* 
*        
 */
public class MerchantLimitAmountObj {

	private String merchantId;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public BigDecimal  getDayAmount() {
		return dayAmount;
	}
	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}
	public BigDecimal getMonthAmount() {
		return monthAmount;
	}
	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}
	private BigDecimal dayAmount;
	private BigDecimal monthAmount;
	
}
