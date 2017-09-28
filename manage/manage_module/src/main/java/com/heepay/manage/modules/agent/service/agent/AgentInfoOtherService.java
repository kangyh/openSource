/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.agent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.agent.dao.agent.AgentInfoDao;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;

/**
 *
 * 描    述：代理商信息维护Service
 *
 * 创 建 者： @author xch
 * 创建时间：
 * 创建描述：
 *.
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
public class AgentInfoOtherService{
	private static final Logger log = LogManager.getLogger();
	private final AgentInfoDao agentInfoDao;

	@Autowired
	public AgentInfoOtherService(AgentInfoDao agentInfoDao) {
		this.agentInfoDao = agentInfoDao;
	}
	public AgentInfo getNameToAgent(AgentInfo info){
		return agentInfoDao.getNameToAgent(info);
	}
	public int updateStatus(AgentInfo info){
		return agentInfoDao.updateStatus(info);
	}

	public void saveCondition(AgentInfo info){
		if (info.getIsNewRecord()){
			info.setUserOrDate();
			if(StringUtils.isBlank(info.getId())){
				info.setId(null);
			}
			agentInfoDao.insert(info);
		}else{
			info.preUpdate();
			agentInfoDao.updateCondition(info);
		}
	}
}