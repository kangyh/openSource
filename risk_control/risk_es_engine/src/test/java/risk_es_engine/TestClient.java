package risk_es_engine;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 描    述：风控系统 ElasticSearch 
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
public class TestClient {
	private Client client;
	@Before
	public void initESClient() {
		// 配置你的es,现在这里只配置了集群的名,默认是elasticsearch,跟服务器的相同
		Settings settings = Settings.settingsBuilder().put("cluster.name","es_cluster").build();
		// 这里可以同时连接集群的服务器,可以多个,并且连接服务是可访问的
		try {
			client = TransportClient.builder().settings(settings).build()
		
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.162.80"),9300));
			//.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.207.133"),9300));
			System.out.println("连接成功！");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@After
	public void closeESClient() {
		client.close();
		System.out.println("连接关闭！");
	}
	/*
	 * 测试开试的入口地址
	 */
	@Test
	public void Tests()
	{
		//TestTermQuery();
		//AddRow();
	}
	
	/**
	 * 精确查询
	 */
	public void TestTermQuery()
	{
		SearchResponse response = client.prepareSearch("test")
				.setTypes("logs")
				.setQuery(QueryBuilders.matchPhrasePrefixQuery("message","86.com"))
				.setFrom(0)
				.setSize(100)
                .execute().actionGet();		
		System.out.println(response);
	}
	/**
	 * 添加记录
	 */
	public void AddRow()
	{
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("message",".");
		ret.put("message","王");
		ret.put("message","@");
		IndexResponse res = client.prepareIndex().setIndex("test").setType("logs").setSource(ret).execute().actionGet();
	}
	
	/**
	 * 查询先择的字段
	 */
	public void TermFieldQuery()
	{
		SearchResponse response = client.prepareSearch("sb").setTypes("sb").setFrom(0).setSize(500)
                .setExplain(false)      
                .addFields(new String[]{"cphm1","jcdid","cplx1","tpid1","tgsj","cdid"})
                .execute().actionGet();
	}
	
	
	/**
	 * 创建索引
	 */
	private void createIndex() {
		// TODO Auto-generated method stub
		for(int i=0; i<10; i++){
			String id = "id"+i;
			String title = "this is title" + i;
			client.prepareIndex("blog", "post").setSource(getBuilderJson(id, title)).execute().actionGet();
			//System.out.println(i);
		}
		System.out.println("索引创建成功！");
	}
	
	
	private String getBuilderJson(String id,String title){
		String json = "";
		try {
			XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
			contentBuilder.field("id",id);
			contentBuilder.field("title",title);
			contentBuilder.field("title",title);
			contentBuilder.field("title",title);
			contentBuilder.field("title",title);
			json = contentBuilder.endObject().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查询某个索引中的内容
	 */
	public void getIndex() {
		GetResponse res = client.prepareGet().setIndex("test")
				.setType("logs").setId("AVhuyrMhMJQ19EGuE4x9").execute().actionGet();
		System.out.println(res.getSource());
	}
	
	
	/**
	 * 搜索索引
	 */
	public void search(){
		//创建查询索引
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch("test");
		//设置查询索引类型
		searchRequestBuilder.setTypes("logs");
		//设置查询类型
		//1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
		// 2.SearchType.SCAN = 扫描查询,无序
		// 3.SearchType.COUNT = 不设置的话,这个为默认值,还有的自己去试试吧
		searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		//设置查询关键字
		// fieldQuery 这个必须是你的索引字段哦,不然查不到数据,这里我只设置两个字段 id ,title
		searchRequestBuilder.setQuery(QueryBuilders.termQuery("message", "id"));
		// 设置查询数据的位置,分页用吧
		searchRequestBuilder.setFrom(0);
		// 设置查询结果集的最大条数
		searchRequestBuilder.setSize(60);
		// 设置是否按查询匹配度排序
		searchRequestBuilder.setExplain(true);
		// 最后就是返回搜索响应信息
		SearchResponse response = searchRequestBuilder.execute().actionGet();
		System.out.println(response);
		
		//获取搜索文档的结果
		SearchHits searchHits = response.getHits();
		SearchHit[] hits = searchHits.getHits();
		System.out.println(hits.length);
		for (int i = 0; i < hits.length; i++) {
			SearchHit hit = hits[i];
			Map result = hit.getSource();
			System.out.println(result);
		}
		System.out.println("查询索引完毕!");
	}
	/**
	 * 获取索引
	 */
	public void get(){
		GetResponse response = client.prepareGet("test", "logs", "AVhuyi2kMJQ19EGuE4x8")
				.execute().actionGet();
		//下面是不在多线程操作，他默认为.setOperationThreaded(true)
		//GetResponse response = client.prepareGet("blog", "post", "AVJjRJVqW-UsQoTouwCF")
		//		.setOperationThreaded(false).execute().actionGet();
		//Map headers = (Map) response.getHeaders();
		Set<String> headers = response.getHeaders();
		System.out.println(headers);//获取请求头
		boolean exists = response.isExists();
		System.out.println(exists);//判断索引是否存在
		String sourceString = response.getSourceAsString();
		System.out.println(sourceString);//获取索引，并打印出索引内容
		String id = response.getId();
		System.out.println(id);//获取索引id
		boolean sourceEmpty = response.isSourceEmpty();
		System.out.println(sourceEmpty);//获取索引的内容是否为空
	}
	/**
	 * 删除索引
	 */
	public void delete(){
		DeleteResponse response = client.prepareDelete("test", "logs", "AVhuywuEMJQ19EGuE4yN")
				.execute().actionGet();
		//下面是不在多线程操作，他默认为.setOperationThreaded(true)
		//GetResponse response = client.prepareDelete("blog", "post", "AVJjRJVqW-UsQoTouwCF")
		//		.setOperationThreaded(false).execute().actionGet();
		boolean isFound = response.isFound();
		System.out.println(isFound);//返回索引是否存在，存在删除
		
	}
	
}
