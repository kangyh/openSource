/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.InnerTransferRecordDao;
import com.heepay.manage.modules.payment.dao.MerchantLogDetailDao;
import com.heepay.manage.modules.payment.entity.MerchantLogDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：paymentService
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
public class MerchantLogDetailService extends CrudService<MerchantLogDetailDao, MerchantLogDetail> {

	@Autowired
	private MerchantLogDetailDao merchantLogDetailDao;

	public MerchantLogDetail get(String id) {
		return super.get(id);
	}
	
	public List<MerchantLogDetail> findList(MerchantLogDetail merchantLogDetail) {
		return super.findList(merchantLogDetail);
	}
	
	public Page<MerchantLogDetail> findPage(Page<MerchantLogDetail> page, MerchantLogDetail merchantLogDetail) {
		return super.findPage(page, merchantLogDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantLogDetail merchantLogDetail) {
		super.save(merchantLogDetail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantLogDetail merchantLogDetail) {
		super.delete(merchantLogDetail);
	}

	@Transactional(readOnly = false)
	public int batchInsertMerchantLogDetails(List<MerchantLogDetail> list) {
		return merchantLogDetailDao.batchInsertMerchantLogDetails(list);
	}
	@Transactional(readOnly = false)
	public int insert(MerchantLogDetail merchantLogDetail) {
		return merchantLogDetailDao.insert(merchantLogDetail);
	}

	@Transactional(readOnly = false)
	public int deleteBySettleId(MerchantLogDetail merchantLogDetail) {
		return merchantLogDetailDao.deleteBySettleId(merchantLogDetail);
	}
}