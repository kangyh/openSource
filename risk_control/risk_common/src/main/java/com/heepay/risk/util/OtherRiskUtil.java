package com.heepay.risk.util;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.common.util.JsonMapperUtil;

/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年12月16日 上午10:08:56
* 类说明
*/
public class OtherRiskUtil {

	public static final Logger logger = LogManager.getLogger();
	/**
	 * 记录风控日志
	 * @param log
	 * @param map
	 */
	public static void riskLoggerInfo(Logger log,Map map)
	{
		JsonMapperUtil jsonMapperUtil = new JsonMapperUtil();
		log.info("risk_message:"+jsonMapperUtil.toJson(map));
	}
	public static void riskLoggerInfo(Logger log,String str)
	{
		
		log.info("risk_message:{"+str+"}");
	}
	public static void riskLoggerDebug(Logger log,Map map)
	{
		JsonMapperUtil jsonMapperUtil = new JsonMapperUtil();
		log.debug("risk_message:"+jsonMapperUtil.toJson(map));
	}
	public static void riskLoggerDeug(Logger log,String str)
	{
		
		log.debug("risk_message:{"+str+"}");
	}
	public static void riskLoggerError(Logger log,Map map)
	{
		JsonMapperUtil jsonMapperUtil = new JsonMapperUtil();
		log.error("risk_message:"+jsonMapperUtil.toJson(map));
	}
	public static void riskLoggerError(Logger log,String str)
	{
		
		log.error("risk_message:{"+str+"}");
	}
	public static void riskRuleEngineLoggerInfo(String info)
	{
		logger.info(info);
	}
}
