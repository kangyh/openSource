/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 *
 * 描    述：.net通道银行支付提供者
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/4/25 13:32 
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
public enum DirectBankProvider {

	UNKNOW("-1", "未知"),
	BANKOWNER("1", "银行自己"),
	UMPAY("2", "联动优势"),//(支持所有银行信用卡，单独成一个银行)
	CMPAY("3","中移动"),//（快捷支付、跨行代付）
	CMBCPAY("4","民生银行"),//（跨行、本行代付）
	TCLUPMP("5","TCL银联"),//(银行卡快捷）
	CMBPAY("6","招商银行"),//（跨行、本行代付）
	CCBPAY("7","建设银行"),//（跨行、本行代付）
	LIANLIANPAY("8","连连银通"),//（快捷）
	PEASEPAY("9","首信易"),
	EPOSPAY("10","易宝"),
	SHANXIPBCPAY("11","ShanXiPBCPay"),
	HEEPAY("12","Heepay"),
	HUISU("13","Huisu"),
	SPAY("14","Spay"),
	CARD70("15","Card70"),
	QUICKPAY("16","QuickPay"),
	CHINABANKPAY("17","ChinaBankPay"),
	CCBSUPERNET("18","建行超网"),
	ZHIFUKA("19","51微信支付"),
	NIANNIANKA("20","年年卡"),
	IPAYNOW("21","现在支付微信支付"),
	SWIFTPASS("22","威富通支付"),
	ALIPAY("23","支付宝二维码支付"),
	WEIXIN("24","微信"),
	JYTPAY("25","金运通"),
	SHOUQIANBA("26","收钱吧"),
	TENPAY("27","财付通"),
	YINSHENG("28","银盛支付"),
	QQPAY("29","QQ钱包支付"),
	CHINAGPAY("30","智惠支付"),
	APPLEPAY("31","苹果支付"),
	KUAIFUPAY("32","快付通"),
	SHORTPAYWITHHOLD("33","快捷二次支付代扣业务通道"),
	CIBPAY("34","兴业银行"),
	TONGTONGPAY("35","统统付"),
	CHANJETPAY("36","畅捷通代扣通道"),
	DCOREPAY("37","点芯支付"),
	XIAMENPINGANPAY("38","厦门平安"),
	HEEPAYJAVA("39","汇付宝Java支付"),
	YISHENG("40", "谊盛支付（支付宝）");

	String _value;

	/**
	 * 存放内容
	 */
	String _Content;

	/**
	 * 私有构造函数
	 *
	 * @param value
	 *            枚举值
	 * @param content
	 *            缓存内容
	 * @return
	 */
	DirectBankProvider(String value, String content) {
		this._value = value;
		this._Content = content;
	}

	/**
	 * 取得枚举对象值
	 *
	 * @return 枚举对象值
	 */
	public String getValue() {
		return this._value;
	}

	/**
	 * 取得内容
	 *
	 * @return 内容
	 */
	public String getContent() {
		return this._Content;
	}

	/**
	 * 根据值取得内容
	 *
	 * @return 内容
	 */

	public static DirectBankProvider getBean(String value) {
		for (DirectBankProvider e : DirectBankProvider.values()) {
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
