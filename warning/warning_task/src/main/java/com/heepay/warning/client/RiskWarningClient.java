package com.heepay.warning.client;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.service.MonitorService;
import com.heepay.rpc.warning.service.WarningService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-11 13:26
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Service
public class RiskWarningClient extends BaseClientDistribute {
    private static final String NODENAME = "risk_rpc";
    private static final String SERVICENAME = "MonitorServiceImpl";
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
        ;
    }

    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }


    public MonitorService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new MonitorService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 通道成功率警告
     *
     * @return
     */
    public String getChannelSuccessRateWarning() {
        try {
            return getClient().getChannelSuccessRateWarning();
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    /**
     * 无订单警告
     *
     * @return
     */
    public String getNoOrderWarning() {
        try {
            return getClient().getNoOrderWarning();
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    /**
     * 无日志警告
     *
     * @return
     */
    public String getPaymentNoLogWarning() {
        try {
            return getClient().getPaymentNoLogWarning();
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    /**
     * 订单成功率警告
     *
     * @return
     */
    public String getOrderSuccessRateWarning() {
        try {
            return getClient().getOrderSuccessRateWarning();
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
}

