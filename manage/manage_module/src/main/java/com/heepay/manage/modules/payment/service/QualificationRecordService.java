/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.QualificationRecordDao;
import com.heepay.manage.modules.payment.entity.QualificationRecord;

/**
 * 
* 
* 描    述：打款认证管理
*
* 创 建 者： yanxb  
* 创建时间： 2016年10月28日 下午1:52:05 
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
public class QualificationRecordService extends CrudService<QualificationRecordDao, QualificationRecord> {

	@Autowired
	QualificationRecordDao qualificationRecordDao;
	
	public Page<QualificationRecord> findPage(Page<QualificationRecord> page, QualificationRecord qualificationRecord) {
		return super.findPage(page, qualificationRecord);
	}

	public QualificationRecord getQualifyId() {
		return qualificationRecordDao.getQualifyId();
	}

}