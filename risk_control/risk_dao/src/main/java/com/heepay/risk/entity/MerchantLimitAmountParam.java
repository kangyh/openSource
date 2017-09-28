package com.heepay.risk.entity;
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
public class MerchantLimitAmountParam {
	String merchantId; //商户编号
	String bankType; //分储蓄卡 和 信用卡
	String accountType ; //对公 对私 通用
	String productType; //快捷 网银 批复
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}

	
}
