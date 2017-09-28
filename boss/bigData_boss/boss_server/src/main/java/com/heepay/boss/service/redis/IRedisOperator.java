package com.heepay.boss.service.redis;

import java.util.TreeSet;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年3月7日 上午11:08:08
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
public interface IRedisOperator {
	/** 
     * 根据pattern 获取所有的keys 
     * @param pattern 
     * @return 
     */  
    TreeSet<String> keys(String pattern); 
}



