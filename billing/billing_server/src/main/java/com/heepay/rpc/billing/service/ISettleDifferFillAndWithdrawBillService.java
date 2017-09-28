package com.heepay.rpc.billing.service;

import com.heepay.billing.entity.SettleDifferRecord;

/**
 * 
 * 
 * 描    述：差错批次数据撤账和补账service，方便事务管理
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年11月18日下午2:24:44 
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
public interface ISettleDifferFillAndWithdrawBillService {
	
	/**
	 * 
	 * @方法说明：差错批次数据入库，补账和撤账逻辑
	 * @author chenyanming
	 * @param settleDifferRecord
	 * @时间：2016年11月18日下午2:27:39
	 */
	public void fillAndWithdrawBillMethod(SettleDifferRecord settleDifferRecord);
	
}







