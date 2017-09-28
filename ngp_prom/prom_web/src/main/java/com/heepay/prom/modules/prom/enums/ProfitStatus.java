/**
 * 
 */
package com.heepay.prom.modules.prom.enums;

/**
 * @author Administrator
 *
 */
public enum ProfitStatus {
	
	/**
	 * 已分润
	 */
    PROFITED("Y","已分润"),
	/**
	 * 未分润
	 */
    UNPROFIT("N","未分润");

	String _value;


	/**
	 * 存放内容
	 */
	String _content;


	/**
	 * 私有构造函数
	 * @param value 枚举值
	 * @param content 缓存内容
	 * @return
	 */
	ProfitStatus(String value, String content) {
		this._value = value;
		this._content = content;
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
		return this._content;
	}


	/**
	 * 根据值取得内容
	 *
	 * @return 内容
	 */

	public static ProfitStatus getBean(String value) {
		for (ProfitStatus e : ProfitStatus.values()) {
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
