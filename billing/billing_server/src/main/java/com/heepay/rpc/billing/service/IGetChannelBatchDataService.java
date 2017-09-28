package com.heepay.rpc.billing.service;

import com.heepay.billing.entity.SettleChannelRecordVo;

/**
 * 
 * 
 * 描    述：通道侧生成结算批次数据和清算明细,并发送给账务系统
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月18日下午3:06:09 
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
public interface IGetChannelBatchDataService {
	/**
	 * 
	 * @方法说明：生成结算数据和清算明细数据,并发送给账务系统
	 * @author chenyanming
	 * @param checkBathno
	 * @时间：2016年11月18日下午3:07:13
	 */
	public void getSettleChannelRecordAndSend(SettleChannelRecordVo settleChannelRecordVo);
}
