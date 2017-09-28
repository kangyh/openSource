package com.heepay.tpds.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.HttpClientUtils;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.rpc.tpds.model.DepositMsgVO;
import com.heepay.tpds.vo.DepositWithdrawVo;
import com.heepay.tpds.vo.PaymentCommon;
import com.heepay.utils.http.HttpClientUtil;

/*  com.heepay.tpds.client;
/**
 * 
 * 
 * 描    述：内部接口   提现接口客户端
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月9日上午10:45:52 
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
public class WithdrawClient {
	
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private PaymentCommon paymentCommon;
	/**
	 * 
	 * 
	 * 描    述：存管户提现
	 *
	 * 创 建 者：dongzhengjiang
	 * 创建时间： 2017年2月13日上午10:45:52 
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
     public  DepositMsgVO DepositWithdraw(DepositWithdrawVo vo)
     {
    	 Map<String,String> map=new HashMap<String,String>();
    	 map.put("merchantId", vo.getMerchantId());
    	 map.put("amount", vo.getAmount());
    	 map.put("merchantOrderNo", vo.getBusinessSeqNo());
    	 map.put("notifyUrl", "");
    	 try
    	 {
    	  String str= HttpClientUtil.RequestPost(paymentCommon.getPaymentDepositUrl(), map);
    	  if(StringUtil.isBlank(str))
    		  return null;
    	  else
    		  return JsonMapperUtil.nonDefaultMapper().fromJson(str, DepositMsgVO.class);
    	 }
    	 catch(Exception ex)
    	 {   
    		 logger.error("存管提现出现异常:{}",ex.getMessage());
    				 
    		 return null;
    	 }
     }
     /**
 	 * 
 	 * 
 	 * 描    述：存管户提现查询
 	 *
 	 * 创 建 者：dongzhengjiang
 	 * 创建时间： 2017年2月14日上午10:45:52 
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
     public  DepositMsgVO DepositWithdrawQuery(String merchantId,String businessSeqNo)
     {
    	 Map<String,String> map=new HashMap<String,String>();
    	 map.put("merchantId", merchantId);
    	 map.put("merchantOrderNo", businessSeqNo);
    	 try
    	 {
    	 String msg= HttpClientUtil.RequestPost(paymentCommon.getPaymentDepositQueryUrl(), map);
    	 return JsonMapperUtil.nonDefaultMapper().fromJson(msg, DepositMsgVO.class);
    	 }
    	 catch(Exception ex)
    	 {
    		 logger.error("存管提现查询出现异常:{}",ex.getMessage());
    		 DepositMsgVO vo=new DepositMsgVO();
    		 vo.setCode(-1);
    		 vo.setMsg("提现失败");
    		 return vo;
    	 }
     }
    
}
