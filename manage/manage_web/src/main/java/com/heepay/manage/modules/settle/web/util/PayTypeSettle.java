package com.heepay.manage.modules.settle.web.util;

import com.heepay.common.util.StringUtil;

/**
 * 产品类型
 * @author zc
 *
 */
public enum PayTypeSettle {
	QUICKPAY("QUICKP","快捷"),
	GATEWAYPAY("GATEWY","网银"),
	BATCOL("BATCOL","代收"),
	REFUND("REFUND","退款"),
	BATPAY("BATPAY","代付"),
	WECHATPAY("WECHAT","微信支付"),
	ALISCAN("ALIPAY","支付宝扫码");
	String _value;

	/**
	 * 存放内容
	 */
	String _Content;
	
	/**
	 * 私有构造函数
	 * @param value 枚举值
	 * @param content 缓存内容
	 * @return 
	 */
	PayTypeSettle(String value, String content) {
		this._value = value;  
		this._Content = content;  
	}  
	
	/**
	 * 取得枚举对象值
	 * @return 枚举对象值
	 */
	public String getValue() {
		return this._value;
	}
	
	/**
	 * 取得内容
	 * @return 内容
	 */
	public String getContent() {
		return this._Content;
	}
	
	/**
	 * 根据值取得内容
	 * 
	 * @return 内容
	 */
	public static PayTypeSettle getBean(String value) {
		for (PayTypeSettle e : PayTypeSettle.values()) {
			if (value.equals(e.getValue())) {
				return e;
			}
		}
		return null;
	}

	public static String labelOf(String val) {
		if (getBean(val) != null) {
			return getBean(val).getContent();
		}
		return null;
	}
	
	public static String getContentByValue(String value){
		if(StringUtil.isBlank(value)){
			return "";
		}
		PayTypeSettle[] types = PayTypeSettle.values();
		for(PayTypeSettle type:types){
			if(value.equals(type.getValue())){
				return type.getContent();
			}
		}
		return "";
		
	}
	
}
