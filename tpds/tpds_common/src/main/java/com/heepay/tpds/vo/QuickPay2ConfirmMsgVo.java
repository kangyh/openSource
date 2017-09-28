package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付2确认返回参数
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年5月2日上午11:40:33 
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
public class QuickPay2ConfirmMsgVo {
	
	private String paymentId;  // 汇付宝支付单号
	private String merchantOrderNo; // 商户交易号
	private String token; // 支付令牌
	private String retCode; // 接口状态码
	private String retMsg; // 接口状态描述
	private String signString; // 签名字符串
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
