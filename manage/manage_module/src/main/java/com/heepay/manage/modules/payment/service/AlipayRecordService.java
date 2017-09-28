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
import com.heepay.manage.modules.payment.entity.AlipayRecord;
import com.heepay.manage.modules.payment.dao.AlipayRecordDao;

/**
 *
 * 描    述：支付宝扫码支付Service
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
public class AlipayRecordService extends CrudService<AlipayRecordDao, AlipayRecord> {

	public AlipayRecord get(String id) {
		return super.get(id);
	}
	
	public List<AlipayRecord> findList(AlipayRecord alipayRecord) {
		return super.findList(alipayRecord);
	}
	
	public Page<AlipayRecord> findPage(Page<AlipayRecord> page, AlipayRecord alipayRecord) {
		return super.findPage(page, alipayRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AlipayRecord alipayRecord) {
		super.save(alipayRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AlipayRecord alipayRecord) {
		super.delete(alipayRecord);
	}
	
}