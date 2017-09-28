/**
 * 
 */
package com.heepay.risk_es_engine;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.queries.TermsQuery;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregator;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.risk.util.DateRiskUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 描    述：大数据 ElasticSearch 服务类
 *
 * 创 建 者：李震
 * 创建时间： 2017年6月2日 下午3:26:26
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
public class EsBigDataService {
	private Configuration configs ;
	private static final Logger log = LogManager.getLogger();
	private  Client client;
	public EsBigDataService()
	{}
	/**
	 * 初始化ElasticSearch
	 */
	@PostConstruct
	public void initESClient() {
		Settings settings;
		try {
			configs = new PropertiesConfiguration("elasticsearch.properties");
			settings = Settings.settingsBuilder().put(configs.getProperty("es.cluster.property.name"),configs.getProperty("es.cluster.property.name.value"))
												 .put("client.transport.sniff",true).build();
			client = ESearchUtil.getClient(settings, configs.getProperty("es.cluster.property.InetAddress").toString(),
					configs.getProperty("es.cluster.property.InetAddress.port").toString());
			log.info("risk_message:{es_ip_address:"+ configs.getProperty("es.cluster.property.InetAddress").toString() 
					+",es_port："+configs.getProperty("es.cluster.property.InetAddress.port").toString()
					+",es_status:success");
		}catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("risk_message:{"+e.getMessage()+"}");
		}
		
	}
	public List<Map<String,String>> getBossDataRecordList(String fields,String equalParam )
	{
		log.info("查询boss数据列表-返回字段-fields："+fields);
		log.info("查询boss数据列表-条件-equalParam："+equalParam);
		if( StringUtils.isBlank(fields) || StringUtils.isBlank(equalParam)) {
			log.info("查询boss数据列表错误，参数为空！");
			return null;
		}
		try
		{
			String[] exportFields = fields.split(",");
			JsonMapperUtil jmu =  new JsonMapperUtil();
			JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
			Map<String,String> equalConditionMap = jmu.fromJson(equalParam, jatype ) ;
			
			Map<String,String> rangeCondition =  null;
			if( !StringUtils.isBlank(equalConditionMap.get("beginTime")) && !StringUtils.isBlank(equalConditionMap.get("endTime"))) {
				rangeCondition =  new HashMap<String,String>();
				rangeCondition.put("begin", equalConditionMap.get("beginTime"));
				rangeCondition.put("end", equalConditionMap.get("endTime"));
				rangeCondition.put("field", "payFinishTime");
			}
			String bankId  = equalConditionMap.get("bankId");
			String channelTransType = equalConditionMap.get("channelTransType");
			String orderBy   = equalConditionMap.get("orderBy");
			String isDesc = equalConditionMap.get("isDesc");
			
			equalConditionMap.remove("beginTime");
			equalConditionMap.remove("endTime");
			equalConditionMap.remove("bankId");
			equalConditionMap.remove("channelTransType");
			equalConditionMap.remove("orderBy");
			equalConditionMap.remove("isDesc");

			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
			
			if(!StringUtil.isBlank(bankId)){
				queryBuilder.must(QueryBuilders.termsQuery("bankId", bankId.split(",")));
			}
			if(!StringUtil.isBlank(channelTransType)){
				queryBuilder.must(QueryBuilders.termsQuery("channelTransType", channelTransType.toLowerCase().split(",")));
			}
			SortOrder sortOrder = SortOrder.DESC;// 排序方式 
			if(!StringUtil.isBlank(isDesc) && "asc".equals(isDesc)){
				sortOrder = SortOrder.ASC;
			}
			if(StringUtil.isBlank(orderBy)){
				orderBy = "payFinishTime";
			}
			
			
			SearchResponse response = client.prepareSearch(configs.getProperty("es.cluster.property.indexG").toString())
										.setTypes(configs.getProperty("es.cluster.property.typeG").toString())
										.setQuery(queryBuilder)
//										.setFrom(Integer.parseInt(page.get("pagefrom").toString()))
//										.setSize(Integer.parseInt(page.get("pagesize").toString()))
										.setFrom(0)
										.setSize(1000000)
										.addSort(orderBy, sortOrder)
						                .setExplain(false)  //设置是否按查询匹配度排序 
						                .addFields(exportFields)
						                .execute().actionGet();
			
			List<Map<String,String>> listresult = new ArrayList<Map<String,String>>();
//			page.put("totalsize", response.getHits().getTotalHits());
			 for(final SearchHit hit:response.getHits()){
				 Map<String, String> map = new HashMap<String, String>();
				 final Iterator<SearchHitField> iterator = hit.iterator(); 
				 while(iterator.hasNext()){
					 final SearchHitField hitfield = iterator.next();
					 if(hitfield.getValue()!=null ){
						 map.put(hitfield.getName(),hitfield.getValue().toString() );
					 }else{
						 map.put(hitfield.getName(),null );
					 }
					
				 }
				 listresult.add(map);
//				 log.info("查询返回数据："+map.toString());
				 
			}
//			 JSONArray jso = new JSONArray();
//		        if(listresult!=null && listresult.size()>0) {
//		        	jso = JSONArray.fromObject(listresult);
//		        }
//		        log.info("查询返回数据："+jso.toString());
			 log.info("risk_message:{totalsize:"+response.getHits().getTotalHits()+",mothed:getTransData}");
			return listresult;
		}catch (Exception e)
		{
			e.printStackTrace();
			log.error("risk_message:{"+ e.getMessage() +",method:getTransData}");
		}
		return null;
	}
	
	public String getBossDataRecordInfo(String fields,String transNo) {
		log.info("查询boss数据列表详情-返回字段-fields："+fields);
		log.info("查询boss数据列表详情-条件-transNo："+transNo);
		if( StringUtils.isBlank(fields) || StringUtils.isBlank(transNo)) {
			log.info("查询boss数据列表详情错误，参数为空！");
			return null;
		}
		try
		{
			String[] exportFields = fields.split(",");
			Map<String,String> equalConditionMap = new HashMap<String,String>();
			equalConditionMap.put("transNo", transNo);
			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			SearchResponse response = client.prepareSearch(configs.getProperty("es.cluster.property.indexG").toString())
										.setTypes(configs.getProperty("es.cluster.property.typeG").toString())
										.setQuery(queryBuilder)
						                .setExplain(false)  //设置是否按查询匹配度排序 
						                .addFields(exportFields)
						                .execute().actionGet();
			
			JSONObject jsonResult = new JSONObject();
			SearchHits hit = response.getHits();
			if( hit!=null && hit.getTotalHits()>0){
				 final Iterator<SearchHitField> iterator = hit.getAt(0).iterator(); 
				 while(iterator.hasNext()){
					 final SearchHitField hitfield = iterator.next();
					 if(hitfield.getValue()!=null ){
						 jsonResult.put(hitfield.getName(),hitfield.getValue().toString() );
					 }else{
						 jsonResult.put(hitfield.getName(),null );
					 }
				 }
				 log.info("查询返回数据："+jsonResult.toString());
				 log.info("risk_message:{totalsize:"+response.getHits().getTotalHits()+",mothed:getBossDataRecordInfo}");
				return jsonResult.toString();
			}else {
				 log.info("查询返回数据：null ");
				return "";
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			log.error("risk_message:{"+ e.getMessage() +",method:getBossDataRecordInfo}");
			
			return "";
		}
		
	}
/**
 * 测试分组 聚合
 * @param conditionJson
 * @param groupFields
 * @return
 */
	public String getBossAggregationInfoNew( String conditionJson ,String groupFields)
	{
		log.info("boss数据汇总-条件-conditionJson："+conditionJson);
		log.info("boss数据汇总-分组字段-staticsJson："+groupFields);
		if( StringUtils.isBlank(conditionJson) || StringUtils.isBlank(groupFields)) {
			log.info("boss数据汇总错误，参数为空！");
			return null;
		}
		JsonMapperUtil jmu = new JsonMapperUtil();
		JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
		Map<String,String> conditionMap = jmu.fromJson(conditionJson, jatype ) ;
		Map<String,String> rangeCondition =  null;
		if( !StringUtils.isBlank(conditionMap.get("beginTime")) && !StringUtils.isBlank(conditionMap.get("endTime"))) {
			rangeCondition =  new HashMap<String,String>();
			rangeCondition.put("begin", conditionMap.get("beginTime"));
			rangeCondition.put("end", conditionMap.get("endTime"));
			rangeCondition.put("field", "payFinishTime");
		}
		String bankId  = conditionMap.get("bankId");
		String channelTransType = conditionMap.get("channelTransType");
		String orderBy   = conditionMap.get("orderBy");
		String isDesc = conditionMap.get("isDesc");
		
		conditionMap.remove("beginTime");
		conditionMap.remove("endTime");
		conditionMap.remove("bankId");
		conditionMap.remove("channelTransType");
		conditionMap.remove("orderBy");
		conditionMap.remove("isDesc");
		
		
		String [] str = groupFields.split(",");
		SearchRequestBuilder srb = null;
		srb =client.prepareSearch(configs.getProperty("es.cluster.property.indexG").toString())
				.setTypes(configs.getProperty("es.cluster.property.typeG").toString());
		/**
		 * 查询条件
		 */
		BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(conditionMap,null);
		ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
		
		if(!StringUtil.isBlank(bankId)){
			queryBuilder.must(QueryBuilders.termsQuery("bankId", bankId.toLowerCase().split(",")));
		}
		if(!StringUtil.isBlank(channelTransType)){
			queryBuilder.must(QueryBuilders.termsQuery("channelTransType", channelTransType.toLowerCase().split(",")));
		}
		SortOrder sortOrder = SortOrder.DESC;// 排序方式 
		if(!StringUtil.isBlank(isDesc) && "asc".equals(isDesc)){
			sortOrder = SortOrder.ASC;
		}
		if(StringUtil.isBlank(orderBy)){
			orderBy = "payFinishTime";
		}
		srb.setQuery(queryBuilder);
		TermsBuilder termsBuilder =  getTermsBuilder(str,0);
		srb.addAggregation(termsBuilder.size(10000));
		SearchResponse sr = srb.execute().actionGet();
		log.info( "结果集："+sr.getHits().getTotalHits() );
		List<Map> listMap = getAggregationInfoSum(sr,str,0);
		JSONArray jso = new JSONArray();
        if(listMap!=null && listMap.size()>0) {
        	jso = JSONArray.fromObject(listMap);
        }
        
        long totalcount = 0;
        if(listMap!=null){
        	for(int i=0;i<listMap.size();i++) {
//            	log.info( "billCount："+ listMap.get(i).get("billCount").toString() +",channelCode："+ listMap.get(i).get("channelCode") 
//            			 +",channelTransType："+ listMap.get(i).get("channelTransType") 
//            			 +",transType："+ listMap.get(i).get("transType") 
//            			 +",merchantId："+ listMap.get(i).get("merchantId") 
//            			 +",bankCode："+ listMap.get(i).get("bankCode") 
//            			 +",bankcardType："+ listMap.get(i).get("bankcardType") 
//            			 +",day："+ listMap.get(i).get("day") 
//            			 );
            	totalcount  = totalcount+  Long.parseLong( listMap.get(i).get("billCount").toString() ) ;
            }
        }
        
        log.info("boss数据汇总-总数量："+totalcount+",统计结果："+jso.toString());
		return jso.toString();
	}
	/**
	 * 递归制造条件
	 * @param str
	 * @param position
	 * @return
	 */
	public TermsBuilder getTermsBuilder(String[] str,Integer position)
	{
		TermsBuilder termsBuilder = null;
		if (str.length>position+1)
		{
			TermsBuilder subTermsBuilder = getTermsBuilder(str,position+1).size(10000);
			
			termsBuilder = AggregationBuilders.terms(str[position]).field(str[position]).subAggregation(subTermsBuilder);
		}else
		{
			termsBuilder = AggregationBuilders.terms(str[position]).field(str[position]);
//			MetricsAggregationBuilder requestAmountSumBuilder =  AggregationBuilders.sum("requestAmountSum").field("requestAmount");
			MetricsAggregationBuilder payAmountSumBuilder =  AggregationBuilders.sum("payAmountSum").field("payAmount");
//			MetricsAggregationBuilder settleAmountSumBuilder =  AggregationBuilders.sum("settleAmountSum").field("settleAmount");
//			termsBuilder.subAggregation(requestAmountSumBuilder);
			termsBuilder.subAggregation(payAmountSumBuilder);
//			termsBuilder.subAggregation(settleAmountSumBuilder);
		}
		return termsBuilder;
	}
	
	/**
	 * 递归获取 结果集中的列
	 * @param bucket_T
	 * @param aggStr
	 * @param position
	 * @return
	 */
	public <T> List<Map> getAggregationInfoSum(T bucket_T,String[] aggStr,Integer position){
		Map aggMap = null;
		List<Map> listMap = new ArrayList<Map>();
		Map map = null;
		if (bucket_T instanceof  SearchResponse){
			 aggMap = ((SearchResponse)bucket_T).getAggregations().asMap();
		}else{
			aggMap = ((Bucket)bucket_T).getAggregations().asMap();
		}
		StringTerms stringTerms = (StringTerms) aggMap.get(aggStr[position]);
		Iterator<Bucket> stringTermsBucket = stringTerms.getBuckets().iterator();
		while(stringTermsBucket.hasNext()){
			Bucket bucket = stringTermsBucket.next();
			if (aggStr.length>position+1){
				List<Map> subListMap = getAggregationInfoSum(bucket,aggStr,position+1);
				for(Map item:subListMap)
				{
					item.put(aggStr[position], bucket.getKey());
				}
				listMap.addAll(subListMap);
			}else{
				Map merchantIds_sum = bucket.getAggregations().asMap();
				map = new HashMap();
				map.put(aggStr[position], bucket.getKey());
				map.put("billCount", String.valueOf(  ( (Long)bucket.getDocCount() )==null?"0":bucket.getDocCount()));
//				map.put("requestAmountSum", ((InternalSum)merchantIds_sum.get("requestAmountSum")).getValue());
				BigDecimal b = new BigDecimal(0);  
				if( merchantIds_sum.get("payAmountSum")!=null ){
					b = new BigDecimal(  ((InternalSum)merchantIds_sum.get("payAmountSum")).value() );
				}
			    //保留4位小数  
			    String  bigd = b.setScale(4, BigDecimal.ROUND_HALF_UP).toString() ;  
				map.put("payAmountSum", bigd );
//				map.put("settleAmountSum", ((InternalSum)merchantIds_sum.get("settleAmountSum")).getValue());
				listMap.add(map);
			}
		}
		return listMap;
	}

	
	
	
	public String getBossAggregationInfoOld( String conditionJson ,String groupFields)
	{
		log.info("boss数据汇总-条件-conditionJson："+conditionJson);
		log.info("boss数据汇总-分组字段-staticsJson："+groupFields);
		JsonMapperUtil jmu = new JsonMapperUtil();
		JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
		Map<String,String> conditionMap = jmu.fromJson(conditionJson, jatype ) ;
		Map<String,String> rangeCondition =  null;
		if( !StringUtils.isBlank(conditionMap.get("beginTime")) && !StringUtils.isBlank(conditionMap.get("endTime"))) {
			rangeCondition =  new HashMap<String,String>();
			rangeCondition.put("begin", conditionMap.get("beginTime"));
			rangeCondition.put("end", conditionMap.get("endTime"));
			rangeCondition.put("field", "payFinishTime");
		}
		String bankId  = conditionMap.get("bankId");
		String channelTransType = conditionMap.get("channelTransType");
		String orderBy   = conditionMap.get("orderBy");
		String isDesc = conditionMap.get("isDesc");
		
		conditionMap.remove("beginTime");
		conditionMap.remove("endTime");
		conditionMap.remove("bankId");
		conditionMap.remove("channelTransType");
		conditionMap.remove("orderBy");
		conditionMap.remove("isDesc");
		
		

		SearchRequestBuilder srb = null;
		srb =client.prepareSearch(configs.getProperty("es.cluster.property.indexG").toString())
				.setTypes(configs.getProperty("es.cluster.property.typeG").toString());
		try {
			/**
			 * 查询条件
			 */
			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(conditionMap,null);
			ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
			
			if(!StringUtil.isBlank(bankId)){
				queryBuilder.must(QueryBuilders.termsQuery("bankId", bankId.toLowerCase().split(",")));
			}
			if(!StringUtil.isBlank(channelTransType)){
				queryBuilder.must(QueryBuilders.termsQuery("channelTransType", channelTransType.toLowerCase().split(",")));
			}
			SortOrder sortOrder = SortOrder.DESC;// 排序方式 
			if(!StringUtil.isBlank(isDesc) && "asc".equals(isDesc)){
				sortOrder = SortOrder.ASC;
			}
			if(StringUtil.isBlank(orderBy)){
				orderBy = "payFinishTime";
			}
			
			srb.setQuery(queryBuilder);
			/**
			 * 统计字段 基本不变
			 */
			SumBuilder requestAmountSumBuilder =  AggregationBuilders.sum("requestAmountSum").field("requestAmount");
			SumBuilder payAmountSumBuilder =  AggregationBuilders.sum("payAmountSum").field("payAmount");
			SumBuilder settleAmountSumBuilder =  AggregationBuilders.sum("settleAmountSum").field("settleAmount");
			/**
			 * 分组字段
			 */
			String[] fields = groupFields.split(",");
			
			TermsBuilder filed0TermBuilder = AggregationBuilders.terms(fields[0]+"Term").field(fields[0]);//最底层的termbuilder 封装Sumbuilder
			filed0TermBuilder.subAggregation(requestAmountSumBuilder);
			filed0TermBuilder.subAggregation(payAmountSumBuilder);
			filed0TermBuilder.subAggregation(settleAmountSumBuilder);
			 
			List<TermsBuilder> tbList = new ArrayList<TermsBuilder>();
			for(int i=1;i<fields.length;i++) {
				TermsBuilder termsBuilder = new TermsBuilder("");
				termsBuilder = AggregationBuilders.terms(fields[i]+"Term").field(fields[i]);
				tbList.add(termsBuilder);
			}
			for(int i=0;i<tbList.size();i++) {
				if(i==0) {
					tbList.get(i).subAggregation(filed0TermBuilder);
				}else{
					tbList.get(i).subAggregation(tbList.get(i-1));
				}
			}
			srb.addAggregation(tbList.get(tbList.size()-1));
			SearchResponse sr = srb.execute().actionGet();
			Terms topTerm=  sr.getAggregations().get(fields[fields.length-1]+"Term");
			
			List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
	        //遍历每一个分组的key 
	        for( int a=0;a<topTerm.getBuckets().size();a++){//merchantId
	        	Terms.Bucket firstBuc =null;
	        	firstBuc = topTerm.getBuckets().get(a);
	        	Terms secondTerms =  firstBuc.getAggregations().get("bankShortNameTerm");
	        	 for(int b=0;b<secondTerms.getBuckets().size();b++){
	        		 Terms.Bucket secondBuc =null;
	        		 secondBuc = secondTerms.getBuckets().get(b);
	        		 Terms thirdTerms =  secondBuc.getAggregations().get("channelTransTypeTerm");
	        		 for(int c=0;c<thirdTerms.getBuckets().size();c++){
	        			 Terms.Bucket thirdBuc =null;
	        			 thirdBuc = thirdTerms.getBuckets().get(c);
	        			 Terms fourTerms =  thirdBuc.getAggregations().get("merchantNameTerm");
	        			 for(int d=0;d<fourTerms.getBuckets().size();d++){
	        				 Terms.Bucket lastBuc =null;
	        				 lastBuc = fourTerms.getBuckets().get(d);
	     	        		Sum requestAmountSum = lastBuc.getAggregations().get("requestAmountSum");
	     		            Sum payAmountSum = lastBuc.getAggregations().get("payAmountSum");
	     		            Sum settleAmountSum = lastBuc.getAggregations().get("settleAmountSum");
	     		            log.info("a="+a+",b="+b+",c="+c+",d=" + d);
	     		            log.info("聚合结果-"+firstBuc.getKey()+","+secondBuc.getKey()+","+thirdBuc.getKey()+","+lastBuc.getKey()+"," + lastBuc.getDocCount() +","+requestAmountSum.getValue()+","+payAmountSum.getValue() + ","+settleAmountSum.getValue());
	     		           Map<String,String> resultMap = new HashMap<String,String>();
	     		            resultMap.put("merchantId", (String)firstBuc.getKey());
	     		           resultMap.put("bankShortName", (String)secondBuc.getKey());
	     		           resultMap.put("channelTransType", (String)thirdBuc.getKey());
	     		           resultMap.put("merchantName", (String)lastBuc.getKey());
	     		          resultMap.put("billCount", String.valueOf(lastBuc.getDocCount()));
	     		         resultMap.put("realAmt", payAmountSum.getValueAsString());
	     		        resultMap.put("settleAmt", settleAmountSum.getValueAsString());
	     		       resultMap.put("receiveAmt", requestAmountSum.getValueAsString());
	     		      resultList.add(resultMap);

	        			 }
	        		 }
	        	 }
	        }
	        JSONArray jso = new JSONArray();
	        if(resultList!=null && resultList.size()>0) {
	        	jso = JSONArray.fromObject(resultList);
	        }
			return jso.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("risk_message:{解析结果异常 :"+e.getMessage()+"}");
			return "";
		}
	}

	/**
	 * 关闭ElasticSearch
	 * @param client
	 */
	@PreDestroy
	public void closeESClient() {
		ESearchUtil.closeESClient(client);
	}
}
