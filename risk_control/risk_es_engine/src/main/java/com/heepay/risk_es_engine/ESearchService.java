
package com.heepay.risk_es_engine;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;

import com.fasterxml.jackson.databind.JavaType;
import com.heepay.codec.Aes;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.RiskChannelReqResult;
import com.heepay.enums.risk.RiskChannelReqType;
import com.heepay.risk.util.DateRiskUtil;

import net.sf.json.JSONObject;
import com.heepay.common.util.Constants;
/**
 * 描    述：风控系统 ElasticSearch 服务类
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2016年11月23日 上午9:36:26
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
public class ESearchService {
	
	private Configuration configs ;
	private static final Logger log = LogManager.getLogger();
	private  Client client;
	public ESearchService()
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
	/**
	 * 同一商户，不同ID，在批付时，不能对同一银行卡付款
		不同商户，不同ID，在批付时，不能对同一银行卡付款
	 * @方法说明：
	 * @时间：2017年7月31日
	 * @创建人：李震
	 */
	public String checkBatchPayRule(Map<String,String> conditionMap) {
		log.info("检查批付规则-参数："+conditionMap);//trans_type bankcard_no
		if(  conditionMap==null ||  StringUtil.isBlank( conditionMap.get("merchant_id") )  ||   StringUtil.isBlank( conditionMap.get("bankcard_no") )  
				 ) {
			log.info("检查批付规则，参数为空！直接pass");
			return "pass";
		}
		try
		{
			SearchRequestBuilder srb = null;
			srb =client.prepareSearch(configs.getProperty("es.cluster.property.index").toString())
					.setTypes(configs.getProperty("es.cluster.property.type").toString());
			Map<String,String> equalMap = new HashMap<String,String>();
			Map<String,String> notEqualMap = new HashMap<String,String>();

			equalMap.put("bankcard_no",  Aes.encryptStr(conditionMap.get("bankcard_no").toLowerCase(), Constants.QuickPay.SYSTEM_KEY).toLowerCase() );
			equalMap.put("trans_type", TransType.BATCHPAY.getValue().toLowerCase() );
			notEqualMap.put("merchant_id", conditionMap.get("merchant_id"));
			
			log.info("检查批付规则-参数："+equalMap+notEqualMap);

			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalMap,null);
			ESearchUtil.getBoolNoMatchQueryBuilder(notEqualMap,queryBuilder);
			SearchResponse response = srb
										.setQuery(queryBuilder)
						                .setExplain(false)  
						                .setSize(0)
						                .execute().actionGet();
			long length = response.getHits().totalHits();
			if( length  > 0 ) {
				log.info("批付规则，阻断，同一商户，不同ID，在批付时，不能对同一银行卡付款！");
				return "block";
			}
			log.info("检查批付规则，放行！");
   			return "pass";
		}catch (Exception e)
		{
			e.printStackTrace();
			log.error("risk_message:{"+ e.getMessage() +",method:checkBatchPayRule}");
			return "pass";
		}
		
	}
	/**
	 * 根据查询条件 得到交易列表
	 * @param fields 查询的字段
	 * @param conditionMap termQuery查询条件
	 * @param paytimecondition rangeQuery查询条件    rangeCondition 含有的字段 为 begin ,end ,field 严格遵守大小写
	 * @param page 分页信息对像
	 * @return
	 */
	public List<Map> getTransData(String[] fields,List<Map> equalConditionMap,List<Map> notequalConditonMap,List<Map> rangeCondition,Map page)
	{
		try
		{
			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			ESearchUtil.getBoolNoMatchQueryBuilder(notequalConditonMap,queryBuilder);
			ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
			
			SortOrder sortOrder = SortOrder.DESC;// 排序方式 
			SearchResponse response = client.prepareSearch(configs.getProperty("es.cluster.property.index").toString())
										.setTypes(configs.getProperty("es.cluster.property.type").toString())
										.setQuery(queryBuilder)
										.setFrom(Integer.parseInt(page.get("pagefrom").toString()))
										.setSize(Integer.parseInt(page.get("pagesize").toString()))
										.addSort("pay_time", sortOrder)
						                .setExplain(false)  //设置是否按查询匹配度排序 
						                .addFields(fields)
						                .execute().actionGet();
			
			List<Map> listresult = new ArrayList<Map>();
			page.put("totalsize", response.getHits().getTotalHits());
			 for(final SearchHit hit:response.getHits()){
				 Map<String, Object> map = new HashMap<String, Object>();
				 final Iterator<SearchHitField> iterator = hit.iterator(); 
				 while(iterator.hasNext()){
				 final SearchHitField hitfield = iterator.next();
				 map.put(hitfield.getName(),hitfield.getValue());
			 }
				 listresult.add(map);
			}
			 JsonMapperUtil jsonMapperUtil = new JsonMapperUtil();
			 log.info("risk_message:{totalsize:"+response.getHits().getTotalHits()+",mothed:getTransData}");
			return listresult;
		}catch (Exception e)
		{
			log.error("risk_message:{"+ e.getMessage() +",method:getTransData}");
		}
		return null;
	}
	/**
	 * 按交易类型得到交易成功的汇总
	 * 
	 * @param equalConditionMap 条件相等的列表
	 * @param notequalConditonMap 条件不等的列表
	 * @param rangeCondition 区间范围
	 * @return
	 */
	public Map<String,String> getAggregationInfo(List<Map> equalConditionMap,List<Map> notequalConditonMap,List<Map> rangeCondition)
	{
		SearchRequestBuilder srb = null;
		srb =client.prepareSearch(configs.getProperty("es.cluster.property.index").toString())
				.setTypes(configs.getProperty("es.cluster.property.type").toString())
				.setSearchType(SearchType.COUNT);
		BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
		ESearchUtil.getBoolNoMatchQueryBuilder(notequalConditonMap,queryBuilder);
		ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
		srb.setQuery(queryBuilder);
		TermsBuilder trans_TypeTermsBuilder = AggregationBuilders.terms("countAgg").field("trans_type");
		srb.addAggregation(trans_TypeTermsBuilder);
		SearchResponse sr = srb.execute().actionGet();
		Map<String, Aggregation> aggMap = sr.getAggregations().asMap();
		StringTerms transTypeTerms = (StringTerms) aggMap.get("countAgg");
		Iterator<Bucket> transTypeBucketIt = transTypeTerms.getBuckets().iterator();
		Map<String,String> transMap = new HashMap<String,String>();
		while(transTypeBucketIt.hasNext())
		{
			Bucket transTypeBucket = transTypeBucketIt.next();
			transMap.put(transTypeBucket.getKey().toString(), String.valueOf(transTypeBucket.getDocCount()));
			System.out.println(transTypeBucket.getKey() + ":" + transTypeBucket.getDocCount());
		}
		log.info("risk_message:{"+ transMap +",mothed:getAggregationInfo}");
		return transMap;
	}
	
	
	/**
	 * 给定商户编号 按产品，取出当日和当月的累计消费
	 * @param merchantId
	 * @param productType  CP05
	 * @param accountType 对公对私
	 * @param bankType 信用卡 还是储蓄卡
	 * @param begintime
	 * @param endtime
	 * @return
	 */
	public Map getDayAndMonthLimitAmount(String merchantId,String productType,String accountType,String bankType)
	{
		log.info("getDayAndMonthLimitAmount：merchantId:{},productType:{},accountType:{},bankType:{}", merchantId, productType, accountType, bankType);
		Date dateMonthBegin = null,dateMonthEnd=null,datetimerunbegin=null,datetimerunend=null;
		long currentDateEnd=0,currentDateBegin=0;
		try {
			datetimerunbegin = new Date();
			
			//参数转换
			Map param = paramCheck(merchantId,productType,accountType,bankType);
			//当月的开始时间
			 dateMonthBegin = DateRiskUtil.startTime(DateUtils.parseDate(DateUtils.getYear()+"-"+DateUtils.getMonthOfYear()+"-"+"1"),0,0,0);
			//当月的结束时间
			 dateMonthEnd = DateRiskUtil.startTime(DateUtils.parseDate(DateUtils.getYear()+"-"+DateUtils.getMonthOfYear()+"-"+DateRiskUtil.getCurrentMonthLastDay()),23,59,59);
			//当天的开始时间
			 currentDateBegin = DateUtils.startTime(new Date()).getTime();
			 currentDateEnd = DateUtils.endTime(new Date()).getTime();
			 //生成度量对像 存放在 aggsum
			MetricsAggregationBuilder aggregationDay =  AggregationBuilders.sum("aggsum").field("pay_amount");
			MetricsAggregationBuilder aggregationMonth = AggregationBuilders.sum("aggsum").field("pay_amount");
			//查询当天的时间范围
			RangeQueryBuilder queryCurrentDayBuilderRange= ESearchUtil.RangeQueryBuilder("pay_time",currentDateBegin,currentDateEnd,true,true);
			//查询当月的时间范围
			RangeQueryBuilder queryCurrentMonthBuilderRange= ESearchUtil.RangeQueryBuilder("pay_time",dateMonthBegin.getTime(),dateMonthEnd.getTime(),true,true);
			List paramExcludeList = amountLimitStatusExcludeList();
			
			//构造当天时间段条件查询
			BoolQueryBuilder queryBuilderlimitForCurrentDay = ESearchUtil.getBoolQueryBuilder(param);
			ESearchUtil.getBoolNoMatchQueryBuilder(paramExcludeList, queryBuilderlimitForCurrentDay); //排除失败的状态
			queryBuilderlimitForCurrentDay.must(queryCurrentDayBuilderRange);
			
			//构造当月时间段条件查询
			BoolQueryBuilder queryBuilderlimitForCurrentMonth = ESearchUtil.getBoolQueryBuilder(param);
			ESearchUtil.getBoolNoMatchQueryBuilder(paramExcludeList, queryBuilderlimitForCurrentMonth); //排除失败的状态
			queryBuilderlimitForCurrentMonth.must(queryCurrentMonthBuilderRange);  
			
			SearchRequestBuilder sbuilderForCurrentDay,sbuilderForCurrentMonth;
			MultiSearchResponse multiSearchResponse =null ;
			sbuilderForCurrentDay = ESearchUtil.getSearchRequestBuilder(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.type").toString()
					,queryBuilderlimitForCurrentDay
					,aggregationDay).setFrom(0).setSize(0);
			
			sbuilderForCurrentMonth = ESearchUtil.getSearchRequestBuilder(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.type").toString()
					,queryBuilderlimitForCurrentMonth
					,aggregationDay).setFrom(0).setSize(0);
			
			multiSearchResponse = client.prepareMultiSearch()
					.add(sbuilderForCurrentDay)
					.add(sbuilderForCurrentMonth)
					.execute().actionGet();
			
			Sum sumForCurrentDay = multiSearchResponse.getResponses()[0].getResponse().getAggregations().get("aggsum"); //当日交易总额
			Sum sumForCurrentMonth = multiSearchResponse.getResponses()[1].getResponse().getAggregations().get("aggsum"); //当月交易总额
			datetimerunend = new Date();
			log.info("risk_message:"
					+ "{merchantId:"+merchantId
					+ ",productType:"+productType
					+ ",accountType:" +accountType
					+ ",bankType:"+bankType
					 +",daybegin:"+currentDateBegin
					 +",dayend:"+currentDateEnd
					 +",monthbegin:"+dateMonthBegin.getTime()
					 + ",monthend:"+dateMonthEnd.getTime()
					 +",dayMax:"+ sumForCurrentDay.value() 
					 +",monthMax:"+ sumForCurrentMonth.value() +",runtime:"+ (datetimerunend.getTime() - datetimerunbegin.getTime()) +"}");
			Map mapResult = new HashMap();
			mapResult.put("day", sumForCurrentDay.value());
			mapResult.put("month",sumForCurrentMonth.value());
			return mapResult;
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("risk_message:{时间转换报错 :"+e.getMessage()+"}");
		}catch(Exception e)	{
			e.printStackTrace();
			log.error("risk_message:{"+e.getMessage()+"}");
		}
		return null;
	}
	public Map getDayLimitAmount(String merchantId,Map<String,List> transTypeList,int dayOffset)
	{
		log.info("getDayLimitAmount：merchantId:{}", merchantId);
		Date datetimerunbegin=null,datetimerunend=null;
		long currentDateEnd=0,currentDateBegin=0;
		try {
			datetimerunbegin = new Date();			
			//参数转换
			Map param = paramCheck(merchantId);
			//当天的开始时间
			if(dayOffset==0)
			 currentDateBegin = DateUtils.startTime(new Date()).getTime();
			else
				currentDateBegin=new Date(new Date().getTime()-(long)dayOffset * 24 * 60 * 60 * 1000).getTime();
			 currentDateEnd = DateUtils.endTime(new Date()).getTime();
			 //生成度量对像 存放在 aggsum
			MetricsAggregationBuilder aggregationDay =  AggregationBuilders.sum("aggsum").field("pay_amount");
			//查询当天的时间范围
			RangeQueryBuilder queryCurrentDayBuilderRange= ESearchUtil.RangeQueryBuilder("pay_time",currentDateBegin,currentDateEnd,true,true);
			List paramExcludeList = amountLimitStatusExcludeList();
			
			//构造当天时间段条件查询
			BoolQueryBuilder queryBuilderlimitForCurrentDay = ESearchUtil.getMultiBoolQueryBuilder(param, transTypeList);
			ESearchUtil.getBoolNoMatchQueryBuilder(paramExcludeList, queryBuilderlimitForCurrentDay); //排除失败的状态
			queryBuilderlimitForCurrentDay.must(queryCurrentDayBuilderRange);
				
			SearchRequestBuilder sbuilderForCurrentDay,sbuilderForCurrentMonth;
			MultiSearchResponse multiSearchResponse =null ;
			sbuilderForCurrentDay = ESearchUtil.getSearchRequestBuilder(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.type").toString()
					,queryBuilderlimitForCurrentDay
					,aggregationDay).setFrom(0).setSize(0);
			
				
			multiSearchResponse = client.prepareMultiSearch()
					.add(sbuilderForCurrentDay)
					.execute().actionGet();
			
			Sum sumForCurrentDay = multiSearchResponse.getResponses()[0].getResponse().getAggregations().get("aggsum"); //当日交易总额
			datetimerunend = new Date();
			log.info("risk_message:"
					+ "{merchantId:"+merchantId
					 +",daybegin:"+currentDateBegin
					 +",dayend:"+currentDateEnd
					 +",dayMax:"+ sumForCurrentDay.value() 
					 +",runtime:"+ (datetimerunend.getTime() - datetimerunbegin.getTime()) +"}");
			Map mapResult = new HashMap();
			mapResult.put("day", sumForCurrentDay.value());
			return mapResult;
		} catch(Exception e)	{
			e.printStackTrace();
			log.error("risk_message:{"+e.getStackTrace()+"}");
		}
		return null;
	}
	/**
	 * 参数转换成 Map
	 * @param merchantId
	 * @param productType
	 * @param accountType
	 * @param bankType
	 * @return
	 */
	public Map paramCheck(String merchantId,String productType,String accountType,String bankType)
	{
		Map param = new HashMap();
		if (StringUtil.notBlank(merchantId))
		{
			param.put("merchant_id", merchantId); //商户号
		}
		if (StringUtil.notBlank(productType))
		{
			productType = productType.toLowerCase();//转小写
			param.put("product_code", productType); //产品编号
		}
		if (StringUtil.notBlank(accountType))
		{
			param.put("bankcard_owner_type", accountType); //对公对私
		}
		if (StringUtil.notBlank(bankType))
		{
			bankType = bankType.toLowerCase();//转小写
			param.put("bankcard_type", bankType); //对公对私
		}
		return param;
	}
	public Map paramCheck(String merchantId)
	{
		Map param = new HashMap();
		if (StringUtil.notBlank(merchantId))
		{
			param.put("merchant_id", merchantId); //商户号
		}
		return param;
	}
	/**
	 * 关闭ElasticSearch
	 * @param client
	 */
	@PreDestroy
	public void closeESClient() {
		ESearchUtil.closeESClient(client);
	}
	/**
	 * 限额状态排除参数列表
	 * @return
	 */
	public List amountLimitStatusExcludeList()
	{
		List paramExcludeList = new ArrayList(); //排状态
		Map paramExclude = new HashMap();
		paramExclude.put("pay_status", "AUDREJ".toLowerCase()); //审核拒绝
		paramExcludeList.add(paramExclude);
		paramExclude = new HashMap();
		paramExclude.put("pay_status", "FAILED".toLowerCase()); //交易失败
        paramExcludeList.add(paramExclude);
        return paramExcludeList;
	}
	
	/**
	 * 李震 begin
	 */
	/**
	 * 
	 * @方法说明：获取通道日限额和月限额
	 * @时间：2017年5月27日
	 * @创建人：李震
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getChannelDayAndMonthLimitAmount(JSONObject json) {
		Map<String, List<Map <String,Object>> > sdf;
		JsonMapperUtil jmu = new JsonMapperUtil();
		JavaType jatype = jmu.createCollectionType(Map.class, String.class,Object.class);
		Map<String,Object> param = jmu.fromJson(json==null?"":json.toString(), jatype ) ;
		log.info("getChannelDayAndMonthLimitAmount：param="+param,param);
		Date dateMonthBegin = null,dateMonthEnd=null,datetimerunbegin=null,datetimerunend=null;
		long currentDateEnd=0,currentDateBegin=0;
		datetimerunbegin = new Date();
		log.info("getChannelDayAndMonthLimitAmount："
				+ "channelCode:{}", param.get("channel_code"));
		try {
		//当月的开始时间
		 dateMonthBegin = DateRiskUtil.startTime(DateUtils.parseDate(DateUtils.getYear()+"-"+DateUtils.getMonthOfYear()+"-"+"1"),0,0,0);
		//当月的结束时间
		 dateMonthEnd = DateRiskUtil.startTime(DateUtils.parseDate(DateUtils.getYear()+"-"+DateUtils.getMonthOfYear()+"-"+DateRiskUtil.getCurrentMonthLastDay()),23,59,59);
		//当天的开始时间
		 currentDateBegin = DateUtils.startTime(new Date()).getTime();
		 currentDateEnd = DateUtils.endTime(new Date()).getTime();
		 //生成度量对像 存放在 aggsum
		MetricsAggregationBuilder aggregationDay =  AggregationBuilders.sum("aggsum_channel").field("amount");//汇总订单金额
		MetricsAggregationBuilder aggregationMonth =  AggregationBuilders.sum("aggsum_channel_month").field("amount");//汇总订单金额
		//查询当天的时间范围
		RangeQueryBuilder queryCurrentDayBuilderRange= ESearchUtil.RangeQueryBuilder("req_time",currentDateBegin,currentDateEnd,true,true);
		//查询当月的时间范围
		RangeQueryBuilder queryCurrentMonthBuilderRange= ESearchUtil.RangeQueryBuilder("req_time",dateMonthBegin.getTime(),dateMonthEnd.getTime(),true,true);
		param.put("request_status", RiskChannelReqType.QUOTA.getValue());//查询类型为QUOTA的数据
		//构造当天时间段条件查询
		BoolQueryBuilder queryBuilderlimitForCurrentDay = ESearchUtil.getBoolQueryBuilder(param);
		queryBuilderlimitForCurrentDay.must(queryCurrentDayBuilderRange);
		
		//构造当月时间段条件查询
		BoolQueryBuilder queryBuilderlimitForCurrentMonth = ESearchUtil.getBoolQueryBuilder(param);
		queryBuilderlimitForCurrentMonth.must(queryCurrentMonthBuilderRange);  
		
		SearchRequestBuilder sbuilderForCurrentDay,sbuilderForCurrentMonth;
		MultiSearchResponse multiSearchResponse =null ;
		//如果参数为空，则分组查询每个通道的限额
		if( param.get("channel_code")==null || "".equals(param.get("channel_code")) ) {
			log.info("getChannelDayAndMonthLimitAmount：param="+json,json);
			
			TermsBuilder channel_codeDayTermBuilder = AggregationBuilders.terms("channel_codeDayTerm").field("channel_code").size(10000);
			TermsBuilder channel_codeMonthTermBuilder = AggregationBuilders.terms("channel_codeMonthTerm").field("channel_code").size(10000);
			
			channel_codeDayTermBuilder.subAggregation(aggregationDay);
			channel_codeMonthTermBuilder.subAggregation(aggregationMonth);
			sbuilderForCurrentDay = ESearchUtil.getSearchRequestBuilderNew(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.typeChannel").toString()
					,queryBuilderlimitForCurrentDay
					,channel_codeDayTermBuilder).setFrom(0).setSize(0);
			
			sbuilderForCurrentMonth = ESearchUtil.getSearchRequestBuilderNew(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.typeChannel").toString()
					,queryBuilderlimitForCurrentMonth
					,channel_codeMonthTermBuilder).setFrom(0).setSize(0);
			multiSearchResponse = client.prepareMultiSearch()
					.add(sbuilderForCurrentDay)
					.add(sbuilderForCurrentMonth)
					.execute().actionGet();
			int x =0;
			List<Map<String,Object>> dayList = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> monthList = new ArrayList<Map<String,Object>>();
			Map allResult = new HashMap();
			for (MultiSearchResponse.Item item : multiSearchResponse.getResponses()) {
				x=x+1;
	            SearchResponse searchResponse = item.getResponse();  
	            Terms channel_codeDayTerm =  searchResponse.getAggregations().get("channel_codeDayTerm");
	            Terms channel_codeMonthTerm =  searchResponse.getAggregations().get("channel_codeMonthTerm");
	            if( channel_codeDayTerm!=null){
	            	for(int d=0;d<channel_codeDayTerm.getBuckets().size();d++){
						 Terms.Bucket buc =null;
						 buc = channel_codeDayTerm.getBuckets().get(d);
		        		Sum aggsum_channel = null;
		        		aggsum_channel = buc.getAggregations().get("aggsum_channel");
		        		Map mapResult = new HashMap();
		        		mapResult.put("channel_code", buc.getKey());
		        		BigDecimal b = new BigDecimal(0);  
						b = new BigDecimal( aggsum_channel.getValue() );
					    //保留2位小数  
		    			mapResult.put("day", b.setScale(2, BigDecimal.ROUND_HALF_UP));
		    			dayList.add(mapResult);
			            log.info("多通道日限额聚合结果channel_code="+buc.getKey()+",dayMoney="+mapResult.get("day")  );
					 }
	            }
	            if( channel_codeMonthTerm!=null){
	            	for(int d=0;d<channel_codeMonthTerm.getBuckets().size();d++){
						 Terms.Bucket buc =null;
						 buc = channel_codeMonthTerm.getBuckets().get(d);
		        		Sum aggsum_channel = null;
		        		aggsum_channel = buc.getAggregations().get("aggsum_channel_month");
		        		Map mapResult = new HashMap();
		        		mapResult.put("channel_code", buc.getKey());
		        		BigDecimal b = new BigDecimal(0);  
						b = new BigDecimal( aggsum_channel.getValue() );
		    			mapResult.put("month",  b.setScale(2, BigDecimal.ROUND_HALF_UP) );
		    			monthList.add(mapResult);
			            log.info("多通道月限额聚合结果channel_code="+buc.getKey()+",monthMoney="+mapResult.get("month") );
					 }
	            }
	            
			}
			allResult.put("dayList", dayList);
			allResult.put("monthList", monthList);
			return allResult;
		}else {
			
			sbuilderForCurrentDay = ESearchUtil.getSearchRequestBuilder(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.typeChannel").toString()
					,queryBuilderlimitForCurrentDay
					,aggregationDay).setFrom(0).setSize(0);
			
			sbuilderForCurrentMonth = ESearchUtil.getSearchRequestBuilder(client,configs.getProperty("es.cluster.property.index").toString()
					,configs.getProperty("es.cluster.property.typeChannel").toString()
					,queryBuilderlimitForCurrentMonth
					,aggregationDay).setFrom(0).setSize(0);
			multiSearchResponse = client.prepareMultiSearch()
					.add(sbuilderForCurrentDay)
					.add(sbuilderForCurrentMonth)
					.execute().actionGet();
			Sum sumForCurrentDay = multiSearchResponse.getResponses()[0].getResponse().getAggregations().get("aggsum_channel"); //当日交易总额
			Sum sumForCurrentMonth = multiSearchResponse.getResponses()[1].getResponse().getAggregations().get("aggsum_channel"); //当月交易总额
			datetimerunend = new Date();
			Map mapResult = new HashMap();
			BigDecimal b1 = new BigDecimal(0);  
			b1 = new BigDecimal( sumForCurrentDay.value() );
			BigDecimal b2 = new BigDecimal(0);  
			b2 = new BigDecimal( sumForCurrentMonth.value() );
			mapResult.put("day", b1.setScale(2, BigDecimal.ROUND_HALF_UP));
			mapResult.put("month",b2.setScale(2, BigDecimal.ROUND_HALF_UP));
			mapResult.put("channel_code", json.getString("channel_code"));
			log.info("单通道限额汇总结果channel_code="+json.getString("channel_code")+",dayMoney="+mapResult.get("day")+",monthMoney="+mapResult.get("month")  );
			return mapResult;
		}	
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("getChannelDayAndMonthLimitAmount,risk_message:{时间转换报错 :"+e.getMessage()+"}");
		}catch(Exception e)	{
			e.printStackTrace();
			log.error("getChannelDayAndMonthLimitAmount,risk_message:{"+e.getMessage()+"}");
		}
		return null;
	}
	/**
	 * 获取通道信息列表
	 * @方法说明：
	 * @时间：2017年5月27日
	 * @创建人：李震
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getChannelList(String[] fields,Map equalConditionMap,Map notequalConditonMap,Map rangeCondition,Map page) {
		if(  fields==null){
			log.info("要查询的字段为空！");
			return null;
		}
		try
		{
			if( equalConditionMap!=null && ( equalConditionMap.get("channel_code")==null || "".equals( equalConditionMap.get("channel_code") ) ) ) {
				equalConditionMap.remove("channel_code");
			}
			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			ESearchUtil.getBoolNoMatchQueryBuilder(notequalConditonMap,queryBuilder);
			/**
			 * 判断范围查询条件是否为空
			 */
			if( rangeCondition!=null) {
				if(  rangeCondition.get("begin")==null || "".equals( rangeCondition.get("begin").toString().trim()  ) ||
						rangeCondition.get("end")==null || "".equals( rangeCondition.get("end").toString().trim()  ) || 
								rangeCondition.get("field")==null || "".equals( rangeCondition.get("field").toString().trim()  ) ){
					rangeCondition = null;
				}
			}
			int pagefrom = 0;
			int pagesize = 0;
			if(page!=null ) {
				pagefrom = StringUtils.toInteger(page.get("pagefrom"));
				pagesize = StringUtils.toInteger(page.get("pagesize"));
			}
			ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
			
			SortOrder sortOrder = SortOrder.DESC;// 排序方式 
			SearchRequestBuilder requestBuilder =client.prepareSearch(configs.getProperty("es.cluster.property.index").toString())
					.setTypes(configs.getProperty("es.cluster.property.typeChannel").toString())
					.setQuery(queryBuilder) ;
			if( pagesize>0 ){
				requestBuilder.setFrom( pagefrom ).setSize( pagesize );
			}
			SearchResponse response = requestBuilder.addSort("req_time", sortOrder)
						                .setExplain(false)  //设置是否按查询匹配度排序 
						                .addFields(fields)
						                .execute().actionGet();
			
			List<Map> listresult = new ArrayList<Map>();
			if(page!=null){
				page.put("totalsize", response.getHits().getTotalHits());
			}
			 for(final SearchHit hit:response.getHits()){
				 Map<String, Object> map = new HashMap<String, Object>();
				 final Iterator<SearchHitField> iterator = hit.iterator(); 
				 while(iterator.hasNext()){
				 final SearchHitField hitfield = iterator.next();
				 if(  "amount".equals(hitfield.getName()) ||  "day_limit_amount".equals(hitfield.getName()) || "month_limit_amount".equals(hitfield.getName())){
					BigDecimal value = new BigDecimal(0);  
					value = new BigDecimal(hitfield.getValue().toString() );
					map.put(hitfield.getName(),value.setScale(2, BigDecimal.ROUND_HALF_UP));
				 }else{
					 map.put(hitfield.getName(),hitfield.getValue());
				 }
				 
			 }
				 listresult.add(map);
			}
			 
			 log.info("risk_message:{totalsize:"+response.getHits().getTotalHits()+",mothed:getChannelList}");
			return listresult;
		}catch (Exception e)
		{
			e.printStackTrace();
			log.error("risk_message:{"+ e.getMessage() +",method:getChannelList}");
		}
		return null;
	}
	
	/**
	 * 得到通道交易的汇总 按交易结果result 分组 
	 * @方法说明：
	 * @时间：2017年5月27日
	 * @创建人：李震
	 */
	public List<Map<String,String>> getChannelAggregationInfo(Map equalConditionMap,Map notequalConditonMap,Map rangeCondition)
	{
		SearchRequestBuilder srb = null;
		srb =client.prepareSearch(configs.getProperty("es.cluster.property.index").toString())
				.setTypes(configs.getProperty("es.cluster.property.typeChannel").toString())
				.setSearchType(SearchType.COUNT);
		if( equalConditionMap!=null && ( equalConditionMap.get("channel_code")==null || "".equals( equalConditionMap.get("channel_code") ) ) ) {
			equalConditionMap.remove("channel_code");
		}
		BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
		ESearchUtil.getBoolNoMatchQueryBuilder(notequalConditonMap,queryBuilder);
		/**
		 * 判断范围查询条件是否为空
		 */
		if( rangeCondition!=null) {
			if(  rangeCondition.get("begin")==null || "".equals( rangeCondition.get("begin").toString().trim()  ) ||
					rangeCondition.get("end")==null || "".equals( rangeCondition.get("end").toString().trim()  ) || 
							rangeCondition.get("field")==null || "".equals( rangeCondition.get("field").toString().trim()  ) ){
				rangeCondition = null;
			}
		}
		ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
		srb.setQuery(queryBuilder);
		TermsBuilder resultTermsBuilder = AggregationBuilders.terms("resultTerm").field("result").size(10000);//聚合结果由默认的top10改成10000
		resultTermsBuilder.subAggregation(AggregationBuilders.terms("request_statusTerm").field("request_status").size(10000).subAggregation(AggregationBuilders.terms("channel_codeTerm").field("channel_code").size(10000)));
		
		srb.addAggregation(resultTermsBuilder);
		SearchResponse sr = srb.setFrom( 0 ).setSize(0).execute().actionGet();
		
		Terms resultTerm=  sr.getAggregations().get("resultTerm");
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		
		for(int c=0;c<resultTerm.getBuckets().size();c++){
			 Terms.Bucket resultBuc =null;
			 resultBuc = resultTerm.getBuckets().get(c);
			 Terms request_statusTerm =  resultBuc.getAggregations().get("request_statusTerm");
			 for(int d=0;d<request_statusTerm.getBuckets().size();d++){
				Terms.Bucket request_statusBuc =null;
				request_statusBuc = request_statusTerm.getBuckets().get(d);
				Terms channel_codeTerm =  request_statusBuc.getAggregations().get("channel_codeTerm");
				for(int f=0;f<channel_codeTerm.getBuckets().size();f++){
					Terms.Bucket channel_codeBuc =null;
					channel_codeBuc = channel_codeTerm.getBuckets().get(f);
					Map<String,String> resultMap = new HashMap<String,String>();
					resultMap.put("result", (String)resultBuc.getKey());
					resultMap.put("request_status", (String)request_statusBuc.getKey());
					resultMap.put("channel_code", (String)channel_codeBuc.getKey());
					resultMap.put("count", String.valueOf(channel_codeBuc.getDocCount()));
					resultList.add(resultMap);
					log.info("resultMap:"+ resultMap );
				}
			 }
		}
		return resultList;
	}
	/**
	 * 得到通道交易的汇总 增加按主机名分组
	 * @方法说明：
	 * @时间：2017年8月17日
	 * @创建人：李震
	 */
	public List<Map<String,String>> getChannelAggInfoWithHost(Map equalConditionMap,Map notequalConditonMap,Map rangeCondition)
	{
		SearchRequestBuilder srb = null;
		srb =client.prepareSearch(configs.getProperty("es.cluster.property.index").toString())
				.setTypes(configs.getProperty("es.cluster.property.typeChannel").toString())
				.setSearchType(SearchType.COUNT);
		if( equalConditionMap!=null && ( equalConditionMap.get("channel_code")==null || "".equals( equalConditionMap.get("channel_code") ) ) ) {
			equalConditionMap.remove("channel_code");
		}
		BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
		ESearchUtil.getBoolNoMatchQueryBuilder(notequalConditonMap,queryBuilder);
		/**
		 * 判断范围查询条件是否为空
		 */
		if( rangeCondition!=null) {
			if(  rangeCondition.get("begin")==null || "".equals( rangeCondition.get("begin").toString().trim()  ) ||
					rangeCondition.get("end")==null || "".equals( rangeCondition.get("end").toString().trim()  ) || 
							rangeCondition.get("field")==null || "".equals( rangeCondition.get("field").toString().trim()  ) ){
				rangeCondition = null;
			}
		}
		ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
		srb.setQuery(queryBuilder);
		TermsBuilder resultTermsBuilder = AggregationBuilders.terms("resultTerm").field("result").size(10000);//聚合结果由默认的top10改成10000
		resultTermsBuilder.
				subAggregation(AggregationBuilders.terms("request_statusTerm").field("request_status").size(10000).
						subAggregation(AggregationBuilders.terms("channel_codeTerm").field("channel_code").size(10000).
								subAggregation(AggregationBuilders.terms("hostTerm").field("host").size(10000))) );
		
		srb.addAggregation(resultTermsBuilder);
		SearchResponse sr = srb.setFrom( 0 ).setSize(0).execute().actionGet();
		long hitnum = sr.getHits().getTotalHits();
		Terms resultTerm=  sr.getAggregations().get("resultTerm");
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		
		for(int c=0;c<resultTerm.getBuckets().size();c++){
			 Terms.Bucket resultBuc =null;
			 resultBuc = resultTerm.getBuckets().get(c);
			 Terms request_statusTerm =  resultBuc.getAggregations().get("request_statusTerm");
			 for(int d=0;d<request_statusTerm.getBuckets().size();d++){
				Terms.Bucket request_statusBuc =null;
				request_statusBuc = request_statusTerm.getBuckets().get(d);
				Terms channel_codeTerm =  request_statusBuc.getAggregations().get("channel_codeTerm");
				for(int f=0;f<channel_codeTerm.getBuckets().size();f++){
					Terms.Bucket channel_codeBuc =null;
					channel_codeBuc = channel_codeTerm.getBuckets().get(f);
					Terms hostTerm = channel_codeBuc.getAggregations().get("hostTerm");
					for(int g=0;g<hostTerm.getBuckets().size();g++) {
						Terms.Bucket hostBuc =null;
						hostBuc = hostTerm.getBuckets().get(g) ;
						Map<String,String> resultMap = new HashMap<String,String>();
						resultMap.put("result", (String)resultBuc.getKey());
						resultMap.put("request_status", (String)request_statusBuc.getKey());
						resultMap.put("channel_code", (String)channel_codeBuc.getKey());
						resultMap.put("host", (String)hostBuc.getKey());
						resultMap.put("count", String.valueOf(hostBuc.getDocCount()));
						resultList.add(resultMap);
						log.info("resultMap:"+ resultMap );
					}
				}
			 }
		}
		if( hitnum==0 ) {
			String[] defaultHosts  =  configs.getProperty("defaultHosts").toString().split("_");
			for( String host : defaultHosts ) {
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("result", "");
				resultMap.put("request_status", "" );
				resultMap.put("channel_code", "" );
				resultMap.put("host", host);
				resultMap.put("count", "0");
				log.info("resultMap:"+ resultMap );
				resultList.add(resultMap);
			}
		}
//		{result=success, request_status=null, host=gateway, count=25, channel_code=708unopay_10481}
		return resultList;
	}
	public static void main(String[] args)  throws Exception {
		
		/*for(int i=0;i<32;i++) {
			StringBuffer sb = new StringBuffer( "2017-06-01 14:22:22,976 INFO " );
			sb.append("risk_message:");
			sb.append("{payment_id:100"+i);    
			sb.append(",channel_code:2017"); 
			sb.append(",channel_partner_code:300"+i);
			sb.append(",channel_partner_name:400"+i);
			sb.append(",channel_type_code:500"+i);
			sb.append(",channel_type_name:700"+i);
			sb.append(",bank_no:600"+i);
			sb.append(",bank_name:800"+i);
			sb.append(",account_type:900"+i);
			sb.append(",business_type:1100"+i);
			sb.append(",amount:13"+i);
			sb.append(",day_limit_amount:0");
			sb.append(",month_limit_amount:0");
			sb.append(",req_time:"+new Date().getTime());
			sb.append(",resp_time:"+new Date().getTime());
			sb.append(",request_status:0");
			if(i%2==0){
				sb.append(",result:SUCCESS"); 
			}else{
				sb.append(",result:FAILURE"); 
			}
			sb.append(",url:www.tsd.com");
			sb.append("}");
			System.out.println(sb.toString());
			try {
				Thread.sleep(200l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		System.out.println( DateUtils.getStrDate("2017-07-14 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime() );
		System.out.println( DateUtils.getStrDate("2017-08-15 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime() );

		System.out.println( DateUtils.getStrDate("2017-08-22 08:00:00", "yyyy-MM-dd HH:mm:ss").getTime() );
		System.out.println( DateUtils.getStrDate("2017-08-22 23:59:59", "yyyy-MM-dd HH:mm:ss").getTime() );
		System.out.println( DateUtils.startTime(new Date()).getTime() );
		System.out.println( DateUtils.endTime(new Date()).getTime() );
		
	}
}

