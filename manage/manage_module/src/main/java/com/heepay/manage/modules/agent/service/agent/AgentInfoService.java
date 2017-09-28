/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.agent;

import java.util.List;

import com.heepay.manage.modules.agent.entity.agentinfochange.AgentInfoChange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import com.heepay.manage.modules.agent.dao.agent.AgentInfoDao;

/**
 *
 * 描    述：代理商信息维护Service
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
public class AgentInfoService extends CrudService<AgentInfoDao, AgentInfo> {

	public AgentInfo get(String id) {
		return super.get(id);
	}
	
	public List<AgentInfo> findList(AgentInfo agentInfo) {
		return super.findList(agentInfo);
	}
	
	public Page<AgentInfo> findPage(Page<AgentInfo> page, AgentInfo agentInfo) {
		return super.findPage(page, agentInfo);
	}

	public Page<AgentInfo> findCheckPage(Page<AgentInfo> page, AgentInfo agentInfo) {
		agentInfo.setPage(page);
		page.setList(dao.findCheckList(agentInfo));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(AgentInfo agentInfo) {
		super.save(agentInfo,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentInfo agentInfo) {
		super.delete(agentInfo);
	}

	@Transactional(readOnly = false)
	public int updateInfoFromChange(AgentInfoChange agentInfoChange){
		return dao.updateInfoFromChange(agentInfoChange);
	}

}