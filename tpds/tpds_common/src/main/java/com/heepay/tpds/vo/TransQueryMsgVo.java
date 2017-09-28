package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付查询返回参数Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月17日上午11:21:41 
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
public class TransQueryMsgVo {
	// 商户ID
	private String merchantId;
	// 商户交易号
	private String merchantOrderNo;
	// 汇付宝订单号
	private String hyGatewayId;
	// 金额
	private String amount;
	// 接口状态码
	private String retCode;
	// 查询状态描述
	private String retMsg;
	// 签名字符串
	private String signString;
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
	public String getHyGatewayId() {
		return hyGatewayId;
	}
	public void setHyGatewayId(String hyGatewayId) {
		this.hyGatewayId = hyGatewayId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
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
