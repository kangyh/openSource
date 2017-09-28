/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月21日下午2:11:18
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
package com.heepay.tpds.util;

import java.util.Map;

import com.heepay.common.util.StringUtil;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月21日下午2:11:18
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
public class StringTpdsUtil {
	
	/**
	 * 
	 * @方法说明：分割字符串
	 * @author xuangang
	 * @param str
	 * @param spliter
	 * @return
	 * @时间：2017年2月21日下午2:12:31
	 */
	public static String[] stringSplit(String str, String spliter) {
		
		if(spliter == null)
			return null;
		
		if(StringUtil.notBlank(str)){
			
			String[] array = str.split(spliter);
			return array;
		}
		
		return null;
	}
	
	public static Map stringSplitToMap(String str, String spliter){
		return null;
	}
	
	public static void main(String args[]){
		
//		String str = "aaaaa&bbbbb&CCC&";
//		
//		String[] a = stringSplit(str, "");
		System.out.println("".length());
	}

}
