package com.heepay.vo;

import com.heepay.billingutils.payment.common.Global_Redis_Key;
import com.heepay.redis.JedisClusterUtil;

import redis.clients.jedis.JedisCluster;

public class TestChannleRouteMessage {

	private static JedisCluster jc = JedisClusterUtil.getJedisCluster();

	public static String generateId(){
		Long gatewayId = jc.incr(Global_Redis_Key.GATEWAY_ID);
		return gatewayId.toString();
	}
	
	public static void main(String agrs[]){
		try {
//			System.out.println(generateId()+"----");
//			
//			System.out.println(JedisClusterUtil.getJedisValue(getter -> getter.get("KEY")));
			
//			long l1= System.currentTimeMillis();
//			System.out.println(System.currentTimeMillis());
//			
//			ChannelRouteMessage channelrouteMsg = FetchRouteMessage.getRouteChannelMesage("522WISDOMQUICKPCREDIT");
//			ChannelRouteMessage channelrouteMsg = FetchRouteMessage.getRouteChannelMesage("525BILPAYAUTHENCREDITCOMMON");
//			long l2 = System.currentTimeMillis();
//			System.out.println(System.currentTimeMillis());

//			
//			MerchantProductVO merrouteMsg = FetchRouteMessage.getMerchantMessage(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+"100013"+"CP08");
			
//			String str = "{\"merchantId\":\"100025\",\"productName\":\"充值\",\"productKey\":\"chargeProductKey100025\",\"backUrl\":null,\"notifyUrl\":null,\"validityDateEnd\":4632954665432,\"validityDateBegin\":1477281065432,\"referer\":null,\"toBalanceOrBankcard\":null,\"productCode\":\"CP01\",\"feeWay\":\"INDEDU\",\"feeSettleCyc\":null,\"settleCyc\":\"0\",\"isRefundable\":\"false\"}";
//			
//			
//			MerchantProductVO merrouteMsg = FetchRouteMessage.getMerchantMessage(str);
//			Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+商户id+产品编码
			
			System.out.println("".equals(null));


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
