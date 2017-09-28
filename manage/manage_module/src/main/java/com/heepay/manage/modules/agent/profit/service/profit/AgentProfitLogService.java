/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.profit.service.profit;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.agent.profit.entity.profit.AgentProfitLog;
import com.heepay.manage.modules.agent.profit.dao.profit.AgentProfitLogDao;

/**
 *
 * 描    述：分润申请记录Service
 *
 * 创 建 者： @author shixp
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
public class AgentProfitLogService extends CrudService<AgentProfitLogDao, AgentProfitLog> {

	public AgentProfitLog get(String id) {
		return super.get(id);
	}
	
	public List<AgentProfitLog> findList(AgentProfitLog agentProfitLog) {
		return super.findList(agentProfitLog);
	}
	
	public Page<AgentProfitLog> findPage(Page<AgentProfitLog> page, AgentProfitLog agentProfitLog) {
		return super.findPage(page, agentProfitLog);
	}

	public Page<AgentProfitLog> findVerifyPage(Page<AgentProfitLog> page, AgentProfitLog agentProfitLog) {
		agentProfitLog.setPage(page);
		page.setList(dao.findVerifyList(agentProfitLog));
		return page;
	}

	@Transactional(readOnly = false)
	public void save(AgentProfitLog agentProfitLog) {
		super.save(agentProfitLog,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentProfitLog agentProfitLog) {
		super.delete(agentProfitLog);
	}

    @Transactional(readOnly = false)
    public void changeStatus(AgentProfitLog agentProfitLog) {
        dao.changeStatus(agentProfitLog);
    }
}