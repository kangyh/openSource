/**
 * 
 */
package risk_es_engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.date.DateUtils;
import com.heepay.risk_es_engine.ESearchService;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class TestChannel {
	
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
	@Test
	public void checkBatchPayRule() {
		Map<String,String> conditionMap =new HashMap<String,String>();
		conditionMap.put("merchant_id", "100005");
		String decBandcard = Aes.decryptStr(  "AB5556C69B99DDD8F58ED15474866CA70DE0FCC68E948EA918B78D527DF5E570", Constants.QuickPay.SYSTEM_KEY);
		conditionMap.put("bankcard_no", decBandcard );
		essearch.checkBatchPayRule(conditionMap);
	}
	
	/**
	 * 测试 获取列表页
	 *
	 *
	 */
//	@Test
	public void getChannelList() {
		String[] fields = new String[]{"payment_id","channel_code","channel_partner_code","channel_partner_name","bank_no" };
		Map condition = new HashMap();
//		condition.put("channel_code", "2017"); 
		
		Map notEqualcondition = new HashMap();
//		notEqualcondition.put("payment_id", "10013"); 
		
		Map paytimeCondition =new HashMap();
		paytimeCondition.put("field", "req_time");
		paytimeCondition.put("begin", "1496906096612");
		paytimeCondition.put("end", "1498665599638");
		Map page = new HashMap();
		page.put("pagefrom", 10);
		page.put("pagesize", 20);
		List<Map> listMap  = essearch.getChannelList(fields, condition, notEqualcondition, paytimeCondition, page);
		System.out.println("获取列表页大小:"+listMap.size());
	}

	
	/**
	 * 测试 当月 当天限额
	 */
//	@Test
	public void getChannelDayAndMonthLimitAmount()
	{
		System.out.println(DateUtils.startTime(new Date()).getTime());
		System.out.println(DateUtils.endTime(new Date()).getTime());
		Map temp = new HashMap();
//		temp.put("pagefrom", 0);
//		temp.put("pagesize", 4);
		temp.put("channel_code", "104bilpay_10209" );
		JSONObject json = JSONObject.fromObject(temp);
		essearch.getChannelDayAndMonthLimitAmount(json);

	}
	
	
//	@Test
	public void getChannelAggregationInfo()
	{
		Map equalcondition = new HashMap();
//		equalcondition.put("pagefrom", "0"); 
//		equalcondition.put("pagesize", "20"); 

//		equalcondition.put("card_type_code", "saving"); 

		Map notEqualcondition = new HashMap();
		
//		notEqualcondition.put("payment_id", "10013"); 
		
		Map paytimeCondition =new HashMap();
		paytimeCondition.put("field", "req_time");
		paytimeCondition.put("begin", "1496906096612");
		paytimeCondition.put("end", "1498665599638");
		
		essearch.getChannelAggregationInfo(equalcondition, notEqualcondition , paytimeCondition);
	}

}
