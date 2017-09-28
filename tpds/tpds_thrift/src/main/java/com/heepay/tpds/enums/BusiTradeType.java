package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：业务操作类型
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
public enum BusiTradeType {
	/**
	 * 修改银行密码
	 */
	M01("M01", "修改银行密码"),
	/**
	 * 重置银行密码
	 */
	M02("M02", "重置银行密码"),
	/**
	 * 客户注册
	 */
	U01("U01", "客户注册"),
	/**
	 * 客户信息修改
	 */
	U02("U02", "客户信息修改"),
	/**
	 * 客户注销
	 */
	U03("U03", "客户注销"),
	/**
	 * 客户绑卡
	 */
	B01("B01", "客户绑卡"),
	/**
	 * 客户解绑
	 */
	B02("B02", "客户解绑"),
	/**
	 * 客户绑卡修改
	 */
	B03("B03", "客户绑卡修改"),
	/**
	 * 标的发布
	 */
	P01("P01", "标的发布"),
	/**
	 * 标的撤标
	 */
	P03("P03", "标的撤标"),
	/**
	 * 标的修改
	 */
	P04("P04", "标的修改"),
	/**
	 * 投标
	 */
	T01("T01", "投标"),
	/**
	 * 取消投标
	 */
	T02("T02", "取消投标"),
	/**
	 * 放款
	 */
	T03("T03", "放款"),
	/**
	 * 还款
	 */
	T04("T04", "还款"),
	/**
	 * 出款
	 */
	T05("T05", "出款"),
	/**
	 * 受让
	 */
	T07("T07", "受让"),
	/**
	 * 营销
	 */
	T10("T10", "营销");
	
	
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
	BusiTradeType(String value, String content) {  
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

	public static BusiTradeType getBean(String value) {
		for (BusiTradeType e : BusiTradeType.values()) {
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
