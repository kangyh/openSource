package com.heepay.risk_es_engine;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;

/**
 * 描    述：风控系统 ElasticSearch 类说明 ElasticSearch 常用的方法封装
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2016年12月2日 上午9:41:54
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
public class ESearchUtil {

	private static final Logger log = LogManager.getLogger();
	/**
	 * 把map 参数的键值对 映射成 TermQueryBuilder 条件列表
	 * @param param
	 * @return
	 */
	public static  List<TermQueryBuilder> termQueryBuilders(Map param)
	{
		List<TermQueryBuilder>  termQueryBuilderlist = new ArrayList<TermQueryBuilder>();
		if (param !=null)
		{
			Iterator it = param.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next(); 
				termQueryBuilderlist.add(QueryBuilders.termQuery(entry.getKey().toString(), entry.getValue().toString()));
			}
			return termQueryBuilderlist;
		}
		return null;
	}
	/**
	 * 眏射单条TermQueryBuilder 条件
	 * @param name
	 * @param value
	 * @return
	 */
	public static TermQueryBuilder  termQueryBuilder(String name, String value)
	{
		return QueryBuilders.termQuery(name, value);
	}
	
	/**
	 * 初始化ElasticSearch 的client 引擎
	 * @param settings
	 * @param inetAddress
	 * @param port
	 * @return
	 */
	public static Client  getClient(Settings settings,String inetAddress,String port)
	{
		Client client = null;
		try {
			client = TransportClient.builder().settings(settings).build()
			.addTransportAddress(new InetSocketTransportAddress(
					InetAddress.getByName(inetAddress),
					Integer.parseInt(port)));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("risk_message:{"+e.getMessage()+"}");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("risk_message:{"+e.getMessage()+"}");
		}
		return client;
		
	}
	/**
	 * 多条件 联合查询
	 * @param param
	 * @return
	 */
	public static BoolQueryBuilder getBoolQueryBuilder(Map param)
	{
		BoolQueryBuilder boolQueryBuilder = 
		QueryBuilders  
        .boolQuery()  ;
		Iterator it = param.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry entry = (Map.Entry)it.next(); 
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey().toString(), entry.getValue().toString().toLowerCase())); //转换小写做条件
		}
       	return boolQueryBuilder;
	}
	public static BoolQueryBuilder getMultiBoolQueryBuilder(Map param,Map<String,List> multiParam)
	{
		BoolQueryBuilder boolQueryBuilder = 
		QueryBuilders  
        .boolQuery()  ;
		Iterator it = param.entrySet().iterator();
		while (it.hasNext())
		{ 
			Map.Entry entry = (Map.Entry)it.next(); 
			
			boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey().toString().toLowerCase(), entry.getValue().toString())); //转换小写做条件
		}
		Iterator it2 = multiParam.entrySet().iterator();
		while (it2.hasNext())
		{ 
			Map.Entry entry = (Map.Entry)it2.next(); 
			
			boolQueryBuilder.must(QueryBuilders.termsQuery(entry.getKey().toString().toLowerCase(), entry.getValue())); //转换小写做条件
		}
		
       	return boolQueryBuilder;
	}
	
	/**
	 * 多条件 联合查询 等于情况
	 * @param param
	 * @return
	 */
	public static BoolQueryBuilder getBoolMatchQueryBuilder(List<Map> param,BoolQueryBuilder boolQueryBuilderParam)
	{
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if (param == null)
		{
			return boolQueryBuilder;
		}
		for(Map map : param)
		{
			Iterator it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next(); 
				boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey().toString(), entry.getValue().toString().toLowerCase())); //转换小写做条件
			}
		}
       	return boolQueryBuilder;
	}
	/**
	 * 多条件 联合查询  不 等于情况
	 * @param param
	 * @return
	 */
	public static BoolQueryBuilder getBoolNoMatchQueryBuilder(List<Map> param,BoolQueryBuilder boolQueryBuilderParam)
	{
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if (param == null)
		{
			return boolQueryBuilder;
		}
		for(Map map : param)
		{
			Iterator it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next(); 
				boolQueryBuilder.mustNot(QueryBuilders.termQuery(entry.getKey().toString(), entry.getValue().toString().toLowerCase())); //转换小写做条件
			}
		}
		//queryBuilder.mustNot(QueryBuilders.termQuery("trans_type", "shares")
		return boolQueryBuilder;
	}
	
	/**
	 * 多条件 联合查询 区间范围情况
	 * @param param
	 * @return
	 */
	public static BoolQueryBuilder getBoolRangeQueryBuilder(List<Map> param,BoolQueryBuilder boolQueryBuilderParam)
	{
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if(param != null) 
		{
			for (int i=0;i<param.size();i++)
			{
				RangeQueryBuilder queryTimeBuilderRange= RangeQueryBuilder(
						param.get(i).get("field").toString(),
						Long.valueOf(param.get(i).get("begin").toString()),
						Long.valueOf(param.get(i).get("end").toString()),true,true);
				boolQueryBuilder.must(queryTimeBuilderRange);
			}
		}
		return boolQueryBuilder;
	}
	
	
	
	
	
	
	
	/**
	 * 在指定的索引库 下的 类型里查询
	 * @param client
	 * @param index
	 * @param type
	 * @return
	 */
	public static SearchRequestBuilder getSearchRequestBuilder (Client client,String index,String type,QueryBuilder queryBuilder,MetricsAggregationBuilder metricsAggregationBuilder )
	{
		if (client != null)
		{
			if (metricsAggregationBuilder !=null){
				return client.prepareSearch(index).setTypes(type).setQuery(queryBuilder).addAggregation(metricsAggregationBuilder);
			}else{
				return client.prepareSearch(index).setTypes(type).setQuery(queryBuilder);
			}
		}
		return null;
	}
	 /** 在指定的索引库 下的 类型里查询  AggregationBuilder
	 * @param client
	 * @param index
	 * @param type
	 * @return
	 */
	public static SearchRequestBuilder getSearchRequestBuilderNew (Client client,String index,String type,QueryBuilder queryBuilder,AggregationBuilder aggregationBuilder )
	{
		if (client != null)
		{
			if (aggregationBuilder !=null){
				return client.prepareSearch(index).setTypes(type).setQuery(queryBuilder).addAggregation(aggregationBuilder);
			}else{
				return client.prepareSearch(index).setTypes(type).setQuery(queryBuilder);
			}
		}
		return null;
	}
	public static <T> RangeQueryBuilder RangeQueryBuilder(String field,T from,T end,Boolean isIncludeLower,Boolean isIncludeUpper)
	{
		return  QueryBuilders.rangeQuery(field)
		        .gt(from)                	//大于 
		        .lt(end)    				//小于
		        .includeLower(isIncludeLower)             //包括下界
		        .includeUpper(isIncludeUpper);            //包括上界
	}
	
	/**
	 * 关闭ElasticSearch
	 * @param client
	 */
	public static void closeESClient(Client client) {
		if (client!=null)
		{
			client.close();
			log.info("risk_message{client:"+ client +",releaseES:success}");
		}
	}
	
	public static BoolQueryBuilder getBoolMatchQueryBuilder(Map equalConditionMap,BoolQueryBuilder boolQueryBuilderParam){
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if (equalConditionMap == null)
		{
			return boolQueryBuilder;
		}
			Iterator it = equalConditionMap.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next(); 
				boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey().toString(), entry.getValue().toString().toLowerCase())); //转换小写做条件
			}
		
       	return boolQueryBuilder;
	}
	public static BoolQueryBuilder getBoolMatchQueryBuilderNoAnalyze(Map equalConditionMap,BoolQueryBuilder boolQueryBuilderParam){
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if (equalConditionMap == null)
		{
			return boolQueryBuilder;
		}
			Iterator it = equalConditionMap.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next(); 
				boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(entry.getKey().toString(), entry.getValue().toString())); 
			}
		
       	return boolQueryBuilder;
	}
	public static BoolQueryBuilder  getBoolNoMatchQueryBuilder(Map notequalConditonMap,BoolQueryBuilder boolQueryBuilderParam){
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if (notequalConditonMap == null)
		{
			return boolQueryBuilder;
		}
		
			Iterator it = notequalConditonMap.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry entry = (Map.Entry)it.next(); 
				boolQueryBuilder.mustNot(QueryBuilders.termQuery(entry.getKey().toString(), entry.getValue().toString().toLowerCase())); //转换小写做条件
			}
		
		//queryBuilder.mustNot(QueryBuilders.termQuery("trans_type", "shares")
		return boolQueryBuilder;
	}
	
	public static BoolQueryBuilder  getBoolRangeQueryBuilder(Map rangeCondition,BoolQueryBuilder boolQueryBuilderParam){
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if(rangeCondition != null) 
		{
				RangeQueryBuilder queryTimeBuilderRange= RangeQueryBuilder(
						rangeCondition.get("field").toString(),
						Long.valueOf(rangeCondition.get("begin").toString()),
						Long.valueOf(rangeCondition.get("end").toString()),true,true);
				boolQueryBuilder.must(queryTimeBuilderRange);

		}
		return boolQueryBuilder;
	}
	
	public static BoolQueryBuilder  getBoolRangeQueryBuilder2(Map rangeCondition,BoolQueryBuilder boolQueryBuilderParam){
		BoolQueryBuilder boolQueryBuilder;
		if (boolQueryBuilderParam == null)
		{
			boolQueryBuilder = QueryBuilders.boolQuery() ;
		}else
		{
			boolQueryBuilder = boolQueryBuilderParam;
		}
		if(rangeCondition != null) 
		{
				RangeQueryBuilder queryTimeBuilderRange= RangeQueryBuilder(
						rangeCondition.get("field").toString(),
						rangeCondition.get("begin").toString(),
						rangeCondition.get("end").toString(),true,true);
				boolQueryBuilder.must(queryTimeBuilderRange);

		}
		return boolQueryBuilder;
	}

}	
	