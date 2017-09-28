package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：订单查询Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月13日下午4:32:48 
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
public class TransQueryVo {
	// 商户号
	private String merchantId;
	// 商户交易号
	private String merchantOrderNo;
	// 版本号
	private String version;
	// 请求时间
	private String requestTime;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
