/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.account.service;

import java.util.List;

import com.heepay.manage.modules.account.entity.InternalAccountMpr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.account.dao.InternalAccountMprDao;

/**
 *
 * 描    述：内部账户映射Service
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
public class InternalAccountMprService extends CrudService<InternalAccountMprDao, InternalAccountMpr> {

	public InternalAccountMpr get(String id) {
		return super.get(id);
	}
	
	public List<InternalAccountMpr> findList(InternalAccountMpr internalAccountMpr) {
		return super.findList(internalAccountMpr);
	}
	
	public Page<InternalAccountMpr> findPage(Page<InternalAccountMpr> page, InternalAccountMpr internalAccountMpr) {
		return super.findPage(page, internalAccountMpr);
	}
	
	@Transactional(readOnly = false)
	public void save(InternalAccountMpr internalAccountMpr) {
		super.save(internalAccountMpr,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(InternalAccountMpr internalAccountMpr) {
		super.delete(internalAccountMpr);
	}
	
}