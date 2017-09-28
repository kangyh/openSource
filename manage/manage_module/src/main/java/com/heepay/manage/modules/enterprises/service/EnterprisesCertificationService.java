/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.enterprises.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.enterprises.entity.EnterprisesCertification;
import com.heepay.manage.modules.enterprises.dao.EnterprisesCertificationDao;

/**
 *
 * 描    述：企业认证对外服务Service
 *
 * 创 建 者： @author yangcl
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
public class EnterprisesCertificationService extends CrudService<EnterprisesCertificationDao, EnterprisesCertification> {

	public EnterprisesCertification get(String id) {
		return super.get(id);
	}
	
	public List<EnterprisesCertification> findList(EnterprisesCertification enterprisesCertification) {
		return super.findList(enterprisesCertification);
	}
	
	public Page<EnterprisesCertification> findPage(Page<EnterprisesCertification> page, EnterprisesCertification enterprisesCertification) {
		return super.findPage(page, enterprisesCertification);
	}
	
	@Transactional(readOnly = false)
	public void save(EnterprisesCertification enterprisesCertification) {
		super.save(enterprisesCertification,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(EnterprisesCertification enterprisesCertification) {
		super.delete(enterprisesCertification);
	}
	
}