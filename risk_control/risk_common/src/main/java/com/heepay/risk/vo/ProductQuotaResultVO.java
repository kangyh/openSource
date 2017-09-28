package com.heepay.risk.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.heepay.enums.risk.ProductQuotaType;

public class ProductQuotaResultVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6443967547902647468L;
	private boolean isSuccess;
	private  BigDecimal  quotaValue;
    private  ProductQuotaType productQuotaType;
    private  int status;
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public BigDecimal getQuotaValue() {
		return quotaValue;
	}
	public void setQuotaValue(BigDecimal quotaValue) {
		this.quotaValue = quotaValue;
	}
	public ProductQuotaType getProductQuotaType() {
		return productQuotaType;
	}
	public void setProductQuotaType(ProductQuotaType productQuotaType) {
		this.productQuotaType = productQuotaType;
	}
	public String getRemark()
	{
		return productQuotaType.getContent()+"限额"+quotaValue;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
