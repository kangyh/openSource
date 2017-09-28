/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.CertificationRecordDao;
import com.heepay.manage.modules.payment.entity.CertificationRecord;

/**
 *
 * 描    述：实名认证Service
 *
 * 创 建 者： @author tyq
 * 创建时间：
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
public class CertificationRecordService extends CrudService<CertificationRecordDao, CertificationRecord> {

	@Autowired
	CertificationRecordDao certificationRecordDao;
	
	public CertificationRecord get(String id) {
		return super.get(id);
	}
	
	public List<CertificationRecord> findList(CertificationRecord certificationRecord) {
		return super.findList(certificationRecord);
	}
	
	public Page<CertificationRecord> findPage(Page<CertificationRecord> page, CertificationRecord certificationRecord) {
		return super.findPage(page, certificationRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(CertificationRecord certificationRecord) {
		super.save(certificationRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CertificationRecord certificationRecord) {
		super.delete(certificationRecord);
	}

	public CertificationRecord getCertificationId() {
		return certificationRecordDao.getCertificationId();
	}
	
}