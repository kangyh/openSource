/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.SubscribeCollectionRecordDetailDao;
import com.heepay.manage.modules.payment.entity.SubscribeCollectionRecordDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：订阅支付明细Service
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
public class SubscribeCollectionRecordDetailService extends CrudService<SubscribeCollectionRecordDetailDao, SubscribeCollectionRecordDetail> {

	public SubscribeCollectionRecordDetail get(String id) {
		return super.get(id);
	}
	
	public List<SubscribeCollectionRecordDetail> findList(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail) {
		return super.findList(subscribeCollectionRecordDetail);
	}
	
	public Page<SubscribeCollectionRecordDetail> findPage(Page<SubscribeCollectionRecordDetail> page, SubscribeCollectionRecordDetail subscribeCollectionRecordDetail) {
		return super.findPage(page, subscribeCollectionRecordDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail) {
		super.save(subscribeCollectionRecordDetail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(SubscribeCollectionRecordDetail subscribeCollectionRecordDetail) {
		super.delete(subscribeCollectionRecordDetail);
	}
	
}