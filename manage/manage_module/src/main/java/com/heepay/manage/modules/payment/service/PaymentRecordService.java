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
import com.heepay.manage.modules.payment.dao.PaymentRecordDao;
import com.heepay.manage.modules.payment.entity.PaymentRecord;

/**
 *
 * 描    述：交易管理Service
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
public class PaymentRecordService extends CrudService<PaymentRecordDao, PaymentRecord> {

	@Autowired
	PaymentRecordDao paymentdao ;
	
	public PaymentRecord get(String id) {
		return super.get(id);
	}
	public PaymentRecord getOneByGatewayId(String transNO) {
		List<PaymentRecord> payments = paymentdao.getOneByGatewayId(transNO);
		if(payments==null||payments.isEmpty()){
			return null;
		}
		return payments.get(0);

	}
	
	public PaymentRecord getFailedByGateWayId(String transNo){
		List<PaymentRecord> payments = paymentdao.getFailedByGateWayId(transNo);
		if(payments==null||payments.isEmpty()){
			return null;
		}
		return payments.get(0);
	}
	
	public PaymentRecord getOne(String transNO){
		return paymentdao.getOne(transNO);
	}
	public PaymentRecord getOneByChargeId(String merchantOrderNo) {
		List<PaymentRecord> records = paymentdao.getOneByChargeId(merchantOrderNo);
		if(records==null||records.isEmpty()){
			return null;
		}
		return records.get(0);
	}
	
	public List<PaymentRecord> findList(PaymentRecord paymentRecord) {
		return super.findList(paymentRecord);
	}

	public List<PaymentRecord> getPaymentsByTransNo(String transNo) {
		return paymentdao.getPaymentsByTransNo(transNo);
	}

	public List<PaymentRecord> findListByTransNo(String qtrans_no) {
    return paymentdao.findListByTransNo(qtrans_no);
  }
  
	
	public Page<PaymentRecord> findPage(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
		return super.findPage(page, paymentRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(PaymentRecord paymentRecord) {
		super.save(paymentRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(PaymentRecord paymentRecord) {
		super.delete(paymentRecord);
	}
	
	@Transactional(readOnly = false)
	public int updatePaymentRecordFail(String paymentId) {
		return paymentdao.updatePaymentRecordFail(paymentId);
	}
	
	public PaymentRecord getPaymentId() {
		return paymentdao.getPaymentId();
	}
	public Page<PaymentRecord> findUserWithdrawPage(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
		return super.findUserWithdrawPage(page,paymentRecord);
	}
	
	@Transactional(readOnly = false)
  public int updatePaymentRecordPaying(String paymentId) {
    return paymentdao.updatePaymentRecordPaying(paymentId);
  }
	public Page<PaymentRecord> findWithdrawDepositPage(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
		paymentRecord.setPage(page);
		page.setList(paymentdao.findWithdrawDepositPage(paymentRecord));
		return page;
	}
	public Page<PaymentRecord> findAggrPayPage(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
		paymentRecord.setPage(page);
		page.setList(paymentdao.findAggrPayPage(paymentRecord));
		return page;
	}
	public Page<PaymentRecord> findQuickPage(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
		paymentRecord.setPage(page);
		page.setList(paymentdao.findQuickPage(paymentRecord));
		return page;
	}
	public List<PaymentRecord> findAggrPayList(List<String> transNo) {
		return paymentdao.findAggrPayList(transNo);
	}

	public List<PaymentRecord> findListByPayment(PaymentRecord paymentRecord) {
		return paymentdao.findListByPayment(paymentRecord);
	}
}