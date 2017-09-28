package com.heepay.manage.modules.reconciliation.web.util;


/***
 *
*
* 描    述：差异类型 CF：重复入库，CZ：冲正单，HD：差异表已存在
*
* 创 建 者： wangl
* 创建时间： 2017-07-28 15:05
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
public enum DifferType {

	/**
	 * 重复入库
	 */
	DIFFER_TYPE_CF("CF", "重复入库"),

	/**
	 * 冲正单
	 */
	DIFFER_TYPE_CZ("CZ", "冲正单"),

	/**
	 * 差异表已存在
	 */
	DIFFER_TYPE_HD("HD", "差异表已存在");


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
	DifferType(String value, String content) {
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

	public static DifferType getBean(String value) {
		for (DifferType e : DifferType.values()) {
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
