package com.heepay.rpc.billing.service;

import org.springframework.stereotype.Service;

/**
 * 
 *
 * 描    述：清结算数据库备份接口
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月23日 上午10:34:16
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
@Service
public interface IBillingDataBackUpService {
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：清结算数据库备份方法
	 * @时间：2017年3月23日 上午10:35:01
	 * @创建人：wangdong
	 */
	void billingDataBackUp() throws Exception;
}
