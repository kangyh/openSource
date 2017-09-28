package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付确认返回参数Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月17日上午11:14:48 
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
public class QuickPayConfirmMsgVo {
	// 汇付宝交易支付号
	private String hyPaymentId;
	// 汇付宝交易订单号
	private String hyGatewayId;
	// 授权码
	private String hyAuthCode;
	// 商户交易号
	private String merchantOrderNo;
	// 金额
	private String payAmount;
	// 接口状态码
	private String retCode;
	// 接口状态描述
	private String retMsg;
	// 签名字符串
	private String signString;
	// 支付结果 该字段值为1000时表示成功
	private String result;
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getHyAuthCode() {
		return hyAuthCode;
	}
	public void setHyAuthCode(String hyAuthCode) {
		this.hyAuthCode = hyAuthCode;
	}
	public String getHyPaymentId() {
		return hyPaymentId;
	}
	public void setHyPaymentId(String hyPaymentId) {
		this.hyPaymentId = hyPaymentId;
	}
	public String getHyGatewayId() {
		return hyGatewayId;
	}
	public void setHyGatewayId(String hyGatewayId) {
		this.hyGatewayId = hyGatewayId;
	}
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
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
