/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.BatchCollectionRecord;

/**
 * 委托收款管理DAO接口
 * @author ld
 * @version V1.0
 */
@MyBatisDao
public interface BatchCollectionRecordDao extends CrudDao<BatchCollectionRecord> {
	
	public void audit(BatchCollectionRecord  batchCollectionRecord);
	
}