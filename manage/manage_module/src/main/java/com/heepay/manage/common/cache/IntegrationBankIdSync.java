/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.heepay.codec.Md5;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.utils.HttpClientUtil;
import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.route.entity.IntegrationChannel;
import com.heepay.manage.modules.route.service.IntegrationChannelService;
import com.heepay.redis.JedisClusterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描    述：从.net同步bankId信息
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/4/21 15:21 
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
public class IntegrationBankIdSync {

	private static final Logger logger = LogManager.getLogger();

	private static PropertiesLoader loader = new PropertiesLoader("fastDFS.properties");

	private static final String BANKID_SYNC_URL = loader.getProperty("bankid.sync.url");

	public static final String BANNK_SYNC_MD5KEY = loader.getProperty("banid.sync.md5key");

	private static HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();

	private IntegrationBankIdSync(){
	}

	private static IntegrationChannelService integrationChannelService = SpringContextHolder.getBean(IntegrationChannelService.class);
	/**
	 * 同步
	 * @return
	 */
	public static void bankIdSync(){
		//获取
		String json = httpClientUtil.sendHttpGet(BANKID_SYNC_URL + "?sign=" + Md5.encode(BANNK_SYNC_MD5KEY));
		if (StringUtil.isBlank(json)){
			logger.info("从.net获取通道信息为空");
		}
		//json转list
		JavaType javaType = JsonMapperUtil.nonEmptyMapper().createCollectionType(ArrayList.class,IntegrationChannel.class);
		List<IntegrationChannel> integrationChannels = JsonMapperUtil.nonEmptyMapper().fromJson(json,javaType);
		//更新数据库
		integrationChannelService.saveList(integrationChannels);

		//更新redis缓存
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		JsonMapperUtil jsonMapperUtil = JsonMapperUtil.nonEmptyMapper();
		for(IntegrationChannel integrationChannel:integrationChannels){
			String jsonObject = jsonMapperUtil.toJson(integrationChannel);
			jedisCluster.set(integrationChannel.getChannelKey(),jsonObject);
		}
	}

}
