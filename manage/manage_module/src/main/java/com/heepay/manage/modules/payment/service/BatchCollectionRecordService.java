/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.enums.CollectionRecordStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.BatchCollectionRecordDao;
import com.heepay.manage.modules.payment.entity.BatchCollectionRecord;

/**
 * 委托收款管理Service
 * @author ld
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class BatchCollectionRecordService extends CrudService<BatchCollectionRecordDao, BatchCollectionRecord> {
	
	@Autowired
	BatchCollectionRecordDao batchCollectionRecordDao;

	public BatchCollectionRecord get(String id) {
		return super.get(id);
	}
	
	public List<BatchCollectionRecord> findList(BatchCollectionRecord batchCollectionRecord) {
		return super.findList(batchCollectionRecord);
	}
	
	public Page<BatchCollectionRecord> findPage(Page<BatchCollectionRecord> page, BatchCollectionRecord batchCollectionRecord) {
		return super.findPage(page, batchCollectionRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(BatchCollectionRecord batchCollectionRecord) {
		super.save(batchCollectionRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(BatchCollectionRecord batchCollectionRecord) {
		super.delete(batchCollectionRecord);
	}
	
	@Transactional(readOnly = false)
	public void auditPass(BatchCollectionRecord batchCollectionRecord) {
		batchCollectionRecord.setStatus(CollectionRecordStatus.FINISH.getValue());
		batchCollectionRecordDao.audit(batchCollectionRecord);
	}
	
	@Transactional(readOnly = false)
	public void auditReject(BatchCollectionRecord batchCollectionRecord) {
//		batchCollectionRecord.setStatus(CollectionRecordStatus.AUDREJ.getValue());
		batchCollectionRecordDao.audit(batchCollectionRecord);
	}
	
}