package com.heepay.risk.dao;

import java.util.List;
import java.util.Map;


import com.heepay.risk.entity.TransInfoObj;

/**
 * 描    述：类说明 交易信息接口定义说明
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2016年12月3日 下午4:17:13
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
public interface RiskTransInfoMapper {
	TransInfoObj getTransInfoList(Map condition,String begintime,String endtime,String pagesize,String pageindex);
	List<Map> getAntiFraudList(Map condition,String begintime,String endtime);
	Map getAggregationInfo();
}
