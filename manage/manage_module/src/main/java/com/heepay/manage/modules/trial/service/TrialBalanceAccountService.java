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
import com.heepay.manage.modules.trial.entity.TrialBalanceAccount;
import com.heepay.manage.modules.trial.dao.TrialBalanceAccountDao;

/**
 *
 * 描    述：试算平衡-账户维度Service
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
public class TrialBalanceAccountService extends CrudService<TrialBalanceAccountDao, TrialBalanceAccount> {

	public TrialBalanceAccount get(String id) {
		return super.get(id);
	}
	
	public List<TrialBalanceAccount> findList(TrialBalanceAccount trialBalanceAccount) {
		return super.findList(trialBalanceAccount);
	}
	
	public Page<TrialBalanceAccount> findPage(Page<TrialBalanceAccount> page, TrialBalanceAccount trialBalanceAccount) {
		return super.findPage(page, trialBalanceAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(TrialBalanceAccount trialBalanceAccount) {
		super.save(trialBalanceAccount,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrialBalanceAccount trialBalanceAccount) {
		super.delete(trialBalanceAccount);
	}
	
}