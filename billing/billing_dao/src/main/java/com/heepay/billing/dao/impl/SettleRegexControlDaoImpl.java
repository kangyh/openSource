package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleRegexControlMapper;
import com.heepay.billing.entity.SettleRegexControl;
import com.heepay.billing.entity.SettleRegexControlExample;

/**
 * 
 * 
 * 描    述：正则表达式dao实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年1月17日下午6:40:38 
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
public class SettleRegexControlDaoImpl extends BaseDaoImpl<SettleRegexControl, SettleRegexControlExample> implements SettleRegexControlMapper {

	//默认构造方法设置命名空间
	public SettleRegexControlDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleRegexControlMapper");
	}

	/**
	 * 
	 * @方法说明： 根据ruleId和ruleKey获取正则表达式
	 * @author chenyanming
	 * @param paymentId
	 * @param ruleControlId
	 * @return
	 * @时间：2017年1月17日下午6:49:12
	 */
	@Override
	public List<SettleRegexControl> queryRegex(Byte ruleKey, Long ruleId, String ruleType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ruleKey", ruleKey);
		map.put("ruleId", ruleId);
		map.put("ruleType", ruleType);
		return super.getSqlSession().selectList(this.getNs()+".queryRegex",map);
	}

}
