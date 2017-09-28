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
import com.heepay.manage.modules.payment.dao.WithdrawRecordDao;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;

/**
 * 提现管理Service
 * @author hs
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class WithdrawRecordService extends CrudService<WithdrawRecordDao, WithdrawRecord> {

	@Autowired
	 WithdrawRecordDao withdrawRecordDao;

	public WithdrawRecord get(String id) {
		return super.get(id);
	}
	
	public List<WithdrawRecord> findList(WithdrawRecord withdrawRecord) {
		return super.findList(withdrawRecord);
	}
	
	public Page<WithdrawRecord> findPage(Page<WithdrawRecord> page, WithdrawRecord withdrawRecord) {
		return super.findPage(page, withdrawRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(WithdrawRecord withdrawRecord) {
		super.save(withdrawRecord, false);
	}
	
	@Transactional(readOnly = false)
	public void delete(WithdrawRecord withdrawRecord) {
		super.delete(withdrawRecord);
	}
	

	@Transactional(readOnly = false)
	public void auditCtrl(WithdrawRecord withdrawRecord){
		withdrawRecordDao.auditCtrl(withdrawRecord);
	}

	public List<WithdrawRecord> findAllList(WithdrawRecord withdrawRecord) {
		return withdrawRecordDao.findAllList(withdrawRecord);
	}

	public WithdrawRecord getWithdrawId() {
		return withdrawRecordDao.getWithdrawId();
	}
	
	@Transactional(readOnly = false)
  public int updateWithdrawDRAING(String id) {
    return withdrawRecordDao.updateWithdrawDRAING(id);
  }
	
}