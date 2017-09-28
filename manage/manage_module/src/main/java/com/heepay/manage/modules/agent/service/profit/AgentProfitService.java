/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.profit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.agent.entity.profit.AgentProfit;
import com.heepay.manage.modules.agent.dao.profit.AgentProfitDao;

/**
 *
 * 描    述：代理商分润管理Service
 *
 * 创 建 者： @author yangliang
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
public class AgentProfitService extends CrudService<AgentProfitDao, AgentProfit> {

	
	public AgentProfit get(String id) {
		AgentProfit agentProfit = super.get(id);
		return agentProfit;
	}
	
	public List<AgentProfit> findList(AgentProfit agentProfit) {
		return super.findList(agentProfit);
	}
	
	public Page<AgentProfit> findPage(Page<AgentProfit> page, AgentProfit agentProfit) {
		return super.findPage(page, agentProfit);
	}

	public Page<AgentProfit> findPageTotal(Page<AgentProfit> page, AgentProfit agentProfit) {
		agentProfit.setPage(page);
		page.setList(dao.findListTotal(agentProfit));
		return page;
	}
	@Transactional(readOnly = false)
	public void updatePayStatus(AgentProfit agentProfit){
		dao.updatePayStatus(agentProfit);
	}

	@Transactional(readOnly = false)
	public void save(AgentProfit agentProfit) {
		super.save(agentProfit);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentProfit agentProfit) {
		super.delete(agentProfit);
	}
	
}