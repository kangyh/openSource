package com.heepay.rpc.billing.service.impl.clear;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.StringUtils;
import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.service.RpcService;


/***
 * 
 * 
 * 描    述：两小时跑批汇总t+0消费实时
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月12日下午6:11:19
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
@RpcService(name="ClearMerchantRecordServiceL",processor=com.heepay.rpc.billing.service.ClearMerchantRecordService.Processor.class)
public class ClearMerchentImpl implements com.heepay.rpc.billing.service.ClearMerchantRecordService.Iface {

	private static final Logger logger = LogManager.getLogger();
	
	
	@Autowired
	private MethodTransactional methodTransactional;
	
	@Autowired
	private MethodTransactional2 methodTransactional2;
	
	

	@Override
	public int saveClearMerchantRecord(ClearMerchantRecordModel clearMerchantRecordModel)throws TException {
		// TODO Auto-generated method stub   
		return 0;
	}
	
	
	@Override
	public int updateClearMerchantRecord(ClearMerchantRecordModel clearMerchantRecordModel)throws TException {
		
		return 0;
	}

	@Override
	public void deleClearMerchantRecord(ClearMerchantRecordModel clearMerchantRecordModel)throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ClearMerchantRecordModel> queryByConditions(ClearMerchantRecordModel clearMerchantRecordModel)throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String getClearMerchantRecordMessage(ClearMerchantRecordModel clearMerchantRecordModel1) throws TException {
		// TODO Auto-generated method stub
		return null;
	}	
	
	/**
	 * 
	 * @方法说明：两小时跑批汇总t+0消费实时
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public void getClearMerchantRecord() throws TException {
		
		logger.info("商户侧t+0 2小时汇总清算记录开始----------->{}");	
		try {
			String message = methodTransactional.getClearMerchantRecordMessage();
				logger.info("商户侧t+0 2小时汇总清算记录------>2:00:00之前----->{}汇总完成");	
			if(!StringUtils.isNotBlank(message)){
				logger.info("商户侧t+0 2小时汇总清算记录------>{}出异常了");	
			}
			
			if(StringUtils.isNotBlank(message)){
				methodTransactional2.getClearMerchantRecordMessage();
				logger.info("商户侧t+0 2小时汇总清算记录------>2:00:00之后----->{}汇总完成");	
			}
		} catch (Exception e) {
			logger.error("商户侧t+0汇总清算出错原因----------->{}",e);
		}
	}


	/* (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.ClearMerchantRecordService.Iface#settleDataSave(java.lang.String)
	 */
	@Override
	public void settleDataSave(String arg0) throws TException {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.ClearMerchantRecordService.Iface#saveClearExceptionData(java.lang.String)
	 */
	@Override
	public void saveClearExceptionData(String arg0) throws TException {
		// TODO Auto-generated method stub
		
	}
}
