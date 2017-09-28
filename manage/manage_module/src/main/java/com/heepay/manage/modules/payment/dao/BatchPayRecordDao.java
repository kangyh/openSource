/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;

/**
 * 批量付款管理DAO接口
 * @author zjx
 * @version V1.0
 */
@MyBatisDao
public interface BatchPayRecordDao extends CrudDao<BatchPayRecord> {
    void audit(BatchPayRecord  batchPayRecord);
    List<BatchPayRecord> getPreAuthList(BatchPayRecord batchPayRecord);
}