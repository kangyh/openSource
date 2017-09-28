/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.ElectronicsSealRecordDao;
import com.heepay.manage.modules.payment.entity.ElectronicsSealRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：电子签章订单Service
 *
 * 创 建 者： @author zjx
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
public class ElectronicsSealRecordService extends CrudService<ElectronicsSealRecordDao, ElectronicsSealRecord> {

	public ElectronicsSealRecord get(String id) {
		return super.get(id);
	}
	
	public List<ElectronicsSealRecord> findList(ElectronicsSealRecord electronicsSealRecord) {
		return super.findList(electronicsSealRecord);
	}
	
	public Page<ElectronicsSealRecord> findPage(Page<ElectronicsSealRecord> page, ElectronicsSealRecord electronicsSealRecord) {
		return super.findPage(page, electronicsSealRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ElectronicsSealRecord electronicsSealRecord) {
		super.save(electronicsSealRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ElectronicsSealRecord electronicsSealRecord) {
		super.delete(electronicsSealRecord);
	}
	
}