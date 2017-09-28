package com.heepay.prom.modules.prom.enums;

/**
 *
 *
 * 描    述：状态
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年7月20日14:47:50
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
public enum Status {

	/**
	 * 启用
	 */
    STATUS_ENABLE("ENABLE","启用"),
	/**
	 * 禁用
	 */
    STATUS_DISABL("DISABL","禁用");

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
    Status(String value, String content) {
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

	public static Status getBean(String value) {
		for (Status e : Status.values()) {
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
