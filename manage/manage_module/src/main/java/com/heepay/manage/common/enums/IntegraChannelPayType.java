/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 *
 * 描    述：.net聚合通道支付类型
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/4/25 14:13 
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
public enum IntegraChannelPayType {

	ALIPAY("22", "支付宝支付"),
	TENPAY("23", "财付通支付"),
	RUYIPAY("24", "如意付"),
	BIANLIPAY("25", "便利付"),
	PAY19("26", "19Pay支付"),
	SOUPAY("27", "嗖!付"),
	XINGQITIAN("28", "天天付"),
	TIANTIANPAY("29", "星启天51支付(微信扫码支付)"),
	WECHATPAY("30", "微信支付");

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
	IntegraChannelPayType(String value, String content) {
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

	public static IntegraChannelPayType getBean(String value) {
		for (IntegraChannelPayType e : IntegraChannelPayType.values()) {
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
