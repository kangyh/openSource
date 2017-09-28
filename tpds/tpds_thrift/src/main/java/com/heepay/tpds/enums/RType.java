package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：充值类型
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
public enum RType {
	/**
	 * 客户代扣充值
	 */
	R01("R01", "客户代扣充值"),
	/**
	 * 客户网银充值
	 */
	R02("R02", "客户网银充值"),
	/**
	 * 营销充值
	 */
	R03("R03", "营销充值"),
	/**
	 * 客户网银充值
	 */
	R04("R04", "代偿充值"),
	/**
	 * 费用充值
	 */
	R05("R05", "费用充值"),
	/**
	 * 垫资充值
	 */
	R06("R06", "垫资充值"),
	/**
	 * 线下充值
	 */
	R07("R07", "线下充值"),
	/**
	 * 认证支付请求
	 */
	R08("R08", "认证支付请求"),
	/**
	 * 认证支付确认
	 */
	R09("R09", "认证支付确认 ");
	
	
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
	RType(String value, String content) {  
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

	public static RType getBean(String value) {
		for (RType e : RType.values()) {
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
