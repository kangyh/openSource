/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.rpc.client;

import com.heepay.hgms.rpc.hgmsweb.module.ExecuteOrderRequest;
import com.heepay.hgms.rpc.hgmsweb.module.ExecuteOrderResult;
import com.heepay.hgms.rpc.hgmsweb.service.ExecuteOrderService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述：手动执行生成订单的接口
 * <p>
 * 创建者: guozx@9186.com
 * 创建时间: 2017-07-31 10:04:03
 * 创建描述: 当自动任务宕机和想提前执行手动生成需要执行的订单
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
@Service
public class ExecuteOrderClient extends BaseClientDistribute {

    private static final String SERVICENAME = "executeOrderServiceImpl";
    private static final String NODENAME = "hgms_server_web";
    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "hgmsClient")
    private ThriftClientProxy clientProxy;
    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }

    @Override
    public void setServiceName(){
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    public ExecuteOrderService.Client getClient(){
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new ExecuteOrderService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 手动执行订单
     * @param executeOrderRequest
     * @return
     */
    public ExecuteOrderResult doExecuteOrder(ExecuteOrderRequest executeOrderRequest) {
        ExecuteOrderResult result = new ExecuteOrderResult();
        try {
            logger.info("进行手动执行订单开始,参数为{}",executeOrderRequest.toString());
            result=  getClient().doExecuteOrder(executeOrderRequest);
            logger.info("进行手动执行订单返回消息:"+result == null ? "":result.toString());
        } catch (TException e) {
            logger.error("进行手动执行订单失败:{}",e);
            e.printStackTrace();
        }finally {
            this.close();
        }
        return result;
    }
}
