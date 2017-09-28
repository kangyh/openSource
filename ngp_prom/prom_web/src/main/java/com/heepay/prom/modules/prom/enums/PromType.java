package com.heepay.prom.modules.prom.enums;

/**
 *
 *
 * 描    述：推广方式
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年7月20日14:47:50
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
public enum PromType {

    /**
     * H5
     */
    PROM_TYPE_H5("H5","H5"),
	/**
	 * 二维码
	 */
    PROM_TYPE_CODE("CODE","二维码"),
	/**
	 * PC
	 */
    PROM_TYPE_PC("PC","PC"),
	/**
	 * APP
	 */
    PROM_TYPE_APP("APP","APP"),
	/**
	 * API
	 */
    PROM_TYPE_API("API","API");

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
    PromType(String value, String content) {
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

	public static PromType getBean(String value) {
		for (PromType e : PromType.values()) {
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
