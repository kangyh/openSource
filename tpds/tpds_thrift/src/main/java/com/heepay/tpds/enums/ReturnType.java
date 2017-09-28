package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：还款方式
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月10日09:29:51
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
public enum ReturnType {
	/**
	 * 一次性还本付息
	 */
	R1("01", "一次性还本付息"),
	/**
	 * 先息后本
	 */
	R2("02", "先息后本"),
	/**
	 * 等额本息
	 */
	R3("03", "等额本息"),
	/**
	 * 等额本金
	 */
	R4("04", "等额本金"),
	/**
	 * 等本等息
	 */
	R5("05", "等本等息"),
	/**
	 * 到期结清
	 */
	R6("06", "到期结清"),
	/**
	 * 一次付息
	 */
	R7("07", "一次付息");
	
	
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
	ReturnType(String value, String content) {  
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

	public static ReturnType getBean(String value) {
		for (ReturnType e : ReturnType.values()) {
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
