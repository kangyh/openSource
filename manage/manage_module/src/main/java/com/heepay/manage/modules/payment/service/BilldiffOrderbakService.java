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
import com.heepay.manage.modules.payment.entity.BilldiffOrderbak;
import com.heepay.manage.modules.payment.dao.BilldiffOrderbakDao;

/**
 *
 * 描    述：单据备份Service
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
public class BilldiffOrderbakService extends CrudService<BilldiffOrderbakDao, BilldiffOrderbak> {

	public BilldiffOrderbak get(String id) {
		return super.get(id);
	}
	
	public List<BilldiffOrderbak> findList(BilldiffOrderbak billdiffOrderbak) {
		return super.findList(billdiffOrderbak);
	}
	
	public Page<BilldiffOrderbak> findPage(Page<BilldiffOrderbak> page, BilldiffOrderbak billdiffOrderbak) {
		return super.findPage(page, billdiffOrderbak);
	}
	
	@Transactional(readOnly = false)
	public void save(BilldiffOrderbak billdiffOrderbak) {
		super.save(billdiffOrderbak,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(BilldiffOrderbak billdiffOrderbak) {
		super.delete(billdiffOrderbak);
	}
	
}