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
import com.heepay.manage.modules.payment.dao.BatchPayRecordDetailDao;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;

/**
 * 转账明细Service
 * @author zjx
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class BatchPayRecordDetailService extends CrudService<BatchPayRecordDetailDao, BatchPayRecordDetail> {

	@Autowired
	private BatchPayRecordDetailDao batchPayRecordDetailDao;

	public BatchPayRecordDetail get(String id) {
		return super.get(id);
	}
	
	public List<BatchPayRecordDetail> findList(BatchPayRecordDetail batchPayRecordDetail) {
		return super.findList(batchPayRecordDetail);
	}
	
	public Page<BatchPayRecordDetail> findPage(Page<BatchPayRecordDetail> page, BatchPayRecordDetail batchPayRecordDetail) {
		return super.findPage(page, batchPayRecordDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(BatchPayRecordDetail batchPayRecordDetail) {
		super.save(batchPayRecordDetail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(BatchPayRecordDetail batchPayRecordDetail) {
		super.delete(batchPayRecordDetail);
	}

	@Transactional(readOnly = false)
	public int updateAuditState(BatchPayRecordDetail detail) {
		return batchPayRecordDetailDao.updateAuditState(detail);
	}

	@Transactional(readOnly = false)
	public List<BatchPayRecordDetail> findListByBatchPayId(List<String> list){
		return batchPayRecordDetailDao.findListByBatchPayId(list);
	}

	@Transactional(readOnly = false)
	public List<BatchPayRecordDetail> findAllList(){
		return batchPayRecordDetailDao.findAllList();
	}
	
	public List<BatchPayRecordDetail> getDetailsByBatchId(List<String> list) {
		return batchPayRecordDetailDao.getDetailsByBatchId(list);
	}

	public BatchPayRecordDetail getBtachPayId() {
		return batchPayRecordDetailDao.getBtachPayId();
	}

	public Page<BatchPayRecordDetail> findPageByStatus(Page<BatchPayRecordDetail> page, BatchPayRecordDetail batchPayRecordDetail) {
		return super.findPageByStatus(page, batchPayRecordDetail);
	}

	public List<BatchPayRecordDetail> findListByStatus(BatchPayRecordDetail batchPayRecordDetail) {
		return super.findListByStatus(batchPayRecordDetail);
	}
	@Transactional(readOnly = false)
  public int updateBatchPayRecordPROCES(String id) {
    return batchPayRecordDetailDao.updateBatchPayRecordPROCES(id);
  }
  
	
}