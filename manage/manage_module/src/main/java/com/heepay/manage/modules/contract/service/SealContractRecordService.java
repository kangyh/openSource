/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.contract.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.contract.dao.SealContractRecordDao;
import com.heepay.manage.modules.contract.entity.SealContractRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：已签约合同Service
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
public class SealContractRecordService extends CrudService<SealContractRecordDao, SealContractRecord> {

	public SealContractRecord get(String id) {
		return super.get(id);
	}
	
	public List<SealContractRecord> findList(SealContractRecord sealContractRecord) {
		return super.findList(sealContractRecord);
	}
	
	public Page<SealContractRecord> findPage(Page<SealContractRecord> page, SealContractRecord sealContractRecord) {
		return super.findPage(page, sealContractRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(SealContractRecord sealContractRecord) {
		super.save(sealContractRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(SealContractRecord sealContractRecord) {
		super.delete(sealContractRecord);
	}
	
}