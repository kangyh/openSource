package com.heepay.manage.modules.settle.web.rabbit;

import com.heepay.manage.modules.settle.entity.SettleChannelRecord;

import java.io.Serializable;
import java.util.List;


/**
 * 
 * 
 * 描    述：结算记录和清算明细
 *
 * 创 建 者：wangl
 * 创建时间： 2016年9月12日上午10:17:23 
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
public class SettleChannelMessage implements Serializable {

	private static final long serialVersionUID = 51478785553780860L;

	private SettleChannelRecordPo settleChannelRecordPo;

	private List<ClearDetailMessage> clearDetailMessage;

	public SettleChannelRecordPo getSettleChannelRecordPo() {
		return settleChannelRecordPo;
	}
	public void setSettleChannelRecordPo(SettleChannelRecordPo settleChannelRecordPo) {
		this.settleChannelRecordPo = settleChannelRecordPo;
	}
	public List<ClearDetailMessage> getClearDetailMessage() {
		return clearDetailMessage;
	}
	public void setClearDetailMessage(List<ClearDetailMessage> clearDetailMessage) {
		this.clearDetailMessage = clearDetailMessage;
	}
}
