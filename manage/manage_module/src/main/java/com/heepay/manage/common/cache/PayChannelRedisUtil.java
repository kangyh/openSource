/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.cache;

import com.google.common.collect.Maps;
import com.heepay.common.util.DateUtil;
import com.heepay.enums.BusinessType;
import com.heepay.enums.CostType;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.route.entity.ChannelRedisVO;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.heepay.redis.JedisClusterUtil;
import redis.clients.jedis.JedisCluster;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：通道缓存工具类
 *
 * 创 建 者： M.Z
 * 创建时间： 2016/9/27 13:31 
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
public class PayChannelRedisUtil {

	//商户通道map放入缓存的key
	private static final String MERCHANT_KEY = "ROUTE_MERCHANT_PAYCHANNEL";

	//本行按比例map放入缓存的key
	private static final String OWNBANK_RATE = "ROUTE_OWNBAK_RATE_PAYCHANNEL";

	//本行按笔数map放入缓存的key
	private static final String OWNBANK_COUNT = "ROUTE_OWNBAK_COUNT_PAYCHANNEL";

	//跨行按比例map放入缓存的key
	private static final String SPNBAK_RATE = "ROUTE_SPNBAK_RATE_PAYCHANNEL";

	//跨行按笔数map放入缓存的key
	private static final String SPNBAK_COUNT = "ROUTE_SPNBAK_COUNT_PAYCHANNEL";

	//每种通道类型对应的银行及卡类型map放入缓存的key
	private static final String CHANNEL_BANK = "ROUTE_CHANNELTYPE_BANKCARD";

	//所有通道信息map放入缓存对应的key
	private static final String CHANNRL_ALL = "ROUTE_CHANNEL_ALLINFO";
	

	private PayChannelRedisUtil(){
	}

	private static PayChannelService payChannelService = SpringContextHolder.getBean(PayChannelService.class);

	/**
	 * @discription 字符串拼接
	 * @author M.Z
	 * @created 2016年10月17日
	 * @param channelRedisVO
	 * @return String
	 */
	private static String addString(ChannelRedisVO channelRedisVO){
		return channelRedisVO.getChannelTypeCode()
				     +channelRedisVO.getCardTypeCode()
				     +channelRedisVO.getAccountType();
	}

