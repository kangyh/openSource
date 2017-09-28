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
import com.heepay.manage.modules.payment.dao.BatchCollectionRecordDetailDao;
import com.heepay.manage.modules.payment.entity.BatchCollectionRecordDetail;

/**
 *
 * 描    述：批量代收Service
 *
 * 创 建 者： @author 杨春龙
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
public class BatchCollectionRecordDetailService extends CrudService<BatchCollectionRecordDetailDao, BatchCollectionRecordDetail> {

	public BatchCollectionRecordDetail get(String id) {
		return super.get(id);
	}
	
	public List<BatchCollectionRecordDetail> findList(BatchCollectionRecordDetail batchCollectionRecordDetail) {
		return super.findList(batchCollectionRecordDetail);
	}
	
	public Page<BatchCollectionRecordDetail> findPage(Page<BatchCollectionRecordDetail> page, BatchCollectionRecordDetail batchCollectionRecordDetail) {
		return super.findPage(page, batchCollectionRecordDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(BatchCollectionRecordDetail batchCollectionRecordDetail) {
		super.save(batchCollectionRecordDetail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(BatchCollectionRecordDetail batchCollectionRecordDetail) {
		super.delete(batchCollectionRecordDetail);
	}
	
}