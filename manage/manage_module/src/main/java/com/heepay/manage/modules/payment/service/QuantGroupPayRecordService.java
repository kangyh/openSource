/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.QuantGroupPayRecordDao;
import com.heepay.manage.modules.payment.entity.QuantGroupPayRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：量化派白条支付记录Service
 *
 * 创 建 者： @author TangWei
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
public class QuantGroupPayRecordService extends CrudService<QuantGroupPayRecordDao, QuantGroupPayRecord> {

	public QuantGroupPayRecord get(String id) {
		return super.get(id);
	}
	
	public List<QuantGroupPayRecord> findList(QuantGroupPayRecord quantGroupPayRecord) {
		return super.findList(quantGroupPayRecord);
	}
	
	public Page<QuantGroupPayRecord> findPage(Page<QuantGroupPayRecord> page, QuantGroupPayRecord quantGroupPayRecord) {
		return super.findPage(page, quantGroupPayRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(QuantGroupPayRecord quantGroupPayRecord) {
		super.save(quantGroupPayRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(QuantGroupPayRecord quantGroupPayRecord) {
		super.delete(quantGroupPayRecord);
	}
	
}