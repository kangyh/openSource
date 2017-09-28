package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：b2c网银支付返回参数Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月17日上午11:25:51 
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
public class B2CPayMsgVo {
	// 支付金额
	private String successAmount;
	// 交易金额
	private String payAmount;
	// 汇付宝订单号
	private String transNo;
	// 支付结果 1000 成功 1002 失败
	private String result;
	// 商户在汇付宝的id
	private String merchantId;
	// 商户的交易号
	private String merchantOrderNo;
	// 接口版本  1.0
	private String version;
	// 签名串，规则见说明
	private String sign;
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
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
