package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：会员状态
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
public enum InfaceType {
	/**
	 * P2P客户 开户
	 */
	KXTB01("KXTB01", "P2P客户 开户"),
	/**
	 * 客户标的信息同步
	 */
	BXTB01("BXTB01", "客户标的信息同步"),
	/**
	 * P2P客户资金充值/提现
	 */
	KPCZ01("KPCZ01", "P2P客户资金充值/提现"),
	/**
	 * 客户资金交易同步
	 */
	ZJTB01("ZJTB01", "客户资金交易同步"),
	/**
	 * 客户交易状态查询
	 */
	JZCX01("JZCX01", "客户交易状态查询"),
	/**
	 * 客户数据信息查询
	 */
	SXCX01("SXCX01", "客户数据信息查询"),
	/**
	 * 监管银行提现
	 */
	BTX001("BTX001", "监管银行提现"),
	/**
	 * 交易状态查询
	 */
	BCX001("BCX001", "交易状态查询"),
	/**
	 * 异步处理结果返回
	 */
	YBTX01("YBTX01", "异步处理结果返回"),
	/**
	 * 批量文件上传/下载
	 */
	PLSC01("PLSC01", "批量文件上传/下载"),
	/**
	 * 客户信息同步
	 */
	U00001("U00001", "客户信息同步"),
	/**
	 * 标的信息同步
	 */
	O00001("O00001", "标的信息同步"),
	/**
	 * 客户/平台资金充值/提现
	 */
	T00001("T00001", "客户/平台资金充值/提现"),
	/**
	 * 第三方支付渠道充值
	 */
	T00003("T00003", "第三方支付渠道充值"),
	/**
	 * 资金交易
	 */
	T00002("T00002", "资金交易"),
	/**
	 * 批量文件上传/下载
	 */
	C00001("C00001", "交易状态查询"),
	/**
	 * 数据信息查询
	 */
	C00002("C00002", "数据信息查询"),
	/**
	 * 异步通知
	 */
	R00001("R00001", "异步通知"),
	/**
	 * 设置密码异步通知
	 */
	U00000("U00000", "设置密码异步通知");
	
	
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
	InfaceType(String value, String content) {  
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

	public static InfaceType getBean(String value) {
		for (InfaceType e : InfaceType.values()) {
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
