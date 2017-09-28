package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付请求返回参数VO
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月17日上午11:10:25 
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
public class QuickPayMsgVo {
	// 汇付宝支付单号
	private String paymentId;
	// 商户交易号
	private String merchantOrderNo;
	// 支付令牌
	private String token;

	// 授权码
	private String authorizationCode;

	// 接口状态码
	private String retCode;
	// 接口状态描述
	private String retMsg;
	// 签名字符串
	private String signString;
	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	
}
