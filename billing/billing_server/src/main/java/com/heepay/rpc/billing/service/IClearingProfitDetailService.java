package com.heepay.rpc.billing.service;

import org.springframework.stereotype.Service;

import com.heepay.rpc.billing.model.ClearShareProfitModel;

/**
 * 
 *
 * 描    述：分润入库和更新商户侧清算记录service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月17日 下午5:45:02
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
public interface IClearingProfitDetailService {

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：分润入库和更新商户侧清算记录
	 * @时间：2016年11月17日 下午5:46:06
	 * @创建人：wangdong
	 */
	void saveClearingProfit(ClearShareProfitModel clearShareProfitVo) throws Exception;
}
