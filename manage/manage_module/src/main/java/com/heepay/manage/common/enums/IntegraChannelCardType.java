/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 *
 * 描    述：.net聚合通道卡类型
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/4/25 14:05 
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
public enum IntegraChannelCardType {


	UNKNOW("-1", "未知"),
	DEPOSITCARD("0", "储蓄卡(借记卡)"),
	CREDIT("1", "信用卡(贷记卡)");

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
	IntegraChannelCardType(String value, String content) {
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

	public static IntegraChannelCardType getBean(String value) {
		for (IntegraChannelCardType e : IntegraChannelCardType.values()) {
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
