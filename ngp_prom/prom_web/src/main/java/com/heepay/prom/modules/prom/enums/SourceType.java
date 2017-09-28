package com.heepay.prom.modules.prom.enums;

/**
 *
 *
 * 描    述：商户来源
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
 * 审核描述：\
 *
 */
public enum SourceType {

    /**
     * 微信
     */
    SOURCE_TYPE_WECHAT("WECHAT","微信"),
	/**
	 * 支付宝
	 */
    SOURCE_TYPE_ALIPAY("ALIPAY","支付宝"),
	/**
	 * APP
	 */
    SOURCE_TYPE_APP("APP","APP"),
    /**
     * 其他
     */
    SOURCE_TYPE_OTHER("OTHER","其他");

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
    SourceType(String value, String content) {
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

	public static SourceType getBean(String value) {
		for (SourceType e : SourceType.values()) {
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
