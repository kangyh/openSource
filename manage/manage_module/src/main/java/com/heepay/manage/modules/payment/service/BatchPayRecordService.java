/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.enums.BatchPayStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.BatchPayRecordDao;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;

/**
 * 批量付款管理Service
 * @author zjx
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class BatchPayRecordService extends CrudService<BatchPayRecordDao, BatchPayRecord> {


	@Autowired
	private BatchPayRecordDao batchPayRecordDao;

	public BatchPayRecord get(String id) {
		return super.get(id);
	}
	
	public List<BatchPayRecord> findList(BatchPayRecord batchPayRecord) {
		return super.findList(batchPayRecord);
	}
	
	public Page<BatchPayRecord> findPage(Page<BatchPayRecord> page, BatchPayRecord batchPayRecord) {
		return super.findPage(page, batchPayRecord);
	}

	public Page<BatchPayRecord> getPreAuthList(Page<BatchPayRecord> page, BatchPayRecord batchPayRecord) {
		batchPayRecord.setPage(page);
		page.setList(batchPayRecordDao.getPreAuthList(batchPayRecord));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(BatchPayRecord batchPayRecord) {
		super.save(batchPayRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(BatchPayRecord batchPayRecord) {
		super.delete(batchPayRecord);
	}

	@Transactional(readOnly = false)
	public void auditPass(BatchPayRecord batchPayRecord) {
		batchPayRecord.setStatus(BatchPayStatus.PENDNG.getValue());
		batchPayRecordDao.audit(batchPayRecord);
	}
	
	@Transactional(readOnly = false)
	public List<BatchPayRecord> findAllList(){
		return batchPayRecordDao.findAllList();
	}

	/**
	 * 
	* @discription 更新转账批次状态
	* @author Administrator       
	* @created 2016年10月19日 下午5:25:16     
	* @param batchPayRecord
	 */
	@Transactional(readOnly = false)
	public void auditReject(BatchPayRecord batchPayRecord) {
		batchPayRecordDao.audit(batchPayRecord);
	}
}