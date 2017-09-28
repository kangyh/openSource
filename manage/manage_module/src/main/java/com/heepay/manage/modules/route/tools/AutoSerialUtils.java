package com.heepay.manage.modules.route.tools;

import com.heepay.redis.JedisClusterUtil;
import redis.clients.jedis.JedisCluster;

import java.text.NumberFormat;

public class AutoSerialUtils {

	public static String generator() {
		// 生成自增长序列
		JedisCluster jc = JedisClusterUtil.getJedisCluster();
		long a = jc.incr("1");
	//	System.out.println(a);

		// 补足至六位
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumIntegerDigits(6);
		formatter.setGroupingUsed(false);
		String s = formatter.format(a);
		return s;
	}
	
	/*public static void main(String[] args) {
		generator();
	}*/
}

	  

		

	 

