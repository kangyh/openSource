package com.heepay.rpc.billing.service;
/**
 * 
 * 
 * 描    述：对账明细数据入库service，方便事务管理
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月18日下午2:54:22 
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
public interface IBillPutInStorageService {
	/**
	 * 
	 * @方法说明：对账明细数据入库
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @param checkBathno
	 * @param fileName
	 * @return
	 * @时间：2016年11月18日下午2:55:49
	 */
	public boolean billPutInStorage(String channelCode, String channelType, String checkBathno, String fileName, String settleWay, String ruleType);
}
