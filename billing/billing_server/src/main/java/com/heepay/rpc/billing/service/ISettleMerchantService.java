/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2016年11月17日下午4:24:43
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

import java.util.Map;

import com.heepay.billing.entity.SettleDicItem;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2016年11月17日下午4:24:43
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
public interface ISettleMerchantService {

//	public void saveSettleMerchantRecord(String transNo);
//	
//	public void test();
	
	public void saveAndSendSettleMsg(Map map, SettleDicItem settleDicItem, String settleCyc);
	
	public void sendProfitMessage(String transNo);
}
