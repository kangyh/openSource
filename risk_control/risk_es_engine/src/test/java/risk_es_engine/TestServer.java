package risk_es_engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.heepay.risk_es_engine.ESearchService;

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
public class TestServer {
	ESearchService essearch = null;
	

	@Before
	public void initESClient() {
		essearch = new ESearchService();
		essearch.initESClient();
	}
	@After
	public void closeESClient() {
		essearch.closeESClient();
	}
		
	
	
	/**
	 * 测试 获取列表页
	 *
	 *
	 */
	//@Test
	public void getTransData() {
		String[] fields = new String[]{"merchant_id","merchant_login_name","trans_type","pay_amount","trans_no","pay_time"};
		List conditionMapList = new ArrayList();
		List notConditioinMapList = new ArrayList();
		List timeConditionList =  new ArrayList();
		
		Map notConditionMap1 = new HashMap();
		notConditionMap1.put("trans_type", "rename");
		Map notConditionMap2 = new HashMap();
		notConditionMap2.put("trans_type", "shares");
		
		notConditioinMapList.add(notConditionMap1);
		notConditioinMapList.add(notConditionMap2);
		
		Map condition = new HashMap();
		condition.put("pay_status", "succes"); //前台只查询成功的，为冻结做服务
		conditionMapList.add(condition);
		//Map timeCondition  = new HashMap();
		//"pay_status": "SUCCES",
		/*
		timeCondition.put("field", "pay_time");
		timeCondition.put("begin", "1481254481000");
		timeCondition.put("end", "1481512897000");*/
		
		//timeConditionList.add(timeCondition);
		
		Map conditionMap = new HashMap();
		/*conditionMapList.add(conditionMap);
		conditionMap.put("trans_type", "charge");*/
		//conditionMap.put("trans_no", "581100001480747714");
		//conditionMap.put("merchant_login_name", "zhangchen@9186.com");
		Map paytimecondition = new HashMap();
		Map page = new HashMap();
		/*Map timeConditon = new HashMap();
		timeConditon.put("begin", "");
		timeConditon.put("end", "");*/
		page.put("pagefrom", 0);
		page.put("pagesize", 50);
		List<Map> listMap  = essearch.getTransData(fields, conditionMapList,notConditioinMapList,timeConditionList,page);
		System.out.println("获取列表页:"+listMap);
	}
	
	/**
	 * 测试反欺诈
	 * @param condition
	 * @param begintime
	 * @param endtime
	 * @return
	 */
	@Test
	public void getAntiFraudList() {
		Map condition;
		/*String begintime = "1481507373999";
		String endtime =   "1481507374001";*/
		
		String begintime = null;
		String endtime =   null;
		condition = new HashMap();
		//condition.put("merchant_login_name", "2998436865@qq.com");
		// TODO Auto-generated method stub
		String[] fields = new String[]{"merchant_id","merchant_login_name","trans_type",
				"pay_amount","trans_no","pay_time","bank_name","trans_id","payment_id"
				,"bank_order_id","transaction_IP","transInBank_ID"};
		List<Map> paytimeConditionList = null;
		Map paytimeCondition = null;
		if(begintime !=null && endtime!=null)
		{
			paytimeConditionList = new ArrayList<Map>();
			paytimeCondition =new HashMap();
			paytimeCondition.put("field", "pay_time");
			paytimeCondition.put("begin", begintime);
			paytimeCondition.put("end", endtime);
			paytimeConditionList.add(paytimeCondition);
		}
		List conditionMapList = new ArrayList();
		if (condition !=null)
		{
			conditionMapList.add(condition); //前台传过来的参数
		}
		
		Map page = new HashMap();
		page.put("pagefrom",0);
		//查询前1000笔交易
		page.put("pagesize", 1000);
		
		List<Map> listMap = essearch.getTransData(fields,conditionMapList,null, paytimeConditionList, page);
		System.out.println("测试反欺诈:"+listMap);
	}
	
	/**
	 * 测试 当月 当天限额
	 */
	//@Test
	public void getDayAndMonthLimitAmount()
	{
		//100007,productType:CP02,accountType:PRIVAT,bankTypeSAVING
		Map map = essearch.getDayAndMonthLimitAmount("20170602MER", "111", "0", "SAVING");
		System.out.println("当月 当天限额:"+map);
	}
	@Test
		public void getDayLimitAmount()
		{
			//100007,productType:CP02,accountType:PRIVAT,bankTypeSAVING
		  List list=Arrays.asList("charge");
		  List list2=Arrays.asList("cp01");
		  Map map=new HashMap<String,List>();
		  map.put("trans_type", list);
		  map.put("product_code", list2);
			Map map2 = essearch.getDayLimitAmount("102115",map ,90);
			System.out.println("当月 当天限额:"+map);
		}
	
	//@Test
	public void getAggregationInfo()
	{
		List<Map> rangeCondition = new ArrayList<Map>();
		List conditionMapList = new ArrayList();
		Map condition = new HashMap();
		condition.put("pay_status", "succes"); //前台只查询成功的，为冻结做服务
		conditionMapList.add(condition);
		
		Map paytimeCondition =new HashMap();
		paytimeCondition.put("field", "pay_time");
		paytimeCondition.put("begin", "1481527591000");
		paytimeCondition.put("end", "1483700470000");
		rangeCondition.add(paytimeCondition);
		
		List<Map> listMap = (List<Map>) essearch.getAggregationInfo( conditionMapList, null, rangeCondition);
	}
	
}
