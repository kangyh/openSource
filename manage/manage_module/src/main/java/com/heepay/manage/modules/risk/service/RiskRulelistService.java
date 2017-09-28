/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.entity.RiskRulelist;
import com.heepay.manage.modules.risk.dao.BossRuleDao;
import com.heepay.manage.modules.risk.dao.RiskRulelistDao;

/**
 *
 * 描    述：风控规则列表Service
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
public class RiskRulelistService extends CrudService<RiskRulelistDao, RiskRulelist> {
	
	@Autowired
	private RiskRulelistDao riskRulelistDao;
	
	public RiskRulelist get(String id) {
		return super.get(id);
	}
	
	public List<RiskRulelist> findList(RiskRulelist riskRulelist) {
		return super.findList(riskRulelist);
	}
	
	public Page<RiskRulelist> findPage(Page<RiskRulelist> page, RiskRulelist riskRulelist) {
		return super.findPage(page, riskRulelist);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskRulelist riskRulelist) {
		super.save(riskRulelist,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskRulelist riskRulelist) {
		super.delete(riskRulelist);
	}
	
	public RiskRulelist getByRuleId(String ruleId){
		return riskRulelistDao.getByRuleId(ruleId);
	}
}