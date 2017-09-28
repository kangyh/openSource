/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.rpc;

import com.heepay.agent.common.constants.RpcConstants;
import com.heepay.agent.rpc.thrift.AgentFundObject;
import com.heepay.agent.rpc.thrift.AgentFundQueryResult;
import com.heepay.agent.rpc.thrift.AgentFundThrift;
import com.heepay.agent.rpc.thrift.PageObject;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 代理商审核Client
 *
 * @author ZhangQiang
 * @version 2017/2/3 18:13
 */
@Component
public class AgentFundThriftClient extends BaseClientDistribute {

    private static final Logger logger = LogManager.getLogger();

    private static final String SERVICENAME = "AgentFundThriftImpl";

    @Resource(name = "agentClient")
    private ThriftClientProxy thriftClientProxy;

    public ThriftClientProxy getThriftClientProxy() {
        return thriftClientProxy;
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(RpcConstants.RPC_NODENAME_AGENT);
    }

    @Override
    public ThriftClientProxy getClientProxy() {
        return thriftClientProxy;
    }

    private AgentFundThrift.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new AgentFundThrift.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    //需要主动关闭连接，1是释放资源，2是可能引起服务端“java.io.IOException: 远程主机强迫关闭了一个现有的连接”异常
    public AgentFundObject selectById(String id) throws TException {
        try {
            return getClient().selectById(id);
        } finally {
            this.close();
        }
    }

    public AgentFundObject select(AgentFundObject agentFundObject) throws TException {
        try {
            return getClient().select(agentFundObject);
        } finally {
            this.close();
        }
    }

    public void delete(AgentFundObject agentFundObject) throws TException {
        try {
            getClient().remove(agentFundObject);
        } finally {
            this.close();
        }
    }

    public int insert(AgentFundObject agentFundObject) throws TException {
        try {
            return getClient().insert(agentFundObject);
        } finally {
            this.close();
        }
    }

    public void update(AgentFundObject agentFundObject) throws TException {
        try {
            getClient().update(agentFundObject);
        } finally {
            this.close();
        }
    }

    public void updateAccountBalance(Map map) throws TException {
        try {
            getClient().updateAccountBalance(map);
        } finally {
            this.close();
        }
    }

    public AgentFundQueryResult selectList(AgentFundObject agentFundObject, PageObject pageObject) throws TException {
        try {
            return getClient().selectList(agentFundObject, pageObject);
        } finally {
            this.close();
        }
    }

    public void profitFund(String agentProfitId) throws TException {
        try {
            getClient().profitFund(agentProfitId);
        } finally {
            this.close();
        }
    }
}
