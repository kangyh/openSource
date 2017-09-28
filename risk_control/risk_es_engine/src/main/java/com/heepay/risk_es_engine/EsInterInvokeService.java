
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
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
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
public class EsInterInvokeService {
	
	private Configuration configs ;
	private static final Logger log = LogManager.getLogger();
	private  Client client;
	private static String propIndex = "es.cluster.property.indexMonitorOrder";
	private static String propType = "es.cluster.property.typeMonitorInvoke";
	public EsInterInvokeService()
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
		 * 接口调用数据列表查询
		 * @param fields
		 * @param equalParam
		 * @param type "request"/"response" 根据类型判断取哪个日期类型
		 * @return
		 */
		public Map<String,List<Map<String,String>>> queryInterfaceDataList(String fields,String equalParam,String type )
		{
			log.info("接口调用数据列表-查询-fields："+fields);
			log.info("接口调用数据列表-查询-equalParam："+equalParam);
			Map<String,List<Map<String,String>>> resultMap = new HashMap<String,List<Map<String,String>>>();
			if( StringUtils.isBlank(fields) || StringUtils.isBlank(equalParam)) {
				log.info("接口调用数据列表-查询错误，参数为空！");
				return null;
			}
			String dateFieldName = "requestDate1";
			// type=PaymentRequestData,PaymentResponseData,GatewayRequestData,GatewayResponseData 
			if( "PaymentResponseData".equals( type ) || "GatewayResponseData".equals( type ) ) {
				dateFieldName = "responseDate1";
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
					rangeCondition.put("begin", equalConditionMap.get("beginTime") );
					rangeCondition.put("end", equalConditionMap.get("endTime")  );
					rangeCondition.put("field", dateFieldName);
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
				
				rangeCondition = getUTCTime( rangeCondition );
				
				
				BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilderNoAnalyze(equalConditionMap,null);
				ESearchUtil.getBoolRangeQueryBuilder2(rangeCondition,queryBuilder);
				
				TermQueryBuilder test = QueryBuilders.termQuery("tags", type.toLowerCase());
				
				queryBuilder.must(test);
						
				SortOrder sortOrder = SortOrder.DESC;// 排序方式 
				if(!StringUtil.isBlank(isDesc) && "asc".equals(isDesc)){
					sortOrder = SortOrder.ASC;
				}
				if(StringUtil.isBlank(orderBy)){
					orderBy = dateFieldName;
				}
				SearchRequestBuilder srb = client.prepareSearch(configs.getProperty(propIndex).toString())
						.setTypes(configs.getProperty(propType).toString())
						.setQuery(queryBuilder)
						.setFrom(Integer.parseInt( pagefrom ))
						.setSize(Integer.parseInt( pagesize )) ;
				
				SearchResponse response = srb
											.addSort(orderBy, sortOrder)
							                .setExplain(false)  //设置是否按查询匹配度排序
							                .addFields(exportFields)
							                .execute().actionGet();
				
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
				 log.info("interfaceData:{totalsize:"+response.getHits().getTotalHits()+",method:queryInterfaceDataList}");
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
	 * 关闭ElasticSearch
	 * @param client
	 */
	@PreDestroy
	public void closeESClient() {
		ESearchUtil.closeESClient(client);
	}

}
