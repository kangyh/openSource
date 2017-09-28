/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.TransferAccountReason;
import com.heepay.manage.modules.payment.dao.TransferAccountReasonDao;

/**
 *
 * 描    述：转账理由Service
 *
 * 创 建 者： @author zjx
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
public class TransferAccountReasonService extends CrudService<TransferAccountReasonDao, TransferAccountReason> {

	@Autowired
	private TransferAccountReasonDao transferAccountReasonDao;

	public TransferAccountReason get(String id) {
		return super.get(id);
	}

	public List<TransferAccountReason> findList(TransferAccountReason transferAccountReason) {
		return super.findList(transferAccountReason);
	}

	public Page<TransferAccountReason> findPage(Page<TransferAccountReason> page, TransferAccountReason transferAccountReason) {
		return super.findPage(page, transferAccountReason);
	}

	@Transactional(readOnly = false)
	public void save(TransferAccountReason transferAccountReason) {
		super.save(transferAccountReason,false);
	}

	@Transactional(readOnly = false)
	public void delete(TransferAccountReason transferAccountReason) {
		super.delete(transferAccountReason);
	}

	@Transactional(readOnly = false)
	public int batchInsert(List<TransferAccountReason> list){
		return transferAccountReasonDao.batchInsert(list);
	}


	@Transactional(readOnly = false)
	public int updateStatus(TransferAccountReason transferAccountReason){
		return transferAccountReasonDao.updateStatus(transferAccountReason);
	}


}