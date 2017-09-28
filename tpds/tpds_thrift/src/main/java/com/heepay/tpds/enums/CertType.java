package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：证件类型
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
public enum CertType {
	/**
	 * 身份证
	 */
	C0("00", "身份证"),
	/**
	 * 居住证
	 */
	C1("01", "居住证"),
	/**
	 * 签证
	 */
	C2("02", "签证"),
	/**
	 * 护照
	 */
	C3("03", "护照"),
	/**
	 * 户口本
	 */
	C4("04", "户口本"),
	/**
	 * 军人证
	 */
	C5("05", "军人证"),
	/**
	 * 港澳通行证
	 */
	C6("06", "港澳通行证");
	
	
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
	CertType(String value, String content) {  
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

	public static CertType getBean(String value) {
		for (CertType e : CertType.values()) {
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
