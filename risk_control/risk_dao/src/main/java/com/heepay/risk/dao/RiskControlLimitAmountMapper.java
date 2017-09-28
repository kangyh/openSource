package com.heepay.risk.dao;

import java.util.List;
import java.util.Map;

import com.heepay.risk.entity.ChannelLimitAmountObj;
import com.heepay.risk.entity.ChannelLimitAmountParam;
import com.heepay.risk.entity.MerchantLimitAmountObj;
import com.heepay.risk.entity.MerchantLimitAmountParam;

/**
 * 
* 
* 描    述：
*
* 创 建 者：   王英雷
* 创建时间： 2016年11月22日 上午11:47:22 
* 创建描述：限额的基本操作

* 
*        
*/

public interface RiskControlLimitAmountMapper {

	MerchantLimitAmountObj getLimitAmountInfo(MerchantLimitAmountParam param);
	public Map getChannelLimitAmountInfo(String param);
	public List<Map> getChannelList(Map equalConditionMap,Map rangeCondition,Map page);
	public List<Map<String,String>> getChannelAggregationInfo(Map equalConditionMap,Map rangeCondition);
	List<Map<String,String>> getChannelAggInfoWithHost(Map equalConditionMap,Map notequalConditonMap,Map rangeCondition);
	
}
