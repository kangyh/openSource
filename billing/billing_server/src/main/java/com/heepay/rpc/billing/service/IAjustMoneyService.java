package com.heepay.rpc.billing.service;
/**
 * 
 * 
 * 描    述：清结算系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年5月18日 下午4:12:23
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
@FunctionalInterface
public interface IAjustMoneyService<T,R> {
	R apply(T t);
}
