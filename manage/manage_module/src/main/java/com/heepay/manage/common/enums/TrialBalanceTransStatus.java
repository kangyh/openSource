package com.heepay.manage.common.enums;

/**
 * TrialBalanceTransType 操作类型
 * 
 * @author ly
 *
 */
public enum TrialBalanceTransStatus {
	/**
	 * 未知
	 */
  PREHAN("PREHAN", "待处理"), 
  INHAND("INHAND", "处理中"), 
  FINISH("FINISH", "已处理"),
  REJECT("REJECT", "审核拒绝");

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
	TrialBalanceTransStatus(String value, String content) {
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

	public static TrialBalanceTransStatus getBean(String value) {
		for (TrialBalanceTransStatus e : TrialBalanceTransStatus.values()) {
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
