package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：标的状态
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
public enum ProjectStatus {
	/**
	 * 发布
	 */
	P1("01", "发布"),
	/**
	 * 投资中
	 */
	P2("02", "投资中"),
	/**
	 * 放款
	 */
	P3("03", "放款"),
	/**
	 * 撤标
	 */
	P5("05", "撤标"),
	/**
	 * 还款中
	 */
	P6("06", "还款中"),
	/**
	 * 结束
	 */
	P7("07", "结束");
	
	
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
	ProjectStatus(String value, String content) {  
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

	public static ProjectStatus getBean(String value) {
		for (ProjectStatus e : ProjectStatus.values()) {
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
