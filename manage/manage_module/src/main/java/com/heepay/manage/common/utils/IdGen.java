/**
 *  
 */
package com.heepay.manage.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator,SessionIdGenerator{

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	@Override
	public Serializable generateId(Session session) {
		// TODO Auto-generated method stub
		return IdGen.uuid();
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return IdGen.uuid();
	}


}
