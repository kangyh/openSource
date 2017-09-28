package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：快捷支付Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月13日下午1:59:23 
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
public class B2CPayVo {
	// 商户的ID
	private String merchantId;
	// 商户交易号
	private String merchantOrderNo;
	// 用户号
	private String merchantUserId;
	// 产品编码
	private String productCode;
	// 交易金额
	private String payAmount;
	// 请求时间
	private String requestTime;
	// 版本号
	private String version;
	// 异步通知URL
	private String notifyUrl;
	// 同步通知URL
	private String callBackUrl;
	// 商品信息
	private String description;
	// 用户ip
	private String clientIp;
	// 防钓鱼时间
	private String reqHyTime;
	// 签名字符串
	private String signString;
	// 选择银行方式
	private String onlineType;
	// 银行id
	private String bankId;
	// 银行名称
	private String bankName;
	// 银行卡类型
	private String bankCardType;
	
	
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
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getCallBackUrl() {
		return callBackUrl;
	}
	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getReqHyTime() {
		return reqHyTime;
	}
	public void setReqHyTime(String reqHyTime) {
		this.reqHyTime = reqHyTime;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	public String getOnlineType() {
		return onlineType;
	}
	public void setOnlineType(String onlineType) {
		this.onlineType = onlineType;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

}


