package com.heepay.risk.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.heepay.date.DateUtils;
import com.heepay.risk.dao.RiskTransInfoMapper;
import com.heepay.risk.entity.MerchantLimitAmountObj;
import com.heepay.risk.entity.TransInfoObj;
import com.heepay.risk_es_engine.ESearchService;

/**
 * 描    述：风控系统  服务类
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2016年12月3日 下午4:27:47
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

@Repository
public class RiskTransInfoDaoImpl implements RiskTransInfoMapper{
	@Autowired
	ESearchService esearch;
	private static final Logger log = LogManager.getLogger(); 
/**
 * condition 所包含的key 枚举 trans_no,pay_time,pay_amount,trans_type,merchant_id,merchant_login_name,pay_status,product_code,bankcard_type,channel_code,merchant_order_no,bankcard_owner_type
 *pagesize 每页显示的数据，pageindex 从 0 开始 显示的索引数
 */
	@Override
	public TransInfoObj getTransInfoList(Map condition, String begintime, String endtime, String pagesize,
			String pageindex) {
		log.info("riskmessage method getTransInfoList 参数  condition:"+ (condition!=null?condition.toString():"")+"begintime:"+(begintime !=null?begintime:"")+"endtime:"+(endtime!=null?endtime:""));
		String[] fields = new String[]{"merchant_id","merchant_login_name","trans_type","pay_amount","trans_no","pay_time","fee_type","fee_amount"};
		List<Map> paytimeConditionList = null;
		Map paytimeCondition = null;
		if(begintime !=null && endtime!=null)
		{
			paytimeConditionList = new ArrayList<Map>();
			paytimeCondition =new HashMap();
			paytimeCondition.put("field", "pay_time");
			paytimeCondition.put("begin", begintime);
			paytimeCondition.put("end", endtime);
			paytimeConditionList.add(paytimeCondition);
		}
		List notConditioinMapList = new ArrayList();
		Map notConditionMap1 = new HashMap();
		notConditioinMapList.add(notConditionMap1);
		notConditionMap1.put("trans_type", "rename");  //去掉实名认证交易
		Map notConditionMap2 = new HashMap();
		notConditioinMapList.add(notConditionMap2);
		notConditionMap2.put("trans_type", "shares");  //去掉分润交易
		
		List conditionMapList = new ArrayList();
		if (condition !=null)
		{
			condition.put("pay_status", "succes"); //前台只查询成功的，为冻结做服务
			
		}else
		{
			condition = new HashMap();
			condition.put("pay_status", "succes"); //前台只查询成功的，为冻结做服务
		}
		conditionMapList.add(condition); //前台传过来的参数
		
		Map page = new HashMap();
		page.put("pagefrom", Integer.parseInt(pageindex)*Integer.parseInt(pagesize));
		page.put("pagesize", pagesize);
		// TODO Auto-generated method stub
		TransInfoObj transInfoObj = new TransInfoObj();
		List<Map> listMap = esearch.getTransData(fields,conditionMapList,notConditioinMapList, paytimeConditionList, page);
		transInfoObj.setTotalsize(page.get("totalsize").toString());
		transInfoObj.setTransinfo(listMap);
		return transInfoObj;
	}
	/**
	 * 根据条件查询反欺诈要求的结果集
	 */
	@Override
	public List<Map> getAntiFraudList(Map condition, String begintime, String endtime) {
		// TODO Auto-generated method stub
		String[] fields = new String[]{"merchant_id","merchant_login_name","trans_type",
				"pay_amount","trans_no","pay_time","bank_name","trans_id","payment_id"
				,"bank_order_id","transaction_IP","transInBank_ID"};
		List<Map> paytimeConditionList = null;
		Map paytimeCondition = null;
		if(begintime !=null && endtime!=null)
		{
			paytimeConditionList = new ArrayList<Map>();
			paytimeCondition =new HashMap();
			paytimeCondition.put("field", "pay_time");
			paytimeCondition.put("begin", begintime);
			paytimeCondition.put("end", endtime);
			paytimeConditionList.add(paytimeCondition);
		}
		List conditionMapList = new ArrayList();
		if (condition !=null)
		{
			conditionMapList.add(condition); //前台传过来的参数
		}
		
		Map page = new HashMap();
		page.put("pagefrom",0);
		//查询前1000笔交易
		page.put("pagesize", 1000);
		// TODO Auto-generated method stub
		TransInfoObj transInfoObj = new TransInfoObj();
		List<Map> listMap = esearch.getTransData(fields,conditionMapList,null, paytimeConditionList, page);
		return listMap;
	}
    /**
     * 按交易类型得到交易成功的汇总
     */
	public Map<String,String> getAggregationInfo()
	{
		List rangeCondition = new ArrayList();
		List conditionMapList = new ArrayList();
		Map<String,String> condition = new HashMap<String,String>();
		condition.put("pay_status", "succes"); //只查询成功
		conditionMapList.add(condition);
		
		long currentDateBegin = DateUtils.startTime(new Date()).getTime(); //当天开始时间
		long currentDateEnd = DateUtils.endTime(new Date()).getTime();//当天结束时间
		
		Map paytimeCondition =new HashMap();
		paytimeCondition.put("field", "pay_time");
		paytimeCondition.put("begin", String.valueOf(currentDateBegin));
		paytimeCondition.put("end", String.valueOf(currentDateEnd));
		rangeCondition.add(paytimeCondition);
		Map<String,String> listMap = (Map<String,String>) esearch.getAggregationInfo( conditionMapList, null, rangeCondition);
		return listMap;
	}
	
}
