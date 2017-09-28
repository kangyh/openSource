package com.heepay.risk.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.heepay.risk.dao.RiskControlLimitAmountMapper;
import com.heepay.risk.entity.ChannelLimitAmountObj;
import com.heepay.risk.entity.ChannelLimitAmountParam;
import com.heepay.risk.entity.MerchantLimitAmountObj;
import com.heepay.risk.entity.MerchantLimitAmountParam;
import com.heepay.risk_es_engine.ESearchService;

import net.sf.json.JSONObject;

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
@Repository
public class RiskControlLimitAmountDaoImpl implements RiskControlLimitAmountMapper {
	@Autowired
	private ESearchService esearch;
	public MerchantLimitAmountObj getLimitAmountInfo(MerchantLimitAmountParam param)
	{
		MerchantLimitAmountObj merchantLimitAmountObj = new MerchantLimitAmountObj();
		merchantLimitAmountObj.setMerchantId(param.getMerchantId());
		merchantLimitAmountObj.setDayAmount(new BigDecimal(0.0));
		merchantLimitAmountObj.setMonthAmount(new BigDecimal(0.0));
		Map map = esearch.getDayAndMonthLimitAmount(param.getMerchantId(), param.getProductType(), param.getAccountType(), param.getBankType());
		merchantLimitAmountObj.setMerchantId(param.getMerchantId());
		merchantLimitAmountObj.setDayAmount(new BigDecimal(map.get("day").toString()));
		merchantLimitAmountObj.setMonthAmount(new BigDecimal(map.get("month").toString()));
		return merchantLimitAmountObj;
	}
	/**
	 * 查询通道日限额 月限额
	 */
	public Map getChannelLimitAmountInfo(String param)
	{
		JSONObject json=JSONObject.fromObject(param);
		Map map = esearch.getChannelDayAndMonthLimitAmount(json);
		return map;
	}
	@Override
	public List<Map> getChannelList(Map equalConditionMap,
			Map rangeCondition, Map page) {
		String[] fields={"payment_id","channel_code","channel_partner_code","channel_partner_name","channel_type_code","channel_type_name"
				,"bank_no","bank_name","account_type","business_type","amount","request_status","result","url","card_type_code","card_type_name","req_time","resp_time","day_limit_amount","month_limit_amount"};
		return esearch.getChannelList(fields, equalConditionMap, null, rangeCondition, page);
	}
	@Override
	public List<Map<String, String>> getChannelAggregationInfo(Map equalConditionMap, Map rangeCondition) {
		return esearch.getChannelAggregationInfo(equalConditionMap,null,rangeCondition);
	}

	@Override
	public List<Map<String, String>> getChannelAggInfoWithHost(Map equalConditionMap, Map notequalConditonMap, Map rangeCondition) {
		return esearch.getChannelAggInfoWithHost(equalConditionMap,notequalConditonMap,rangeCondition);
	}
}
