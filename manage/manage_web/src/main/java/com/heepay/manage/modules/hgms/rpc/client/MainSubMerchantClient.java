/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.rpc.client;

import com.heepay.common.util.Constants;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.payment.model.MainSubMerchantModel;
import com.heepay.rpc.payment.service.MainSubMerchantService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-04-21-16:14
 * 创建描述: 虚拟账户互转绑定上下级关系
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
public class MainSubMerchantClient extends BaseClientDistribute {

    private static final String SERVICENAME = "MainSubMerchantServiceImpl";
    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "paymentClient")
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
        ClientThreadLocal.getInstance().setNodename(Constants.RPCNodeName.PAYMENTRPC);
    }

    public MainSubMerchantService.Client getClient(){
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new MainSubMerchantService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 保存资金归集账户上下关系, 用于虚拟账户互转
     * @param mainMerchantId
     * @param subMerchantId
     */
    public void saveMainSubMerchant(String mainMerchantId,String subMerchantId){
        MainSubMerchantModel model = new MainSubMerchantModel();
        model.setMainMerchantId(Integer.valueOf(mainMerchantId));
        model.setSubMerchantId(Integer.valueOf(subMerchantId));
        model.setEnable(1);
        try {
            logger.info("绑定上下级关系用于汇付宝虚拟账户转账开始参数为mainMerchantId:{},subMerchantId:{}",mainMerchantId,subMerchantId);
            getClient().saveMainSubMerchant(model);
        } catch (TException e) {
            logger.error("绑定上下级关系用于汇付宝虚拟账户转账失败:{}",e);
            e.printStackTrace();
        }finally {
            this.close();
        }
    }




}
