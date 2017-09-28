package com.heepay.billing.dao.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleWarnRecordMapper;
import com.heepay.billing.entity.SettleWarnRecord;
import com.heepay.billing.entity.SettleWarnRecordExample;
import com.heepay.enums.billing.BillingWaringStatus;

/**
 * *
 * 
* 
* 描    述：  告警记录service实现类
*
* 创 建 者： wangjie
* 创建时间：  2016年9月29日下午9:38:26
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
@Repository
public class SettleWarnRecordDaoImpl extends BaseDaoImpl<SettleWarnRecord, SettleWarnRecordExample> implements SettleWarnRecordMapper {

	//默认构造方法设置命名空间
		public SettleWarnRecordDaoImpl() {
			super.setNs("com.heepay.billing.dao.SettleWarnRecordMapper");
		}

		
		@Override
		public int insert(SettleWarnRecord record) {
			return super.getSqlSession().insert(this.getNs() + ".insert", record);
		}

		
		/**
		 * 根据warnContext更新告警记录表
		 */
		public void updateSettleWarnRecordByWarnContext(String warnContext){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", BillingWaringStatus.STATUSY.getValue());
			map.put("oper_time", new Date());
			map.put("warn_context", warnContext);
			super.getSqlSession().update(this.getNs() + ".updateSettleWarnRecordByWarnContext", map);
		}

	
}









