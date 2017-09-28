/**
 * 
 */
package com.heepay.manage.common.enums;

/**
 * @author Administrator
 *
 */
public enum RiskBwCategory {
	
	
	MERCHANT_ID("MERCHANT_ID", "商户ID"), 
	BANKCARD("BANKCARD", "银行卡号"), 
	MOBILE("MOBILE", "手机号"),
	IP("IP", "IP地址"),
	IDCARD("IDCARD", "身份证号");
	String _value;

	/**
	 * 存放内容
	 */
	String _Content;

	/**
	 * 私有构造函数
	 * 
	 * @param value
	 *            枚举值
	 * @param content
	 *            缓存内容
	 * @return
	 */
	RiskBwCategory(String value, String content) {
		this._value = value;
		this._Content = content;
	}

	/**
	 * 取得枚举对象值
	 * 
	 * @return 枚举对象值
	 */
	public String getValue() {
		return this._value;
	}

	/**
	 * 取得内容
	 * 
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

	public static RiskBwCategory getBean(String value) {
		for (RiskBwCategory e : RiskBwCategory.values()) {
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

}
