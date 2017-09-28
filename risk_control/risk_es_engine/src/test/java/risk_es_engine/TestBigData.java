/**
 * 
 */
package risk_es_engine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.heepay.date.DateUtils;
import com.heepay.risk_es_engine.EsBigDataService;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class TestBigData {
	
	EsBigDataService essearch = null;
	

	@Before
	public void initESClient() {
		essearch = new EsBigDataService();
		essearch.initESClient();
	}
	@After
	public void closeESClient() {
		essearch.closeESClient();
	}
//	@Test
	public void getGatewayRecordList() {
//		String fields = "merchantNo,transNo,merchantId,requestAmount,payAmount,bankcardType,cardholderGender,"
//				+ "channelCode,channelTransType,bankCode,requestStatus,requestIp,provinceId,requestTime,payFinishTime,"
//				+ "settlePart,bankId,bankShortName,productCode,productNumber,productDescription,settleAmount,settleStatus,"
//				+ "createPerson,createTime,updatePerson,updateTime,channleNo,feeAmount,merchantName";
		String fields = "channelCode,channelTransType,transType,merchantId,bankCode,bankcardType,day";
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginTime", "1490976000000");
		map.put("endTime", "1498838399000");
		
		map.put("bankCode", "103");
//		map.put("channelTransType", "UNIPAY,QUICKPAY");
//		map.put("orderBy", "requestTime");
		map.put("isDesc", "asc");
//		map.put("createPerson", "lizhen");
//		map.put("updatePerson", "lizhen");
		String equalParam = JSONObject.fromObject(map).toString();
		
		essearch.getBossDataRecordList(fields, equalParam);
		
	}
//	@Test
	public void getBossDataRecordInfo() { 
		String fields = "transNo,merchantOrderNo,merchantId,merchantName,channelTransType,bankId,bankShortName,bankcardType,requestStatus,requestAmount,payAmount,productDescription,provinceId,payFinishTime,settleAmount,requestTime";
		String transNo  = "1496647105901";
		String x = essearch.getBossDataRecordInfo(fields, transNo);
		System.out.println(x);
	}
	@Test
	public void getBossAggregationInfo() {
		Map<String,String> map = new HashMap<String,String>();
		/*map.put("beginTime", "1490976000000");
		map.put("endTime", "1498838399000");*/
//		map.put("createPerson", "lizhen");
//		map.put("updatePerson", "lizhen");
//		map.put("bankCode", "103");
		String conditionJson  = JSONObject.fromObject(map).toString();
		//channelCode（多了）,channelTransType,transType,merchantId（少了）,bankCode（少了）,bankcardType,day
		String groupFields2 = "channelCode,bankCode,day,channelTransType,transType,merchantId,bankcardType";
		
		essearch.getBossAggregationInfoNew(conditionJson, groupFields2);
		
	}
//	@Test
	public  void hahah() {
		System.out.println(  "今天开始时间"+DateUtils.startTime(new Date()).getTime() );
		System.out.println(  "今天结束时间"+DateUtils.endTime(new Date()).getTime() );
		for(int i=0;i<12;i++) {
			StringBuffer sb = new StringBuffer( "2017-06-05 10:22:22,976 INFO " );
			sb.append("risk_message:");
			if(i%2==0) {
				sb.append("{merchantNo:523001");  
			}else{
				sb.append("{merchantNo:523002");  
			}
			sb.append(",transNo:"+new Date().getTime()); 
			if(i%2==0) {
				sb.append(",merchantId:1007001");
			}else{
				sb.append(",merchantId:1007002");
			}
			sb.append(",requestAmount:20");
			sb.append(",payAmount:20");
			sb.append(",bankcardType:SAVING");
			sb.append(",cardholderGender:1");
			if(i%2==0) {
				sb.append(",channelCode:123");
			}else{
				sb.append(",channelCode:456");
			}
			sb.append(",channelTransType:"+"TESTPAY");//改
//			map.put("bankId", "1,2");
//			map.put("channelTransType", "UNIPAY,QUICKPAY,TESTPAY");
			sb.append(",bankCode:"+"4");//改
			if(i%2==0) {
				sb.append(",requestStatus:0");
			}else{
				sb.append(",requestStatus:1");
			}
			sb.append(",requestIp:0");
			sb.append(",provinceId:0");
			sb.append(",requestTime:"+new Date().getTime());
			sb.append(",payFinishTime:"+new Date().getTime());
			sb.append(",settlePart:0");
			sb.append(",bankId:"+"4"); //改
			sb.append(",bankShortName:"+"bsn4"); //改
			if(i%2==0) {
				sb.append(",productCode:CP21"); 
			}else{
				sb.append(",productCode:CP22"); 
			}
			if(i%2==0) {
				sb.append(",productNumber:3457"); 
			}else{
				sb.append(",productNumber:7543"); 
			}
			if(i%2==0) {
				sb.append(",productDescription:test");
			}else{
				sb.append(",productDescription:testH");
			}
			sb.append(",settleAmount:10"); 
			if(i%2==0) {
				sb.append(",settleStatus:Y"); 
			}else{
				sb.append(",settleStatus:N"); 
			}
			sb.append(",createPerson:lizhen"); 
			sb.append(",createTime:"+new Date().getTime()); 
			sb.append(",updatePerson:lizhen"); 
			sb.append(",updateTime:"+new Date().getTime()); 
			if(i%2==0) {
				sb.append(",channleNo:2043001"); 
			}else{
				sb.append(",channleNo:2043002"); 
			}
			sb.append(",feeAmount:10");
			sb.append(",merchantName:testmer"); 
			sb.append("}");
			System.out.println(sb.toString());
			try {
				Thread.sleep(200l);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
