package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付请求Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月13日下午3:08:16 
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
public class QuickPayVo {
	// 商户的ID
	private String merchantId;
	// 商户交易号
	private String merchantOrderNo;
	// 用户号
	private String merchantUserId;
	// 交易金额
	private String payAmount;
	// 请求时间
	private String requestTime;
	// 版本号
	private String version;
	// 产品编码
	private String productCode;
	// 授权码
	private String hyAuthCode;
	// 持卡人姓名
	private String ownerName;
	// 持卡人身份证号
	private String ownerCertNo;
	// 持卡人手机号
	private String ownerMobile;
	// 银行卡号
	private String bankCardNo;
	// 卡有效期
	private String validate;
	// 卡验证码
	private String cvv2;
	// 商品信息
	private String description;
	// 用户名
	private String merchantUserName;
	// 用户请求IP
	private String requestUserIp;
	// 通知URL
	private String notifyUrl;
	// 签名字符串
	private String signString;
	// 银行卡信息
	private String bankCardInfo;
	
	public String getBankCardInfo() {
		return bankCardInfo;
	}
	public void setBankCardInfo(String bankCardInfo) {
		this.bankCardInfo = bankCardInfo;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getHyAuthCode() {
		return hyAuthCode;
	}
	public void setHyAuthCode(String hyAuthCode) {
		this.hyAuthCode = hyAuthCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerCertNo() {
		return ownerCertNo;
	}
	public void setOwnerCertNo(String ownerCertNo) {
		this.ownerCertNo = ownerCertNo;
	}
	public String getOwnerMobile() {
		return ownerMobile;
	}
	public void setOwnerMobile(String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMerchantUserName() {
		return merchantUserName;
	}
	public void setMerchantUserName(String merchantUserName) {
		this.merchantUserName = merchantUserName;
	}
	public String getRequestUserIp() {
		return requestUserIp;
	}
	public void setRequestUserIp(String requestUserIp) {
		this.requestUserIp = requestUserIp;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	
}









