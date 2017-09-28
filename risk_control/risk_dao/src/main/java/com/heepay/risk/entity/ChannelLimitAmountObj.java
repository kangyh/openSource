/**
 * 
 */
package com.heepay.risk.entity;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 */
public class ChannelLimitAmountObj {
	
	private String channelCode;
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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
