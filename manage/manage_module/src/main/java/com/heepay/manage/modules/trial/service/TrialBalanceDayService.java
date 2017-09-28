/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.trial.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.trial.entity.TrialBalanceDay;
import com.heepay.manage.modules.trial.dao.TrialBalanceDayDao;

/**
 *
 * 描    述：试算平衡-汇总Service
 *
 * 创 建 者： @author 杨春龙
 * 创建时间：
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
@Service
@Transactional(readOnly = true)
public class TrialBalanceDayService extends CrudService<TrialBalanceDayDao, TrialBalanceDay> {

	public TrialBalanceDay get(String id) {
		return super.get(id);
	}
	
	public List<TrialBalanceDay> findList(TrialBalanceDay trialBalanceDay) {
		return super.findList(trialBalanceDay);
	}
	
	public Page<TrialBalanceDay> findPage(Page<TrialBalanceDay> page, TrialBalanceDay trialBalanceDay) {
		return super.findPage(page, trialBalanceDay);
	}
	
	@Transactional(readOnly = false)
	public void save(TrialBalanceDay trialBalanceDay) {
		super.save(trialBalanceDay,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrialBalanceDay trialBalanceDay) {
		super.delete(trialBalanceDay);
	}
	
}