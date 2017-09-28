package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleRuleSecondMapper;
import com.heepay.billing.entity.SettleRuleSecond;
import com.heepay.billing.entity.SettleRuleSecondExample;
import com.heepay.enums.billing.BillingBecomeEffect;

/**
 * 
 * 
 * 描    述：二代规则dao实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年5月9日上午11:36:46 
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
public class SettleRuleSecondDaoImpl extends BaseDaoImpl<SettleRuleSecond, SettleRuleSecondExample> implements SettleRuleSecondMapper {
	//默认构造方法设置命名空间
	public SettleRuleSecondDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleRuleSecondMapper");
	}

	/**
	 * 
	 * @方法说明： 获取指定的二代规则表达式
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @param billType
	 * @param settleWay
	 * @return
	 * @时间：2017年5月9日下午1:55:44
	 */
	@Override
	public SettleRuleSecond getSetlleRuleSecond(String channelCode, String channelType, String billType,
			String settleWay) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("settleWay", settleWay);
		map.put("status", BillingBecomeEffect.BBEY.getValue());
		map.put("billType", billType);
		return super.getSqlSession().selectOne(this.getNs()+".getSettleRuleSecond",map);
		
	}
	
	
}
