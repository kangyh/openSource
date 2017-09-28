package com.heepay.queue.consume;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.billing.model.ClearMerchantRecordModel;


/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月8日下午3:31:21
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
public interface IMqMerchantClearing {
	public void billingHandle(ClearMerchantRecordModel model);
	public void settleDataSave(String message) throws TException;
	public void saveClearExceptionData(String message) throws TException;

}
