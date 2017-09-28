package com.heepay.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 
* 
* 描    述：用户端结算明细集合
*
* 创 建 者： xuangang
* 创建时间：  2016年9月12日下午5:02:59
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
public class SettleMerchantRecordMessage implements Serializable{

	
		private static final long serialVersionUID = -5056344867775801742L;
		//用户侧结算记录集合map的key为商户编码
		private Map<String, List<SettleChannelMessage>> SettleChannelRecordMap = new HashMap<String, List<SettleChannelMessage>>();

		public Map<String, List<SettleChannelMessage>> getSettleChannelRecordMap() {
			return SettleChannelRecordMap;
		}

		public void setSettleChannelRecordMap(Map<String, List<SettleChannelMessage>> settleChannelRecordMap) {
			SettleChannelRecordMap = settleChannelRecordMap;
		}
}
