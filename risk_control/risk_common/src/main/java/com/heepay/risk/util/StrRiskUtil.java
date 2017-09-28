package com.heepay.risk.util;
/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年12月16日 上午10:08:32
* 类说明
*/

public class StrRiskUtil {

	/**
	 * 字符串为 null 或者为 "" 时返回 true
	 */
	public static boolean isBlank(Object str) {
		return str == null || "".equals(str.toString().trim()) || "null".equals(str)? true : false;
	}
}
