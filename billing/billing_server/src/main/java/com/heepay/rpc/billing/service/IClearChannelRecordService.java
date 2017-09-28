package com.heepay.rpc.billing.service;

import java.util.Map;

import com.heepay.rpc.billing.model.ClearChannelRecordModel;

/**
 * 
 * 
 * 描    述：通道段通用接口
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月9日上午9:12:35 
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
public interface IClearChannelRecordService {
	public void billingHandle(ClearChannelRecordModel model);
	public void billingHandle(Map<String, String> map);
}
