package com.heepay.rpc.billing.message;

import java.io.Serializable;
import java.util.List;

import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.vo.ClearDetailMessage;

/***
 * 
* 
* 描    述：结算记录和清算明细
*
* 创 建 者： xuangang
* 创建时间：  2016年9月12日下午4:32:45
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
public class SettleMerchantMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4946173065449810591L;
    //结算记录
	private SettleMerchantRecord settleMerchantRecord;
	//清算明细
	private List<ClearDetailMessage> clearMerchantMessage;
	public SettleMerchantRecord getSettleMerchantRecord() {
		return settleMerchantRecord;
	}
	public void setSettleMerchantRecord(SettleMerchantRecord settleMerchantRecord) {
		this.settleMerchantRecord = settleMerchantRecord;
	}
	public List<ClearDetailMessage> getClearMerchantMessage() {
		return clearMerchantMessage;
	}
	public void setClearMerchantMessage(
			List<ClearDetailMessage> clearMerchantMessage) {
		this.clearMerchantMessage = clearMerchantMessage;
	}
	
	
	
}