package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：签约查询Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月13日下午4:29:00 
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
public class SignQueryVo {
	// 商户号
	private String merchantId;
	// 用户号
	private String merchantUserId;
	// 版本号
	private String version;
	// 产品编码
	private String productCode;
	// 请求时间
	private String requestTime;
	// 签名字符串
	private String signString;
	// 银行卡类型
	private String bankCardType;
	
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantUserId() {
		return merchantUserId;
	}
	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
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
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getSignString() {
		return signString;
	}
	public void setSignString(String signString) {
		this.signString = signString;
	}
	
}
