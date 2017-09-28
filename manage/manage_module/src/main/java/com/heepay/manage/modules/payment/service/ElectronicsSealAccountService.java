/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.ElectronicsSealAccountDao;
import com.heepay.manage.modules.payment.entity.ElectronicsSealAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：签宝账户管理Service
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
public class ElectronicsSealAccountService extends CrudService<ElectronicsSealAccountDao, ElectronicsSealAccount> {

	public ElectronicsSealAccount get(String id) {
		return super.get(id);
	}
	
	public List<ElectronicsSealAccount> findList(ElectronicsSealAccount electronicsSealAccount) {
		return super.findList(electronicsSealAccount);
	}
	
	public Page<ElectronicsSealAccount> findPage(Page<ElectronicsSealAccount> page, ElectronicsSealAccount electronicsSealAccount) {
		return super.findPage(page, electronicsSealAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(ElectronicsSealAccount electronicsSealAccount) {
		super.save(electronicsSealAccount,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ElectronicsSealAccount electronicsSealAccount) {
		super.delete(electronicsSealAccount);
	}
	
}