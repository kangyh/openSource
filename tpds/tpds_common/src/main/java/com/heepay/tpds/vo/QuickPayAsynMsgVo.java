package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付异步通知Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月17日下午1:37:28 
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
public class QuickPayAsynMsgVo {
	// 完成金额
	private String successAmount;
	// 订单金额
	private String payAmount;
	// 银行流水号
	private String bankSerialNo;
	// 汇付宝交易号
	private String paymentId;
	// 支付结果描述
	private String result;
	// 商户ID
	private String merchantId;
	// 商户交易号
	private String merchantOrderNo;
	// 版本号
	private String version;
	// 签名字符串
	private String signString;
	public String getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getBankSerialNo() {
		return bankSerialNo;
	}
	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}

}
