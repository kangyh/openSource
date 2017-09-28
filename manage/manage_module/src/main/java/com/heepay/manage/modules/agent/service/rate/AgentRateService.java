/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.rate;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.agent.entity.rate.AgentRate;
import com.heepay.manage.modules.agent.dao.rate.AgentRateDao;

/**
 *
 * 描    述：设置代理商费率Service
 *
 * 创 建 者： @author xch
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
public class AgentRateService extends CrudService<AgentRateDao, AgentRate> {

	public AgentRate get(String id) {
		return super.get(id);
	}
	
	public List<AgentRate> findList(AgentRate agentRate) {
		return super.findList(agentRate);
	}
	
	public Page<AgentRate> findPage(Page<AgentRate> page, AgentRate agentRate) {
		return super.findPage(page, agentRate);
	}
	
	@Transactional(readOnly = false)
	public void save(AgentRate agentRate) {
		super.save(agentRate,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentRate agentRate) {
		super.delete(agentRate);
	}
	
}