	/**
	 * @discription 从缓存中取map
	 * @author M.Z
	 * @created 2016年10月17日
	 * @param key
	 * @return Map<String,String>
	 */
	private static Map<String,String> getRedisMap(String key){
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		String jsonInfo = jedisCluster.get(key);
		//json字符串转map
		Map<String,String> map = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo,Map.class);
		//第一次时创建新map
		if (null==map){
			map = Maps.newHashMap();
		}
		return map;
	}

	/**
	 * @discription 遍历map删除缓存和map中的旧数据并将新map放入缓存中
	 * @author M.Z
	 * @created 2016年10月17日
	 * @param map,todayDate,mapKey
	 */
	private static void delOldDataFromRedis(Map<String,String> map,String todayDate,String mapKey){
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		//遍历map
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			if (!entry.getValue().equals(todayDate)) {
				//将旧数据从Redis中删除
				jedisCluster.del(String.valueOf(entry.getKey()));
				it.remove();
			}
		}
		//将map放入缓存
		String string = JsonMapper.toJsonString(map);
		jedisCluster.set(mapKey,string);
	}
	/**
	 * 查询出产品明细配置中有效期内的通道信息，加载到redis缓存，
	 * 使用 bankNo, channelTypeCode, cardTypeCode，accountType，businessType，merchantId六个字段拼加为key
	 */
	public static void queryMerchantChannel() {
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		//取时间戳
		String todayDate = DateUtil.getTodayYYYYMMDDHHmmss();
        //缓存中取map
		Map<String,String> merchantMap = getRedisMap(MERCHANT_KEY);
		//查询数据
		List<ChannelRedisVO> channelRedisVOs = payChannelService.selectChannel();
		//将数据写入Redis和map中
		for(ChannelRedisVO channelRedisVO:channelRedisVOs){
			String key = channelRedisVO.getBankNo()
					+addString(channelRedisVO)
					+channelRedisVO.getBusinessType()
					+channelRedisVO.getMerchantId();
			String value = JsonMapper.toJsonString(channelRedisVO);
			//放入缓存
			jedisCluster.set(key,value);
			//放入map
			merchantMap.put(key,todayDate);
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(merchantMap,todayDate,MERCHANT_KEY);
	}

	/**
	 * @discription 清除通道缓存
	 * @author M.Z
	 * @created 2016年10月20日20:53:54
	 * @param
	 */
	public static void delChannelRedis(String key){
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		//从缓存中取
		String jsonInfo = jedisCluster.get(key);
		//json字符串转map
		Map<String,String> map = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo,Map.class);
		if (null != map){
			//遍历map
			for (Map.Entry<String, String> entry : map.entrySet()){
				//将数据从Redis中删除
				jedisCluster.del(entry.getKey());
			}
			//将收集key的map从redis中删除
			jedisCluster.del(key);
		}
	}

	/**
	 * 查询有效期内的优先级最高，或者最低成本的默认通道添加到缓存，
	 * 本行：使用 bankNo, channelTypeCode, cardTypeCode，accountType，本行，costType六个字段拼加为key
	 * 跨行：使用 channelTypeCode, cardTypeCode，accountType，跨行，costType五个字段拼加为key
	 */
	public static void queryPayChannel() {
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		String todayDate = DateUtil.getTodayYYYYMMDDHHmmss();
		//缓存中取map
		Map<String,String> ownRateMap = getRedisMap(OWNBANK_RATE);
		//查询数据
		List<ChannelRedisVO> channelRateRedisVOs = payChannelService.selectRateList();
		String i = null;
		//将数据写入Redis和map中
		for(ChannelRedisVO channelRedisVO:channelRateRedisVOs){
			//银行+支付通道+银行卡+对公对私
			String j = channelRedisVO.getBankNo()
					+ addString(channelRedisVO);
			if(!j.equals(i)){
				String key = channelRedisVO.getBankNo()
						+addString(channelRedisVO)
						+BusinessType.OWNBAK.getValue()
						+CostType.RATE.getValue();
				String value = JsonMapper.toJsonString(channelRedisVO);
				//放入缓存
				jedisCluster.set(key,value);
				//放入map
				ownRateMap.put(key,todayDate);
			}
			i = j;
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(ownRateMap,todayDate,OWNBANK_RATE);

		//本行按笔数
		//缓存中取map
		Map<String,String> ownCountMap = getRedisMap(OWNBANK_COUNT);
		List<ChannelRedisVO> channelCountRedisVOs = payChannelService.selectCountList();
		String k = null;
		for(ChannelRedisVO channelRedisVO:channelCountRedisVOs){
			//银行+支付通道+银行卡
			String j = channelRedisVO.getBankNo()
					+ addString(channelRedisVO);
			if(!j.equals(k)){
				String key = channelRedisVO.getBankNo()
						+addString(channelRedisVO)
						+BusinessType.OWNBAK.getValue()
						+CostType.COUNT.getValue();
				String value = JsonMapper.toJsonString(channelRedisVO);
				//放入缓存
				jedisCluster.set(key,value);
				//放入map
				ownCountMap.put(key,todayDate);
			}
			k = j;
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(ownCountMap,todayDate,OWNBANK_COUNT);

		//（跨行按比例）通道类型+卡类型+对公对私+跨行+成本类型
		//缓存中取map
		Map<String,String> spnRateMap = getRedisMap(SPNBAK_RATE);
		List<ChannelRedisVO> channelRedisVOs = payChannelService.selectSpnChannel();
		String l = null;
		for(ChannelRedisVO channelRedisVO:channelRedisVOs){
			//通道类型+卡类型+对公对私
			String j = addString(channelRedisVO);
			if(!j.equals(l)){
				String key = addString(channelRedisVO)
						+ BusinessType.SPNBAK.getValue()
						+ CostType.RATE.getValue();
				String value = JsonMapper.toJsonString(channelRedisVO);
				//放入缓存
				jedisCluster.set(key,value);
				//放入map
				spnRateMap.put(key,todayDate);
			}
			l = j;
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(spnRateMap,todayDate,SPNBAK_RATE);

		//跨行按笔数
		//缓存中取map
		Map<String,String> spnCountMap = getRedisMap(SPNBAK_COUNT);
		List<ChannelRedisVO> channelRedisCountVOs = payChannelService.selectSpnChannelCount();
		String m = null;
		for(ChannelRedisVO channelRedisVO:channelRedisCountVOs){
			//通道类型+卡类型+对公对私
			String f = addString(channelRedisVO);
			if(!f.equals(m)){
				String key = addString(channelRedisVO)
						+ BusinessType.SPNBAK.getValue()
						+ CostType.COUNT.getValue();
				String value = JsonMapper.toJsonString(channelRedisVO);
				//放入缓存
				jedisCluster.set(key,value);
				//放入map
				spnCountMap.put(key,todayDate);
			}
			m = f;
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(spnCountMap,todayDate,SPNBAK_COUNT);

	}

	/**
	 * 查询出每种通道类型对应的银行及卡类型，加载到redis缓存，
	 * 使用 channelType字段拼为key
	 */
	public static void queryChannelType() {
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		String todayDate = DateUtil.getTodayYYYYMMDDHHmmss();
		//缓存中取map
		Map<String,String> channelTypeBankMap = getRedisMap(CHANNEL_BANK);
		//查询数据
		List<PayChannel> payChannels = payChannelService.selectChannelType();
		for(PayChannel payChannel:payChannels){
			String cardTypeCode = "null";
			if (payChannel.getCardTypeCode() != null){
				cardTypeCode = payChannel.getCardTypeCode();
			}
			String finalCardTypeCode = cardTypeCode;
			jedisCluster.hset(payChannel.getChannelTypeCode(),payChannel.getBankNo(),finalCardTypeCode);
			//放入map
			channelTypeBankMap.put(payChannel.getChannelTypeCode(),todayDate);
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(channelTypeBankMap,todayDate,CHANNEL_BANK);
	}

	/**
	 * 查询出每条通道的所有信息，加载到redis缓存，
	 * 使用 channelCode字段拼为key
	 */
	public static void queryPayChannelAll() {
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		String todayDate = DateUtil.getTodayYYYYMMDDHHmmss();
		//缓存中取map
		Map<String,String> channelAllInfoMap = getRedisMap(CHANNRL_ALL);
		//查询数据
		List<PayChannel> payChannels = payChannelService.findAllList();
		for (PayChannel payChannel:payChannels){
			String key = payChannel.getChannelCode();
			//默认结算周期为t+1
			//payChannel.setOrderSettlePeriod("1");
			String value = JsonMapper.toJsonString(payChannel);
			jedisCluster.set(key,value);
			//放入map
			channelAllInfoMap.put(key,todayDate);
		}
		//遍历map删除缓存和map中的旧数据并将新map放入缓存中
		delOldDataFromRedis(channelAllInfoMap,todayDate,CHANNRL_ALL);
	}

	/**
	 * 删除路由通道的缓存
	 */
	public static void delRouteMap(String merchantChannelKey) {
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		//查询数据
		List<PayChannel> payChannels = payChannelService.findAllList();
		for (PayChannel payChannel : payChannels){
			jedisCluster.del(Constants.PAY_CHANNEL_ROUTE + ":" + payChannel.getChannelCode());
		}
	}
}
