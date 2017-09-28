package com.heepay.risk_es_engine;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import com.heepay.risk_es_engine.EsBigDataService;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年6月6日 下午6:45:27
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
public class Run {
	static EsBigDataService essearch = null;
	

	
	public static void initESClient() {
		essearch = new EsBigDataService();
		essearch.initESClient();
	}
	
	public static void closeESClient() {
		essearch.closeESClient();
	}
	public static  void main(String[] args) {
		initESClient();
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginTime", "1496592000283");
		map.put("endTime", "1496678399283");
		map.put("createPerson", "lizhen");
		map.put("updatePerson", "lizhen");
		 
		String conditionJson  = JSONObject.fromObject(map).toString();
		
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("productNumber", "8831");
		map2.put("productDescription", "test");
//		map2.put("settleStatus", "N");
//		map2.put("bankCode", "11000");
		String staticsJson = JSONObject.fromObject(map2).toString();
		
		essearch.getBossAggregationInfoNew(conditionJson, staticsJson);
		closeESClient();
	}

}
