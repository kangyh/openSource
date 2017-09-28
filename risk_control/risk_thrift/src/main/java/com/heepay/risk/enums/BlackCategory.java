package com.heepay.risk.enums;
/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年4月21日 下午1:43:23
 * 创建描述：黑名单分类枚举
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
public enum BlackCategory {
	
	BLACK_MERCHANT_CODE("MERCHANT_ID","商户编码"),
	BLACK_MERCHANT_NAME("MERCHANT_NAME","商户名称"),
	BLACK_MOBILE("MOBILE","手机号"),
	BLACK_BANKCARD("BANKCARD","银行卡号"),
	BLACK_OWER_NAME("OWER_NAME","法人姓名"),
	BLACK_OWER_IDCARD("OWER_IDCARD","法人身份证号"),
	BLACK_BUZI_CODE("BUZI_CODE","营业执照编号"),
	BLACK_IP("BLACK_IP","IP");
    String _value; 
	
	
	String _content;
	
	
	BlackCategory(String value, String content) {  
		this._value = value;  
		this._content = content;  
	}  
	
	
	public String getValue() {
		return this._value;
	}
	
	
	public String getContent() {
		return this._content;
	}
}

 