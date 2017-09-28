/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.InnerTransferRecord;
import com.heepay.manage.modules.payment.dao.InnerTransferRecordDao;

/**
 *
 * 描    述：会聚财转账查询Service
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
public class InnerTransferRecordService extends CrudService<InnerTransferRecordDao, InnerTransferRecord> {

	@Autowired
	private InnerTransferRecordDao innerTransferRecordDao;

	public InnerTransferRecord get(String id) {
		return super.get(id);
	}
	
	public List<InnerTransferRecord> findList(InnerTransferRecord innerTransferRecord) {
		return super.findList(innerTransferRecord);
	}
	
	public Page<InnerTransferRecord> findPage(Page<InnerTransferRecord> page, InnerTransferRecord innerTransferRecord) {
		return super.findPage(page, innerTransferRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(InnerTransferRecord innerTransferRecord) {
		super.save(innerTransferRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(InnerTransferRecord innerTransferRecord) {
		super.delete(innerTransferRecord);
	}

	public List<InnerTransferRecord> findTransferList(List<String> transNo) {
		return innerTransferRecordDao.findTransferList(transNo);
	}

}