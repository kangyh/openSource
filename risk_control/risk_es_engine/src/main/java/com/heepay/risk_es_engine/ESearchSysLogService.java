package com.heepay.risk_es_engine;

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
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.MultiSearchRequestBuilder;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.date.DateUtils;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年3月16日 下午2:14:04
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
@Service
public class ESearchSysLogService {
	private Configuration configs ;
	private static final Logger log = LogManager.getLogger();
	private  Client client;
	public ESearchSysLogService()
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
	 * 查询当天最新的ErrorInfo信息
	 * @param timeSpan
	 * @return
	 */
	public List<Map<String,String>> getSysErrorLogByTimeSpan(String timeSpan){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try
		{
		String[] sysLogs = new String[]{"payment_server",
				"billing_server",
				"billing_notify",
				"gateway_server",
				"manage_server",
				"manage_web",
				"ngp_web",
				"ngp_job",
				"payment_notify",
				"payment_web",
				"payment_api",
				"risk",
				"cbms_web",
				"cbms_server"};
		SearchRequestBuilder searchRequestSyslog =null;
		MultiSearchResponse multiSearchResponse =null ;
		BulkRequestBuilder bulk = client.prepareBulk(); 
		UpdateRequest updateItem = null;
		MultiSearchRequestBuilder multiSearchRequestBuilder = client.prepareMultiSearch();
		//查询当前两天的时间范围
		Date dateBegin = DateUtils.addHours(DateUtils.startTime(new Date()), -24);
		Date dateEnd = DateUtils.endTime(new Date());
		RangeQueryBuilder queryTimeSpanBuilderRange= ESearchUtil.RangeQueryBuilder("logtime_utc",dateBegin,dateEnd,true,true);
		List<Map> equalConditionMap = new ArrayList<Map>();
		Map conditionMap = new HashMap();
		conditionMap.put("isFlag", "0"); //检索未处理的异常日志
		conditionMap.put("priority", "error"); //log级别是 error
		equalConditionMap.add(conditionMap);
		BoolQueryBuilder queryBuilder = ESearchUtil.getBoolMatchQueryBuilder(equalConditionMap,null);
		queryBuilder.must(queryTimeSpanBuilderRange);
		for (int index=0;index<sysLogs.length;index++){
			searchRequestSyslog = ESearchUtil.getSearchRequestBuilder(client,configs.getProperty("es.cluster.property.sysLogindex").toString()
					,sysLogs[index]+"-"+DateUtils.getDateStr(new Date(), "yyyy-MM").replace("-", ".")
					,queryBuilder
					,null).addFields("message").setFrom(0).setSize(1000);
			multiSearchRequestBuilder.add(searchRequestSyslog);
		}
		multiSearchResponse = multiSearchRequestBuilder.execute().actionGet(); //得到返回结果
		
		Map<String, String> map = null;
		for (int index=0,length = multiSearchResponse.getResponses().length;index<length;index++)
		{
			//System.out.println(multiSearchResponse.getResponses()[index].getResponse().getHits().getTotalHits());
			 for(final SearchHit hit:multiSearchResponse.getResponses()[index].getResponse().getHits()){
				  map = new HashMap<String, String>();
				 final Iterator<SearchHitField> iterator = hit.iterator(); 
				 while(iterator.hasNext()){
					 final SearchHitField hitfield = iterator.next();
					 map.put(hitfield.getName(),hitfield.getValue());
				 }
				 map.put("_id", hit.getId());
				 map.put("_type", hit.getType());
				 list.add(map);
				 updateItem = new UpdateRequest(configs.getProperty("es.cluster.property.sysLogindex").toString(), 
						 hit.getType(), hit.getId()).doc(XContentFactory.jsonBuilder().startObject().field("isFlag", "1").endObject());
				 bulk.add(updateItem);  
				 System.out.println(map);
			 }
		}
		bulk.execute();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return list;
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
