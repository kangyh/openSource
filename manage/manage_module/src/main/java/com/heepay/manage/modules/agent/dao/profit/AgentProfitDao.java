/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.dao.profit;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.agent.entity.profit.AgentProfit;

import java.util.List;

/**
 *
 * 描    述：代理商分润管理DAO接口
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
@MyBatisDao
public interface AgentProfitDao extends CrudDao<AgentProfit> {
    public List<AgentProfit> findListTotal(AgentProfit agentProfit);
    public void updatePayStatus(AgentProfit agentProfit);
}