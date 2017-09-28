package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付确认Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月13日下午3:26:59 
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
public class QuickPayConfirmVo {
	// 商户的ID
	private String merchantId;
	// 商户交易号
	private String merchantOrderNo;
	// 用户号
	private String merchantUserId;
	// 汇付宝支付单号
	private String hyPaymentId;
	// 交易金额
	private String payAmount;
	// 请求时间
	private String requestTime;
	// 版本号
	private String version;
	// 产品编码
	private String productCode;
	// 验证码
	private String verifyCode;
	// 支付令牌
	private String token;
	// 信用卡信息
	private String creditCardInfo;
	// 卡有效期
	private String validate;
	// 卡验证码
	private String cvv2;
	// 用户请求IP
	private String requestUserIp;
	// 签名字符串
	private String signString;

	//授权码
	private String authorizationCode;

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getCreditCardInfo() {
		return creditCardInfo;
	}
	public void setCreditCardInfo(String creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
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
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	public String getRequestUserIp() {
		return requestUserIp;
	}
	public void setRequestUserIp(String requestUserIp) {
		this.requestUserIp = requestUserIp;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	public String getHyPaymentId() {
		return hyPaymentId;
	}

	public void setHyPaymentId(String hyPaymentId) {
		this.hyPaymentId = hyPaymentId;
	}
}













