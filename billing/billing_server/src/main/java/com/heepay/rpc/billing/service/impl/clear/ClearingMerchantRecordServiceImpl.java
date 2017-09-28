package com.heepay.rpc.billing.service.impl.clear;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.rpc.service.RpcService;


/***
 * 
 * 
 * 描    述：用户清算记录接口根据清算流水号查询结算状态
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
@RpcService(name="ClearingMerchantRecordServiceWL",processor=com.heepay.rpc.billing.service.ClearingMerchantRecordService.Processor.class)
public class ClearingMerchantRecordServiceImpl implements com.heepay.rpc.billing.service.ClearingMerchantRecordService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordDao ;
	
	
	/**
	 * 
	 * @方法说明：根据交易订单号（支付单号）  查询商户侧结算状态（N：未结算，D：结算中 Y：已结算）'
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public String queryMerchantSettleStatusByTransNo(String transNo) throws TException {
		
		logger.info("根据交易订单号(支付单号)查询--{商户侧}--结算状态查询开始--->{}"+transNo);
		ClearMerchantRecord selectByTranNo = clearMerchantRecordDao.selectByTranNo(transNo);
		if(null==selectByTranNo){
			 
			logger.info("根据交易订单号(支付单号)查询--{商户侧}--结算状态结算状态查询结果为:--->无记录"+transNo);
			return "";
		}
		logger.info("根据交易订单号(支付单号)查询--{商户侧}--结算状态结算状态查询结果为:--->{}"+selectByTranNo.getSettleStatus());
		return selectByTranNo.getSettleStatus();
	}
	
	
}
