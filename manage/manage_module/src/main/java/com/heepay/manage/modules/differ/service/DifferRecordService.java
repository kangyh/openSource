/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.differ.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differ.entity.DifferRecord;
import com.heepay.manage.modules.differ.dao.DifferRecordDao;

/**
 *
 * 描    述：差异处理平台Service
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
public class DifferRecordService extends CrudService<DifferRecordDao, DifferRecord> {

	public DifferRecord get(String id) {
		return super.get(id);
	}
	
	public List<DifferRecord> findList(DifferRecord differRecord) {
		return super.findList(differRecord);
	}
	
	public Page<DifferRecord> findPage(Page<DifferRecord> page, DifferRecord differRecord) {
		return super.findPage(page, differRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(DifferRecord differRecord) {
		super.save(differRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(DifferRecord differRecord) {
		super.delete(differRecord);
	}
	
}