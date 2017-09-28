/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.rpc;

import com.heepay.agent.rpc.thrift.AgentUserObject;
import com.heepay.agent.rpc.thrift.AgentUserThrift;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;

/**
 * 代理商用户Client
 *
 * @author ZhangQiang
 * @version 2017/2/3 18:13
 */
public class AgentUserClient extends BaseClient {

    private static final Logger logger = LogManager.getLogger();

    private static final String SERVICENAME = "agentUserThriftImpl";
    private static final String NODENAME = "agent_server";

    //private ThriftClientProxy thriftClientProxy;


    private AgentUserThrift.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new AgentUserThrift.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }



    public int insert(AgentUserObject agentUserObject) throws TException {
        try {
            return getClient().insert(agentUserObject);
        } finally {
            this.close();
        }
    }


}
