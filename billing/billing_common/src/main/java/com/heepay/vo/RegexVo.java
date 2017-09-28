package com.heepay.vo;

import org.springframework.stereotype.Component;

/**
 * 
 * 
 * 描    述：去除特殊字符的正则表达式实体类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年3月17日下午4:17:01 
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
@Component
public class RegexVo {
	
	private String regex;

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
