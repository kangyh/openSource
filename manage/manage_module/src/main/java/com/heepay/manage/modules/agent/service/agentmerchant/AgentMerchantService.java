/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.agentmerchant;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.agent.entity.agentmerchant.AgentMerchant;
import com.heepay.manage.modules.agent.dao.agentmerchant.AgentMerchantDao;

/**
 *
 * 描    述：代理商商户功能Service
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
public class AgentMerchantService extends CrudService<AgentMerchantDao, AgentMerchant> {

	public AgentMerchant get(String id) {
		return super.get(id);
	}
	
	public List<AgentMerchant> findList(AgentMerchant agentMerchant) {
		return super.findList(agentMerchant);
	}
	
	public Page<AgentMerchant> findPage(Page<AgentMerchant> page, AgentMerchant agentMerchant) {
		return super.findPage(page, agentMerchant);
	}
	
	@Transactional(readOnly = false)
	public void save(AgentMerchant agentMerchant) {
		super.save(agentMerchant,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AgentMerchant agentMerchant) {
		super.delete(agentMerchant);
	}
	
}