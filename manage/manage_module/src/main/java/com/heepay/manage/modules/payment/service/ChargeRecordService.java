/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.dao.ChargeRecordDao;

/**
 * 充值管理Service
 * @author ld
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class ChargeRecordService extends CrudService<ChargeRecordDao, ChargeRecord> {

	@Autowired
	ChargeRecordDao chargeRecordDao;
	
	public ChargeRecord get(String id) {
		return super.get(id);
	}
	
	public List<ChargeRecord> findList(ChargeRecord chargeRecord) {
		return super.findList(chargeRecord);
	}
	
	public Page<ChargeRecord> findPage(Page<ChargeRecord> page, ChargeRecord chargeRecord) {
		return super.findPage(page, chargeRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ChargeRecord chargeRecord) {
		super.save(chargeRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ChargeRecord chargeRecord) {
		super.delete(chargeRecord);
	}

	public ChargeRecord getChargeId() {
		return chargeRecordDao.getChargeId();
	}
	@Transactional(readOnly = false)
	public int updatechargeRecordCAGING(String id) {
    return chargeRecordDao.updatechargeRecordCAGING(id);
  }
}