package com.heepay.prom.modules.prom.enums;

/**
 * @author 李震
 *
 * 2017年9月15日
 */
public enum ProfitType {
	
	/**
	 * 师徒分润
	 */
    MASTER_DISCIPLE_PROFIT("1","师徒分润"),
    /**
	 * 汇元分润(推广分润)
	 */
    SPREAD_PROFIT("2","推广分润"),
	/**
	 * 推荐分润
	 */
    RECOMMENDATION_PROFIT("3","推荐分润");

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
	ProfitType(String value, String content) {
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

	public static ProfitType getBean(String value) {
		for (ProfitType e : ProfitType.values()) {
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
