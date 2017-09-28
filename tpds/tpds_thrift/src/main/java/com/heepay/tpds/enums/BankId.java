package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：银行编码
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
public enum BankId {
	/**
	 * 中国工商银行 
	 */
	B102("102", "中国工商银行"),
	/**
	 * 中国农业银行
	 */
	B103("103", "中国农业银行"),
	/**
	 * 中国银行
	 */
	B104("104", "中国银行"),
	/**
	 * 中国建设银行
	 */
	B105("105", "中国建设银行"),
	/**
	 * 交通银行
	 */
	B301("301", "交通银行"),
	/**
	 * 中信银行
	 */
	B302("302", "中信银行"),
	/**
	 * 中国光大银行
	 */
	B303("303", "中国光大银行"),
	/**
	 * 华夏银行
	 */
	B304("304", "华夏银行"),
	/**
	 * 中国民生银行 
	 */
	B305("305", "中国民生银行"),
	/**
	 * 广发银行 
	 */
	B306("306", "广发银行"),
	/**
	 * 平安银行 
	 */
	B307("307", "平安银行"),
	/**
	 * 招商银行 
	 */
	B308("308", "招商银行"),
	/**
	 * 兴业银行 
	 */
	B309("309", "兴业银行"),
	/**
	 * 上海浦东发展银行 
	 */
	B310("310", "上海浦东发展银行"),
	/**
	 * 城市商业银行 
	 */
	B313("313", "城市商业银行"),
	/**
	 * 中国邮政储蓄银行 
	 */
	B403("403", "中国邮政储蓄银行");
	
	
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
	BankId(String value, String content) {  
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

	public static BankId getBean(String value) {
		for (BankId e : BankId.values()) {
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
