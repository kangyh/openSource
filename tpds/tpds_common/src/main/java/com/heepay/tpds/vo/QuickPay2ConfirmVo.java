package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付2确认请求参数
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年5月2日下午1:39:24 
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
public class QuickPay2ConfirmVo {
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
	// 持卡人姓名
	private String ownerName;
	// 持卡人身份证号
	private String ownerCertNo;
	// 持卡人手机号
	private String ownerMobile;
	// 银行卡号
	private String bankCardNo;
	// 验证码
	private String verifyCode;
	// 支付令牌
	private String token;
	
	// 授权码
	private String hyAuthCode;
	// 卡有效期
	//private String validate;
	// 卡验证码
	//private String cvv2;
	// 用户请求IP
	private String requestUserIp;
	// 签名字符串
	private String signString;
	
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
	public String getHyPaymentId() {
		return hyPaymentId;
	}
	public void setHyPaymentId(String hyPaymentId) {
		this.hyPaymentId = hyPaymentId;
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
	public String getHyAuthCode() {
		return hyAuthCode;
	}
	public void setHyAuthCode(String hyAuthCode) {
		this.hyAuthCode = hyAuthCode;
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

}
