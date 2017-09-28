package com.heepay.manage.common.enums;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-06-27 17:29:42
 * 创建描述: 规则模式    "ACCTIME":"按时间"	 "ACCBLCE":"按金额"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public enum HgmsRuleMode {
	/**
	 * 未知
	 */
	ACCORDINGTIME("ACCTIME", "按时间"),
	ACCORDINGBALANCE("ACCBLCE", "按金额");

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
	HgmsRuleMode(String value, String content) {
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

	public static HgmsRuleMode getBean(String value) {
		for (HgmsRuleMode e : HgmsRuleMode.values()) {
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
