/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.rpc.client;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.EnterprisesCertificationModel;
import com.heepay.rpc.payment.service.EnterprisesCertificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * 描述：商户认证client
 *
 * 创建者  B.HJ
 * 创建时间 2017-02-07-18:06
 * 创建描述：商户认证client
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Service
public class EnterprisesCertificationClient extends BaseClientDistribute {

    private static final String SERVICENAME = "EnterprisesCertificationServiceImpl";
    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "paymentClient")
    private ThriftClientProxy clientProxy;

    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }

    private EnterprisesCertificationService.Client  getClient(){
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new EnterprisesCertificationService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 企业实名认证
     * @param enterprisesCertificationModel
     * @return
     */
    public String corporateIndentities(EnterprisesCertificationModel enterprisesCertificationModel){
        try {
            return getClient().certification(enterprisesCertificationModel);
        } catch (Exception e) {
            logger.error("调用企业认证时出错",e);
            return "{\"errorCode\":\"-1\"}";
        } finally {
            this.close();
        }
    }


    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
    }


}
