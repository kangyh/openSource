package com.heepay.risk.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.risk.QuotaResponseStatus;
import com.heepay.risk.service.IBankFraudTransactionService;
import com.heepay.risk.vo.BankFraudQueryVO;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.service.BankFraudService;
import com.heepay.rpc.service.RpcService;
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年12月19日 
* 创建描述：银行欺诈下行查询接口

* 
*        
 */
@Component
@RpcService(name="BankFraudServiceImpl",processor=BankFraudService.Processor.class)
public class BankFraudServiceImpl implements  BankFraudService.Iface{
   @Autowired
   private IBankFraudTransactionService  bankFraudTransactionService;
   private static final Logger logger = LogManager.getLogger();
   private static final Object obj=new Object();
	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月19日 
	* 创建描述：支付账号动态查询

	* 
	*        
	 */
	@Override
	public AsyncMsgVO GetAccountDynamic(String arg0) throws TException {
		String jsonStr="";
		boolean flag=false;
		synchronized(obj)
		{
		int index=arg0.indexOf("attachments");
		if(index>-1)
			jsonStr=arg0.substring(0,index);
		logger.info("支付账号动态查询请求参数:"+jsonStr);
		AsyncMsgVO result=new AsyncMsgVO();
		result.setMsg(QuotaResponseStatus.SUCCESS.getContent());
		result.setStatus(QuotaResponseStatus.SUCCESS.getValue());
		BankFraudQueryVO vo=JsonMapperUtil.buildNonDefaultMapper().fromJson(arg0, BankFraudQueryVO.class);
		
		flag=this.bankFraudTransactionService.SaveAccountDynamic(vo);
		
		if(!flag)
			result.setStatus(QuotaResponseStatus.FAIL.getValue());
		return result;
		}
	}
	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月19日 
	* 创建描述：支付账号动态查询解除

	* 
	*        
	 */
	@Override
	public AsyncMsgVO GetAccountDynamicRelease(String arg0) throws TException {
		String jsonStr="";
		synchronized(obj)
		{
		int index=arg0.indexOf("attachments");
		if(index>-1)
			jsonStr=arg0.substring(0,index);
		logger.info("支付账号动态查询解除请求参数:"+jsonStr);
		AsyncMsgVO result=new AsyncMsgVO();
		result.setMsg(QuotaResponseStatus.SUCCESS.getContent());
		result.setStatus(QuotaResponseStatus.SUCCESS.getValue());
		BankFraudQueryVO vo=JsonMapperUtil.buildNonDefaultMapper().fromJson(arg0, BankFraudQueryVO.class);
	   boolean flag=this.bankFraudTransactionService.SaveReleaseFeedback(vo);
	   if(!flag)
			result.setStatus(QuotaResponseStatus.FAIL.getValue());
	   return result;
		}
	}
	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月19日 
	* 创建描述：获取账号主体详细信息

	* 
	*        
	 */
	@Override
	public AsyncMsgVO GetAccountSubjectDetailList(String arg0) throws TException {
		String jsonStr="";
		synchronized(obj)
		{
		int index=arg0.indexOf("attachments");
		if(index>-1)
			jsonStr=arg0.substring(0,index);
		logger.info("获取账号主体详细信息请求参数:"+jsonStr);
		BankFraudQueryVO vo=JsonMapperUtil.buildNonDefaultMapper().fromJson(arg0, BankFraudQueryVO.class);
		AsyncMsgVO result=new AsyncMsgVO();
		result.setMsg(QuotaResponseStatus.SUCCESS.getContent());
		result.setStatus(QuotaResponseStatus.SUCCESS.getValue());
		if(vo==null)
		return null;
		boolean flag=this.bankFraudTransactionService.SaveAccountDetail(vo);
		  if(!flag)
				result.setStatus(QuotaResponseStatus.FAIL.getValue());
		return result;
		}
	    
	}
	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月19日 
	* 创建描述：按交易流水号查询银行卡/支付帐号

	* 
	*        
	 */
	@Override
	public AsyncMsgVO GetBankCardInfoByTransSerialNo(String arg0) throws TException {
		String jsonStr="";
		synchronized(obj)
		{
		int index=arg0.indexOf("attachments");
		if(index>-1)
			jsonStr=arg0.substring(0,index);
		logger.info("根据交易号获取银行卡信息请求参数:"+jsonStr);
		BankFraudQueryVO vo=JsonMapperUtil.buildNonDefaultMapper().fromJson(arg0, BankFraudQueryVO.class);
		AsyncMsgVO result=new AsyncMsgVO();
		result.setMsg(QuotaResponseStatus.SUCCESS.getContent());
		result.setStatus(QuotaResponseStatus.SUCCESS.getValue());
		boolean flag=bankFraudTransactionService.SaveTransCardPaymentAccount(vo);
		 if(!flag)
				result.setStatus(QuotaResponseStatus.FAIL.getValue());
		return result;
		}
	}
	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月19日 
	* 创建描述：关联全支付账号查询

	* 
	*        
	 */
	@Override
	public AsyncMsgVO GetPaymentAccountList(String arg0) throws TException {
		String jsonStr="";
		synchronized(obj)
		{
		int index=arg0.indexOf("attachments");
		if(index>-1)
			jsonStr=arg0.substring(0,index);
		logger.info("关联全支付账号查询请求参数:"+jsonStr);
		BankFraudQueryVO vo=JsonMapperUtil.buildNonDefaultMapper().fromJson(arg0, BankFraudQueryVO.class);
		AsyncMsgVO result=new AsyncMsgVO();
		result.setMsg(QuotaResponseStatus.SUCCESS.getContent());
		result.setStatus(QuotaResponseStatus.SUCCESS.getValue());
		if(vo==null)
		return null;
		boolean flag=this.bankFraudTransactionService.SavePaymentAccount(vo);
		 if(!flag)
				result.setStatus(QuotaResponseStatus.FAIL.getValue());
		return result;
		}
	}
	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年12月19日 
	* 创建描述：支付账户交易明细查询

	* 
	*        
	 */
	@Override
	public AsyncMsgVO GetTransDetailList(String arg0) throws TException {
		String jsonStr="";
		synchronized(obj)
		{
		int index=arg0.indexOf("attachments");
		if(index>-1)
			jsonStr=arg0.substring(0,index);
		logger.info("支付账户交易明细查询请求参数:"+jsonStr);
		BankFraudQueryVO vo=JsonMapperUtil.buildNonDefaultMapper().fromJson(arg0, BankFraudQueryVO.class);
		AsyncMsgVO result=new AsyncMsgVO();
		result.setMsg(QuotaResponseStatus.SUCCESS.getContent());
		result.setStatus(QuotaResponseStatus.SUCCESS.getValue());
		if(vo==null)
		return null;
		boolean flag=this.bankFraudTransactionService.SaveTransInfo(vo);
		if(!flag)
			result.setStatus(QuotaResponseStatus.FAIL.getValue());
		return result;
		}
	}
	

}
