package com.heepay.manage.modules.reconciliation.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;

/***
 * 
 * 
 * 描    述：对账参数的dao
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午3:29:16
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
@MyBatisDao
public interface SettleRuleControlDao extends CrudDao<SettleRuleControl> {

	SettleRuleControl getEntity(int ruleControlId);

	void updateEntity(SettleRuleControl settleRuleControl);

	int saveEntity(SettleRuleControl settleRuleControl);

	/**
	 * @方法说明：删除操作
	 * @时间：2017年1月19日上午11:45:12
	 * @创建人：wangl
	 */
	int deleteEntity(Long ruleControlId);

	/**
	 * @方法说明：查看是否重复
	 * @时间： 2017-05-12 03:58 PM
	 * @创建人：wangl
	 */
     int repeat(SettleRuleControl settleRuleControl);
}
