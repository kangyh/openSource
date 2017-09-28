package com.heepay.manage.common.utils;


/**
 * 
 *
 * 描    述：费率审核
 *
 * 创 建 者：   wangl
 * 创建时间：2017-05-09 09:48 AM
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
public enum RateAudit {
	/**
	 * S 未审核
	 */
	RATE_AUDIT_S("S", "未审核"),

	/**
	 * Y 审核通过
	 */
	RATE_AUDIT_Y("Y", "审核通过"),

	/**
	 * N 审核失败
	 */
	RATE_AUDIT_N("N", "审核不通过");


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
	RateAudit(String value, String content) {
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

	public static RateAudit getBean(String value) {
		for (RateAudit e : RateAudit.values()) {
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
