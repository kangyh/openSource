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
import com.heepay.manage.modules.risk.dao.RiskLoginBlacklistDao;
import com.heepay.manage.modules.risk.entity.RiskLoginBlacklist;

/**
 *
 * 描    述：risk_login_blacklistService
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
public class RiskLoginBlacklistService extends CrudService<RiskLoginBlacklistDao, RiskLoginBlacklist> {

	public RiskLoginBlacklist get(String id) {
		return super.get(id);
	}
	
	public List<RiskLoginBlacklist> findList(RiskLoginBlacklist riskLoginBlacklist) {
		return super.findList(riskLoginBlacklist);
	}
	
	public Page<RiskLoginBlacklist> findPage(Page<RiskLoginBlacklist> page, RiskLoginBlacklist riskLoginBlacklist) {
		return super.findPage(page, riskLoginBlacklist);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskLoginBlacklist riskLoginBlacklist) {
		super.save(riskLoginBlacklist,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskLoginBlacklist riskLoginBlacklist) {
		super.delete(riskLoginBlacklist);
	}
	
}