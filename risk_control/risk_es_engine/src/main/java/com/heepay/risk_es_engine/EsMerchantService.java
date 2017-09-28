/**
 * 
 */
package com.heepay.risk_es_engine;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JavaType;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;

import net.sf.json.JSONArray;


/**
 * @author 李震
 *
 */
@Component
public class EsMerchantService {
	
	private Configuration configs ;
	private static final Logger log = LogManager.getLogger();
	private  Client client;
	private static String propIndex = "es.cluster.property.indexMonitorOrder";
	private static String propType = "es.cluster.property.typeMonitorOrder";
	private static String transOrderIndex = "es.cluster.property.index";
	private static String transOrderType = "es.cluster.property.type";
	private static String syslogIndex = "es.cluster.property.syslogsIndex";
	private static String defaultHostsProp="defaultHosts";
	public EsMerchantService()
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
			e.printStackTrace();
			log.error("risk_message:{"+e.getMessage()+"}");
		}
	}
	/**
	 * 索引商户订单数据
	 * @param json 
	 * @return
	 */
	public boolean indexMerchantOrder( String json ) {
		 if( StringUtils.isBlank(json) ) {
			 log.info( "参数为空！" );
			 return false;
		 }
	     try {
			IndexRequestBuilder   builder  = client.prepareIndex( configs.getString(propIndex),  configs.getString(propType) )  
			            /*.setId( JSONObject.parseObject(json).getString("transNo")  )*/.setSource(json) ;
			builder.execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
			log.error( "索引数据异常！"+e.getMessage() );
			return false;
		}  
		return true;
	}
	/**
	 * 商户某时间段内的订单个数-查询 (外部正式商户无订单报警)
	 * @param equalParam
	 * @return
	 */
	public List<Map> queryMerMoniOrderCount( String equalParam  )
	{
		log.info("商户总订单个数-查询-equalParam："+equalParam);
		if(  StringUtils.isBlank(equalParam)) {
			log.info("商户总订单个数-查询错误，参数为空！");
			return null;
		}
		String groupFields = "host,transType";
		String [] str = groupFields.split(",");
		try
		{
			JsonMapperUtil jmu =  new JsonMapperUtil();
			JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
			Map<String,String> equalConditionMap = jmu.fromJson(equalParam, jatype ) ;
			
			Map<String,String> rangeCondition =  null;
			if( !StringUtils.isBlank(equalConditionMap.get("beginTime")) && !StringUtils.isBlank(equalConditionMap.get("endTime"))) {
				rangeCondition =  new HashMap<String,String>();
				rangeCondition.put("begin", equalConditionMap.get("beginTime"));
				rangeCondition.put("end", equalConditionMap.get("endTime"));
				rangeCondition.put("field", "createTime");
			}
			
			equalConditionMap.remove("beginTime");
			equalConditionMap.remove("endTime");
			
			SearchRequestBuilder srb = null;
			srb =client.prepareSearch(configs.getProperty(propIndex).toString())
					.setTypes(configs.getProperty(propType).toString()) ;
			
			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilderNoAnalyze(equalConditionMap,null);
			ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
			srb.setQuery(queryBuilder);
			
			TermsBuilder termsBuilder =  getTermsBuilder(str,0);
			srb.addAggregation(termsBuilder.size(10000));
			
			SearchResponse response = srb
										.setFrom(0)
										.setSize(100)
						                .setExplain(false)  //设置是否按查询匹配度排序 
						                .execute().actionGet();
			
			List<Map> listMap = getAggregationInfoSum(response,str,0);
			
			
			
//			if(response==null || response.getHits()==null ){
//				return 0;
//			}
//			log.info("merchantMonitor:{totalsize:"+response.getHits().getTotalHits()+",method:queryMerMoniOrderCount }");
//			return response.getHits().getTotalHits() ;
	        return listMap;
		}catch (Exception e){
			e.printStackTrace();
			log.error("merchantMonitor:{"+ e.getMessage() +",method:queryMerMoniOrderCount }");
			return null;
		}
	}
	
	/**
	 * 查询商户交易成功的订单笔数 (外部正式商户无订单报警)
	 * @param conditionJson
	 * @param groupFields
	 * @return
	 */
	public List<Map> querySucMerMoniOrderCount( String equalParam )
	{
		log.info("商户交易成功订单个数-查询-equalParam："+equalParam);
		if(  StringUtils.isBlank(equalParam)) {
			log.info("商户交易成功订单汇总数据-查询错误，参数为空！");
			return null;
		}
		String groupFields = "host,trans_type";
		String [] str = groupFields.split(",");
		try
		{
			JsonMapperUtil jmu =  new JsonMapperUtil();
			JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
			Map<String,String> equalConditionMap = jmu.fromJson(equalParam, jatype ) ;
			
			Map<String,String> rangeCondition =  null;
			if( !StringUtils.isBlank(equalConditionMap.get("beginTime")) && !StringUtils.isBlank(equalConditionMap.get("endTime"))) {
				rangeCondition =  new HashMap<String,String>();
				rangeCondition.put("begin", equalConditionMap.get("beginTime"));
				rangeCondition.put("end", equalConditionMap.get("endTime"));
				rangeCondition.put("field", "pay_time");
			}
			
			equalConditionMap.remove("beginTime");
			equalConditionMap.remove("endTime");
			
			SearchRequestBuilder srb = null;
			srb = client.prepareSearch(configs.getProperty(transOrderIndex).toString())
					.setTypes(configs.getProperty(transOrderType).toString());
			equalConditionMap.put("pay_status", "succes") ;
			BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
			
			srb.setQuery(queryBuilder);
			
			TermsBuilder termsBuilder =  getTermsBuilder(str,0);
			srb.addAggregation(termsBuilder.size(10000));
			
			SearchResponse response = srb
										.setFrom(0)
										.setSize(100)
						                .setExplain(false)  //设置是否按查询匹配度排序 
						                .execute().actionGet();
			List<Map> listMap = getAggregationInfoSum(response,str,0);
			
			return listMap;
//			if(response==null || response.getHits()==null ){
//				return 0;
//			}
//			log.info("merchantMonitor:{totalsize:"+response.getHits().getTotalHits()+",method:querySucMerMoniOrderCount }");
//			return response.getHits().getTotalHits() ;
		}catch (Exception e){
			e.printStackTrace();
			log.error("merchantMonitor:{"+ e.getMessage() +",method:querySucMerMoniOrderCount }");
			return null;
		}
	}
	/**
	 * 查询成功订单个数和总订单个数 (外部正式商户无订单报警)
	 * @方法说明：
	 * @时间：2017年8月11日
	 * @创建人：李震
	 */
	public Map<String,List<Map>>  getOrderCount( String equalParam1,String  equalParam2) {
		String[] defaultHosts  =  configs.getProperty(defaultHostsProp).toString().split("_");
		Map<String,List<Map>> map = new HashMap<String,List<Map>>();
		List<Map> sucList = null;
		sucList = this.querySucMerMoniOrderCount(equalParam1);
		List<Map> allList = null;
		allList =  this.queryMerMoniOrderCount(equalParam2) ;
		Map submap = null;
		if( sucList==null || sucList.size()==0 ){
			sucList = new ArrayList<Map>();
			for( String host : defaultHosts ) {
				submap= new HashMap();
				submap.put("trans_type", "" );
				submap.put("orderCount", "" );
				submap.put("host", host );
				sucList.add( submap );
			}
		}
		if( allList==null || allList.size()==0 ){
			allList = new ArrayList<Map>();
			for( String host : defaultHosts ) {
				submap= new HashMap();
				submap.put("transType", "" );
				submap.put("orderCount", "" );
				submap.put("host", host );
				allList.add( submap );
			}
		}
		
		map.put("sucList", sucList);
		map.put("allList", allList);
		log.info("查询订单成功数量和总数量，map="+map.toString());
		return map;
	}
	/**
	 * 判断一段时间内订单是否存在
	 * @方法说明：
	 * @时间：2017年8月11日
	 * @创建人：李震
	 */
	public Map<String,String> isExistsMerchantOrderLog (String equalParam) {
		JsonMapperUtil jmu =  new JsonMapperUtil();
		JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
		Map<String,String> equalConditionMap = jmu.fromJson(equalParam, jatype ) ;
		Map<String,String> rangeCondition =  null;
		if( !StringUtils.isBlank(equalConditionMap.get("beginTime")) && !StringUtils.isBlank(equalConditionMap.get("endTime"))) {
			rangeCondition =  new HashMap<String,String>();
			rangeCondition.put("begin", equalConditionMap.get("beginTime"));
			rangeCondition.put("end", equalConditionMap.get("endTime"));
			rangeCondition.put("field", "@timestamp");
		}
		equalConditionMap.remove("beginTime");
		equalConditionMap.remove("endTime");
		Date beginDate = null ;
		try {
			beginDate = DateUtils.getStrDate( rangeCondition.get("begin"), "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/**查看payment日志**/
		String paymentType = "payment_server-"+DateUtils.format(beginDate, "yyyy.MM") ;
		rangeCondition = getUTCTime( rangeCondition );
		String[] defaultHosts  =  configs.getProperty(defaultHostsProp).toString().split("_");
		List<String> hostList = new ArrayList<String>();
		for( int i=0;i<defaultHosts.length;i++) {
			equalConditionMap.put("host", defaultHosts[i]);
			BoolQueryBuilder queryBuilder = null;
			queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			ESearchUtil.getBoolRangeQueryBuilder2(rangeCondition,queryBuilder);
			SearchResponse response = client.prepareSearch(        configs.getProperty(syslogIndex).toString()   )
					.setTypes( paymentType )
					.setQuery(queryBuilder)
					.setFrom(0)
					.setSize(100)
	                .setExplain(false)  //设置是否按查询匹配度排序 
	                .execute().actionGet();
			boolean hasLog = true;
			if(response==null || response.getHits()==null  ){
				log.info ("主机"+ defaultHosts[i]+"，查看payment日志是否生成，结果为空！");
				hasLog = false;
			}else if ( response.getHits().getTotalHits()<=0 ){
				log.info ("主机"+ defaultHosts[i]+"，查看payment日志是否生成，结果集大小为0！");
				hasLog = false;
			}else{
				log.info ("主机"+ defaultHosts[i]+"，有payment日志生成");
			}
			if(hasLog==false) {
				hostList.add(defaultHosts[i]);
			}
		}
		StringBuffer result = new StringBuffer("");
		for(int i=0;i<hostList.size();i++) {
			if(i==0) {
				result.append(hostList.get(i));
			}else{
				result.append(","+hostList.get(i));
			}
		}
		log.info( "检查是否有payment日志生成，没有payment日志生成的主机：" + result);
		/**查看gateway日志**/
		String gatewayType = "gateway_server-"+DateUtils.format(beginDate, "yyyy.MM") ;
		List<String> hostList_gateway = new ArrayList<String>();
		for( int i=0;i<defaultHosts.length;i++) {
			equalConditionMap.put("host", defaultHosts[i]);
			BoolQueryBuilder queryBuilder = null;
			queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
			ESearchUtil.getBoolRangeQueryBuilder2(rangeCondition,queryBuilder);
			SearchResponse response = client.prepareSearch(        configs.getProperty(syslogIndex).toString()   )
					.setTypes( gatewayType )
					.setQuery(queryBuilder)
					.setFrom(0)
					.setSize(100)
	                .setExplain(false)  //设置是否按查询匹配度排序 
	                .execute().actionGet();
			boolean hasLog = true;
			if(response==null || response.getHits()==null  ){
				log.info ("主机"+ defaultHosts[i]+"，查看gateway日志是否生成，结果为空！");
				hasLog = false;
			}else if ( response.getHits().getTotalHits()<=0 ){
				log.info ("主机"+ defaultHosts[i]+"，查看gateway日志是否生成，结果集大小为0！");
				hasLog = false;
			}else{
				log.info ("主机"+ defaultHosts[i]+"，有gateway日志生成");
			}
			if(hasLog==false) {
				hostList_gateway.add(defaultHosts[i]);
			}
		}
		StringBuffer result_gateway = new StringBuffer("");
		for(int i=0;i<hostList_gateway.size();i++) {
			if(i==0) {
				result_gateway.append(hostList_gateway.get(i));
			}else{
				result_gateway.append(","+hostList_gateway.get(i));
			}
		}
		log.info( "检查是否有gateway日志生成，没有gateway日志生成的主机：" + result_gateway);
		
		Map<String,String> resMap = new HashMap<String,String>();
		
		resMap.put("payment_server", result.toString() );
		resMap.put("gateway_server", result_gateway.toString() );
		return resMap ;
	}
	public static void main(String[] args) throws Exception {
		Map<String,String> rangeCondition = new HashMap<String,String>();
		rangeCondition.put("begin", "2017-08-01 22:00:05");
		rangeCondition.put("end", "2017-08-03 22:00:05");
		System.out.println( EsMerchantService.getUTCTime(rangeCondition) );
	}
	/**
	 * 获取utc时间
	 * @方法说明：
	 * @时间：2017年8月11日
	 * @创建人：李震
	 */
	public static Map<String,String> getUTCTime( Map<String,String> rangeCondition )  {  
		String begin = rangeCondition.get("begin");
		String end = rangeCondition.get("end");
		try{
	        // 1、取得中国时间：  
	        Calendar calBegin = Calendar.getInstance(); 
	        calBegin.setTime( DateUtils.getStrDate(begin, "yyyy-MM-dd HH:mm:ss")  );
	        Calendar calEnd = Calendar.getInstance() ; 
	        calEnd.setTime( DateUtils.getStrDate(end, "yyyy-MM-dd HH:mm:ss")  );
	        // 2、取得时间偏移量：  
	        int zoneOffset = calBegin.get(java.util.Calendar.ZONE_OFFSET);  
	        // 3、取得夏令时差：  
	        int dstOffset = calBegin.get(java.util.Calendar.DST_OFFSET);  
	        // 4、从中国时间里扣除这些差量，即可以取得UTC时间：  
	        calBegin.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
	        calEnd.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
	        calEnd.set(Calendar.MILLISECOND, 999);
	        Date beginDate = calBegin.getTime();
	        Date endDate = calEnd.getTime();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	          
	        	rangeCondition.put("begin", format.format( beginDate ));
	        	rangeCondition.put("end", format.format(endDate) ); 
	        	System.out.println( "转化后的时间："+rangeCondition.toString() );
	            return  rangeCondition;  
        }catch(Exception e)  
        {  
            log.info("时间转化为UTC时间异常！"+e.getMessage() );
        	e.printStackTrace() ;  
        }  
        return null ;  
    } 
		/**
		 * 商户订单列表数据-查询
		 * @param fields
		 * @param equalParam
		 * @return
		 */
		public Map<String,List<Map<String,String>>> queryMerMoniInfoList(String fields,String equalParam  )
		{
			log.info("商户订单列表数据-查询-fields："+fields);
			log.info("商户订单列表数据-查询-equalParam："+equalParam);
			Map<String,List<Map<String,String>>> resultMap = new HashMap<String,List<Map<String,String>>>();
			if( StringUtils.isBlank(fields) || StringUtils.isBlank(equalParam)) {
				log.info("商户订单订单列表数据-查询错误，参数为空！");
				return null;
			}
			try
			{
				String[] exportFields = fields.split(",");
//				exportFields[exportFields.length] = "_id";
				JsonMapperUtil jmu =  new JsonMapperUtil();
				JavaType jatype = jmu.createCollectionType(Map.class, String.class,String.class);
				Map<String,String> equalConditionMap = jmu.fromJson(equalParam, jatype ) ;
				
				Map<String,String> rangeCondition =  null;
				if( !StringUtils.isBlank(equalConditionMap.get("beginTime")) && !StringUtils.isBlank(equalConditionMap.get("endTime"))) {
					rangeCondition =  new HashMap<String,String>();
					rangeCondition.put("begin", DateUtils.format(Long.parseLong(equalConditionMap.get("beginTime")),"yyyyMMddHHmmss") );
					rangeCondition.put("end", DateUtils.format(Long.parseLong(equalConditionMap.get("endTime")),"yyyyMMddHHmmss") );
					rangeCondition.put("field", "createTime");
				}

				String orderBy   = equalConditionMap.get("orderBy");
				String isDesc = equalConditionMap.get("isDesc");
				String pagefrom = equalConditionMap.get("pagefrom") ;
				String pagesize = equalConditionMap.get("pagesize") ;
				
				equalConditionMap.remove("beginTime");
				equalConditionMap.remove("endTime");
				equalConditionMap.remove("orderBy");
				equalConditionMap.remove("isDesc");
				equalConditionMap.remove("pagefrom");
				equalConditionMap.remove("pagesize");
				
				BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilderNoAnalyze(equalConditionMap,null);
				ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
				
				
				SortOrder sortOrder = SortOrder.DESC;// 排序方式 
				if(!StringUtil.isBlank(isDesc) && "asc".equals(isDesc)){
					sortOrder = SortOrder.ASC;
				}
				if(StringUtil.isBlank(orderBy)){
					orderBy = "createTime";
				}
				SearchResponse response = client.prepareSearch(configs.getProperty(propIndex).toString())
											.setTypes(configs.getProperty(propType).toString())
											.setQuery(queryBuilder)
											.setFrom(Integer.parseInt( pagefrom ))
											.setSize(Integer.parseInt( pagesize ))
											.addSort(orderBy, sortOrder)
							                .setExplain(false)  //设置是否按查询匹配度排序
							                .addFields(exportFields)
							                .execute().actionGet();
//				returnMap.put("totalsize", ""+response.getHits().getTotalHits());
				
				List<Map<String,String>> listresult = new ArrayList<Map<String,String>>();
				
				 for(final SearchHit hit:response.getHits()){
					 Map<String, String> map = new HashMap<String, String>();
					 final Iterator<SearchHitField> iterator = hit.iterator(); 
					 map.put("orderId", hit.getId());
					 while(iterator.hasNext()){
						 final SearchHitField hitfield = iterator.next();
						 if(hitfield.getValue()!=null ){
							 map.put(hitfield.getName(),hitfield.getValue().toString() );
						 }else{
							 map.put(hitfield.getName(),null );
						 }
						 
						
					 }
					 listresult.add(map);
					 log.info("查询返回数据："+map.toString());
				}
				 log.info("merchantMonitor:{totalsize:"+response.getHits().getTotalHits()+",method:queryMerMoniAggInfoList}");
				 resultMap.put("list", listresult);
				 
				 List<Map<String,String>> tatalsizelist = null; tatalsizelist = new ArrayList<Map<String,String>>();
				 Map<String,String> totalsizeMap = null; totalsizeMap = new HashMap<String,String>();
				 totalsizeMap.put("totalsize", ""+response.getHits().getTotalHits());
				 tatalsizelist.add(totalsizeMap);
				 
				 resultMap.put("totalsizelist",tatalsizelist );
				 
				return resultMap;
			}catch (Exception e)
			{
				e.printStackTrace();
				log.error("merchantMonitor:{"+ e.getMessage() +",method:queryMerMoniAggInfoList}");
			}
			return null;
		}
		
		/**
		 * 商户订单详情数据-查询
		 * @param fields
		 * @param equalParam
		 * @return
		 */
		public String queryMerMoniInfoDetail(String fields,String orderId  ) {
			log.info("商户订单详情数据-查询-fields："+fields);
			log.info("商户订单详情数据-查询-equalParam："+orderId);
			if( StringUtils.isBlank(fields) || StringUtils.isBlank(orderId)) {
				log.info("商户订单详情列表数据-查询错误，参数为空！");
				return null;
			}
			try
			{
				String[] exportFields = fields.split(",");


				GetResponse response = client.prepareGet(configs.getProperty(propIndex).toString(), configs.getProperty(propType).toString(), orderId)
						.setFields(exportFields).execute().actionGet();
				Map<String, Object> map = new HashMap<String, Object>();
				//判断非空
				if(response!=null && response.isExists()){
					for( int x=0;x<exportFields.length;x++) {
						map.put(exportFields[x] , response.getField(exportFields[x]).getValue());
					}
				}
				
				 log.info("查询返回数据："+map.toString());
				 String json = JSONObject.toJSONString(map);
				return json;
			}catch (Exception e)
			{
				e.printStackTrace();
				log.error("merchantMonitor:{"+ e.getMessage() +",method:queryMerMoniAggInfoList}");
			}
			return null;
		}
		
		/**
		 * 商户监控订单汇总 暂时无用
		 * @param conditionJson
		 * @param groupFields
		 * @return
		 */
			public String getMerMoniAggInfo( String conditionJson ,String groupFields)
			{
				log.info("商户监控订单汇总-条件-conditionJson："+conditionJson);
				log.info("商户监控订单汇总-分组字段-staticsJson："+groupFields);
				if( StringUtils.isBlank(conditionJson) || StringUtils.isBlank(groupFields)) {
					log.info("商户监控订单汇总错误，参数为空！");
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
					rangeCondition.put("field", "createTime");
				}
				conditionMap.remove("beginTime");
				conditionMap.remove("endTime");
				
				
				String [] str = groupFields.split(",");
				SearchRequestBuilder srb = null;
				srb =client.prepareSearch(configs.getProperty(propIndex).toString())
						.setTypes(configs.getProperty(propType).toString());
				/**
				 * 查询条件
				 */
				BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilderNoAnalyze(conditionMap,null);
				ESearchUtil.getBoolRangeQueryBuilder(rangeCondition,queryBuilder);
				
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
		            	totalcount  = totalcount+  Long.parseLong( listMap.get(i).get("orderCount").toString() ) ;
		            }
		        }
		        
		        log.info("商户监控订单数据汇总-总数量："+totalcount+",统计结果："+jso.toString());
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
				map = new HashMap();
				map.put(aggStr[position], bucket.getKey());
				map.put("orderCount", String.valueOf(  ( (Long)bucket.getDocCount() )==null?"0":bucket.getDocCount()));
				listMap.add(map);
			}
		}
		return listMap;
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
