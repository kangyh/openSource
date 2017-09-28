package com.heepay.manage.modules.reconciliation.web.rpc.client;

import com.heepay.rpc.billing.service.AdjustMoneyService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdjustMoneyClient extends BaseClientDistribute {

    private static final String SERVICENAME = "AdjustMoneyServiceImpl";

    private static final String NODENAME = "billing_rpc";

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "billingClient")
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


    public AdjustMoneyService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new AdjustMoneyService.Client(ClientThreadLocal.getInstance().getProtocol());
    }


    
/**
     * @方法说明：调账管理
     * @时间： 2017-05-11 05:12 PM
     * @创建人：wangl
     */

    public String adjustMoney(String msg) {
        AdjustMoneyService.Client client = this.getClient();
        try {
            String result = client.updateAccounting(msg);
            logger.info("调账管理--->{调取清结算接口}--->{返回结果}"+result);
            return result;
        } catch (Exception e) {
            logger.error("调账管理--->{异常}{}",e);
            return "";
        } finally {
            this.close();
        }
    }
}


