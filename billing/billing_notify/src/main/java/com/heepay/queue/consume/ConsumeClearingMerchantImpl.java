package com.heepay.queue.consume;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.rpc.billing.model.ClearMerchantRecordModel;
import com.heepay.rpc.client.ConsumeClearMerchantClient;

/**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：xuangang
 * 创建时间： 2016年9月8日下午3:48:41 
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
public class ConsumeClearingMerchantImpl implements IMqMerchantClearing {

	@Autowired
	ConsumeClearMerchantClient mqHandleClient;
	@Override
	public void billingHandle(ClearMerchantRecordModel model) {
		mqHandleClient.insertMerchantRecord(model);
	}
	
	/**
	 * @描述  清结算数据商户侧、通道侧合并处理
	 * @author xuangang
	 * @date   2017-05-19
	 */
	@Override
	public void settleDataSave(String message) throws TException {
		// TODO Auto-generated method stub
		mqHandleClient.settleDataSave(message);
	}
	
	public void saveClearExceptionData(String message)throws TException{
		
		mqHandleClient.saveClearExceptionData(message);
	}

}
