/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;

/**
 * 转账明细DAO接口
 * @author zjx
 * @version V1.0
 */
@MyBatisDao
public interface BatchPayRecordDetailDao extends CrudDao<BatchPayRecordDetail> {

    int updateAuditState(BatchPayRecordDetail detail);
    List<BatchPayRecordDetail> findListByBatchPayId(List<String> list);
    List<BatchPayRecordDetail> getDetailsByBatchId(List<String> list);
	BatchPayRecordDetail getBtachPayId();
	
	 int  updateBatchPayRecordPROCES(String id);

}