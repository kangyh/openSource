/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.PreUnionpayRecord;
import com.heepay.manage.modules.payment.dao.PreUnionpayRecordDao;

/**
 *
 * 描    述：银联扫码预下单Service
 *
 * 创 建 者： @author ld
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
public class PreUnionpayRecordService extends CrudService<PreUnionpayRecordDao, PreUnionpayRecord> {

	public PreUnionpayRecord get(String id) {
		return super.get(id);
	}
	
	public List<PreUnionpayRecord> findList(PreUnionpayRecord preUnionpayRecord) {
		return super.findList(preUnionpayRecord);
	}
	
	public Page<PreUnionpayRecord> findPage(Page<PreUnionpayRecord> page, PreUnionpayRecord preUnionpayRecord) {
		return super.findPage(page, preUnionpayRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(PreUnionpayRecord preUnionpayRecord) {
		super.save(preUnionpayRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(PreUnionpayRecord preUnionpayRecord) {
		super.delete(preUnionpayRecord);
	}
	
}