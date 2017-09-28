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
import com.heepay.manage.modules.payment.entity.Authentication;
import com.heepay.manage.modules.payment.dao.AuthenticationDao;

/**
 *
 * 描    述：鉴权信息Service
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
public class AuthenticationService extends CrudService<AuthenticationDao, Authentication> {

	public Authentication get(String id) {
		return super.get(id);
	}
	
	public List<Authentication> findList(Authentication authentication) {
		return super.findList(authentication);
	}
	
	public Page<Authentication> findPage(Page<Authentication> page, Authentication authentication) {
		return super.findPage(page, authentication);
	}
	
	@Transactional(readOnly = false)
	public void save(Authentication authentication) {
		super.save(authentication,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(Authentication authentication) {
		super.delete(authentication);
	}
	
}