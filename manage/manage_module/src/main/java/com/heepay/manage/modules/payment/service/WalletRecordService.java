/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.WalletRecordDao;
import com.heepay.manage.modules.payment.entity.WalletRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述： 钱包Service
 *
 * 创 建 者： liudh
 * 创建时间：	 2017/7/6 14:00
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
public class WalletRecordService extends CrudService<WalletRecordDao, WalletRecord> {

	@Autowired
	private WalletRecordDao walletRecordDao;

	public WalletRecord get(String id) {
		return super.get(id);
	}
	
	public List<WalletRecord> findList(WalletRecord walletRecord) {
		return super.findList(walletRecord);
	}

	public List<WalletRecord> findDifBillList(WalletRecord walletRecord) {
		return walletRecordDao.findDifBillList(walletRecord);
	}

	public Page<WalletRecord> findPage(Page<WalletRecord> page, WalletRecord walletRecord) {
		return super.findPage(page, walletRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(WalletRecord walletRecord) {
		super.save(walletRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(WalletRecord walletRecord) {
		super.delete(walletRecord);
	}

	@Transactional(readOnly = false)
	public void updateStatusByTransNo(WalletRecord walletRecord) {
		walletRecordDao.updateStatusByTransNo(walletRecord);
	}

}