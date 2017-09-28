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
import com.heepay.manage.modules.risk.entity.BossRule;
import com.heepay.manage.modules.risk.dao.BossRuleDao;
import com.heepay.manage.modules.risk.dao.RiskOrderDao;

/**
 *
 * 描    述：报表规则配置Service
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
public class BossRuleService extends CrudService<BossRuleDao, BossRule> {
	
	@Autowired
	private BossRuleDao bossRuleDao;
	
	public BossRule get(String id) {
		return super.get(id);
	}
	
	public List<BossRule> findList(BossRule bossRule) {
		return super.findList(bossRule);
	}
	
	public Page<BossRule> findPage(Page<BossRule> page, BossRule bossRule) {
		return super.findPage(page, bossRule);
	}
	
	public void save(BossRule bossRule) {
		super.save(bossRule,false);
	}
	
	public void delete(BossRule bossRule) {
		super.delete(bossRule);
	}
	
	public BossRule selectByStatus(String status){
		return bossRuleDao.selectByStatus(status);
	}
	
}