/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.agentinfochange;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.agent.entity.agentinfochange.AgentInfoChange;
import com.heepay.manage.modules.agent.dao.agentinfochange.AgentInfoChangeDao;

/**
 *
 * 描    述：代理商信息变更表Service
 *
 * 创 建 者： @author TangWei
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
public class AgentInfoChangeService extends CrudService<AgentInfoChangeDao, AgentInfoChange> {

	public AgentInfoChange get(String id) {
		return super.get(id);
	}
	
	public List<AgentInfoChange> findList(AgentInfoChange agentInfoChange) {
		return super.findList(agentInfoChange);
	}
	
	public Page<AgentInfoChange> findPage(Page<AgentInfoChange> page, AgentInfoChange agentInfoChange) {
		return super.findPage(page, agentInfoChange);
	}

	public Page<AgentInfoChange> findCheckPage(Page<AgentInfoChange> page, AgentInfoChange agentInfoChange) {
		agentInfoChange.setPage(page);
		page.setList(dao.findCheckList(agentInfoChange));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(AgentInfoChange agentInfoChange) {
		super.save(agentInfoChange,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentInfoChange agentInfoChange) {
		super.delete(agentInfoChange);
	}
	
}