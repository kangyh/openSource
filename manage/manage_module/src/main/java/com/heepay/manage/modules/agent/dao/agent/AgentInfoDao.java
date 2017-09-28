/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.dao.agent;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import com.heepay.manage.modules.agent.entity.agentinfochange.AgentInfoChange;

import java.util.List;

/**
 *
 * 描    述：代理商信息维护DAO接口
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
@MyBatisDao
public interface AgentInfoDao extends CrudDao<AgentInfo> {
    public int updateStatus(AgentInfo info);
    public int updateCondition(AgentInfo info);
    //根据实体信息获取对象
    public AgentInfo  getNameToAgent(AgentInfo info);
    //获取代理商审核列表
    public List<AgentInfo> findCheckList(AgentInfo agentInfo);

    //从change更新到info中；
    public int updateInfoFromChange(AgentInfoChange agentInfoChange);
}