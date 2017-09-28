package com.heepay.manage.modules.payment.param;

/**
 * 
* 
* 描    述：
*
* 创 建 者： 杨春龙  
* 创建时间： 2017年1月4日 下午5:07:20 
* 创建描述：快捷支付异步通知返回签名类
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
public class QuicPayResult {
	@ParamConfig(isSign=true,required=true)
	private String successAmount; 
	@ParamConfig(isSign=true,required=true)
	private String payAmount;
	@ParamConfig(isSign=true,required=true)
	private String transNo;
	@ParamConfig(isSign=true,required=true)
	private String result;
	@ParamConfig(isSign=true,required=true)
	private String merchantId;
	@ParamConfig(isSign=true,required=true)
	private String merchantOrderNo;
	@ParamConfig(isSign=true,required=true)
	private String version;
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
	
	

    
}
