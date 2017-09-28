
package com.heepay.manage.modules.payment.param;


/***
 * 
* 
* 描    述： 签名参数基础类
*
* 创 建 者： 杨春龙  
* 创建时间： 2016年12月29日 下午2:17:19 
* 创建描述：统一快捷支付签名校验
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

public  class BaseParam  {
	
	
	//商户id
	@ParamConfig(isSign=true,required=true)
	String merchantId;
	//商户订单号
	@ParamConfig(isSign=true,required=true)
	String merchantOrderNo;
	//商户用户id
	@ParamConfig(isSign=true,required=true)
	String merchantUserId;
	//交易金额
	@ParamConfig(isSign=true,required=true)
	String payAmount;
	//版本
	@ParamConfig(isSign=true,required=true)
    String version;
    //要访问的产品id
	@ParamConfig(isSign=true,required=true)
    String productCode;		
	//异步通知URL
	@ParamConfig(isSign=true,required=true)
	String notifyUrl; 	
	
	
	private String signWithoutKey;	
	
	private String desc;
	
	private String key;
	public String validate(){
	  return "";	
	}
	
	public String getSignWithoutKey() {
		return signWithoutKey;
	}

	public void setSign(String signWithoutKey) {
		this.signWithoutKey = signWithoutKey;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setSignWithoutKey(String signWithoutKey) {
		this.signWithoutKey = signWithoutKey;
	}


}
