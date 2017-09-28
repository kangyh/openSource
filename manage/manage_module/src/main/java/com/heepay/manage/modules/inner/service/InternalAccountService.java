/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.inner.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.inner.entity.InternalAccount;
import com.heepay.manage.modules.inner.dao.InternalAccountDao;

/**
 *
 * 描    述：内部账户Service
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
public class InternalAccountService extends CrudService<InternalAccountDao, InternalAccount> {

  @Autowired
  private InternalAccountDao internalAccountDao;
  
	public InternalAccount get(String id) {
		return super.get(id);
	}
	
	public List<InternalAccount> findList(InternalAccount internalAccount) {
		return super.findList(internalAccount);
	}
	
	public Page<InternalAccount> findPage(Page<InternalAccount> page, InternalAccount internalAccount) {
		return super.findPage(page, internalAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(InternalAccount internalAccount) {
		super.save(internalAccount,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(InternalAccount internalAccount) {
		super.delete(internalAccount);
	}
	
	public Map<String, Object> getMaxId(Map<String, Object> paramsMap){
	  return internalAccountDao.getMaxId(paramsMap);
	}
	
}