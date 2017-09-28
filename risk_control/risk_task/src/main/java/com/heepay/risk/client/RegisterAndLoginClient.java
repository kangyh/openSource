package com.heepay.risk.client;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.service.RegisterAndLoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegisterAndLoginClient extends BaseClientDistribute {
    private static final String SERVICENAME = "RegisterAndLoginServiceImpl";

    private static final String NODENAME = "risk_rpc";

    private static final Logger log = LogManager.getLogger();
    @Resource(name = "riskClient")
    private ThriftClientProxy clientProxy;

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }


    public RegisterAndLoginService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new RegisterAndLoginService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 风控商户登录/注册规则
     *
     * @param paraJson
     * @return
     */
    public String ExecuteRegisterAndLoginRule(String paraJson) {
        try {
            return getClient().ExecuteRegisterAndLoginRule(paraJson);
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
}
