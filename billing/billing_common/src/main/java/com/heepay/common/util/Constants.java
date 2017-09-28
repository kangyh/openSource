/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年1月10日上午11:51:54
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
package com.heepay.common.util;


/***
 * 
 * 
 * 描    述：常量类
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年1月10日上午11:51:54
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
public class Constants {
	 
		//清结算系统使用
		public static final class Clear{
			//币种人民币
			public static final String CURRENCY_RMB = "156";
			
			//对账状态：已对
			public static final String CHECK_STATUS_Y = "Y";
			//对账状态：未对
			public static final String CHECK_STATUS_N = "N";
			//结算状态：未结
			public static final String SETTLE_STATUS_N = "N";
			//结算状态：未结
			public static final String SETTLE_STATUS_Y = "Y";
			//结算状态：结算中
			public static final String SETTLE_STATUS_D = "D";
			//交易结算周期
			public static final String T0 = "0";
				//交易结算周期
			public static final String T1 = "1";
			
			//交易结算周期X
			public static final String TX = "X";
			
			public static final String BILLINGRPC = "billing_rpc";
			
			//风控系统后台和清结算系统后台页面显示条件区分
			public static final String RISK = "risk";
			public static final String OTHER = "other";
			
			public static final String TK  = "TK";  //财付通退款标识
		}		
		
	}

