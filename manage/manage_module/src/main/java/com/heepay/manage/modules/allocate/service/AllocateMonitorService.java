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
import com.heepay.manage.modules.allocate.entity.AllocateMonitor;
import com.heepay.manage.modules.allocate.dao.AllocateMonitorDao;

/**
 *
 * 描    述：调拨监听Service
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
public class AllocateMonitorService extends CrudService<AllocateMonitorDao, AllocateMonitor> {

  @Autowired
  private AllocateMonitorDao allocateMonitorDao;
  
	public AllocateMonitor get(String id) {
		return super.get(id);
	}
	
	public List<AllocateMonitor> findList(AllocateMonitor allocateMonitor) {
		return super.findList(allocateMonitor);
	}
	
	public Page<AllocateMonitor> findPage(Page<AllocateMonitor> page, AllocateMonitor allocateMonitor) {
		return super.findPage(page, allocateMonitor);
	}
	
	@Transactional(readOnly = false)
	public void save(AllocateMonitor allocateMonitor) {
		super.save(allocateMonitor,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AllocateMonitor allocateMonitor) {
		super.delete(allocateMonitor);
	}
	
	@Transactional(readOnly = false)
  public int updateAllocateMonitor(AllocateMonitor allocateMonitor){
    return allocateMonitorDao.updateAllocateMonitor(allocateMonitor);
  }
	
}