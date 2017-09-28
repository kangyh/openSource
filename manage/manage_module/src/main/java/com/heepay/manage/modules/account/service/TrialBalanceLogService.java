/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.account.entity.TrialBalanceLog;
import com.heepay.manage.modules.account.dao.TrialBalanceLogDao;

/**
 *
 * 描    述：试算平衡Service
 *
 * 创 建 者： @author ycl
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
public class TrialBalanceLogService extends CrudService<TrialBalanceLogDao, TrialBalanceLog> {

	public TrialBalanceLog get(String id) {
		return super.get(id);
	}
	
	public List<TrialBalanceLog> findList(TrialBalanceLog trialBalanceLog) {
		return super.findList(trialBalanceLog);
	}
	
	public Page<TrialBalanceLog> findPage(Page<TrialBalanceLog> page, TrialBalanceLog trialBalanceLog) {
		return super.findPage(page, trialBalanceLog);
	}
	
	@Transactional(readOnly = false)
	public void save(TrialBalanceLog trialBalanceLog) {
		super.save(trialBalanceLog,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrialBalanceLog trialBalanceLog) {
		super.delete(trialBalanceLog);
	}
	
}