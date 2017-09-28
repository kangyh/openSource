package com.heepay.tpds.enums;


/**
 * 
 *
 * 描    述：响应码及响应信息
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
public enum HyCode {
	/**
	 * 处理失败
	 */
	FAIL00000("FAIL", "处理失败"),
	/**
	 * 验签失败
	 */
	FAIL88888("88888", "验签失败"),
	/**
	 * 无符合条件记录
	 */
	FAIL40000("40000", "支付令牌获取失败"),
	/**
	 * 请联系客户人员
	 */
	FAIL43000("43000", "请联系客户人员"),
	/**
	 * 风控处理中
	 */
	FAIL44000("44000", "风控处理中"),
	/**
	 * 验证码失效，请重新发送短信息
	 */
	FAIL45000("45000", "验证码失效，请重新发送短信息"),
	/**
	 * 平台商户未注册
	 */
	FAIL50000("50000", "平台商户未注册"),
	/**
	 * 该商户设置密码成功未发异步通知
	 */
	FAIL60000("60000", "该商户设置密码成功未发异步通知"),
	/**
	 * 该商户没有校验密码
	 */
	FAIL70000("70000", "该商户没有校验密码"),
	/**
	 * 受理成功
	 */
	SUCC10000("10000", "受理成功"),
	/**
	 * 受理中
	 */
	DING10000("20000", "受理中");


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
	HyCode(String value, String content) {
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

	public static HyCode getBean(String value) {
		for (HyCode e : HyCode.values()) {
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
