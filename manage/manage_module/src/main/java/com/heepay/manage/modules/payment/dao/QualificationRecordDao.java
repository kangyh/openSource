/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.QualificationRecord;

/**
 * 批量付款管理DAO接口
 * @author zjx
 * @version V1.0
 */
@MyBatisDao
public interface QualificationRecordDao extends CrudDao<QualificationRecord> {

	QualificationRecord getQualifyId();
    
}