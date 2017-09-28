package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：职业类型
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
public enum JobType {
	/**
	 * 政府机关
	 */
	J0("0", "政府机关"),
	/**
	 * 邮政
	 */
	J1("1", "邮政"),
	/**
	 * 电信
	 */
	J3("3", "电信"),
	/**
	 * IT
	 */
	J4("4", "IT"),
	/**
	 * 商业/贸易
	 */
	J5("5", "商业/贸易"),
	/**
	 * 金融
	 */
	J6("6", "金融"),
	/**
	 * 旅游/饭店
	 */
	J7("7", "旅游/饭店"),
	/**
	 * 医疗卫生
	 */
	J8("8", "医疗卫生"),
	/**
	 * 房地产
	 */
	J9("9", "房地产"),
	/**
	 * 交通运输
	 */
	J10("10", "交通运输"),
	/**
	 * 法律司法
	 */
	J11("11", "法律司法"),
	/**
	 * 文化娱乐
	 */
	J12("12", "文化娱乐"),
	/**
	 * 媒体广告
	 */
	J13("13", "媒体广告"),
	/**
	 * 科研/教育
	 */
	J14("14", "科研/教育"),
	/**
	 * 学生
	 */
	J15("15", "学生"),
	/**
	 * 农林牧渔
	 */
	J16("16", "农林牧渔"),
	/**
	 * 矿业/制造
	 */
	J17("17", "矿业/制造"),
	/**
	 * 自由职业
	 */
	J18("18", "自由职业"),
	/**
	 * 其他
	 */
	JX("X", "其他");
	
	
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
	JobType(String value, String content) {  
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

	public static JobType getBean(String value) {
		for (JobType e : JobType.values()) {
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
