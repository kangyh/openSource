/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.service.rate;

import com.heepay.manage.modules.agent.dao.rate.AgentRateDao;
import com.heepay.manage.modules.agent.entity.rate.AgentRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class AgentRateOtherService {
    private static final Logger log = LogManager.getLogger();
    private final AgentRateDao agentRateDao;

    @Autowired
    public AgentRateOtherService(AgentRateDao agentRateDao) {
        this.agentRateDao = agentRateDao;
    }

    public AgentRate getAgentRate(AgentRate agentRate) {
        return agentRateDao.getAgentRate(agentRate);
    }

}