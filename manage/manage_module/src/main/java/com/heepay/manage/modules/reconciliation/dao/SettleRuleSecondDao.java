package com.heepay.manage.modules.reconciliation.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleSecond;
import com.heepay.manage.modules.reconciliation.entity.SettleWarnRecord;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年5月9日 下午1:09:15
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
public interface SettleRuleSecondDao extends CrudDao<SettleRuleSecond> {

	SettleRuleSecond getEntity(int ruleControlId);

	void saveEntity(SettleRuleSecond settleRuleSecond);

	int addEntity(SettleRuleSecond settleRuleSecond);

	/**
	 * @方法说明：删除操作
	 * @时间：2017年1月19日上午11:45:12
	 * @创建人：wangl
	 */
	int deleteEntity(Long ruleControlId);
	SettleRuleSecond getEntityByConditon(SettleRuleSecond settleRuleSecond);

	
}

 