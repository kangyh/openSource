package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：提现类型
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
public enum WType {
	/**
	 * 客户提现
	 */
	W01("W01", "客户提现"),
	/**
	 * 营销提现
	 */
	W02("W02", "营销提现"),
	/**
	 * 代偿提现
	 */
	W03("W03", "代偿提现"),
	/**
	 * 费用提现
	 */
	W04("W04", "费用提现"),
	/**
	 * 垫资提现
	 */
	W05("W05", "垫资提现"),
	
	/**
	 * 存管提现
	 */
	W06("W06", "存管提现");
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
	WType(String value, String content) {  
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

	public static WType getBean(String value) {
		for (WType e : WType.values()) {
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
