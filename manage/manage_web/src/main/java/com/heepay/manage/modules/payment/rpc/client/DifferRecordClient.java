package com.heepay.manage.modules.payment.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.service.DifferRecordService;

@Service
public class DifferRecordClient extends BaseClientDistribute {

    @Resource(name = "paymentClient")
    private ThriftClientProxy proxy;

    private static final String SERVICE_NAME = "DifferRecordService";

    private static Logger log = LogManager.getLogger();

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICE_NAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
    }

    @Override
    public ThriftClientProxy getClientProxy() {
        return proxy;
    }

    private DifferRecordService.Client getClient() {
        this.setNodename();
        this.setServiceName();
        this.setTMultiplexedProtocol();
        return new DifferRecordService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    public AsyncMsgModel suppleTransForDiffer(String paymentId, String operator) {
        try {
            return this.getClient().suppleTransForDiffer(paymentId, operator);
        } catch (TException e) {
            log.error(e);
            log.error("差异处理平台suppleTransForDiffer，调用payment_server异常，paymentId={}", paymentId);
            return new AsyncMsgModel();
        } finally {
            this.close();
        }
    }

    public AsyncMsgModel suppleTransForComplaint(String paymentId, String operator, String processDesc) {
        try {
            return this.getClient().suppleTransForComplaint(paymentId, operator, processDesc);
        } catch (TException e) {
            log.error(e);
            log.error("投诉处理平台suppleTransForComplaint，调用payment_server异常，paymentId={}", paymentId);
            return new AsyncMsgModel();
        } finally {
            this.close();
        }
    }

    public AsyncMsgModel cancelTrans(String paymentId, String operator, String operateSource, String processDesc) {
        try {
            return this.getClient().cancelTrans(paymentId, operator, operateSource, processDesc);
        } catch (TException e) {
            log.error(e);
            log.error("投诉处理平台cancelTrans，调用payment_server异常，paymentId={}", paymentId);
            return new AsyncMsgModel();
        } finally {
            this.close();
        }
    }

}
