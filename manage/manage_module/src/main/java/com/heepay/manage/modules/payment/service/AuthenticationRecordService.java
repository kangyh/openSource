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
import com.heepay.manage.modules.payment.entity.AuthenticationRecord;
import com.heepay.manage.modules.payment.dao.AuthenticationRecordDao;

/**
 *
 * 描    述：鉴权记录Service
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
public class AuthenticationRecordService extends CrudService<AuthenticationRecordDao, AuthenticationRecord> {

	public AuthenticationRecord get(String id) {
		return super.get(id);
	}
	
	public List<AuthenticationRecord> findList(AuthenticationRecord authenticationRecord) {
		return super.findList(authenticationRecord);
	}
	
	public Page<AuthenticationRecord> findPage(Page<AuthenticationRecord> page, AuthenticationRecord authenticationRecord) {
		return super.findPage(page, authenticationRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(AuthenticationRecord authenticationRecord) {
		super.save(authenticationRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AuthenticationRecord authenticationRecord) {
		super.delete(authenticationRecord);
	}
	
}