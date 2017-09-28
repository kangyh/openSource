/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.deposit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.deposit.entity.DepositWithdrawRecord;
import com.heepay.manage.modules.deposit.dao.DepositWithdrawRecordDao;

/**
 *
 * 描    述：查询Service
 *
 * 创 建 者： @author 王亚洪
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
public class DepositWithdrawRecordService extends CrudService<DepositWithdrawRecordDao, DepositWithdrawRecord> {

	public DepositWithdrawRecord get(String id) {
		return super.get(id);
	}
	
	public List<DepositWithdrawRecord> findList(DepositWithdrawRecord depositWithdrawRecord) {
		return super.findList(depositWithdrawRecord);
	}
	
	public Page<DepositWithdrawRecord> findPage(Page<DepositWithdrawRecord> page, DepositWithdrawRecord depositWithdrawRecord) {
		return super.findPage(page, depositWithdrawRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(DepositWithdrawRecord depositWithdrawRecord) {
		super.save(depositWithdrawRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(DepositWithdrawRecord depositWithdrawRecord) {
		super.delete(depositWithdrawRecord);
	}
	
}