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
import com.heepay.manage.modules.payment.dao.RefundRecordDao;
import com.heepay.manage.modules.payment.entity.RefundRecord;

/**
 *
 * 描    述：退款Service
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
public class RefundRecordService extends CrudService<RefundRecordDao, RefundRecord> {

	@Autowired
	RefundRecordDao refundRecordDao;
	
	public RefundRecord get(String id) {
		return super.get(id);
	}
	
	public List<RefundRecord> findList(RefundRecord refundRecord) {
		return super.findList(refundRecord);
	}
	
	public Page<RefundRecord> findPage(Page<RefundRecord> page, RefundRecord refundRecord) {
		return super.findPage(page, refundRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(RefundRecord refundRecord) {
		super.save(refundRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RefundRecord refundRecord) {
		super.delete(refundRecord);
	}

	public RefundRecord getRefundId() {
		return refundRecordDao.getRefundId();
	}
	 @Transactional(readOnly = false)
	  public int updateRefundRecordRFDING(String id) {
	    return refundRecordDao.updateRefundRecordRFDING(id);
	  }
}