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
import com.heepay.manage.modules.trial.entity.TrialBalancePaymentDifferent;
import com.heepay.manage.modules.trial.dao.TrialBalancePaymentDifferentDao;

/**
 *
 * 描    述：交易和账务数据日终校验Service
 *
 * 创 建 者： @author yangcl
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
public class TrialBalancePaymentDifferentService extends CrudService<TrialBalancePaymentDifferentDao, TrialBalancePaymentDifferent> {

	public TrialBalancePaymentDifferent get(String id) {
		return super.get(id);
	}
	
	public List<TrialBalancePaymentDifferent> findList(TrialBalancePaymentDifferent trialBalancePaymentDifferent) {
		return super.findList(trialBalancePaymentDifferent);
	}
	
	public Page<TrialBalancePaymentDifferent> findPage(Page<TrialBalancePaymentDifferent> page, TrialBalancePaymentDifferent trialBalancePaymentDifferent) {
		return super.findPage(page, trialBalancePaymentDifferent);
	}
	
	@Transactional(readOnly = false)
	public void save(TrialBalancePaymentDifferent trialBalancePaymentDifferent) {
		super.save(trialBalancePaymentDifferent,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrialBalancePaymentDifferent trialBalancePaymentDifferent) {
		super.delete(trialBalancePaymentDifferent);
	}
	
}