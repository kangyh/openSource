package com.heepay.manage.modules.reconciliation.web.util;


/***
 *
*
* 描    述：01:存在空值 02:商户侧处理失败 03:通道侧处理失败 04:清算数据已存在 05：商户侧清算数据已存在 06：通道侧清算数据已存在
*
* 创 建 者： wangl
* 创建时间： 2017-08-15 14:23
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
public enum ClearingExceptionStatus {
	/**
	 * 存在空值
	 */
	CLEARING_EXCEPTION_STATUS_01("01", "存在空值"),

	/**
	 * 商户侧处理失败
	 */
	CLEARING_EXCEPTION_STATUS_02("02", "商户侧处理失败"),

	/**
	 * 通道侧处理失败
	 */
	CLEARING_EXCEPTION_STATUS_03("03", "通道侧处理失败"),

	/**
	 * 商户侧处理失败
	 */
	CLEARING_EXCEPTION_STATUS_04("04", "商户侧处理失败"),

	/**
	 * 商户侧清算数据已存在
	 */
	CLEARING_EXCEPTION_STATUS_05("05", "商户侧清算数据已存在"),

	/**
	 * 通道侧清算数据已存在
	 */
	CLEARING_EXCEPTION_STATUS_06("06", "通道侧清算数据已存在");


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
	ClearingExceptionStatus(String value, String content) {
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

	public static ClearingExceptionStatus getBean(String value) {
		for (ClearingExceptionStatus e : ClearingExceptionStatus.values()) {
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
