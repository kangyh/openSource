package com.heepay.warning.vo;
/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月16日 上午10:13:20
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
public enum SendType {
	SMS("sms","短信"),
	EMAIL("email","邮箱"),
	WECHAT("wechat","微信"),
	ALL("all","所有");
	
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
	SendType(String value, String content) {  
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


}

 