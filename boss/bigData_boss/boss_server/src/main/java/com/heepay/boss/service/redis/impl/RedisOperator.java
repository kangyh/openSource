package com.heepay.boss.service.redis.impl;

import java.util.Map;
import java.util.TreeSet;

import com.heepay.boss.service.redis.IRedisOperator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年3月7日 上午11:08:41
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
public class RedisOperator implements IRedisOperator {
	  
	private static final Logger logger = LogManager.getLogger();
     private JedisCluster jedisCluster; 
    @Override  
    public TreeSet<String> keys(String pattern){  
        //logger.debug("Start getting keys...");  
        TreeSet<String> keys = new TreeSet<>();  
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();  
        for(String k : clusterNodes.keySet()){  
            JedisPool jp = clusterNodes.get(k);  
            Jedis connection = jp.getResource();  
            try {  
                keys.addAll(connection.keys(pattern));  
            } catch(Exception e){  
                logger.error("Getting keys error: {}", e);  
            } finally{  
                //logger.debug("Connection closed.");  
                connection.close();//用完一定要close这个链接！！！  
            }  
        } 
        return keys;  
    }

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}  
}  


