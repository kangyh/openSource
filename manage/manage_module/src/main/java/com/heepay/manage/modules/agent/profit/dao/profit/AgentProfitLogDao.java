/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.profit.dao.profit;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.agent.profit.entity.profit.AgentProfitLog;
import java.util.List;

/**
 *
 * 描    述：分润申请记录DAO接口
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
@MyBatisDao
public interface AgentProfitLogDao extends CrudDao<AgentProfitLog> {
	public List<AgentProfitLog> findVerifyList(AgentProfitLog agentProfitLog);
	public void changeStatus(AgentProfitLog agentProfitLog);
}