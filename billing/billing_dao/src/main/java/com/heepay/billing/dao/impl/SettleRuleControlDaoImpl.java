package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleRuleControlMapper;
import com.heepay.billing.entity.SettleRuleControl;
import com.heepay.billing.entity.SettleRuleControlExample;
import com.heepay.enums.billing.BillingBecomeEffect;

/**
 * 
 * 
 * 描    述：规则表达式实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月23日下午5:30:02 
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
public class SettleRuleControlDaoImpl extends BaseDaoImpl<SettleRuleControl, SettleRuleControlExample> implements SettleRuleControlMapper {

	//默认构造方法设置命名空间
		public SettleRuleControlDaoImpl() {
			super.setNs("com.heepay.billing.dao.SettleRuleControlMapper");
		}

		/**
		 * 
		 * @方法说明： 获取账单明细的规则表达式
		 * @author chenyanming
		 * @param channelCode
		 * @param channelType
		 * @return
		 * @时间：2016年9月23日下午6:12:40
		 */
		@Override
		public SettleRuleControl getSettleRuleControl(String channelCode, String channelType, String billType, String settleWay) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelCode", channelCode);
			map.put("channelType", channelType);
			map.put("settleWay", settleWay);
			map.put("status", BillingBecomeEffect.BBEY.getValue());
			map.put("billType", billType);
			return super.getSqlSession().selectOne(this.getNs()+".getSettleRuleControl",map);
		}

		
	
}









