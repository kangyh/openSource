/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.entity.RiskInternalMonitorMerchant;
import com.heepay.manage.modules.risk.dao.RiskInternalMonitorMerchantDao;

/**
 *
 * 描    述：内部监控商户配制表Service
 *
 * 创 建 者： @author wj
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
public class RiskInternalMonitorMerchantService extends CrudService<RiskInternalMonitorMerchantDao, RiskInternalMonitorMerchant> {

	public RiskInternalMonitorMerchant get(String id) {
		return super.get(id);
	}
	
	public List<RiskInternalMonitorMerchant> findList(RiskInternalMonitorMerchant riskInternalMonitorMerchant) {
		return super.findList(riskInternalMonitorMerchant);
	}
	
	public Page<RiskInternalMonitorMerchant> findPage(Page<RiskInternalMonitorMerchant> page, RiskInternalMonitorMerchant riskInternalMonitorMerchant) {
		return super.findPage(page, riskInternalMonitorMerchant);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskInternalMonitorMerchant riskInternalMonitorMerchant) {
		super.save(riskInternalMonitorMerchant,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskInternalMonitorMerchant riskInternalMonitorMerchant) {
		super.delete(riskInternalMonitorMerchant);
	}
	
}