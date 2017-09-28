package com.heepay.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 描    述：结算批次和结算明细数据对象
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月12日上午10:12:46 
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
public class SettleChannelRecordMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5056344867775801742L;
	
	private Map<String, List<SettleChannelMessage>> SettleChannelRecordMap = new HashMap<String, List<SettleChannelMessage>>();

	public Map<String, List<SettleChannelMessage>> getSettleChannelRecordMap() {
		return SettleChannelRecordMap;
	}

	public void setSettleChannelRecordMap(Map<String, List<SettleChannelMessage>> settleChannelRecordMap) {
		SettleChannelRecordMap = settleChannelRecordMap;
	}
	
	
}
