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
import com.heepay.manage.modules.payment.dao.NotifyRecordDao;
import com.heepay.manage.modules.payment.entity.NotifyRecord;

/**
 *
 * 描    述：异步通知Service
 *
 * 创 建 者： @author zc
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
public class NotifyRecordService extends CrudService<NotifyRecordDao, NotifyRecord> {
  @Autowired
  NotifyRecordDao notifyRecordDao;

	public NotifyRecord get(String id) {
		return super.get(id);
	}
	
	public List<NotifyRecord> findList(NotifyRecord notifyRecord) {
		return super.findList(notifyRecord);
	}
	
	public Page<NotifyRecord> findPage(Page<NotifyRecord> page, NotifyRecord notifyRecord) {
		return super.findPage(page, notifyRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(NotifyRecord notifyRecord) {
		super.save(notifyRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(NotifyRecord notifyRecord) {
		super.delete(notifyRecord);
	}
	@Transactional(readOnly = false)
	public int updateStatusById(NotifyRecord notifyRecord) {
	  return notifyRecordDao.updateStatusById(notifyRecord);
	}

	public NotifyRecord getNotifyId() {
		return notifyRecordDao.getNotifyId();
	}
}