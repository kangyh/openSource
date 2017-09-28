/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.account.dao.MerchantTransferDao;
import com.heepay.manage.modules.account.entity.MerchantTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：账户转账Service
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
public class MerchantTransferService extends CrudService<MerchantTransferDao, MerchantTransfer> {

	@Autowired
	private MerchantTransferDao merchantTransferDao;

	public MerchantTransfer get(String id) {
		return super.get(id);
	}
	
	public List<MerchantTransfer> findList(MerchantTransfer merchantTransfer) {
		return super.findList(merchantTransfer);
	}
	
	public Page<MerchantTransfer> findPage(Page<MerchantTransfer> page, MerchantTransfer merchantTransfer) {
		return super.findPage(page, merchantTransfer);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantTransfer merchantTransfer) {
		super.save(merchantTransfer,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantTransfer merchantTransfer) {
		super.delete(merchantTransfer);
	}

	@Transactional(readOnly = false)
	public void updateStatus(MerchantTransfer merchantTransfer) {
		merchantTransferDao.updateStatus(merchantTransfer);
	}
}