package com.heepay.risk.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.risk.dao.RiskTransInfoMapper;
import com.heepay.risk.entity.TransInfoObj;
import com.heepay.rpc.risk.model.FreezeResponse;
import com.heepay.rpc.risk.model.TransactionModel;
import com.heepay.rpc.risk.service.TransactionService;
import com.heepay.rpc.service.RpcService;
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年12月05日 
* 创建描述：获取成功交易订单列表

* 
*        
 */
@Component
@RpcService(name="TransactionServiceImpl",processor=TransactionService.Processor.class)
public class TransactionServiceImpl implements com.heepay.rpc.risk.service.TransactionService.Iface {
    @Autowired
	private RiskTransInfoMapper riskTransInfoMapper;
	private static final Logger logger = LogManager.getLogger();
	/**
	 * 获取成功交易订单列表
	 * @param whereCause 查询条件的json字符串
	 * @param pageIndex 页索引 初始化为0
	 * @param pageSize  每页显示的记录数
	 * @return   成功交易订单列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public FreezeResponse getTransactionList(String whereCause, int pageIndex, int pageSize) throws TException {
		logger.info("查询条件信息:{}",whereCause);
		TransInfoObj obj=null;
		@SuppressWarnings("rawtypes")
		HashMap whereMap=new HashMap();
		FreezeResponse result=new FreezeResponse();
		String startTime=null;
		String endTime=null;
		List<TransactionModel> list=new  ArrayList<TransactionModel>();
		if(StringUtil.isBlank(whereCause))
			obj= riskTransInfoMapper.getTransInfoList(new HashMap(),null, null, String.valueOf(pageSize),  String.valueOf(pageIndex));
		else
		{
			HashMap<String,Object> map=JsonMapperUtil.buildNonDefaultMapper().fromJson(whereCause, HashMap.class);
			if(map!=null)
			{
				if(map.get("merchantId")!=null&&!StringUtil.isBlank(map.get("merchantId").toString()))
					whereMap.put("merchant_id", map.get("merchantId").toString());
				if(map.get("merchantLoginName")!=null&&!StringUtil.isBlank(map.get("merchantLoginName").toString()))
					whereMap.put("merchant_login_name", map.get("merchantLoginName").toString());
				if(map.get("transNo")!=null&&!StringUtil.isBlank(map.get("transNo").toString()))
					whereMap.put("trans_no", map.get("transNo").toString());
				if(map.get("transType")!=null&&!StringUtil.isBlank(map.get("transType").toString()))
					whereMap.put("trans_type", map.get("transType").toString());
				if(map.get("beginCreateTime")!=null)
					startTime=String.valueOf((Long)map.get("beginCreateTime"));
				if(map.get("endCreateTime")!=null)
					endTime=String.valueOf((Long)map.get("endCreateTime"));
				obj= riskTransInfoMapper.getTransInfoList(whereMap,startTime, endTime, String.valueOf(pageSize),  String.valueOf(pageIndex));
			}
		}
		
		if(obj!=null)
		{
			result.setTotalCount(Long.valueOf(obj.getTotalsize()));
			List<Map> mapList=obj.getTransinfo();
			//logger.info("查询结果信息:{}",JsonMapperUtil.buildNonDefaultMapper().toJson(obj.getTransinfo()));
			for(Map tempMap:mapList)
			{
				TransactionModel model=new TransactionModel();
				model.setMerchantId(tempMap.get("merchant_id")!=null?tempMap.get("merchant_id").toString():"");
				model.setMerchantLoginName(tempMap.get("merchant_login_name")!=null?tempMap.get("merchant_login_name").toString():"");
				model.setTransType(tempMap.get("trans_type")!=null?tempMap.get("trans_type").toString():"");
				model.setSuccessAmount(tempMap.get("pay_amount")!=null?tempMap.get("pay_amount").toString():"");
				model.setTransNo(tempMap.get("trans_no")!=null?tempMap.get("trans_no").toString():"");
				model.setSuccessTime(tempMap.get("pay_time")!=null?DateUtil.dateToString(new Date(Long.parseLong(tempMap.get("pay_time").toString()))):"");
				model.setFeeWay(tempMap.get("fee_type")!=null?tempMap.get("fee_type").toString():"");
				model.setFee(tempMap.get("fee_amount")!=null?tempMap.get("fee_amount").toString():"");
				list.add(model);
			}
			result.setTransList(list);
		}
		return result;
	
	}

}
