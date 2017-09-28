/**
 * 
 */
package com.heepay.manage.modules.route.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.heepay.redis.JedisClusterUtil;
/**
 * <p>Title:CacheTest.java</p> 
 * <p>Description:通道缓存测试类</p>
 * <p>Company:hy</p>
 * @author M.Z
 * @date 2016年8月16日 下午5:26:35
 */
public class CacheTest {
	
	private static Logger logger = LogManager.getLogger();
	
    @Test
	public void testGetJedisValue(){
		/*//商户通道	
	    JedisClusterUtil.getJedisValue(getter -> getter.get("310EBANKBATCHPCREDITnull"));
		JedisClusterUtil.getJedisValue(getter -> getter.get("310DIRCONBATCHPSAVINGnull"));
		JedisClusterUtil.getJedisValue(getter -> getter.get("402DIRCONQUICKPSAVINGnull"));
		//通道（按比例）
		JedisClusterUtil.getJedisValue(getter -> getter.get("310DIRCONBATCHPSAVINGRATE"));
		JedisClusterUtil.getJedisValue(getter -> getter.get("402YSEPAYB2BEBSAVINGRATE"));
		//通道（按比数）
		JedisClusterUtil.getJedisValue(getter -> getter.get("310EBANKBATCHPCREDITCOUNT"));
		JedisClusterUtil.getJedisValue(getter -> getter.get("402DIRCONQUICKPSAVINGCOUNT"));*/
    	
    	//通道类型对应的银行及卡类型
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("B2BEB", "310")));
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("B2BEB", "402")));
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("BATCHP", "310")));
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("BATCHP", "402")));
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("BATCHP", "510")));
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("QUICKP", "402")));
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hget("SIGN", "631")));  	
//    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.hgetAll("QUICKP")));
    	
    	//跨行通道
    	logger.info(JedisClusterUtil.getJedisValue(getter -> getter.get("308DIRCONBATCHPSAVINGPRIVATRATE")));
    }
}

