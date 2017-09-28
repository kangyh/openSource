package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleChannelRecordMapper;
import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleChannelRecordExample;
import com.heepay.billingutils.util.Constants;
import com.heepay.date.DateUtils;

@Repository
public class SettleChannelRecordDaoImpl extends BaseDaoImpl<SettleChannelRecord, SettleChannelRecordExample> implements SettleChannelRecordMapper{
	//默认构造方法设置命名空间
		public SettleChannelRecordDaoImpl() {
			super.setNs("com.heepay.billing.dao.SettleChannelRecordMapper");
		}
		
		@Override
		public int insert(SettleChannelRecord record) {
			return super.getSqlSession().insert(this.getNs() + ".insert", record);
		}

		/**
		 * // 核账完成，修改结算记录中结算状态为已结算（chen）
		 */
		@Override
		public void updateSettleChannelRecordStatus(String settleBath) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("finishTime", DateUtils.getDate());
			map.put("settleStatus", Constants.SETTLE_STATUS_Y);
			map.put("checkStatus", Constants.CHECK_STATUS_Y);
			map.put("settleBath", settleBath);
			super.getSqlSession().selectList(this.getNs()+".updateSettleChannelRecordStatus",map);
			
		}
		//通道结算的记录状态修改
		@Override
		public int updateByPrimaryKey(SettleChannelRecord record) {
			
			super.getSqlSession().update(this.getNs()+".updateChannelByPrimaryKey", record);
			return 0;
		}

		@Override
		public List<SettleChannelRecord> getSettleChannelRecordByStatus(String checkStatus, String settleStatus,String date) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkStatus", checkStatus);
			map.put("settleStatus", settleStatus);
			map.put("settleTime", date);
			return super.getSqlSession().selectList(this.getNs()+".getSettleChannelRecordByStatus",map);
		}

		@Override
		public List<SettleChannelRecord> getSettleChannelRecordByCode(String channelCode, String channelType,
				String date,String settleStatus,String settleBath) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelCode", channelCode);
			map.put("channelType", channelType);
			map.put("settleTime", date);
			map.put("settleStatus", settleStatus);
			map.put("settleBath", settleBath);
			return super.getSqlSession().selectList(this.getNs()+".getSettleChannelRecordByCode",map);
		}
}
