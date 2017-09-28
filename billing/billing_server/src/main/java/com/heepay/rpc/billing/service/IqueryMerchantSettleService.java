/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年7月7日上午9:18:21
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
package com.heepay.rpc.billing.service;

import com.heepay.rpc.billing.model.SettleBatchMsgModel;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年7月7日上午9:18:21
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
public interface IqueryMerchantSettleService {
	/**
	 * 
	 * @方法说明：查询商户侧清算数据
	 * @author xuangang
	 * @param settleBatch
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @时间：2017年7月7日上午9:22:26
	 */
	public SettleBatchMsgModel querySettleBathMsg(String settleBatch, int pageNum, int pageSize);

}
