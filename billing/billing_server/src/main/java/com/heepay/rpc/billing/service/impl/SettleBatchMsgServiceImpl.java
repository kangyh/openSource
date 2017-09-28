package com.heepay.rpc.billing.service.impl;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.rpc.billing.model.SettleBatchMsgModel;
import com.heepay.rpc.billing.service.impl.query.QueryMerchantSettleServiceImpl;
import com.heepay.rpc.service.RpcService;

/**
 * 
 * 
 * 描    述：根据结算批次号分页查询通道和商户侧结算明细数据
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年7月6日下午5:04:06 
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
@RpcService(name = "SettleBatchMsgServiceImpl", processor = com.heepay.rpc.billing.service.SettleBatchService.Processor.class)
public class SettleBatchMsgServiceImpl implements com.heepay.rpc.billing.service.SettleBatchService.Iface {

	@Autowired
	QueryChannelSettleMsgServiceImpl QueryChannelSettleMsgServiceImpl;
	
	@Autowired
	QueryMerchantSettleServiceImpl queryMerchantSettleServiceImpl;
	
	@Override
	public SettleBatchMsgModel queryChannelSettleBatch(String settleBatch, int pageNum, int pageSize) throws TException {
		return QueryChannelSettleMsgServiceImpl.querySettleBathMsg(settleBatch, pageNum, pageSize);
		
	}

	@Override
	public SettleBatchMsgModel queryMerchantSettleBatch(String settleBatch, int pageNum, int pageSize) throws TException {
		
		return queryMerchantSettleServiceImpl.querySettleBathMsg(settleBatch, pageNum, pageSize);
		
	}

}
