package com.heepay.risk.enums;
/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年4月21日 下午1:50:46
 * 创建描述：黑白名单分类
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
public enum BlackType {
	
   BLACK("BLACK","黑名单"),
   WHILE("WHILE","白名单"),
   GRAY("GRAY","灰名单");

   String _value;
   String _content;
	
	
	BlackType(String value, String content) {  
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

 