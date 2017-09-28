package com.heepay.manage.common.enums;

/**
 * OperationType 操作类型
 * 
 * @author ly
 *
 */
public enum OperationType {
	/**
	 * 未知
	 */
	SAVE("SAVEXX", "保存"), 
	UPDATE("UPDATE", "更新"), 
	UPDATEPRO("UPDPRO", "只更新商户产品"),
	SAVEPRO("SAVPRO", "只保存商户产品");

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
	OperationType(String value, String content) {
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

	public static OperationType getBean(String value) {
		for (OperationType e : OperationType.values()) {
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
