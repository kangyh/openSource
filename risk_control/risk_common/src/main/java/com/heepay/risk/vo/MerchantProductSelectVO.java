package com.heepay.risk.vo;

public class MerchantProductSelectVO {
   private String status;
   private String productCode;
   private int merchantId;
   private String accountType;
   private String bankcardType;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getProductCode() {
	return productCode;
}
public void setProductCode(String productCode) {
	this.productCode = productCode;
}
public int getMerchantId() {
	return merchantId;
}
public void setMerchantId(int merchantId) {
	this.merchantId = merchantId;
}
public String getAccountType() {
	return accountType;
}
public void setAccountType(String accountType) {
	this.accountType = accountType;
}
public String getBankcardType() {
	return bankcardType;
}
public void setBankcardType(String bankcardType) {
	this.bankcardType = bankcardType;
}
}
