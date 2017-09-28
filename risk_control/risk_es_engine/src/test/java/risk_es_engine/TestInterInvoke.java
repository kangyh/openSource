package risk_es_engine;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.heepay.risk_es_engine.EsInterInvokeService;

/**
 * @author 李震
 *
 * 2017年9月21日
 */
public class TestInterInvoke {
	
	EsInterInvokeService essearch = null;
	

	@Before
	public void initESClient() {
		essearch = new EsInterInvokeService();
		essearch.initESClient();
	}
	@After
	public void closeESClient() {
		essearch.closeESClient();
	}
	
	@Test
	public void queryData () {
		String fields = "responseDate,merchantOrderNo,paymentId,data,responseDate1";
		Map<String,String> equalParam = new HashMap<String,String>();
		equalParam.put("beginTime", "2017-09-09 07:23:23");
		equalParam.put("endTime", "2017-09-09 07:33:04");
		equalParam.put("pagefrom", "0");
		equalParam.put("pagesize", "60");
		
		essearch.queryInterfaceDataList(fields, JSONObject.toJSONString(equalParam), "GatewayResponseData");
	}

}
