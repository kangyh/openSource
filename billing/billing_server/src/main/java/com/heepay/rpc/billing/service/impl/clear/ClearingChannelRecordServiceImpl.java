package com.heepay.rpc.billing.service.impl.clear;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.rpc.service.RpcService;


/***
 * 
 * 
 * 描    述：商户侧清算记录接口根据清算流水号查询结算状态
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
@RpcService(name="ClearingChannelRecordServiceWL",processor=com.heepay.rpc.billing.service.ClearingChannelRecordService.Processor.class)
public class ClearingChannelRecordServiceImpl implements com.heepay.rpc.billing.service.ClearingChannelRecordService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearChannelRecordMapper ClearChannelRecordDao ;
	
	@Autowired
	SettleDifferChannelMapper settleDifferChannelDaoImpl;
	
	/**
	 * 
	 * @方法说明：根据交易订单号（支付单号）  查询通道侧结算状态（N：未结算，D：结算中 Y：已结算）'
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	@Override
	public String queryChannelSettleStatusByTransNo(String transNo) throws TException {
		
		logger.info("根据交易订单号(支付单号)查询--{通道侧}--结算状态查询开始--->{}"+transNo);
		try{
			ClearChannelRecord queryClearChannelRecordByTranNo = ClearChannelRecordDao.queryClearChannelRecordByTranNo(transNo);
			if(null != queryClearChannelRecordByTranNo){ 			
				
				return queryClearChannelRecordByTranNo.getSettleStatus();
			}else{ //清算表不存在，查询通道差异表
				SettleDifferChannel settleDifferChannel = settleDifferChannelDaoImpl.querySettleDifferChannelByTransNo(transNo);
				
				if(settleDifferChannel != null){
					return settleDifferChannel.getErrorStatus(); //差错状态（N：未处理 D：处理中 Y：已处理）    
				}
				return "";
			}	
		}catch(Exception e){
			logger.error("交易系统查询交易结算状态异常,交易订单号：{}");
		}
		return "";
			
	}	
	
}
