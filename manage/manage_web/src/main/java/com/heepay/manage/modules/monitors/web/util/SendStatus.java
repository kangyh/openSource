package com.heepay.manage.modules.monitors.web.util;


/***
 * 
* 
* 描    述：发送状态：  INIT：未发送  ING：发送中  FAIL：发送失败  OK：发送成功
*
* 创 建 者： wangl
* 创建时间：  20 Mar 201718:31:55
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
public enum SendStatus {
	
	/**
	 * 未发送
	 */
	SENDSTATUS_INIT("INIT","未发送"),
	
	/**
	 * 发送中
	 */
	SENDSTATUS_ING("ING","发送中"),
	
	/**
	 * 发送失败
	 */
	SENDSTATUS_FAIL("FAIL","发送失败"),
	
	/**
	 * 发送成功
	 */
	SENDSTATUS_OK("OK", "发送成功");

	
	String _value;

	/**
	 * 存放内容
	 */
	String _Content;

	/**
	 * 私有构造函数
	 * @param value 枚举值
	 * @param content 缓存内容
	 * @return
	 */
	SendStatus(String value, String content) {
		this._value = value;  
		this._Content = content;  
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
		return this._Content;
	}

	/**
	 * 根据值取得内容
	 * 
	 * @return 内容
	 */

	public static SendStatus getBean(String value) {
		for (SendStatus e : SendStatus.values()) {
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
