//package com.heepay.billing.route;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.Test;
//
//import com.heepay.common.util.Constants;
//import com.heepay.vo.ChannelRouteMessage;
//import com.heepay.vo.MerchantProductVO;
//
//
////@RunWith(SpringJUnit4ClassRunner.class)
//public class FetchRouteMessageTest {
//	private static Logger logger = LogManager.getLogger();
//	
////	@Autowired
////	private FetchRouteMessage fetchRouteMessage;
//	
//	@Test
//	public  void testgetRouteChannelMesage(){
//		
//		ChannelRouteMessage msg = FetchRouteMessage.getRouteChannelMesage("402DIRCONQUICKPSAVINGPUBLIC");
//		
//		logger.info("返回报文"+msg);
//	}
//	
//	@Test
//	public void testgetMerchantMessage(){
//		
//		MerchantProductVO msg = FetchRouteMessage.getMerchantMessage(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+"100019"+"CP05");
//		
//		
//		logger.info("返回报文"+msg.getProductCode());
//	}
//}
