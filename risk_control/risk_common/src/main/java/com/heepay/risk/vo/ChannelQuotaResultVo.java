package com.heepay.risk.vo;

import java.math.BigDecimal;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年5月27日 下午3:42:25
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
public class ChannelQuotaResultVo {
	private int status;
	private String message;
	private BigDecimal dayAmount;
	private BigDecimal monthAmount;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BigDecimal getDayAmount() {
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

}

 