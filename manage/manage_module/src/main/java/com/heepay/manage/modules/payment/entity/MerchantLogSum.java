package com.heepay.manage.modules.payment.entity;

import java.math.BigDecimal;

public class MerchantLogSum {
	//交易类型
	private String type;
	//交易类型-名称
	private String content;
	//借
	private BigDecimal leftAmount=BigDecimal.ZERO;
	//贷
	private BigDecimal rightAmount=BigDecimal.ZERO;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getLeftAmount() {
		return leftAmount;
	}
	public void setLeftAmount(BigDecimal leftAmount) {
		this.leftAmount = leftAmount;
	}
	public BigDecimal getRightAmount() {
		return rightAmount;
	}
	public void setRightAmount(BigDecimal rightAmount) {
		this.rightAmount = rightAmount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
