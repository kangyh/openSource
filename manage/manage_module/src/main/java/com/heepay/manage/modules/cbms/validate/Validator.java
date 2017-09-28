/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 名称：校验器：利用正则表达式校验邮箱、手机号等
 * <p>
 * 创建者  B.HJ
 * 创建时间 2016-09-25-10:54
 * 创建描述：校验器：利用正则表达式校验邮箱、手机号等
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class Validator {
	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * 正则表达式：验证手机号		^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$
	 */
	public static final String REGEX_MOBILE = "(^13\\d{9}$)|(^14\\d{9}$)|(^15\\d{9}$)|(^16\\d{9}$)|(^17\\d{9}$)|(^18\\d{9}$)|(^19\\d{9}$)";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "[\u4e00-\u9fa5]+";

	/**
	 * 正则表达式：HGMS商户名称验证（）
	 */
	public static final String COMPANY_NAME = "[\u4E00-\u9FA5\uF900-\uFA2D|\uFF08|\uFF09]+";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    public static final String REGEX_ID_CARD2 = "^\\d{15}|^\\d{17}([0-9]|X|x)$";
	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	/**
	 * 正则表达式2：验证URL,可以不带http
	 * */
	public static final String REGEX_URL2 = "((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)|((www.)|[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	
	/**
	 * 日期格式 \\d{4}-\\d{2}-\\d{2}$"
	 */
	public static final String REGEX_DATE = "\\d{4}-\\d{2}-\\d{2}$";

	/**
	 * 正则表达式: 验证时间 yyyy-MM-dd HH:mm:ss
	 */
	public static final String REGEX_TIME = "^(\\d{4})-(0\\d|1[0-2])-(0\\d|[12]\\d|3[01])\\s(0\\d|1\\d|2[0-3]):[0-5]\\d:([0-5]\\d)$";

    /**
     * 正则表达式: 验证数字类型
     */
    public static final String REGEX_INTEGER = "^(0|[1-9][0-9]*)$";

	/**
	 * 校验日期
	 * 
	 * @param date
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isDate(String date) {
		return Pattern.matches(REGEX_DATE, date);
	}

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String username) {
		return Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {
		return Pattern.compile(REGEX_CHINESE).matcher(chinese).find();
	}

	/**
	 * HGMS商户名称
	 *
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isCompanyName(String chinese) {
		return Pattern.compile(COMPANY_NAME).matcher(chinese).find();
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}
	/**
	 * 校验身份证2 更严格
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard2(String idCard) {
		return Pattern.matches(REGEX_ID_CARD2, idCard);
	}
	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl2(String url) {
		if( Pattern.matches(REGEX_URL2, url) == true ){
			int index = url.indexOf(".");
			String sub = url.substring(0, index);
			if( sub.toLowerCase().contains("wwww")) {
				return false;
			}
			int n = url.length()-url.replace(".", "").length();
			if(url.toLowerCase().contains("www")  &&   n<2  ){
				  return false;
			}
			if(url.toLowerCase().contains(".www")){
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		return Pattern.matches(REGEX_URL, url);
	}
	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean isIPAddr(String ipAddr) {
		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}
	
	public  static  boolean isIP(String addr)
	{
		if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
		{
			return false;
		}
		/**
		 * 判断IP格式和范围
		 * 
		 */
		String rexp = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pat = Pattern.compile(rexp);  
		Matcher mat = pat.matcher(addr);  
		boolean ipAddress = mat.find();
		return ipAddress;
	}

    /**
     * 固定电话号码验证
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     * @param  str
     * @return 验证通过返回true
     */
	public static boolean isPhone(final String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^([0-9]{3,4}-)?[0-9]{7,8}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{7,8}$");         // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 校验时间格式
	 * @param time yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static boolean isTime(String time){ return Pattern.matches(REGEX_TIME,time); }

    /**
     * 验证零和非零开头的数字
     * @param str
     * @return
     */
    public static boolean isInteger(String str){return Pattern.matches(REGEX_INTEGER,str);}
}
