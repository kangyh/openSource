/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.allocate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.allocate.entity.AllocateRecord;
import com.heepay.manage.modules.allocate.dao.AllocateRecordDao;

/**
 *
 * 描    述：备付金调拨Service
 *
 * 创 建 者： @author 王亚洪
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
public class AllocateRecordService extends CrudService<AllocateRecordDao, AllocateRecord> {

  @Autowired
  private AllocateRecordDao allocateRecordDao;
  
	public AllocateRecord get(String id) {
		return super.get(id);
	}
	
	public List<AllocateRecord> findList(AllocateRecord allocateRecord) {
		return super.findList(allocateRecord);
	}
	
	public Page<AllocateRecord> findPage(Page<AllocateRecord> page, AllocateRecord allocateRecord) {
		return super.findPage(page, allocateRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AllocateRecord allocateRecord) {
		super.save(allocateRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AllocateRecord allocateRecord) {
		super.delete(allocateRecord);
	}

  @Transactional(readOnly = false)
  public void updateAllocateRecord(AllocateRecord allocateRecord) {
    allocateRecordDao.updateAllocateRecord(allocateRecord);
  }
  
}