package com.heepay.rpc.billing.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.heepay.rpc.billing.model.ClearMerchantRecordModel;


/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年9月9日上午9:34:43
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
public interface IMerchantClearing {
	public void billingHandle(ClearMerchantRecordModel model);
	public void billingHandle(Map<String, String> map);
	public void saveClearExceptionData(Map<String, String> map);
}
