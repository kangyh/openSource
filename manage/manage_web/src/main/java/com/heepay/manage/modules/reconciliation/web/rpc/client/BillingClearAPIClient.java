package com.heepay.manage.modules.reconciliation.web.rpc.client;


import com.heepay.enums.OperatorType;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import com.heepay.rpc.payment.model.ClearChannelQueryModel;
import com.heepay.rpc.payment.model.ClearChannelQueryRecordModel;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;
import com.heepay.rpc.payment.service.BillingClearAPIService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BillingClearAPIClient extends BaseClientDistribute {

    private static final String SERVICENAME = "BillingClearAPIServiceImpl";

    private static final String NODENAME = "payment_rpc";

    private static final Logger log = LogManager.getLogger();
    @Resource(name = "paymentbillapiClient")
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


    public BillingClearAPIService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new BillingClearAPIService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 撤单
     *
     * @param
     * @return
     */
    public AsyncMsgModel cancelMerchantTrans(String paymentId, String payAmount, String feeAmount) {
        try {
            return getClient().cancelTrans(paymentId, payAmount, feeAmount, OperatorType.BILLIN.getValue());
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }


    /**
     * 补单
     *
     * @param
     * @return
     */
    public AsyncMsgModel suppleMerchantTrans(String paymentId, String payAmount, String feeAmount) {
        try {
            return getClient().suppleTrans(paymentId, payAmount, feeAmount, OperatorType.BILLIN.getValue());
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * 查询用户测交易信息
     *
     * @param chargeId
     * @return
     */
    public ClearMerchantQueryRecordModel merchantQueryTransByPaymentId(String chargeId) {
        try {
            return getClient().merchantQueryTransByPaymentId(chargeId);
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * 查询通道交易信息
     *
     * @param chargeId
     * @return
     */
    public ClearChannelQueryRecordModel channelQueryTransByPaymentId(String chargeId) {
        try {
            return getClient().channelQueryTransByPaymentId(chargeId);
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }


    /**
     * 根据transNo查出通道侧数据
     */
    public ClearChannelQueryModel channelQueryTransByTransNo(String transNo) {
        try {
            return getClient().channelQueryTransByTransNo(transNo);
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * 根据上游流水号交易数据
     */
    public ClearMerchantQueryRecordModel queryTransByUnionpaySerialNo(String bankNo) {
        try {
            return getClient().queryTransByUnionpaySerialNo(bankNo);
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }


}
