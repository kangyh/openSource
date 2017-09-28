package com.heepay.tpds.util;

import java.util.Date;

import com.heepay.date.DateUtils;

/**
 * 
 * 
 * 描 述：关键信息生成规则
 *
 * 创 建 者：chenyanming 创建时间： 2017年2月16日下午4:08:34 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class TpdsDataUtils {
	
	
	// 生成三个随机数字
	public static String getTNumber() {

		return String.valueOf(Math.round(Math.random() * 900));

	}

	/**
	 * 
	 * @方法说明：接入系统编码 接入系统编码（system_no） 前三位生成规则：（A~Z）、（a~z）
	 *              第4位生产规则：（A~Z）、（a~z）、（0~9）
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午4:09:50
	 */
	public static String systemNo() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			String char1 = getRandomChar();
			sb.append(char1);
		}
		String charNumber = getRandomCharNumber();
		sb.append(charNumber);

		return sb.toString();
	}

	/**
	 * 
	 * @方法说明：平台流水号 前4位固定字符：“tpds” 第5~18位为当前系统时间：“YYYYMMDDhimmss”
	 *             第19~26位为：随机数（数字）
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午4:11:46
	 */
	public static String businessOrderNo() {
		StringBuilder sb = new StringBuilder();
		sb.append("tpds");
		String dateStr = DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss");
		sb.append(dateStr);
		int n = 8;
		String numberStr = getNumberStr(n);
		sb.append(numberStr);
		return sb.toString();
	}

	/**
	 * 
	 * @方法说明：获取指定位数的数字字符串
	 * @author chenyanming
	 * @param n
	 * @时间：2017年2月16日下午6:03:40
	 */
	private static String getNumberStr(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			String randChar = String.valueOf(Math.round(Math.random() * 9));
			sb.append(randChar);
		}
		return sb.toString();

	}

	/**
	 * 
	 * @方法说明：业务流水号 P2p商户平台侧 前4位接入系统编码：“system_no”，不足4位右补0。
	 *              第5~18位为当前系统时间：“YYYYMMDDhimmss” 第19~26位为：随机数（数字） 银行平台侧
	 *              前4位接入系统编码：“system_no”，不足4位右补0。
	 *              第5~18位为当前系统时间：“YYYYMMDDhimmss” 第19~26位为：随机数（数字）
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午4:12:51
	 */
	public static String businessSeqNo(String systemNo) {
		StringBuilder sb = new StringBuilder();
		sb.append(systemNo);
		String dateStr = DateUtils.getDateStr(new Date(), "yyyyMMddHHmmss");
		sb.append(dateStr);
		int n = 8;
		String numberStr = getNumberStr(n);
		sb.append(numberStr);
		return sb.toString();
	}

	/**
	 * 
	 * @方法说明：商户台账号 第1位固定字符值：“M”(大写) 前2~5位接入系统编码：“system_no”，不足4位右补0。
	 *             前6~10位序列数值（数字0~9）
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午4:15:37
	 */
	public static String merchantAccountNo(String systemNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("M");
		sb.append(systemNo);
		int n = 5;
		String numberStr = getNumberStr(n);
		sb.append(numberStr);
		return sb.toString();
	}

	/**
	 * 
	 * @方法说明：客户台账号 第1位固定字符值：“C”(大写) 前2~5位接入系统编码：“system_no”，不足4位右补0。
	 *             前6~10位序列数值（数字0~9）
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午4:16:50
	 */
	public static String customAccountNo(String systemNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("C");
		sb.append(systemNo);
		int n = 5;
		String numberStr = getNumberStr(n);
		sb.append(numberStr);
		return sb.toString();
	}

	/**
	 * 
	 * @方法说明：随机生成字符，含大写、小写
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午5:33:59
	 */
	private static String getRandomChar() {
		int index = (int) Math.round(Math.random() * 1);
		String randChar = "";
		switch (index) {
		case 0:// 大写字符
			randChar = String.valueOf((char) Math.round(Math.random() * 25 + 65));
			break;
		case 1:// 小写字符
			randChar = String.valueOf((char) Math.round(Math.random() * 25 + 97));
			break;
		/*
		 * default:// 数字 randChar = String.valueOf(Math.round(Math.random() *
		 * 9)); break;
		 */
		}
		return randChar;
	}

	/**
	 * 
	 * @方法说明：随机生成字符，含大写、小写、数字
	 * @author chenyanming
	 * @return
	 * @时间：2017年2月16日下午5:33:59
	 */
	private static String getRandomCharNumber() {
		int index = (int) Math.round(Math.random() * 2);
		String randChar = "";
		switch (index) {
		case 0:// 大写字符
			randChar = String.valueOf((char) Math.round(Math.random() * 25 + 65));
			break;
		case 1:// 小写字符
			randChar = String.valueOf((char) Math.round(Math.random() * 25 + 97));
			break;
		default:// 数字
			randChar = String.valueOf(Math.round(Math.random() * 9));
			break;
		}
		return randChar;
	}

	
}
