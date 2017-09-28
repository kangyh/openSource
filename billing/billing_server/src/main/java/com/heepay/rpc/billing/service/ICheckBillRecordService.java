package com.heepay.rpc.billing.service;

import org.springframework.stereotype.Service;

import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleDifferRecord;

/**
 * 
 *
 * 描    述：对账主方法的service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月17日 下午5:42:02
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
public interface ICheckBillRecordService {

	/**
	 * 
	 * @方法说明：对账方法
	 * @时间：2016年11月17日 下午5:43:38
	 * @创建人：wangdong
	 */
	boolean billCompare(String checkBathno);
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：差异单入库
	 * @时间：2016年11月17日 下午6:19:22
	 * @创建人：wangdong
	 */
	void insertSettleDifferRecordMethod(SettleChannelLog log,ClearChannelRecord cRecord,ClearMerchantRecord mRecord,SettleBillRecord bRecord,SettleDifferRecord settleDifferRecord) throws Exception;
	
}
