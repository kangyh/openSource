package com.heepay.rpc.billing.enums;

import com.heepay.enums.billing.BillingDifferType;

/**
 * 
 * 
 * 描    述：异常明细的差异类型
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年7月27日下午2:22:36 
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
public enum SettleDifferType {
	/**
	 * 重复入库
	 */
	DIFFERTYPEF("CF", "重复入库"),
	/**
	 * 冲正单
	 */
	DIFFERTYPEZ("CZ", "冲正单"),
	/**
	 * 差异表已存在
	 */
	DIFFERTYPED("HD", "差异表已存在");
	
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
	SettleDifferType(String value, String content) {  
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

	public static SettleDifferType getBean(String value) {
		for (SettleDifferType e : SettleDifferType.values()) {
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
