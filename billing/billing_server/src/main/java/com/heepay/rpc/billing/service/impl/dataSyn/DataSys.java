/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月29日上午11:08:27
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
package com.heepay.rpc.billing.service.impl.dataSyn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.billing.service.IDataSysService;
import com.heepay.rpc.client.BillingClearAPIClient;
import com.heepay.rpc.service.RpcService;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月29日上午11:08:27
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
@Component
@RpcService(name="DataSysServiceImpl",processor=com.heepay.rpc.billing.service.OldRecordService.Processor.class)
public class DataSys implements com.heepay.rpc.billing.service.OldRecordService.Iface{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	BillingClearAPIClient billingClearAPIClient;
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;
	
	@Autowired
	IDataSysService dataSysServiceImpl;
	
	public String dataSynchronize(String transNo, String paymentId){
		
		logger.info("清结算历史数据同步开始， 交易订单号：{}， 支付单号：{}", transNo, paymentId);
		
		Map map = new HashMap();
		
		map.put("transNo", transNo);
		map.put("paymentId", paymentId);
		
		int num = clearChannelRecordDaoImpl.queryChannelByPaymentIdTransNo(map);
		
		if(num == 0){
			logger.info("清结算历史数据不存在， 交易订单号：{}， 支付单号：{}", transNo, paymentId);
			return "无历史数据需要同步";
		}

		try{
			dataSysServiceImpl.dataSynchronize(transNo, paymentId);
		}catch(Exception e){
			
		}
		return "历史数据同步结束";
	}
	/**
	 * 历史数据修复
	 */
	public String alldataSynchronize(){
		
		logger.info("---------清结算历史数据同步开始--------");
		
		 Map<String, Object> returnMap = new HashMap<String, Object>();
		 
		 int total = 0;  //总笔数
		 int successNum = 0; //成功总笔数
		 int failNum = 0;    //失败总笔数
		 List<String> failLisy = new ArrayList<String>();
		
		List<Map<String, String>> list = clearChannelRecordDaoImpl.queryValIsNull();
		
		if(list != null && list.size() != 0){
			
			total = list.size();
			for(Map<String, String> map: list){
				
				String paymentId= map.get("paymentId");
				String transNo =  map.get("transNo");
				
				if(paymentId == null || "".equals(paymentId)){
					failNum++;
					continue;
				}					
				try{
					dataSysServiceImpl.dataSynchronize(transNo, paymentId);
					successNum++;
				}catch(Exception e){
					logger.error("历史数据修复异常，交易订单号：{}， 支付单号：{}", transNo, paymentId);
					failNum++;
					failLisy.add(transNo);
				}			
			}		
		}else{
			logger.info("无需要修复的历史数据！");
		}		
		
		returnMap.put("success", successNum+"");
		returnMap.put("fail", failNum+"");
		returnMap.put("total", total+"");
//		returnMap.put("list",failLisy);		//明细暂时不需要
		
		String json = JsonMapperUtil.nonDefaultMapper().toJson(returnMap);
		return json;
	}	

}
